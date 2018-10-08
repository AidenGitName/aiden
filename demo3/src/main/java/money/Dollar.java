package money;

// Dollar 클래스가 필요 없어졌다.
//public class Dollar extends Money {
public class Dollar {
    //1. Dolloar가 10이 되도록 세팅.
//    int amount = 10;
    // amount 가 5*2 =10이 반환되도록 세팅.
//    int amount = 5*2;
    // amount를 값을 입력받아 저장할수 있도록 변경
    // Money를 상속받아서 Money의 amount 사용
//    private int amount;

    // 달러 생성자의 초기값 설정
//    public Dollar(int amount) {

    // 생성자 제거
    // currncy를 매개변수로 설정
//    Dollar(int amount,String currency) {
//        // 두 생성자가 같아졌으므로 상위클래스로 올리기
////        this.amount = amount;
////        this.currency = currency;
//        super(amount,currency);
//    }

    // 곱셈 매개변수를 받아 곱셈 연산을 할수 있는 메소드 생성.
//    int times(int multiplier) {
    // Doller를 반환하는 메소드로 변경
//    Dollar times(int multiplier) {

    // Money 를 반환하는 메소드로 변경
    // 두 클래스의 메소드가 같아졌다 상위 클래스로 이동
//    @Override
//    Money times(int multiplier) {
//        // 5=2 = 10 을 리턴
//        // return 5*2;
//        // amount에 mulitplier을 연산하여 결과 리턴
////        return this.amount*=multiplier;
//        // 연산후 Dollar.amount를 재설정하여 리턴
////        return Money.dollar(amount * multiplier);
//        // 인라인 해제, currency넘기기
////        return new Dollar(this.amount*multiplier,currency);
//        // 리턴 생성자를 Money로 변경
//        return new Money(this.amount * multiplier, currency);
//    }

    // franc와 dollar의 형태가 같아졌으므로 부모클래스에서 공통으로 사용
//    @Override
//    public boolean equals(Object obj) {
//        // amount 가 같으면 같은 객체로 지정
//        // Dollar dollar = (Dollar) obj
//        // 부모객체 비교로 변경
//        Money money = (Money) obj;
//        return amount == money.amount;
//    }
    // Dollar와 Franc의 메소드가 같아졌다. 상위클래스로 올리기
//    @Override
//    String currency() {
//        return currency;
//    }
}
