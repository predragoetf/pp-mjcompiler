// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclListMultiple extends ConstDeclList {

    private ConstDeclList ConstDeclList;
    private NonTypedConstAssignment NonTypedConstAssignment;

    public ConstDeclListMultiple (ConstDeclList ConstDeclList, NonTypedConstAssignment NonTypedConstAssignment) {
        this.ConstDeclList=ConstDeclList;
        if(ConstDeclList!=null) ConstDeclList.setParent(this);
        this.NonTypedConstAssignment=NonTypedConstAssignment;
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.setParent(this);
    }

    public ConstDeclList getConstDeclList() {
        return ConstDeclList;
    }

    public void setConstDeclList(ConstDeclList ConstDeclList) {
        this.ConstDeclList=ConstDeclList;
    }

    public NonTypedConstAssignment getNonTypedConstAssignment() {
        return NonTypedConstAssignment;
    }

    public void setNonTypedConstAssignment(NonTypedConstAssignment NonTypedConstAssignment) {
        this.NonTypedConstAssignment=NonTypedConstAssignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.accept(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclList!=null) ConstDeclList.traverseTopDown(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclList!=null) ConstDeclList.traverseBottomUp(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclListMultiple(\n");

        if(ConstDeclList!=null)
            buffer.append(ConstDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonTypedConstAssignment!=null)
            buffer.append(NonTypedConstAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclListMultiple]");
        return buffer.toString();
    }
}
