// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ClassExtendOption ClassExtendOption;
    private ClassLocalVars ClassLocalVars;
    private ClassMethodSection ClassMethodSection;

    public ClassDecl (String I1, ClassExtendOption ClassExtendOption, ClassLocalVars ClassLocalVars, ClassMethodSection ClassMethodSection) {
        this.I1=I1;
        this.ClassExtendOption=ClassExtendOption;
        if(ClassExtendOption!=null) ClassExtendOption.setParent(this);
        this.ClassLocalVars=ClassLocalVars;
        if(ClassLocalVars!=null) ClassLocalVars.setParent(this);
        this.ClassMethodSection=ClassMethodSection;
        if(ClassMethodSection!=null) ClassMethodSection.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ClassExtendOption getClassExtendOption() {
        return ClassExtendOption;
    }

    public void setClassExtendOption(ClassExtendOption ClassExtendOption) {
        this.ClassExtendOption=ClassExtendOption;
    }

    public ClassLocalVars getClassLocalVars() {
        return ClassLocalVars;
    }

    public void setClassLocalVars(ClassLocalVars ClassLocalVars) {
        this.ClassLocalVars=ClassLocalVars;
    }

    public ClassMethodSection getClassMethodSection() {
        return ClassMethodSection;
    }

    public void setClassMethodSection(ClassMethodSection ClassMethodSection) {
        this.ClassMethodSection=ClassMethodSection;
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
        if(ClassExtendOption!=null) ClassExtendOption.accept(visitor);
        if(ClassLocalVars!=null) ClassLocalVars.accept(visitor);
        if(ClassMethodSection!=null) ClassMethodSection.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassExtendOption!=null) ClassExtendOption.traverseTopDown(visitor);
        if(ClassLocalVars!=null) ClassLocalVars.traverseTopDown(visitor);
        if(ClassMethodSection!=null) ClassMethodSection.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassExtendOption!=null) ClassExtendOption.traverseBottomUp(visitor);
        if(ClassLocalVars!=null) ClassLocalVars.traverseBottomUp(visitor);
        if(ClassMethodSection!=null) ClassMethodSection.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ClassExtendOption!=null)
            buffer.append(ClassExtendOption.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassLocalVars!=null)
            buffer.append(ClassLocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethodSection!=null)
            buffer.append(ClassMethodSection.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
