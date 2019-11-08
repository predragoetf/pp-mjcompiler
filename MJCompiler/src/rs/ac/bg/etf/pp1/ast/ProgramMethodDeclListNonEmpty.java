// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ProgramMethodDeclListNonEmpty extends ProgramMethodDeclList {

    private ProgramMethodDeclList ProgramMethodDeclList;
    private MethodDecl MethodDecl;

    public ProgramMethodDeclListNonEmpty (ProgramMethodDeclList ProgramMethodDeclList, MethodDecl MethodDecl) {
        this.ProgramMethodDeclList=ProgramMethodDeclList;
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public ProgramMethodDeclList getProgramMethodDeclList() {
        return ProgramMethodDeclList;
    }

    public void setProgramMethodDeclList(ProgramMethodDeclList ProgramMethodDeclList) {
        this.ProgramMethodDeclList=ProgramMethodDeclList;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramMethodDeclList!=null) ProgramMethodDeclList.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramMethodDeclListNonEmpty(\n");

        if(ProgramMethodDeclList!=null)
            buffer.append(ProgramMethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramMethodDeclListNonEmpty]");
        return buffer.toString();
    }
}
