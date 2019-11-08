// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class FormParsList extends FormPars {

    private FormPars FormPars;
    private TypedDeclaration TypedDeclaration;

    public FormParsList (FormPars FormPars, TypedDeclaration TypedDeclaration) {
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.TypedDeclaration=TypedDeclaration;
        if(TypedDeclaration!=null) TypedDeclaration.setParent(this);
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
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
        if(FormPars!=null) FormPars.accept(visitor);
        if(TypedDeclaration!=null) TypedDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(TypedDeclaration!=null) TypedDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(TypedDeclaration!=null) TypedDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsList(\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypedDeclaration!=null)
            buffer.append(TypedDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsList]");
        return buffer.toString();
    }
}
