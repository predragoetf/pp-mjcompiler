// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class ConditionComplex extends Condition {

    private Condition Condition;
    private CodeGenFixupConditionComplex CodeGenFixupConditionComplex;
    private CondTerm CondTerm;

    public ConditionComplex (Condition Condition, CodeGenFixupConditionComplex CodeGenFixupConditionComplex, CondTerm CondTerm) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CodeGenFixupConditionComplex=CodeGenFixupConditionComplex;
        if(CodeGenFixupConditionComplex!=null) CodeGenFixupConditionComplex.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CodeGenFixupConditionComplex getCodeGenFixupConditionComplex() {
        return CodeGenFixupConditionComplex;
    }

    public void setCodeGenFixupConditionComplex(CodeGenFixupConditionComplex CodeGenFixupConditionComplex) {
        this.CodeGenFixupConditionComplex=CodeGenFixupConditionComplex;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CodeGenFixupConditionComplex!=null) CodeGenFixupConditionComplex.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CodeGenFixupConditionComplex!=null) CodeGenFixupConditionComplex.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CodeGenFixupConditionComplex!=null) CodeGenFixupConditionComplex.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionComplex(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CodeGenFixupConditionComplex!=null)
            buffer.append(CodeGenFixupConditionComplex.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionComplex]");
        return buffer.toString();
    }
}
