// generated with ast extension for cup
// version 0.8
// 6/1/2018 16:33:34


package rs.ac.bg.etf.pp1.ast;

public class FactorFuncCall extends Factor {

    private Designator Designator;
    private SemAnEraseParamList SemAnEraseParamList;
    private CallActualParams CallActualParams;

    public FactorFuncCall (Designator Designator, SemAnEraseParamList SemAnEraseParamList, CallActualParams CallActualParams) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.SemAnEraseParamList=SemAnEraseParamList;
        if(SemAnEraseParamList!=null) SemAnEraseParamList.setParent(this);
        this.CallActualParams=CallActualParams;
        if(CallActualParams!=null) CallActualParams.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public SemAnEraseParamList getSemAnEraseParamList() {
        return SemAnEraseParamList;
    }

    public void setSemAnEraseParamList(SemAnEraseParamList SemAnEraseParamList) {
        this.SemAnEraseParamList=SemAnEraseParamList;
    }

    public CallActualParams getCallActualParams() {
        return CallActualParams;
    }

    public void setCallActualParams(CallActualParams CallActualParams) {
        this.CallActualParams=CallActualParams;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(SemAnEraseParamList!=null) SemAnEraseParamList.accept(visitor);
        if(CallActualParams!=null) CallActualParams.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(SemAnEraseParamList!=null) SemAnEraseParamList.traverseTopDown(visitor);
        if(CallActualParams!=null) CallActualParams.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(SemAnEraseParamList!=null) SemAnEraseParamList.traverseBottomUp(visitor);
        if(CallActualParams!=null) CallActualParams.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorFuncCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SemAnEraseParamList!=null)
            buffer.append(SemAnEraseParamList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CallActualParams!=null)
            buffer.append(CallActualParams.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorFuncCall]");
        return buffer.toString();
    }
}
