// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class GlobalsListEmpty extends GlobalsList {

    public GlobalsListEmpty () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalsListEmpty(\n");

        buffer.append(tab);
        buffer.append(") [GlobalsListEmpty]");
        return buffer.toString();
    }
}
