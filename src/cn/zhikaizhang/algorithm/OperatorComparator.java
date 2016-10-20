package cn.zhikaizhang.algorithm;

public class OperatorComparator {

    public static char compareOperator(Unit u1, Unit u2) throws ExpressionIllegalException{

        if(!(u1.isOperator() && u2.isOperator())){
            throw new ExpressionIllegalException();
        }
        if(u1.isNormalOperator() && u2.isNormalOperator()){
            if(u1.getType() == Unit.Type.POWER && u2.getType() == Unit.Type.NEGATIVE){
                return '<';
            }
            return u1.getPriority()>=u2.getPriority()?'>':'<';
        }else if(u2.getType() == Unit.Type.LEFT_BRACKET){
            return '<';
        }else if(u1.getType() == Unit.Type.LEFT_BRACKET){
            if(u2.isNormalOperator()){
                return '<';
            }else if(u2.getType() == Unit.Type.RIGHT_BRACKET){
                return '=';
            }
        }else if(u1.getType() == Unit.Type.START_STOP_SIGN){
            if(u2.isNormalOperator()){
                return '<';
            }else if(u2.getType() == Unit.Type.START_STOP_SIGN){
                return '=';
            }
        }else if(u1.isNormalOperator()){
            return '>';
        }
        return 'e';
    }
}
