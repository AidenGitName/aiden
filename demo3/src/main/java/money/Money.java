package money;

// 추상메소드 사용을 위한 추상메소드 선언
//  abstract class  Money{
// 콘크리트 클래스로 재설정
// class Money {
// Expresion implement
class Money implements Expression {
    protected int amount;
    protected String currency;
    // 같은 생성자 상위클래스에서 선언
    Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    // Dollar 객체와 Franc 의 객체비교
    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
//        return amount == money.amount;
        // amount와 클래스가 같은지 비교
//        return amount == money.amount && getClass().equals(money.getClass());
        // 클래스가 아니라 currency 비교
        return amount == money.amount && currency == money.currency;
    }
    // Dollar를 반환하는 메소드 생성
    static Money dollar(int amount){
        // currency 정보 추가
//        return new Doller(amount,"USD");
        // Dollar 참조 제거
        return new Money(amount,"USD");
    }
    // Franc를 반환하는 메소드 생성
    static Money franc(int amount){
        return new Money(amount,"CHF");
    }
    // Money의 times 추상메소드로 생성(자식객체에서 정의해야함)
//    abstract Money times(int multiplier);
    // Money에서 times 정의
//    Money times(int multiplier) {
    // times도 Expression이 될수있다.
    @Override
    public Expression times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    // currency 도입을 위해 부모 클래스에서 선언
    // 상속받은 클래스의 메소드 선언이 같이졌으므로 Money에서 메소드 정의
//    abstract String currency();
    String currency(){
        return currency;
    }

    // 보다 정확한 메세지를 보기위해 toString 정의
     @Override
     public String toString() {
         return amount + "  "+ currency;
     }

     // plus 정의
//    Money plus(Money addend) {
    // plus는 Expression을 리턴해야한다.
    @Override
    public Expression plus(Expression addend) {
        // amount에 addend을 더해서 리턴
//        return new Money(amount + addend.amount, currency);
        // Sum을 리턴
        return new Sum(this,addend);
    }

    // Expression 인터페이스에 추가하기 위한 메소드

//    public Money reduce(String to){
    // 공용이여야하기떄문에 Bank 매개변수 추가
    @Override
    public Money reduce(Bank bank,String to){
        // 환율정보 bank가 해야할일.
//        int rate = (currency.equals("CHF") && to.equals("USD"))? 2 : 1;
        // bank에서 환율 가져오기
        int rate = bank.rate(currency,to);
        return new Money(amount / rate, to);
    }
}

