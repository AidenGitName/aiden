package money;

import java.util.Hashtable;

public class Bank {
        Money reduce(Expression source, String to) {
        // 가짜 구현
//        return Money.dollar(10);

        // sum으로 이동
        // Sum을 받아 덧셈 연산후 리턴.
//        Sum sum = (Sum) source;
//        int amount = sum.augend.amount + sum.addend.amount;
//        return  new Money(amount,to);

//        // Money가 넘어왔을경우 리턴
//        if(source instanceof Money) return (Money) source;
//        // Sum이 넘어왔을경우 리턴
//        Sum sum = (Sum) source;
//        return sum.reduce(to);

        // 캐스팅 제거
//        return source.reduce(to);
        return source.reduce(this, to);
    }
    // 환율정보
//    int rate(String from, String to){
//        // CHF-USD 환율정보
//        return (from.equals("CHF") && to.equals("USD"))? 2 : 1;
//    }
    // 환율 저장 테이블
    private Hashtable rates = new Hashtable();
    // 환율 설정
    void addRate(String from, String to, int rate){
        rates.put(new Pair(from,to), new Integer(rate));
    }
    // 환율 얻기
    int rate(String from, String to){
        // 같은 화폐의 덧셈 환율은 1
        if(from.equals(to)) return 1;
        Integer rate = (Integer) rates.get(new Pair(from,to));
        return rate.intValue();
    }
}
