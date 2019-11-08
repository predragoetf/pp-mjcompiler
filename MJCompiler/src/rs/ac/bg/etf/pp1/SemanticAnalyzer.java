package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
//import rs.ac.bg.etf.pp1.ast.SemAnProgramName;
//import rs.ac.bg.etf.pp1.ast.SyntaxNode;
//import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

	HashMap<SyntaxNode, Object> lookup;
	Stack<Obj> scopeOwnerStack;
	public static HashMap<Designator, Struct> designatorTypeLookup;

	boolean errorDetected = false;
	int printCallCount = 0;
	Obj currentMethod = null;
	boolean returnFound = false;
	int nVars;
	Obj currentProcessedType = null;
	public static Struct boolStructNode = null;
	public static HashMap<Obj, Integer> arraySizeLookup = new HashMap<>();
	int currentLevel;
	ArrayList<Struct> currentFunctionCallArgumentTypes;
	ArrayList<Obj> currentMethodParameters;
	public static HashMap<SyntaxNode, Scope> ownerScopeLookup = new HashMap<>();
	Logger log = Logger.getLogger(getClass());
	private Struct currentMethodReturnType;
	private int currentMethodParCount;


	
	DumpSymbolTableVisitor dstv = new DumpSymbolTableVisitor();

	public void getNodeInfo(Obj objToVisit) {
		objToVisit.accept(dstv);
		log.info("\n" + dstv.getOutput());
		dstv = new DumpSymbolTableVisitor();
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void openScope(Obj pushMe) {
		report_info("Otvaranje scope ", null);
		scopeOwnerStack.push(pushMe);
		currentLevel++;
		Tab.openScope();
	}

	public Obj closeScope(boolean isClassScope) {
		report_info("Zatvaranje scope ", null);
		Obj retObj = scopeOwnerStack.pop();
		if (!isClassScope) {
			Tab.chainLocalSymbols(retObj);
		} else {
			// TODO
		}
		currentLevel--;
		Tab.closeScope();
		return retObj;
	}

	// dodati Bool u tabelu simbola u konstruktoru!
	public SemanticAnalyzer() {

		lookup = new HashMap<>();
		scopeOwnerStack = new Stack<>();
		currentMethodParameters = new ArrayList<>();
		currentFunctionCallArgumentTypes = new ArrayList<>();
		designatorTypeLookup = new HashMap<>();
		currentLevel = -1;
		boolStructNode = new Struct(7008);
		Tab.insert(Obj.Type, "bool", boolStructNode);

		// ubacivanje len
		int lenPC = Code.pc;
		Tab.lenObj.setAdr(lenPC);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		Code.put(Code.load);
		Code.put(0);
		Code.put(Code.arraylength);

		Code.put(Code.exit);
		Code.put(Code.return_);

		// ubacivanje char
		int charPC = Code.pc;
		Tab.chrObj.setAdr(charPC);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		// obrada za chr->nista!
		Code.put(Code.load);
		Code.put(0);

		Code.put(Code.exit);
		Code.put(Code.return_);

		// ubacivanje ord
		int ordPC = Code.pc;
		Tab.ordObj.setAdr(ordPC);
		Code.put(Code.enter);
		Code.put(1);
		Code.put(1);

		// obrada za ord->nista!

		Code.put(Code.load);
		Code.put(0);

		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// ubacivanje Obj cvora za program, otvaranje program scope-a
	public void visit(SemAnProgramName semAnProgramName) {
		Obj myObj = Tab.insert(Obj.Prog, semAnProgramName.getName(), Tab.noType);
		openScope(myObj);
		report_info("Prepoznato programsko ime : " + semAnProgramName.getName(), semAnProgramName);
	}

	// zatvaranje program scope-a i prevezivanje
	public void visit(Program program) {
		Obj objMain = Tab.find("main");
		if (objMain.equals(Tab.noObj)) {
			report_error("Greska: nije pronadjena main metoda!", null);
			errorDetected = true;
		} else if (objMain.getKind() != Obj.Meth) {
			report_error("Greska: main mora biti metoda!", null);
			errorDetected = true;
		}
		// provera da li je main void i koliko ima lokalnih promenljivih
		else if (objMain.getType() != Tab.noType) {
			report_error("Greska: Main nije void metoda", null);
			errorDetected = true;
		} else if (objMain.getLevel() != 0) {
			report_error("Greska: Main ima " + objMain.getLevel() + " formalnih parametara, a ocekivano 0", null);
			errorDetected = true;
		}

		// Tab.chainLocalSymbols(program.getSemAnProgramName().obj);
		closeScope(false);
		report_info("Zatvoren program! ", null);
	}

	// ekstrakcija tipa ako postoji u tabeli simbola, ako ne postoji, pise error i
	// vraca noType
	public void visit(Type type) {
		Obj objNode = Tab.find(type.getTypeName());
		if (objNode == Tab.noObj) {
			report_error("Navedeno ime tipa " + type.getTypeName() + " nije regularno ", type);
			type.struct = Tab.noType;
			errorDetected = true;
		} else {
			report_info("Prepoznat tip " + type.getTypeName(), type);
			type.struct = objNode.getType();
			currentProcessedType = objNode;
		}
	}

	public void visit(ConstDeclListSingle constDeclListSingle) {
		constDeclListSingle.struct = constDeclListSingle.getTypedConstAssignment().getType().struct;
	}

	// pravljenje i ubacivanje Obj cvora za konstantu, uz provere da li je
	// odgovarajuceg tipa i da li je ime slobodno
	// proveriti da li ubacivanje u tabelu simbola treba da se uradi za a ako je
	// npr. napisano const int a=5, b=true;
	public void visit(NonTypedConstAssignmentNum nonTypedConstAssignmentNum) {
		String cName = nonTypedConstAssignmentNum.getCName();
		Obj objNode = Tab.find(cName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto
			report_error("Greska, neuspesno deklarisanje konstante s imenom " + cName + ", to ime je vec zauzeto! ",
					nonTypedConstAssignmentNum);
			errorDetected = true;
			return;
		}

		// provera da li je tip dobar
		if (currentProcessedType.getType() != Tab.intType) {
			errorDetected = true;
			report_error("Greska, tipovi sa leve i desne strane se ne slazu", nonTypedConstAssignmentNum);
		} else {
			// ubacivanje u tabelu simbola
			report_info("Prepoznata deklaracija globalne konstante " + cName + " tipa int vrednosti "
					+ nonTypedConstAssignmentNum.getValue(), nonTypedConstAssignmentNum);
			Obj myObj = Tab.insert(Obj.Con, cName, Tab.intType);
			myObj.setAdr(nonTypedConstAssignmentNum.getValue());
		}
	}

	public void visit(NonTypedConstAssignmentChar nonTypedConstAssignmentChar) {
		String cName = nonTypedConstAssignmentChar.getCName();
		Obj objNode = Tab.find(cName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto
			report_error("Greska, neuspesno deklarisanje konstante s imenom " + cName + ", to ime je vec zauzeto! ",
					nonTypedConstAssignmentChar);
			errorDetected = true;
			return;
		}

		// provera da li je tip dobar
		if (currentProcessedType.getType() != Tab.charType) {
			errorDetected = true;
			report_error("Greska, tipovi sa leve i desne strane se ne slazu", nonTypedConstAssignmentChar);
			return;
		} else {
			// ubacivanje u tabelu simbola
			report_info("Prepoznata eklaracija globalne konstante " + cName + " tipa char vrednosti "
					+ nonTypedConstAssignmentChar.getValue(), nonTypedConstAssignmentChar);
			Obj myObj = Tab.insert(Obj.Con, cName, Tab.charType);
			myObj.setAdr(nonTypedConstAssignmentChar.getValue());
		}
	}

	// za bool promenljive provere tipa se rade na nivou obj cvora, ne na nivou
	// struct cvora
	public void visit(NonTypedConstAssignmentBool nonTypedConstAssignmentBool) {
		String cName = nonTypedConstAssignmentBool.getCName();
		Obj objNode = Tab.find(cName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto
			report_error("Greska, neuspesno deklarisanje konstante s imenom " + cName + ", to ime je vec zauzeto! ",
					nonTypedConstAssignmentBool);
			errorDetected = true;
			return;
		}

		// provera da li je tip dobar
		if (currentProcessedType != Tab.find("bool"))// TODO
		{
			errorDetected = true;
			report_error("Greska, tipovi sa leve i desne strane se ne slazu", nonTypedConstAssignmentBool);
			return;
		} else {
			// ubacivanje u tabelu simbola
			Boolean val = nonTypedConstAssignmentBool.getValue();

			report_info("Prepoznata deklaracija globalne konstante " + cName + " tipa bool vrednosti "
					+ nonTypedConstAssignmentBool.getValue(), nonTypedConstAssignmentBool);
			Obj myObj = Tab.insert(Obj.Con, cName, boolStructNode);
			if (val)
				myObj.setAdr(1);
			else
				myObj.setAdr(0);
		}

	}

	@Override
	public void visit(TypedDeclarationScalar typedDeclarationScalar) {

		String vName = typedDeclarationScalar.getVName();
		Obj objNode = Tab.find(vName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto ako je pronadjeni objekat u istom level-u
			if ((Tab.currentScope().findSymbol(vName) != null) && (Tab.currentScope().findSymbol(vName) != Tab.noObj)) {
				report_error(
						"Greska, neuspesno deklarisanje promenljive s imenom " + vName + ", to ime je vec zauzeto! ",
						typedDeclarationScalar);
				errorDetected = true;
				return;
			}
		}

		// ubacivanje u tabelu simbola
		report_info("Prepoznata deklaracija skalarne promenljive " + vName, typedDeclarationScalar);
		Obj myObj = Tab.insert(Obj.Var, vName, currentProcessedType.getType());
		// podesavanje nivoa
		myObj.setLevel(currentLevel);

	}

	@Override
	public void visit(TypedDeclarationVector typedDeclarationVector) {
		String vName = typedDeclarationVector.getVName();
		Obj objNode = Tab.find(vName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto ako je pronadjeni objekat u istom level-u
			if ((Tab.currentScope().findSymbol(vName) != null) && (Tab.currentScope().findSymbol(vName) != Tab.noObj)) {
				report_error(
						"Greska, neuspesno deklarisanje promenljive s imenom " + vName + ", to ime je vec zauzeto! ",
						typedDeclarationVector);
				errorDetected = true;
				return;
			}
		}

		// ubacivanje u tabelu simbola
		report_info("Prepoznata deklaracija vektorske promenljive " + vName, typedDeclarationVector);
		Obj myObj = Tab.insert(Obj.Var, vName, new Struct(Struct.Array, currentProcessedType.getType()));
		// podesavanje nivoa
		myObj.setLevel(currentLevel);
	}

	public void visit(NonTypedDeclarationScalar nonTypedDeclarationScalar) {
		String vName = nonTypedDeclarationScalar.getVName();
		Obj objNode = Tab.find(vName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto ako je pronadjeni objekat u istom level-u
			if ((Tab.currentScope().findSymbol(vName) != null) && (Tab.currentScope().findSymbol(vName) != Tab.noObj)) {
				report_error(
						"Greska, neuspesno deklarisanje promenljive s imenom " + vName + ", to ime je vec zauzeto! ",
						nonTypedDeclarationScalar);
				errorDetected = true;
				return;
			}
		}

		// ubacivanje u tabelu simbola
		report_info("Prepoznata deklaracija skalarne promenljive " + vName, nonTypedDeclarationScalar);
		Obj myObj = Tab.insert(Obj.Var, vName, currentProcessedType.getType());
		// podesavanje nivoa
		myObj.setLevel(currentLevel);
	}

	public void visit(NonTypedDeclarationVector nonTypedDeclarationVector) {
		String vName = nonTypedDeclarationVector.getVName();
		Obj objNode = Tab.find(vName);
		// provera da li je ime dostupno
		if (objNode != Tab.noObj) {
			// ime je zauzeto ako je pronadjeni objekat u istom level-u
			if ((Tab.currentScope().findSymbol(vName) != null) && (Tab.currentScope().findSymbol(vName) != Tab.noObj)) {
				report_error(
						"Greska, neuspesno deklarisanje promenljive s imenom " + vName + ", to ime je vec zauzeto! ",
						nonTypedDeclarationVector);
				errorDetected = true;
				return;
			}
		}

		// ubacivanje u tabelu simbola
		report_info("Prepoznata deklaracija vektorske promenljive " + vName, nonTypedDeclarationVector);
		Obj myObj = Tab.insert(Obj.Var, vName, new Struct(Struct.Array, currentProcessedType.getType()));
		// podesavanje nivoa
		myObj.setLevel(currentLevel);
	}

	@Override
	public void visit(MethodDecl methodDecl) {

		// provera return
		if (!returnFound && scopeOwnerStack.peek().getType() != Tab.noType) {
			report_error("Metoda cela obradjena, nije void, a nema return! ", methodDecl);
		}
		closeScope(false);
	}

	@Override
	public void visit(MethodReturnTypeTyped methodReturnTypeTyped) {
		currentMethodReturnType = methodReturnTypeTyped.getType().struct;
	}

	@Override
	public void visit(MethodReturnTypeVoid methodReturnTypeVoid) {
		currentMethodReturnType = Tab.noType;
	}

	// ubacivanje objektnog cvora metode u tabelu simbola
	@Override
	public void visit(SemAnMethodDeclOpenScopeEmpty semAnMethodDeclOpenScopeEmpty) {
		// report_info("SemAnMethodDeclOpenScope", semAnMethodDeclOpenScopeEmpty);
		// provera dostupnosti imena
		if (!(semAnMethodDeclOpenScopeEmpty.getParent() instanceof MethodDecl)) {
			report_error("Fatal: los roditelj", semAnMethodDeclOpenScopeEmpty);
			errorDetected = true;
			return;
		} else {
			MethodDecl p = (MethodDecl) (semAnMethodDeclOpenScopeEmpty.getParent());
			String mName = p.getMName();

			Obj objNode = Tab.find(mName);
			// provera da li je ime dostupno
			if (objNode != Tab.noObj) {
				// ime je zauzeto ako je pronadjeni objekat u istom level-u
				if ((Tab.currentScope().findSymbol(mName) != null)
						&& (Tab.currentScope().findSymbol(mName) != Tab.noObj)) {
					report_error(
							"Greska, neuspesno deklarisanje metode s imenom " + mName + ", to ime je vec zauzeto! ",
							semAnMethodDeclOpenScopeEmpty);
					errorDetected = true;
					return;
				}
			}

			objNode = Tab.insert(Obj.Meth, mName, currentMethodReturnType);
			// sad neka podesavanja cvora koje se rade za metodu

			// otvaranje novog scope-a
			openScope(objNode);

			/*
			 * Tab.currentScope.addToLocals(objNode); { Tab.openScope(); //for za ubacivanje
			 * argumenata Tab.currentScope.addToLocals(new Obj(Obj.Var, "i", intType, 0,
			 * 1)); //end for objNode.setLocals(Tab.currentScope.getLocals());
			 * Tab.closeScope(); }
			 */
		}
	}

	public void visit(MethodFormParsNone methodFormParsNone) {
		// postaviti broj parametara metode na 0
		currentMethodParCount = 0;
		scopeOwnerStack.peek().setLevel(0);
	}

	public void visit(MethodFormParsSome methodFormParsSome) {
		// postaviti broj parametara metode na izbrojani broj
		scopeOwnerStack.peek().setLevel(currentMethodParCount);
		currentMethodParCount = 0;
	}

	public void visit(FormParsSingle formParsSingle) {
		currentMethodParCount = 1;
		// dodavanje u scope se radi u TypedDeclaration
	}

	public void visit(FormParsList formParsList) {
		currentMethodParCount++;
		// dodavanje u scope se radi u TypedDeclaration
	}

	@Override
	public void visit(FactorNumConstant factorNumConstant) {
		factorNumConstant.struct = Tab.intType;
	}

	@Override
	public void visit(FactorCharConstant factorCharConstant) {
		factorCharConstant.struct = Tab.charType;
	}

	@Override
	public void visit(FactorBoolConstant factorBoolConstant) {
		factorBoolConstant.struct = Tab.find("bool").getType();
	}

	@Override
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}

	@Override
	public void visit(FactorConstructor factorConstructor) {
		if (factorConstructor.getType().struct.getKind() != Struct.Class) {
			report_error("Greska: Poziv konstruktora za tip koji nije klasa ", factorConstructor);
			errorDetected = true;
			return;
		}
		factorConstructor.struct = factorConstructor.getType().struct;
	}

	@Override
	public void visit(FactorArrayAllocation factorArrayAllocation) {
		if (!(factorArrayAllocation.getExpr().struct.equals(Tab.intType))) {
			report_error("Greska: nekorektan izraz za dimenzije niza ", factorArrayAllocation);
			errorDetected = true;
			return;
		}
		SyntaxNode obj = factorArrayAllocation;
		while (!(obj instanceof DesignatorStatementAssignment)) {
			obj = obj.getParent();
		}

		arraySizeLookup.put(((DesignatorStatementAssignment) obj).getDesignator().obj, new Integer(1));
		// TODO treba dodati vracanje tipa tj. setovanje struct polja za faktor
		factorArrayAllocation.struct = new Struct(Struct.Array, factorArrayAllocation.getType().struct);
	}

	@Override
	public void visit(FactorDesignator factorDesignator) {
		factorDesignator.struct = designatorTypeLookup.get(factorDesignator.getDesignator());
	}

	@Override
	public void visit(FactorFuncCall factorFuncCall) {
		Obj method = factorFuncCall.getDesignator().obj;
		// provera da li je ovo metoda klase ili globalna metoda i da li je vidljiva
		Obj findResult = Tab.find(method.getName());
		if (findResult.equals(Tab.noObj)) {
			report_error("Nije pronadjena metoda imena " + method.getName(), factorFuncCall);
			errorDetected = true;
			return;
		} else if (findResult.getKind() != Obj.Meth) {
			report_error("Dezignator imena " + method.getName() + " nije metoda ", factorFuncCall);
			errorDetected = true;
			return;
		}
		// CallActualParams je napunio listu tipova argumenata

		// provera da li je dobar broj argumenata pri pozivu
		int actualArgCnt = currentFunctionCallArgumentTypes.size();
		if (method.getLevel() != actualArgCnt) {
			report_error("Ocekivano " + method.getLevel() + " argumenata, a pri pozivu prosledjeno " + actualArgCnt,
					factorFuncCall);
			errorDetected = true;
			return;
		}
		Collection<Obj> expectedArgs = method.getLocalSymbols();
		// provera da li je dobar set argumenata prosledjen, osim ako se fja zove "len"
		if (method.getName().equals("len")) {
			// proveriti samo da li je argument niz int-ova ili char-ova
			Struct argtype = currentFunctionCallArgumentTypes.get(0);
			if (argtype.getKind() != Struct.Array || (argtype.getElemType().getKind() != Struct.Int
					&& argtype.getElemType().getKind() != Struct.Char)) {
				// report_error("Kind: "+ argtype.getElemType().getKind(), null);
				report_error("Ne preklapa se tip ocekivanog i stvarnog argumenta ", factorFuncCall);
				errorDetected = true;
				return;
			}
		} else {

			int cnt = 0;
			for (Obj obj : expectedArgs) {
				if (cnt >= actualArgCnt)
					break;
				Struct foundType = currentFunctionCallArgumentTypes.get(cnt);
				if (!foundType.assignableTo(obj.getType()) || !foundType.compatibleWith(obj.getType())) {
					report_error("Ne preklapa se tip ocekivanog i stvarnog argumenta " + (cnt + 1) + "/" + actualArgCnt,
							factorFuncCall);
					errorDetected = true;
					return;
				}
				cnt++;
			}
		}
		//report_info("Poziv globalnog metoda "+method.getName(), factorFuncCall);
		//getNodeInfo(method);

		// setovanje tipa factor na tip koji fja vraca
		factorFuncCall.struct = designatorTypeLookup.get(factorFuncCall.getDesignator());
		currentFunctionCallArgumentTypes = new ArrayList<>();
	}

	@Override
	public void visit(CallActualParamsNone callActualParamsNone) {
		currentFunctionCallArgumentTypes = new ArrayList<>();
	}

	@Override
	public void visit(SemAnEraseParamListEmpty semAnEraseParamListEmpty) {
		// TODO
		currentFunctionCallArgumentTypes = new ArrayList<>();
	}

	@Override
	public void visit(ActParsSingle actParsSingle) {
		// currentFunctionCallArgumentTypes = new ArrayList<>();
		currentFunctionCallArgumentTypes.add(actParsSingle.getExpr().struct);
	}

	@Override
	public void visit(ActParsMultiple actParsMultiple) {
		currentFunctionCallArgumentTypes.add(actParsMultiple.getExpr().struct);
	}

	@Override
	public void visit(ExprTermSingle exprTermSingle) {
		exprTermSingle.struct = exprTermSingle.getExprTerm().struct;
	}

	@Override
	public void visit(ExprTermSum exprTermSum) {
		if (exprTermSum.getExpr().struct == null || exprTermSum.getTerm().struct == null) {
			report_error("Greska: Propagacija greske ", exprTermSum);
			errorDetected = true;
			return;
		}

		if (!(exprTermSum.getExpr().struct.equals(Tab.intType))
				|| !(exprTermSum.getTerm().struct.equals(Tab.intType))) {
			report_error("Greska: ocekivan tip int za sabirke u zbiru ", exprTermSum);
			errorDetected = true;
			return;
		}

		exprTermSum.struct = Tab.intType;
	}

	@Override
	public void visit(ExprTermPositive exprTermPositive) {
		exprTermPositive.struct = exprTermPositive.getTerm().struct;
	}

	@Override
	public void visit(ExprTermNegative exprTermNegative) {
		if (!(exprTermNegative.getTerm().struct.equals(Tab.intType))) {
			report_error("Greska: minus ispred terma koji nije int ", exprTermNegative);
			errorDetected = true;
			return;
		}

		exprTermNegative.struct = Tab.intType;
	}

	@Override
	public void visit(TermFactor termFactor) {
		termFactor.struct = termFactor.getFactor().struct;
	}

	@Override
	public void visit(TermProduct termProduct) {
		try {

			if (!(termProduct.getFactor().struct.equals(Tab.intType))
					|| !(termProduct.getTerm().struct.equals(Tab.intType))) {
				report_error("Greska: ocekivan tip int za cinioce u proizvodu ", termProduct);
				errorDetected = true;
				return;
			}
			termProduct.struct = Tab.intType;
		} catch (Exception e) {
			report_error("Prosledjivanje greske ", termProduct);
			errorDetected = true;
			return;
		}

	}

	@Override
	public void visit(StatementRead statementRead) {
		Obj designatorObj = statementRead.getDesignator().obj;

		if (designatorObj == null) {
			report_error("Prosledjivanje greske", statementRead);
			errorDetected = true;
			return;
		}
		// provera da li je dobar scope
		if (Tab.find(designatorObj.getName()).equals(Tab.noObj)) {
			report_error("Greska: nije pronadjena promenljiva u scope-u ", statementRead);
			errorDetected = true;
			return;
		}
		// provera da li je designator varijabla, polje, element niza
		if (designatorObj.getKind() == Obj.Var) {

		} else if (designatorObj.getKind() == Obj.Fld) {
			// TODO ? ? ?
		} else if (designatorObj.getKind() == Obj.Elem) {
			// TODO ? ? ?
		} else {
			report_error("Greska: nije dobar tip designatora ", statementRead);
			errorDetected = true;
			return;
		}
		// provera da li je tip designator-a int, char ili bool
		Struct designatorType = designatorTypeLookup.get(statementRead.getDesignator());
		if (designatorType == null) {
			report_error("Prosledjivanje greske ", statementRead);
			errorDetected = true;
			return;
		}
		if (!(designatorType.equals(Tab.intType) || designatorType.equals(Tab.charType)
				|| designatorType.equals(boolStructNode))) {
			report_error("Greska: Detektovan read u nedozvoljen tip ", statementRead);
			errorDetected = true;
			return;
		}
		report_info("Detektovan dozvoljen read ", statementRead);

	}

	@Override
	public void visit(StatementPrintSimple statementPrintSimple) {
		Struct exprType = statementPrintSimple.getExpr().struct;
		if (exprType == null) {
			report_error("Prosledjivanje greske ", statementPrintSimple);
			errorDetected = true;
			return;
		}
		if (!(exprType.equals(Tab.intType) || exprType.equals(Tab.charType) || exprType.equals(boolStructNode))) {
			report_error("Greska: Detektovan print u nedozvoljen tip ", statementPrintSimple);
			errorDetected = true;
			return;
		}
		printCallCount++;
	}

	@Override
	public void visit(StatementPrintComplex statementPrintComplex) {
		Struct exprType = statementPrintComplex.getExpr().struct;
		if (exprType == null) {
			report_error("Prosledjivanje greske ", statementPrintComplex);
			errorDetected = true;
			return;
		}
		if (!(exprType.equals(Tab.intType) || exprType.equals(Tab.charType) || exprType.equals(boolStructNode))) {
			report_error("Greska: Detektovan print u nedozvoljen tip ", statementPrintComplex);
			errorDetected = true;
			return;
		}
		printCallCount++;
	}

	@Override
	public void visit(StatementReturn statementReturn) {
		if (scopeOwnerStack.peek().getKind() != Obj.Meth) {
			report_error("Pronadjen return van funkcije! ", statementReturn);
			errorDetected = true;
			return;
		}
		// report_info(statementReturn.getStatementReturnValue().struct.toString(),
		// null);
		if (!(statementReturn.getStatementReturnValue().struct.equals(scopeOwnerStack.peek().getType()))) {
			report_error("Neodgovarajuci tip povratne vrednosti! ", statementReturn);
			errorDetected = true;
			return;
		}
		returnFound = true;
	}

	@Override
	public void visit(StatementReturnValueEmpty statementReturnValueEmpty) {
		statementReturnValueEmpty.struct = Tab.noType;
	}

	@Override
	public void visit(StatementReturnValueNotEmpty statementReturnValueNotEmpty) {
		statementReturnValueNotEmpty.struct = statementReturnValueNotEmpty.getExpr().struct;
	}

	@Override
	public void visit(DesignatorIdentifier designatorIdentifier) {
		String vName = designatorIdentifier.getVName();

		// provera da li je ime poznato
		Obj obj = Tab.find(vName);
		if (obj.equals(Tab.noObj)) {
			report_error("Greska: ime " + vName + " nije pronadjeno", designatorIdentifier);
			errorDetected = true;
			return;
		}

		// provere tipa designator-a

		// ako je promenljiva
		if (obj.getKind() == Obj.Var) {
			designatorTypeLookup.put(designatorIdentifier, obj.getType());
			designatorIdentifier.obj = obj;
			String nivo = "";
			if (obj.getLevel() == 0) {
				report_info("Koriscenje globalne varijable " + vName, designatorIdentifier);
				getNodeInfo(obj);
				nivo = "globalne ";
			} else {
				nivo = "lokalne ";
				//sad ovde treba  proveriti ko je vlasnik scope-a
				Obj owner = scopeOwnerStack.peek();
				if (owner.getKind() == Obj.Meth)
				{
					//moramo da izbrojimo 
					
					Collection<Obj> scopevars = owner.getLocalSymbols();
					
					int fpars = owner.getLevel();
					int cnt = 0;
					
					for ( Obj v : scopevars )
					{
						if (v.equals(obj))
						{
							break;
						}
						cnt++;
					}
					
					if (cnt<fpars)
					{
						//formalni parametar!
						report_info("Koriscenje formalnog argumenta fje " + vName, designatorIdentifier);
						getNodeInfo(obj);
					}
					else
					{
						report_info("Koriscenje lokalne promenljive " + vName, designatorIdentifier);
						getNodeInfo(obj);
					}
					
				}
			}
			
			// TODO dump polja iz tabele simbola
			
			//dohvatiti metodu kojoj je ovo argument :O
			
		}
		// ako je konstanta
		else if (obj.getKind() == Obj.Con) {
			designatorTypeLookup.put(designatorIdentifier, obj.getType());
			designatorIdentifier.obj = obj;
			report_info("Koriscenje globalne konstante " + vName, designatorIdentifier);
			getNodeInfo(obj);
		}
		// ako je polje klase u kojoj se nalazimo
		else if (obj.getKind() == Obj.Fld) {
			// TODO
		} else if (obj.getKind() == Obj.Meth) {
			designatorTypeLookup.put(designatorIdentifier, obj.getType());
			designatorIdentifier.obj = obj;
			report_info("Koriscenje metode " + vName, designatorIdentifier);
			getNodeInfo(obj);
		} else {
			report_error("Greska: Neocekivani tip designatora!", designatorIdentifier);
			errorDetected = true;
			return;
		}
	}

	@Override
	public void visit(DesignatorFieldReferencing designatorFieldReferencing) {
		// TODO preraditi DesignatorArrayDereferencing
	}

	@Override
	public void visit(DesignatorArrayDereferencing designatorArrayDereferencing) {
		try {
			Obj obj = designatorArrayDereferencing.getDesignator().obj;

			// provera da li je tip dereferencijabilan
			Struct prevLvlType = designatorTypeLookup.get(designatorArrayDereferencing.getDesignator());
			if (prevLvlType.getKind() != Struct.Array) {
				report_error("Greska: Pokusaj dereferenciranja promenljive koja nije nizovska",
						designatorArrayDereferencing);
				errorDetected = true;
				return;
			}

			// prosledjivanje objektnog cvora
			designatorArrayDereferencing.obj = obj;

			// prosledjivanje tipa

			Struct thisLvlType = prevLvlType.getElemType();
			designatorTypeLookup.put(designatorArrayDereferencing, thisLvlType);

			report_info("Pristup elementu niza " + obj.getName(), designatorArrayDereferencing);
		} catch (Exception e) {
			report_error("Prosledjivanje greske ", designatorArrayDereferencing);
			errorDetected = true;
			return;
		}
	}

	@Override
	public void visit(DesignatorStatementAssignment designatorStatementAssignment) {
		// dohvatanje tipa leve i desne strane
		Struct leftSideType = designatorTypeLookup.get(designatorStatementAssignment.getDesignator());
		Struct rightSideType = designatorStatementAssignment.getExpr().struct;

		if (rightSideType == null || leftSideType == null) {
			report_error("Prosledjivanje greske ", designatorStatementAssignment);
			errorDetected = true;
			return;
		}

		// dohvatanje objektnog cvora za levu stranu
		Obj designatorObj = designatorStatementAssignment.getDesignator().obj;
		// provera da li je gadjani objekat vidljiv iz ovog scope-a
		if ((designatorObj == null) || designatorObj.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementAssignment);
			errorDetected = true;
			return;
		}

		Obj findResult = Tab.find(designatorObj.getName());
		if (findResult.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementAssignment);
			errorDetected = true;
			return;
		}

		// provera da li je dozvoljena dodela u designator po tipu designatora
		if (designatorObj.getKind() == Obj.Var) {
			// provera da li su tipovi kompatibilni
			if (!(rightSideType.compatibleWith(leftSideType))) {
				report_error("Greska: tipovi nisu kompatibilni ", designatorStatementAssignment);
				errorDetected = true;
				return;
			}
			if (!rightSideType.assignableTo(leftSideType)) {
				report_error("Greska: tip s desne strane nije dodeljiv tipu s leve! ", designatorStatementAssignment);
				errorDetected = true;
				return;
			}
			report_info("Dodela dozvoljena ", designatorStatementAssignment);
		} else if (designatorObj.getKind() == Obj.Fld) {
			// TODO
		} else {
			report_error("Nedozvoljen lvalue pri dodeli ", designatorStatementAssignment);
			errorDetected = true;
			return;
		}

	}

	@Override
	public void visit(DesignatorStatementIncrement designatorStatementIncrement) {
		Obj myObj = designatorStatementIncrement.getDesignator().obj;
		// provera da li je gadjani objekat vidljiv iz ovog scope-a
		if (myObj == null || myObj.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementIncrement);
			errorDetected = true;
			return;
		}
		Obj findResult = Tab.find(myObj.getName());
		if (findResult.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementIncrement);
			errorDetected = true;
			return;
		}

		// proveriti tip objekta na koji designator ukazuje (promenljiva, element niza,
		// polje objekta)
		if (myObj.getKind() == Obj.Var) {
			report_info("Detektovano inkrementiranje ", designatorStatementIncrement);
		} else if (myObj.getKind() == Obj.Elem) {
			// TODO sta je ovo? ? ?
		} else {
			report_error("Nedozvoljeno inkrementiranje ", designatorStatementIncrement);
			errorDetected = true;
			return;
		}

		// proveriti da li je int
		Struct designatorType = designatorTypeLookup.get(designatorStatementIncrement.getDesignator());
		if (!designatorType.equals(Tab.intType)) {
			report_error("Greska: mogu se inkrementirati samo promenljive ili polja tipa int ",
					designatorStatementIncrement);
			errorDetected = true;
			return;
		}
	}

	@Override
	public void visit(DesignatorStatementDecrement designatorStatementDecrement) {
		Obj myObj = designatorStatementDecrement.getDesignator().obj;
		// provera da li je gadjani objekat vidljiv iz ovog scope-a
		if (myObj == null || myObj.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementDecrement);
			errorDetected = true;
			return;
		}
		Obj findResult = Tab.find(myObj.getName());
		if (findResult.equals(Tab.noObj)) {
			report_error("Greska: promenljiva nije pronadjena u ovom scope-u ", designatorStatementDecrement);
			errorDetected = true;
			return;
		}

		// proveriti tip objekta na koji designator ukazuje (promenljiva, element niza,
		// polje objekta)
		if (myObj.getKind() == Obj.Var) {
			report_info("Detektovano dekrementiranje ", designatorStatementDecrement);
		} else if (myObj.getKind() == Obj.Elem) {
			// TODO sta je ovo? ? ?
		} else {
			report_error("Nedozvoljeno dekrementiranje ", designatorStatementDecrement);
			errorDetected = true;
			return;
		}

		// proveriti da li je int
		Struct designatorType = designatorTypeLookup.get(designatorStatementDecrement.getDesignator());
		if (!designatorType.equals(Tab.intType)) {
			report_error("Greska: mogu se dekrementirati samo promenljive ili polja tipa int ",
					designatorStatementDecrement);
			errorDetected = true;
			return;
		}
	}

	@Override
	public void visit(DesignatorStatementCall designatorStatementCall) {
		Obj method = designatorStatementCall.getDesignator().obj;
		// provera da li je ovo metoda klase ili globalna metoda i da li je vidljiva
		Obj findResult = Tab.find(method.getName());
		if (findResult.equals(Tab.noObj)) {
			report_error("Nije pronadjena metoda imena " + method.getName(), designatorStatementCall);
			errorDetected = true;
			return;
		} else if (findResult.getKind() != Obj.Meth) {
			report_error("Dezignator imena " + method.getName() + " nije metoda ", designatorStatementCall);
			errorDetected = true;
			return;
		}
		// CallActualParams je napunio listu tipova argumenata

		// provera da li je dobar broj argumenata pri pozivu
		int actualArgCnt = currentFunctionCallArgumentTypes.size();
		if (method.getLevel() != actualArgCnt) {
			report_error("Ocekivano " + method.getLevel() + " argumenata, a pri pozivu prosledjeno " + actualArgCnt,
					designatorStatementCall);
			errorDetected = true;
			return;
		}
		// provera da li je dobar set argumenata prosledjen
		Collection<Obj> expectedArgs = method.getLocalSymbols();
		int cnt = 0;
		for (Obj obj : expectedArgs) {
			if (cnt >= actualArgCnt)
				break;
			Struct foundType = currentFunctionCallArgumentTypes.get(cnt);
			if (!foundType.assignableTo(obj.getType()) || !foundType.compatibleWith(obj.getType())) {
				report_error("Ne preklapa se tip ocekivanog i stvarnog argumenta " + (cnt + 1) + "/" + actualArgCnt,
						designatorStatementCall);
				errorDetected = true;
				return;
			}
			cnt++;
		}

		//report_info("Poziv globalne metode "+method.getName(), designatorStatementCall);
		//getNodeInfo(method);
		
		currentFunctionCallArgumentTypes = new ArrayList<>();
	}

	@Override
	public void visit(CodeGenGetPcEmpty codeGenGetPcEmpty) {
		ownerScopeLookup.put(codeGenGetPcEmpty, Tab.currentScope);
	}

	@Override
	public void visit(VarDeclListSingle varDeclListSingle) {
		SyntaxNode sn = varDeclListSingle.getParent();
		while (sn != null && !(sn instanceof GlobalsElemVar)) {
			sn = sn.getParent();
		}
		if (sn != null) {
			nVars++;
		}
	}

	@Override
	public void visit(VarDeclListMultiple varDeclListMultiple) {
		SyntaxNode sn = varDeclListMultiple.getParent();
		while (sn != null && !(sn instanceof GlobalsElemVar)) {
			sn = sn.getParent();
		}
		if (sn != null) {
			nVars++;
		}
	}

	@Override
	public void visit(CondFactComplex condFactComplex) {

		try {

			// proveriti da li su tipovi za CondFactComplex kompatibilni
			Struct leftType = condFactComplex.getExpr().struct;
			Struct rightType = condFactComplex.getExpr1().struct;
			if (!leftType.compatibleWith(rightType)) {
				report_error("Greska: tipovi u condition-u nisu kompatibilni", condFactComplex);
				errorDetected = true;
				return;
			}
			// proveriti da li je operator != ili == ako su tipa klase ili niza
			if (leftType.isRefType() && rightType.isRefType()) {
				boolean isEqual;
				boolean isUnequal;
				isEqual = (condFactComplex.getRelop() instanceof RelopEquivalent);
				isUnequal = (condFactComplex.getRelop() instanceof RelopNotEqual);

				if (!(isEqual || isUnequal)) {
					report_error("Greska: za ref tipove su dozvoljene samo operacije == i != u condition-u",
							condFactComplex);
					errorDetected = true;
					return;
				}
			}

		} catch (Exception e) {
			report_error("Prosledjivanje greske", condFactComplex);
			errorDetected = true;
			return;
		}

	}

	@Override
	public void visit(CondFactSingle condFactSingle) {
		try {
			// mora da bude boolean tip za expr!
			Struct exprType = condFactSingle.getExpr().struct;
			if (!exprType.equals(SemanticAnalyzer.boolStructNode)) {
				report_error("Greska: izraz u condition-u nije boolean", condFactSingle);
				errorDetected = true;
				return;
			}
		} catch (Exception e) {
			report_error("Prosledjivanje greske", condFactSingle);
			errorDetected = true;
			return;
		}
	}

	@Override
	public void visit(StatementBreak statementBreak)
	{
		//provera da li je unutar do while petlje i ako jeste dohvatanje petlje
		SyntaxNode p = statementBreak.getParent();
		while(p != null && !(p instanceof StatementDoWhile))
		{
			p = p.getParent();
		}
		if (p==null)
		{
			report_error("Greska: break se mora nalaziti unutar do while petlje", statementBreak);
			errorDetected=true;
			return;
		}
		
	}
	
	@Override
	public void visit(StatementContinue statementContinue)
	{
		//provera da li je unutar do while petlje i ako jeste dohvatanje petlje
		SyntaxNode p = statementContinue.getParent();
		while(p != null && !(p instanceof StatementDoWhile))
		{
			p = p.getParent();
		}
		if (p==null)
		{
			report_error("Greska: break se mora nalaziti unutar do while petlje", statementContinue);
			errorDetected=true;
			return;
		}
		
	}
	
	public boolean passed() {
		return !errorDetected;
	}

}
