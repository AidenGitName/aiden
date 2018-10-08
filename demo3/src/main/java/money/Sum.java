package money;

public class Sum implements Expression {
//    Money augend;
//    Money addend;
    // Expression화
    Expression augend;
    Expression addend;

    //생성자 생성
//    Sum(Money augend, Money addend){
    // 생성자도 Expession을 가질수 있다
    Sum(Expression augend, Expression addend){

        this.augend = augend;
        this.addend = addend;
    }
    @Override
    public Money reduce(Bank bank, String to){
//        int amount = augend.amount + addend.amount;
        // 환율정보를 담아서 계산
        int amount = augend.reduce(bank, to).amount +addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    // times 구현
    @Override
    public Expression times(int multiplier) {
        return new Sum(augend.times(multiplier), addend.times(multiplier));
    }
}
