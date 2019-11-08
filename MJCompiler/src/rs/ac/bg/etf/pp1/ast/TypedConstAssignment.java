// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class TypedConstAssignment implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private NonTypedConstAssignment NonTypedConstAssignment;

    public TypedConstAssignment (Type Type, NonTypedConstAssignment NonTypedConstAssignment) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.NonTypedConstAssignment=NonTypedConstAssignment;
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public NonTypedConstAssignment getNonTypedConstAssignment() {
        return NonTypedConstAssignment;
    }

    public void setNonTypedConstAssignment(NonTypedConstAssignment NonTypedConstAssignment) {
        this.NonTypedConstAssignment=NonTypedConstAssignment;
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
        if(Type!=null) Type.accept(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(NonTypedConstAssignment!=null) NonTypedConstAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypedConstAssignment(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NonTypedConstAssignment!=null)
            buffer.append(NonTypedConstAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypedConstAssignment]");
        return buffer.toString();
    }
}
