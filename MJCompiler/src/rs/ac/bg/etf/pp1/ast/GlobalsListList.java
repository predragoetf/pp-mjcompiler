// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class GlobalsListList extends GlobalsList {

    private GlobalsList GlobalsList;
    private GlobalsElem GlobalsElem;

    public GlobalsListList (GlobalsList GlobalsList, GlobalsElem GlobalsElem) {
        this.GlobalsList=GlobalsList;
        if(GlobalsList!=null) GlobalsList.setParent(this);
        this.GlobalsElem=GlobalsElem;
        if(GlobalsElem!=null) GlobalsElem.setParent(this);
    }

    public GlobalsList getGlobalsList() {
        return GlobalsList;
    }

    public void setGlobalsList(GlobalsList GlobalsList) {
        this.GlobalsList=GlobalsList;
    }

    public GlobalsElem getGlobalsElem() {
        return GlobalsElem;
    }

    public void setGlobalsElem(GlobalsElem GlobalsElem) {
        this.GlobalsElem=GlobalsElem;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(GlobalsList!=null) GlobalsList.accept(visitor);
        if(GlobalsElem!=null) GlobalsElem.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(GlobalsList!=null) GlobalsList.traverseTopDown(visitor);
        if(GlobalsElem!=null) GlobalsElem.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(GlobalsList!=null) GlobalsList.traverseBottomUp(visitor);
        if(GlobalsElem!=null) GlobalsElem.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GlobalsListList(\n");

        if(GlobalsList!=null)
            buffer.append(GlobalsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(GlobalsElem!=null)
            buffer.append(GlobalsElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GlobalsListList]");
        return buffer.toString();
    }
}
