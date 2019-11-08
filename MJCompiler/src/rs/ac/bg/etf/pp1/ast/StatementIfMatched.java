// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class StatementIfMatched extends Statement {

    private Condition Condition;
    private CodeGenJmpFixup CodeGenJmpFixup;
    private Statement Statement;
    private CodeGenFixupStatementIfMatched CodeGenFixupStatementIfMatched;
    private Statement Statement1;

    public StatementIfMatched (Condition Condition, CodeGenJmpFixup CodeGenJmpFixup, Statement Statement, CodeGenFixupStatementIfMatched CodeGenFixupStatementIfMatched, Statement Statement1) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CodeGenJmpFixup=CodeGenJmpFixup;
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.CodeGenFixupStatementIfMatched=CodeGenFixupStatementIfMatched;
        if(CodeGenFixupStatementIfMatched!=null) CodeGenFixupStatementIfMatched.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
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

    public CodeGenFixupStatementIfMatched getCodeGenFixupStatementIfMatched() {
        return CodeGenFixupStatementIfMatched;
    }

    public void setCodeGenFixupStatementIfMatched(CodeGenFixupStatementIfMatched CodeGenFixupStatementIfMatched) {
        this.CodeGenFixupStatementIfMatched=CodeGenFixupStatementIfMatched;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(CodeGenFixupStatementIfMatched!=null) CodeGenFixupStatementIfMatched.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(CodeGenFixupStatementIfMatched!=null) CodeGenFixupStatementIfMatched.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CodeGenJmpFixup!=null) CodeGenJmpFixup.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(CodeGenFixupStatementIfMatched!=null) CodeGenFixupStatementIfMatched.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementIfMatched(\n");

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

        if(CodeGenFixupStatementIfMatched!=null)
            buffer.append(CodeGenFixupStatementIfMatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementIfMatched]");
        return buffer.toString();
    }
}
