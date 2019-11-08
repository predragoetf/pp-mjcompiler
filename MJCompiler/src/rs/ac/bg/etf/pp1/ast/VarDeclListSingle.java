// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListSingle extends VarDeclList {

    private TypedDeclaration TypedDeclaration;

    public VarDeclListSingle (TypedDeclaration TypedDeclaration) {
        this.TypedDeclaration=TypedDeclaration;
        if(TypedDeclaration!=null) TypedDeclaration.setParent(this);
    }

    public TypedDeclaration getTypedDeclaration() {
        return TypedDeclaration;
    }

    public void setTypedDeclaration(TypedDeclaration TypedDeclaration) {
        this.TypedDeclaration=TypedDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TypedDeclaration!=null) TypedDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypedDeclaration!=null) TypedDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypedDeclaration!=null) TypedDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListSingle(\n");

        if(TypedDeclaration!=null)
            buffer.append(TypedDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListSingle]");
        return buffer.toString();
    }
}
