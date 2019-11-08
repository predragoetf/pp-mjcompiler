package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {

	private int varCount;

	private int paramCnt;

	private int mainPc;

	int globalVarsCnt = 0;

	public int getMainPc() {
		return mainPc;
	}

	Scope currentScope = Tab.currentScope;

	public static HashMap<CodeGenJmpFixup, ArrayList<Integer>> hashMadjija = new HashMap<>();
	public static ArrayList<Integer> fixmeDearOR = new ArrayList<>();
	public static ArrayList<Integer> fixmeDearIF = new ArrayList<>();
	public static HashMap<CodeGenFixupStatementIfMatched, Integer> fixmeDearEndOfELSE = new HashMap<>();
	public static HashMap<StatementDoWhile, ArrayList<Integer>> doWhileBreakFixupLookup = new HashMap<>();
	public static Stack<Integer> doAdrStack = new Stack<>();
	static {
		doAdrStack.push(0);
		
	}
	
	/**
	 * U hes tabeli prosledjenog opsega trazi Obj cvor sa imenom name, pocevsi od
	 * prosledjenog opsega, pa redom kroz opsege na nizim nivoima. Povratna
	 * vrednost: - pronadjeni Obj cvor, ako je pretrazivanje bilo uspesno. -
	 * Tab.noObj objekat, ako je pretrazivanje bilo neuspesno.
	 */
	public static Obj find(String name, Scope scope) {
		Obj resultObj = null;
		for (Scope s = scope; s != null; s = s.getOuter()) {
			if (s.getLocals() != null) {
				resultObj = s.getLocals().searchKey(name);
				if (resultObj != null)
					break;
			}
		}
		return (resultObj != null) ? resultObj : Tab.noObj;
	}
	
	@Override
	public void visit(CodeGenGetPcEmpty codeGenGetPcEmpty) {
		String methodName = ((MethodDecl) codeGenGetPcEmpty.getParent()).getMName();
		if ("main".equalsIgnoreCase(methodName)) {
			mainPc = Code.pc;
		}
		Obj methodObjNode = find(methodName, SemanticAnalyzer.ownerScopeLookup.get(codeGenGetPcEmpty).getOuter());
		methodObjNode.setAdr(Code.pc);

		/*
		 * // Collect arguments and local variables. SyntaxNode methodNode =
		 * codeGenGetPcEmpty.getParent(); VarCounter varCnt = new VarCounter();
		 * methodNode.traverseTopDown(varCnt); FormParamCounter fpCnt = new
		 * FormParamCounter(); methodNode.traverseTopDown(fpCnt);
		 * 
		 * // Generate the entry. Code.put(Code.enter); Code.put(fpCnt.getCount());
		 * Code.put(varCnt.getCount() + fpCnt.getCount());
		 */

		Code.put(Code.enter);
		Code.put(methodObjNode.getLevel());
		Code.put(methodObjNode.getLocalSymbols().size());

	}

	/*
	 * @Override public void visit(CodeGenInsertFunction codeGenInsertFunction) {
	 * //ubacivanje len int lenPC = Code.pc; Tab.lenObj.setAdr( lenPC);
	 * Code.put(Code.enter); Code.put(1); Code.put(1);
	 * 
	 * Code.put(Code.arraylength);
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_);
	 * 
	 * //ubacivanje char int charPC = Code.pc; Tab.chrObj.setAdr( charPC);
	 * Code.put(Code.enter); Code.put(1); Code.put(1);
	 * 
	 * //obrada za chr->nista!
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_);
	 * 
	 * //ubacivanje char int ordPC = Code.pc; Tab.ordObj.setAdr( ordPC);
	 * Code.put(Code.enter); Code.put(1); Code.put(1);
	 * 
	 * //obrada za ord->nista!
	 * 
	 * Code.put(Code.exit); Code.put(Code.return_);
	 * 
	 * }
	 */

	@Override
	public void visit(VarDeclOk VarDeclOk) {
		varCount++;
	}

	@Override
	public void visit(FormParsList FormPars) {
		paramCnt++;
	}

	@Override
	public void visit(FormParsSingle FormPars) {
		paramCnt++;
	}

	@Override
	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(StatementReturn statementReturn) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(DesignatorStatementAssignment designatorStatementAssignment) {

		Obj desObj = designatorStatementAssignment.getDesignator().obj;

		// slucajevi leve strane

		// a koje je int stanje steka: vrednost a

		// a koje je niz intova stanje steka: vrednost a (adresa niza), Expr ->

		// a[3] koje je int stanje steka: vrednost a (adresa niza), index (3), Expr ->
		// treba da se uradi astore/bastore

		if (desObj.getType().getKind() == Struct.Array) {
			Struct elemType = SemanticAnalyzer.designatorTypeLookup.get(designatorStatementAssignment.getDesignator());
			if (elemType.getKind() == Struct.Char) {
				Code.put(Code.bastore);
			} else if (elemType.getKind() == Struct.Int) {
				Code.put(Code.astore);
			} else if (elemType.getKind() == SemanticAnalyzer.boolStructNode.getKind()) {
				Code.put(Code.astore);
			} else if (elemType.getKind() == Struct.Class) {
				// TODO neke reference na objekte klase
			}
			// TODO razmisliti sta se desi ako dodeljujemo niz nizu
			else if (elemType.getKind() == Struct.Array) {
				Code.store(desObj);
				Code.put(Code.pop);
			}
		} else {
			Code.store(desObj);
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
		Designator des = designatorStatementIncrement.getDesignator();
		Struct tip = SemanticAnalyzer.designatorTypeLookup.get(des);
		// ako je inkrementiranje promenljive, onda mozemo da uradimo const_1, add,
		// store
		if (des.obj.getType().getKind() == Struct.Int) {
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(des.obj);
		}
		// ako je inkrementiranje niza, to je djubre koje se ne desava (nije tip int)

		// ako je inkrementiranje elementa niza, onda na steku vec imamo adr i index, pa
		// uradimo dup2, aload, const_1, add, astore
		if (des.obj.getType().getKind() == Struct.Array) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}

	@Override
	public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
		Designator des = designatorStatementDecrement.getDesignator();
		Struct tip = SemanticAnalyzer.designatorTypeLookup.get(des);
		// ako je inkrementiranje promenljive, onda mozemo da uradimo const_1, add,
		// store
		if (des.obj.getType().getKind() == Struct.Int) {
			Code.put(Code.const_m1);
			Code.put(Code.add);
			Code.store(des.obj);
		}
		// ako je inkrementiranje niza, to je djubre koje se ne desava (nije tip int)

		// ako je inkrementiranje elementa niza, onda na steku vec imamo adr i index, pa
		// uradimo dup2, aload, const_1, add, astore
		if (des.obj.getType().getKind() == Struct.Array) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.put(Code.const_m1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}

	@Override
	public void visit(NumConstPositive NumConstPositive) {
		Code.load(new Obj(Obj.Con, "$", Tab.intType, NumConstPositive.getValue(), 0));
	}

	@Override
	public void visit(CharConst CharConst) {
		Code.load(new Obj(Obj.Con, "$", Tab.intType, CharConst.getValue(), 0));
	}

	@Override
	public void visit(BoolConst BoolConst) {
		Code.load(new Obj(Obj.Con, "$", SemanticAnalyzer.boolStructNode, (BoolConst.getValue() ? 1 : 0), 0));
	}

	@Override
	public void visit(DesignatorIdentifier designatorIdentifier) {
		Obj myObj = designatorIdentifier.obj;
		// uvek stavimo na stek, osim ako je poziv metode
		if (myObj.getKind() != Obj.Meth) {
			Code.load(myObj);
		}
		/*
		 * //provera da li je u pitanju lokalna ili globalna promenljiva if
		 * (myObj.getLevel() == 0) { //ako je globalna, treba uraditi getstatic
		 * //Code.put(Code.getstatic); //Code.put(myObj.getAdr());
		 * Code.put(Code.const_); Code.put4(myObj.getAdr()); } else { //ako je lokalna,
		 * treba uraditi load ili loadN Code.put(Code.const_);
		 * Code.put4(myObj.getAdr()); }
		 */
	}

	@Override
	public void visit(DesignatorFieldReferencing designatorFieldReferencing) {

	}

	@Override
	public void visit(ExprTermSum exprTermSum) {
		Addop addop = exprTermSum.getAddop();
		if (addop instanceof AddopAdd) {
			Code.put(Code.add);
		} else if (addop instanceof AddopSub) {
			Code.put(Code.sub);
		}
	}

	@Override
	public void visit(ExprTermNegative exprTermNegative) {
		Code.put(Code.neg);
	}

	@Override
	public void visit(TermProduct termProduct) {
		Mulop mulop = termProduct.getMulop();
		if (mulop instanceof MulopMul) {
			Code.put(Code.mul);
		} else if (mulop instanceof MulopDiv) {
			// TODO provera da nije deljenje sa nulom
			Code.put(Code.div);
		} else if (mulop instanceof MulopMod) {
			// TODO provera da nije deljenje sa nulom
			Code.put(Code.rem);
		}

	}

	@Override
	public void visit(FactorDesignator factorDesignator) {
		Designator des = factorDesignator.getDesignator();
		boolean isGlobal = (des.obj.getLevel() == 0);

		// provera tipa designator-a
		// TODO mozda je bolje da se umesto po tipu obj cvora ovo radi po tome da li smo
		// dosli iz DesignatorIdentifier ili iz DesignatorArrayDereferencing

		// ako je konstanta, ne radi nista, vrednost je vec na steku
		if (des.obj.getKind() == Obj.Con) {

		}
		// ako je promenljiva, proveriti da li je nizovska, klasna itd.
		else if (des instanceof DesignatorIdentifier) {

		} else if (des instanceof DesignatorFieldReferencing) {
			// TODO
		} else if (des instanceof DesignatorArrayDereferencing) {
			// na steku su nam adresa niza i broj indeksa

			// treba da proverimo kog tipa su elementi niza

			Struct s = des.obj.getType();

			while (s.getKind() == Struct.Array || s.getKind() == Struct.Class) {
				s = s.getElemType();
			}

			if (s.getKind() == Struct.Int) {
				Code.put(Code.aload);
			} else if (s.getKind() == Struct.Char) {
				Code.put(Code.baload);
			} else if (s.getKind() == SemanticAnalyzer.boolStructNode.getKind()) {
				Code.put(Code.aload);// TODO sta ovde treba, aload ili baload?
			}
		}

	}

	@Override
	public void visit(FactorFuncCall factorFuncCall) {

		// proveriti da li je ime fje chr, ord ili len, ako jeste ne treba raditi
		// function call

		// za globalne funkcije je ovo lako, jer ce designator u sebi direktno
		// da u obj sadrzi objektni cvor metode
		Designator methodObj = factorFuncCall.getDesignator();

		// sracunavanje adrese gde treba da se skoci
		// sta pise u polju Adr za method cvorove? adresa prve instrukcije!
		int dest_adr = methodObj.obj.getAdr() - Code.pc;

		// odredjivanje broja formalnih parametara je lako: pise u Lvl obj cvora!
		int formParNum = methodObj.obj.getLevel();
		// odredjivanje broja lokalnih promenljivih je malo teze, treba da dohvatimo
		// opseg te metode
		Collection c = methodObj.obj.getLocalSymbols();
		int scopeSize = c.size();

		// staviti na stek actual params? ne, to su expr iz zagrade vec odradili

		Code.put(Code.call);
		Code.put2(dest_adr);

	}

	@Override
	public void visit(DesignatorStatementCall designatorStatementCall) {

		// proveriti da li je ime fje chr, ord ili len, ako jeste ne treba raditi
		// function call

		// za globalne funkcije je ovo lako, jer ce designator u sebi direktno
		// da u obj sadrzi objektni cvor metode
		Designator methodObj = designatorStatementCall.getDesignator();

		// sracunavanje adrese gde treba da se skoci
		// sta pise u polju Adr za method cvorove? adresa prve instrukcije!
		int dest_adr = methodObj.obj.getAdr() - Code.pc;

		// odredjivanje broja formalnih parametara je lako: pise u Lvl obj cvora!
		int formParNum = methodObj.obj.getLevel();
		// odredjivanje broja lokalnih promenljivih je malo teze, treba da dohvatimo
		// opseg te metode
		Collection c = methodObj.obj.getLocalSymbols();
		int scopeSize = c.size();

		// staviti na stek actual params? ne, vec se nalaze na steku (CallActualParams
		// ih ostavi)

		Code.put(Code.call);
		Code.put2(dest_adr);

		// ako nije void metoda, ciscenje steka!
		if (!methodObj.obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}

	@Override
	public void visit(SemAnEraseParamList semAnEraseParamList) {
		// Code.put(Code.pop);//brisanje adrese funkcije sa steka
	}

	@Override
	public void visit(FactorArrayAllocation factorArrayAllocation) {
		// dohvatanje cvora u koji se alocira niz
		SyntaxNode obj = factorArrayAllocation;
		while (!(obj instanceof DesignatorStatementAssignment)) {
			obj = obj.getParent();
		}

		Code.put(Code.newarray);
		// proveriti tip podataka, ako su char-ovi, niz bajtova se alocira, inace niz
		// reci
		if (factorArrayAllocation.getType().struct.equals(Tab.charType)) {
			// char
			Code.put(0);
		} else {
			Code.put(1);
		}

		/*
		 * Obj arrayObj = ((DesignatorStatementAssignment)obj).getDesignator().obj;
		 * //citanje adrese sa expression stack-a u referencu na niz //ovde je sad bitno
		 * da li je niz lokalni ili globalni, to odredimo tako sto pogledamo level obj
		 * Code.store(arrayObj); //ovo je ocistilo adresu niza sa steka
		 */
	}

	@Override
	public void visit(StatementPrintSimple statementPrintSimple) {
		Code.put(Code.const_5);

		if (statementPrintSimple.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}

	}

	@Override
	public void visit(StatementPrintComplex statementPrintComplex) {
		if (statementPrintComplex.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(StatementRead statementRead) {
		// ako je samo promenljiva
		// proverimo koji je tip designator-a u koji se cita
		Struct type = statementRead.getDesignator().obj.getType();
		while (type.getKind() != Struct.Int && type.getKind() != Struct.Char
				&& type.getKind() != SemanticAnalyzer.boolStructNode.getKind()) {
			type = type.getElemType();
		}

		if (type.getKind() == Struct.Int) {
			Code.put(Code.read);
		} else if (type.getKind() == Struct.Char) {
			Code.put(Code.bread);
		} else if (type.getKind() == SemanticAnalyzer.boolStructNode.getKind()) {
			Code.put(Code.read);
		}

		if (statementRead.getDesignator().obj.getType().getKind() == Struct.Array) {
			Struct elemType = SemanticAnalyzer.designatorTypeLookup.get(statementRead.getDesignator());
			// ako nije char
			if (elemType.equals(Tab.charType)) {
				// ako je char
				Code.put(Code.bastore);
			} else {
				Code.put(Code.astore);
			}

		}
		if (statementRead.getDesignator().obj.getType().getKind() == Struct.Class) {

		}
		if (statementRead.getDesignator().obj.getType().getKind() == Struct.Int
				|| statementRead.getDesignator().obj.getType().getKind() == Struct.Char
				|| type.getKind() == SemanticAnalyzer.boolStructNode.getKind()) {

			Code.store(statementRead.getDesignator().obj);
			Code.put(Code.pop);
		}

	}

	public void visit(CondFactSingle condFactSingle) {
		Code.put(Code.const_1);
		Code.putFalseJump(Code.eq, 0);
		int adr = Code.pc - 2;
		fixmeDearOR.add(adr);

	}

	@Override
	public void visit(CondFactComplex condFactComplex) {
		// treba diskutovati tip relacione operacije
		// na steku: 2 vrednosti koje treba uporediti
		Expr leftExpr = condFactComplex.getExpr();
		Expr rightExpr = condFactComplex.getExpr1();

		Relop relop = condFactComplex.getRelop();

		if (relop instanceof RelopEquivalent)// ==
		{
			// ako su izrazi jednaki
			Code.putFalseJump(Code.eq, 0);
		} else if (relop instanceof RelopNotEqual)// !=
		{
			Code.putFalseJump(Code.ne, 0);
		} else if (relop instanceof RelopGreater)// >
		{
			Code.putFalseJump(Code.gt, 0);
		} else if (relop instanceof RelopLesser)// <
		{
			Code.putFalseJump(Code.lt, 0);
		} else if (relop instanceof RelopGreaterOrEqual)// >=
		{
			Code.putFalseJump(Code.ge, 0);
		} else if (relop instanceof RelopLesserOrEqual)// <=
		{
			Code.putFalseJump(Code.le, 0);
		}
		int adr1 = Code.pc - 2;
		fixmeDearOR.add(adr1);
	}

	@Override
	public void visit(StatementIfMatched statementIfMatched) {
		int staDaPatchujem = fixmeDearEndOfELSE.get(statementIfMatched.getCodeGenFixupStatementIfMatched());
		Code.fixup(staDaPatchujem);
	}

	@Override
	public void visit(CodeGenFixupStatementIfMatchedEmpty codeGenFixupStatementIfMatchedEmpty) {

		// POSLEDNJA INSTRUKCIJA U IF SEKCIJI: treba da se ugradi jmp na kraj ELSE grane
		Code.putJump(0);
		// upisati ovu adresu negde da bi se na kraju prepoznavanja statement-a
		// fixup-ovala
		int mojaadresa = Code.pc - 2;
		fixmeDearEndOfELSE.put(codeGenFixupStatementIfMatchedEmpty, mojaadresa);

		// POCETAK ELSE SEKCIJE

		// FIXUP
		// sad obradjivanje kome mi treba da radimo fixup
		// kad se dodje do kraja condition-a, ako smo zakljucili da su svi OR-ovani
		// iskazi netacni, treba da se ode ovde

		CodeGenJmpFixup cgjf = ((StatementIfMatched) codeGenFixupStatementIfMatchedEmpty.getParent())
				.getCodeGenJmpFixup();
		ArrayList<Integer> myFixMeDearOR = hashMadjija.get(cgjf);

		for (Integer adr : myFixMeDearOR) {
			Code.fixup(adr);
		}
		fixmeDearOR = new ArrayList<>();

	}

	@Override
	public void visit(CodeGenFixupStatementIfUnmatchedEmpty codeGenFixupStatementIfUnmatchedEmpty) {
		// POSLEDNJA INSTRUKCIJA U IF SEKCIJI: ne umece se jmp na posle else, jer else
		// ne postoji!

		// POCETAK ELSE SEKCIJE: nema

		// FIXUP
		CodeGenJmpFixup cgjf = ((StatementIfUnmatched) codeGenFixupStatementIfUnmatchedEmpty.getParent())
				.getCodeGenJmpFixup();
		ArrayList<Integer> myFixMeDearOR = hashMadjija.get(cgjf);

		for (Integer adr : myFixMeDearOR) {
			Code.fixup(adr);
		}
		fixmeDearOR = new ArrayList<>();
	}

	@Override
	public void visit(CodeGenFixupConditionComplexEmpty codeGenFixupConditionComplexEmpty) {
		//proveriti da li je ovo deo conditiona u if-u ili u while-u
		SyntaxNode p = codeGenFixupConditionComplexEmpty.getParent();
		while( p!= null 
				&& !(p instanceof StatementDoWhile) 
				&& !(p instanceof StatementIfUnmatched) 
				&& !(p instanceof StatementIfMatched))
		{
			p = p.getParent();
		}
		
		Code.putJump(doAdrStack.peek());
		int mojaadresa = Code.pc - 2;
		fixmeDearIF.add(mojaadresa);

		// uraditi fixup iz fixmeDearOR
		for (Integer adr : fixmeDearOR) {
			Code.fixup(adr);
		}
		fixmeDearOR = new ArrayList<>();
	}

	@Override
	public void visit(StatementBreak statementBreak) {
		// provera da li je unutar do while petlje i ako jeste dohvatanje petlje
		SyntaxNode p = statementBreak.getParent();
		while (p != null && !(p instanceof StatementDoWhile)) {
			p = p.getParent();
		}
		// ovo treba da skace na prvu instrukciju posle WHILE
		Code.putJump(0);
		int adr = Code.pc - 2;
		// prijavi se za fixup!
		ArrayList<Integer> list = doWhileBreakFixupLookup.get(p);
		if (list==null)
		{
			list = new ArrayList<Integer>();
			
		}
		list.add(adr);
		doWhileBreakFixupLookup.put((StatementDoWhile)p, list);

	}

	@Override
	public void visit(StatementContinue statementContinue) {
		// provera da li je unutar do while petlje i ako jeste dohvatanje petlje
		SyntaxNode p = statementContinue.getParent();
		while (p != null && !(p instanceof StatementDoWhile)) {
			p = p.getParent();
		}
		// ovo treba da skace na prvu instrukciju posle DO
		Code.putJump(doAdrStack.peek());
		int adr = Code.pc - 2;
		// prijavi se za fixup!

	}
	
	@Override
	public void visit(CodeGenFixupAfterDoEmpty codeGenFixupAfterDoEmpty)
	{
		//stavi na stek adresu
		int doAdr= Code.pc;
		doAdrStack.push(doAdr);
	}
	
	@Override
	public void visit(CodeGenFixupAfterWhileEmpty codeGenFixupAfterWhileEmpty)
	{
		//ovo je prva instrukcija posle while petlje
		Code.putJump(doAdrStack.peek());
		
		//dohvati listu svih break-ova kojima treba da se uradi fixup
		ArrayList<Integer> breakAdr = doWhileBreakFixupLookup.get((StatementDoWhile)codeGenFixupAfterWhileEmpty.getParent());
		if (breakAdr!=null)
		{
			//fixup break-ova
			for (Integer adr: breakAdr)
			{
				Code.fixup(adr);
			}
		}
		
		//fixup fj-ova iz poslednje zagrade
		CodeGenJmpFixup cgjf = ((StatementDoWhile)( codeGenFixupAfterWhileEmpty.getParent() )).getCodeGenJmpFixup();
		for (Integer adr : hashMadjija.get(cgjf) )
		{
			Code.fixup(adr);
		}
		
		doAdrStack.pop();
		
	}
	
	// pocetak IF grane, prodje i setuje sve (1==1fj && 2==2fj jmp) jmp-ove
	@Override
	public void visit(CodeGenJmpFixupEmpty codeGenJmpFixupEmpty) {
		// PRVA INSTRUKCIJA IF SEKCIJE

		// FIXUP
		// u ovom trenutku se u fixmeDearOR nalazi poslednja zagrada tj. njeni AND
		// uslovi i njihove fj adrese
		hashMadjija.put(codeGenJmpFixupEmpty, fixmeDearOR);// sacuvamo to u hash mapu
		// ovi iz poslednje zagrade treba da skacu sa fj ili na else granu ako psotoji
		// (to obradi CodeGenFixupStatementIfMatched),
		// ili na prvu instrukciju posle if sekcije ako ne postoji
		// (to obradi CodeGenFixupStatementIfUnmatched)

		fixmeDearOR = new ArrayList<>();// da li treba ovaj reset? treba, da bi se sad ovde punili uslovi iz sledeceg
										// ugnjezdenog if-a

		//ako je do while smena, ne treba da se uradi fixup jump-ova, jer treba da skacu na pocetak, a ne ovde
		if (codeGenJmpFixupEmpty.getParent() instanceof StatementDoWhile) {

		} else {
			// prolazimo kroz listu adresa svih OR-ova, tu su sve zagrade osim prve
			// za svaku od njih treba da podesimo da im je true condition (jmp) jednak ovoj
			// adresi
			for (Integer adr : fixmeDearIF) {
				Code.fixup(adr);
			}
			fixmeDearIF = new ArrayList<>();
			// OVO RADI KAKO TREBA
		}

	}
}
