// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class StatementReturn extends Statement {

    private StatementReturnValue StatementReturnValue;

    public StatementReturn (StatementReturnValue StatementReturnValue) {
        this.StatementReturnValue=StatementReturnValue;
        if(StatementReturnValue!=null) StatementReturnValue.setParent(this);
    }

    public StatementReturnValue getStatementReturnValue() {
        return StatementReturnValue;
    }

    public void setStatementReturnValue(StatementReturnValue StatementReturnValue) {
        this.StatementReturnValue=StatementReturnValue;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementReturnValue!=null) StatementReturnValue.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementReturnValue!=null) StatementReturnValue.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementReturnValue!=null) StatementReturnValue.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementReturn(\n");

        if(StatementReturnValue!=null)
            buffer.append(StatementReturnValue.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementReturn]");
        return buffer.toString();
    }
}
