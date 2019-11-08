// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class MethodLocalVarsExistent extends MethodLocalVars {

    private MethodLocalVars MethodLocalVars;
    private VarDecl VarDecl;

    public MethodLocalVarsExistent (MethodLocalVars MethodLocalVars, VarDecl VarDecl) {
        this.MethodLocalVars=MethodLocalVars;
        if(MethodLocalVars!=null) MethodLocalVars.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public MethodLocalVars getMethodLocalVars() {
        return MethodLocalVars;
    }

    public void setMethodLocalVars(MethodLocalVars MethodLocalVars) {
        this.MethodLocalVars=MethodLocalVars;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodLocalVars!=null) MethodLocalVars.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodLocalVars!=null) MethodLocalVars.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodLocalVars!=null) MethodLocalVars.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodLocalVarsExistent(\n");

        if(MethodLocalVars!=null)
            buffer.append(MethodLocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodLocalVarsExistent]");
        return buffer.toString();
    }
}
