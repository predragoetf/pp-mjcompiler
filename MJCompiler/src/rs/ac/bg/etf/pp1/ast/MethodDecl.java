// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodReturnType MethodReturnType;
    private String mName;
    private SemAnMethodDeclOpenScope SemAnMethodDeclOpenScope;
    private MethodFormPars MethodFormPars;
    private MethodLocalVars MethodLocalVars;
    private CodeGenGetPc CodeGenGetPc;
    private MethodStatementList MethodStatementList;

    public MethodDecl (MethodReturnType MethodReturnType, String mName, SemAnMethodDeclOpenScope SemAnMethodDeclOpenScope, MethodFormPars MethodFormPars, MethodLocalVars MethodLocalVars, CodeGenGetPc CodeGenGetPc, MethodStatementList MethodStatementList) {
        this.MethodReturnType=MethodReturnType;
        if(MethodReturnType!=null) MethodReturnType.setParent(this);
        this.mName=mName;
        this.SemAnMethodDeclOpenScope=SemAnMethodDeclOpenScope;
        if(SemAnMethodDeclOpenScope!=null) SemAnMethodDeclOpenScope.setParent(this);
        this.MethodFormPars=MethodFormPars;
        if(MethodFormPars!=null) MethodFormPars.setParent(this);
        this.MethodLocalVars=MethodLocalVars;
        if(MethodLocalVars!=null) MethodLocalVars.setParent(this);
        this.CodeGenGetPc=CodeGenGetPc;
        if(CodeGenGetPc!=null) CodeGenGetPc.setParent(this);
        this.MethodStatementList=MethodStatementList;
        if(MethodStatementList!=null) MethodStatementList.setParent(this);
    }

    public MethodReturnType getMethodReturnType() {
        return MethodReturnType;
    }

    public void setMethodReturnType(MethodReturnType MethodReturnType) {
        this.MethodReturnType=MethodReturnType;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName=mName;
    }

    public SemAnMethodDeclOpenScope getSemAnMethodDeclOpenScope() {
        return SemAnMethodDeclOpenScope;
    }

    public void setSemAnMethodDeclOpenScope(SemAnMethodDeclOpenScope SemAnMethodDeclOpenScope) {
        this.SemAnMethodDeclOpenScope=SemAnMethodDeclOpenScope;
    }

    public MethodFormPars getMethodFormPars() {
        return MethodFormPars;
    }

    public void setMethodFormPars(MethodFormPars MethodFormPars) {
        this.MethodFormPars=MethodFormPars;
    }

    public MethodLocalVars getMethodLocalVars() {
        return MethodLocalVars;
    }

    public void setMethodLocalVars(MethodLocalVars MethodLocalVars) {
        this.MethodLocalVars=MethodLocalVars;
    }

    public CodeGenGetPc getCodeGenGetPc() {
        return CodeGenGetPc;
    }

    public void setCodeGenGetPc(CodeGenGetPc CodeGenGetPc) {
        this.CodeGenGetPc=CodeGenGetPc;
    }

    public MethodStatementList getMethodStatementList() {
        return MethodStatementList;
    }

    public void setMethodStatementList(MethodStatementList MethodStatementList) {
        this.MethodStatementList=MethodStatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodReturnType!=null) MethodReturnType.accept(visitor);
        if(SemAnMethodDeclOpenScope!=null) SemAnMethodDeclOpenScope.accept(visitor);
        if(MethodFormPars!=null) MethodFormPars.accept(visitor);
        if(MethodLocalVars!=null) MethodLocalVars.accept(visitor);
        if(CodeGenGetPc!=null) CodeGenGetPc.accept(visitor);
        if(MethodStatementList!=null) MethodStatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodReturnType!=null) MethodReturnType.traverseTopDown(visitor);
        if(SemAnMethodDeclOpenScope!=null) SemAnMethodDeclOpenScope.traverseTopDown(visitor);
        if(MethodFormPars!=null) MethodFormPars.traverseTopDown(visitor);
        if(MethodLocalVars!=null) MethodLocalVars.traverseTopDown(visitor);
        if(CodeGenGetPc!=null) CodeGenGetPc.traverseTopDown(visitor);
        if(MethodStatementList!=null) MethodStatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodReturnType!=null) MethodReturnType.traverseBottomUp(visitor);
        if(SemAnMethodDeclOpenScope!=null) SemAnMethodDeclOpenScope.traverseBottomUp(visitor);
        if(MethodFormPars!=null) MethodFormPars.traverseBottomUp(visitor);
        if(MethodLocalVars!=null) MethodLocalVars.traverseBottomUp(visitor);
        if(CodeGenGetPc!=null) CodeGenGetPc.traverseBottomUp(visitor);
        if(MethodStatementList!=null) MethodStatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodReturnType!=null)
            buffer.append(MethodReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+mName);
        buffer.append("\n");

        if(SemAnMethodDeclOpenScope!=null)
            buffer.append(SemAnMethodDeclOpenScope.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodFormPars!=null)
            buffer.append(MethodFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodLocalVars!=null)
            buffer.append(MethodLocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenGetPc!=null)
            buffer.append(CodeGenGetPc.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodStatementList!=null)
            buffer.append(MethodStatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
