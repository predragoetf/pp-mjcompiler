// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclListSingle extends ConstDeclList {

    private TypedConstAssignment TypedConstAssignment;

    public ConstDeclListSingle (TypedConstAssignment TypedConstAssignment) {
        this.TypedConstAssignment=TypedConstAssignment;
        if(TypedConstAssignment!=null) TypedConstAssignment.setParent(this);
    }

    public TypedConstAssignment getTypedConstAssignment() {
        return TypedConstAssignment;
    }

    public void setTypedConstAssignment(TypedConstAssignment TypedConstAssignment) {
        this.TypedConstAssignment=TypedConstAssignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypedConstAssignment!=null) TypedConstAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypedConstAssignment!=null) TypedConstAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypedConstAssignment!=null) TypedConstAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclListSingle(\n");

        if(TypedConstAssignment!=null)
            buffer.append(TypedConstAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclListSingle]");
        return buffer.toString();
    }
}
