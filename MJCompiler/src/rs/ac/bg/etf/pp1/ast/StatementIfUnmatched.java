// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class StatementIfUnmatched extends Statement {

    private Condition Condition;
    private CodeGenJmpFixup CodeGenJmpFixup;
    private Statement Statement;
    private CodeGenFixupStatementIfUnmatched CodeGenFixupStatementIfUnmatched;

    public StatementIfUnmatched (Condition Condition, CodeGenJmpFixup CodeGenJmpFixup, Statement Statement, CodeGenFixupStatementIfUnmatched CodeGenFixupStatementIfUnmatched) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CodeGenJmpFixup=CodeGenJmpFixup;
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.CodeGenFixupStatementIfUnmatched=CodeGenFixupStatementIfUnmatched;
        if(CodeGenFixupStatementIfUnmatched!=null) CodeGenFixupStatementIfUnmatched.setParent(this);
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

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public CodeGenFixupStatementIfUnmatched getCodeGenFixupStatementIfUnmatched() {
        return CodeGenFixupStatementIfUnmatched;
    }

    public void setCodeGenFixupStatementIfUnmatched(CodeGenFixupStatementIfUnmatched CodeGenFixupStatementIfUnmatched) {
        this.CodeGenFixupStatementIfUnmatched=CodeGenFixupStatementIfUnmatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(CodeGenFixupStatementIfUnmatched!=null) CodeGenFixupStatementIfUnmatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(CodeGenFixupStatementIfUnmatched!=null) CodeGenFixupStatementIfUnmatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(CodeGenFixupStatementIfUnmatched!=null) CodeGenFixupStatementIfUnmatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementIfUnmatched(\n");

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

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenFixupStatementIfUnmatched!=null)
            buffer.append(CodeGenFixupStatementIfUnmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementIfUnmatched]");
        return buffer.toString();
    }
}
