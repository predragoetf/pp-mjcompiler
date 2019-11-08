// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListMultiple extends VarDeclList {

    private VarDeclList VarDeclList;
    private NonTypedDeclaration NonTypedDeclaration;

    public VarDeclListMultiple (VarDeclList VarDeclList, NonTypedDeclaration NonTypedDeclaration) {
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.NonTypedDeclaration=NonTypedDeclaration;
        if(NonTypedDeclaration!=null) NonTypedDeclaration.setParent(this);
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public NonTypedDeclaration getNonTypedDeclaration() {
        return NonTypedDeclaration;
    }

    public void setNonTypedDeclaration(NonTypedDeclaration NonTypedDeclaration) {
        this.NonTypedDeclaration=NonTypedDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(NonTypedDeclaration!=null) NonTypedDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(NonTypedDeclaration!=null) NonTypedDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(NonTypedDeclaration!=null) NonTypedDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListMultiple(\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonTypedDeclaration!=null)
            buffer.append(NonTypedDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListMultiple]");
        return buffer.toString();
    }
}
