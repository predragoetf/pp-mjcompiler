// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private SemAnProgramName SemAnProgramName;
    private GlobalsList GlobalsList;
    private ProgramMethodDeclList ProgramMethodDeclList;
    private CodeGenInsertFunction CodeGenInsertFunction;

    public Program (SemAnProgramName SemAnProgramName, GlobalsList GlobalsList, ProgramMethodDeclList ProgramMethodDeclList, CodeGenInsertFunction CodeGenInsertFunction) {
        this.SemAnProgramName=SemAnProgramName;
        if(SemAnProgramName!=null) SemAnProgramName.setParent(this);
        this.GlobalsList=GlobalsList;
        if(GlobalsList!=null) GlobalsList.setParent(this);
        this.ProgramMethodDeclList=ProgramMethodDeclList;
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.setParent(this);
        this.CodeGenInsertFunction=CodeGenInsertFunction;
        if(CodeGenInsertFunction!=null) CodeGenInsertFunction.setParent(this);
    }

    public SemAnProgramName getSemAnProgramName() {
        return SemAnProgramName;
    }

    public void setSemAnProgramName(SemAnProgramName SemAnProgramName) {
        this.SemAnProgramName=SemAnProgramName;
    }

    public GlobalsList getGlobalsList() {
        return GlobalsList;
    }

    public void setGlobalsList(GlobalsList GlobalsList) {
        this.GlobalsList=GlobalsList;
    }

    public ProgramMethodDeclList getProgramMethodDeclList() {
        return ProgramMethodDeclList;
    }

    public void setProgramMethodDeclList(ProgramMethodDeclList ProgramMethodDeclList) {
        this.ProgramMethodDeclList=ProgramMethodDeclList;
    }

    public CodeGenInsertFunction getCodeGenInsertFunction() {
        return CodeGenInsertFunction;
    }

    public void setCodeGenInsertFunction(CodeGenInsertFunction CodeGenInsertFunction) {
        this.CodeGenInsertFunction=CodeGenInsertFunction;
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
        if(SemAnProgramName!=null) SemAnProgramName.accept(visitor);
        if(GlobalsList!=null) GlobalsList.accept(visitor);
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.accept(visitor);
        if(CodeGenInsertFunction!=null) CodeGenInsertFunction.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SemAnProgramName!=null) SemAnProgramName.traverseTopDown(visitor);
        if(GlobalsList!=null) GlobalsList.traverseTopDown(visitor);
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.traverseTopDown(visitor);
        if(CodeGenInsertFunction!=null) CodeGenInsertFunction.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SemAnProgramName!=null) SemAnProgramName.traverseBottomUp(visitor);
        if(GlobalsList!=null) GlobalsList.traverseBottomUp(visitor);
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.traverseBottomUp(visitor);
        if(CodeGenInsertFunction!=null) CodeGenInsertFunction.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(SemAnProgramName!=null)
            buffer.append(SemAnProgramName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalsList!=null)
            buffer.append(GlobalsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramMethodDeclList!=null)
            buffer.append(ProgramMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenInsertFunction!=null)
            buffer.append(CodeGenInsertFunction.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
