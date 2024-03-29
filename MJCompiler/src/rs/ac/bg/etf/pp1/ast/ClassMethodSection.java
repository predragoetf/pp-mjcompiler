// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ClassMethodSection implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ClassMethodList ClassMethodList;

    public ClassMethodSection (ClassMethodList ClassMethodList) {
        this.ClassMethodList=ClassMethodList;
        if(ClassMethodList!=null) ClassMethodList.setParent(this);
    }

    public ClassMethodList getClassMethodList() {
        return ClassMethodList;
    }

    public void setClassMethodList(ClassMethodList ClassMethodList) {
        this.ClassMethodList=ClassMethodList;
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
        if(ClassMethodList!=null) ClassMethodList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassMethodList!=null) ClassMethodList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassMethodList!=null) ClassMethodList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassMethodSection(\n");

        if(ClassMethodList!=null)
            buffer.append(ClassMethodList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassMethodSection]");
        return buffer.toString();
    }
}
