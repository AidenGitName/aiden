package money;

public interface Expression {
//    Money reduce(String to);
    // Bank 매개변수 추가
    Money reduce(Bank bank, String to);

    // plus 선언
    Expression plus(Expression tenFrancs);

    Expression times(int multiplier);
}
