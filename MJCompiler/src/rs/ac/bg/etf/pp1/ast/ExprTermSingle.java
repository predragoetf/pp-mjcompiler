// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ExprTermSingle extends Expr {

    private ExprTerm ExprTerm;

    public ExprTermSingle (ExprTerm ExprTerm) {
        this.ExprTerm=ExprTerm;
        if(ExprTerm!=null) ExprTerm.setParent(this);
    }

    public ExprTerm getExprTerm() {
        return ExprTerm;
    }

    public void setExprTerm(ExprTerm ExprTerm) {
        this.ExprTerm=ExprTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprTerm!=null) ExprTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprTerm!=null) ExprTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprTerm!=null) ExprTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprTermSingle(\n");

        if(ExprTerm!=null)
            buffer.append(ExprTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExprTermSingle]");
        return buffer.toString();
    }
}
