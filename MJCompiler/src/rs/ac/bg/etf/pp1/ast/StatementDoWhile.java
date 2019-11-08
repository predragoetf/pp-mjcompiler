// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class StatementDoWhile extends Statement {

    private CodeGenFixupAfterDo CodeGenFixupAfterDo;
    private Statement Statement;
    private Condition Condition;
    private CodeGenJmpFixup CodeGenJmpFixup;
    private CodeGenFixupAfterWhile CodeGenFixupAfterWhile;

    public StatementDoWhile (CodeGenFixupAfterDo CodeGenFixupAfterDo, Statement Statement, Condition Condition, CodeGenJmpFixup CodeGenJmpFixup, CodeGenFixupAfterWhile CodeGenFixupAfterWhile) {
        this.CodeGenFixupAfterDo=CodeGenFixupAfterDo;
        if(CodeGenFixupAfterDo!=null) CodeGenFixupAfterDo.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CodeGenJmpFixup=CodeGenJmpFixup;
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.setParent(this);
        this.CodeGenFixupAfterWhile=CodeGenFixupAfterWhile;
        if(CodeGenFixupAfterWhile!=null) CodeGenFixupAfterWhile.setParent(this);
    }

    public CodeGenFixupAfterDo getCodeGenFixupAfterDo() {
        return CodeGenFixupAfterDo;
    }

    public void setCodeGenFixupAfterDo(CodeGenFixupAfterDo CodeGenFixupAfterDo) {
        this.CodeGenFixupAfterDo=CodeGenFixupAfterDo;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CodeGenJmpFixup getCodeGenJmpFixup() {
        return CodeGenJmpFixup;
    }

    public void setCodeGenJmpFixup(CodeGenJmpFixup CodeGenJmpFixup) {
        this.CodeGenJmpFixup=CodeGenJmpFixup;
    }

    public CodeGenFixupAfterWhile getCodeGenFixupAfterWhile() {
        return CodeGenFixupAfterWhile;
    }

    public void setCodeGenFixupAfterWhile(CodeGenFixupAfterWhile CodeGenFixupAfterWhile) {
        this.CodeGenFixupAfterWhile=CodeGenFixupAfterWhile;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CodeGenFixupAfterDo!=null) CodeGenFixupAfterDo.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.accept(visitor);
        if(CodeGenFixupAfterWhile!=null) CodeGenFixupAfterWhile.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CodeGenFixupAfterDo!=null) CodeGenFixupAfterDo.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseTopDown(visitor);
        if(CodeGenFixupAfterWhile!=null) CodeGenFixupAfterWhile.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CodeGenFixupAfterDo!=null) CodeGenFixupAfterDo.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseBottomUp(visitor);
        if(CodeGenFixupAfterWhile!=null) CodeGenFixupAfterWhile.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDoWhile(\n");

        if(CodeGenFixupAfterDo!=null)
            buffer.append(CodeGenFixupAfterDo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenJmpFixup!=null)
            buffer.append(CodeGenJmpFixup.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenFixupAfterWhile!=null)
            buffer.append(CodeGenFixupAfterWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDoWhile]");
        return buffer.toString();
    }
}
