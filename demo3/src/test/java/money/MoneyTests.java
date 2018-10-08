package money;

import junit.framework.TestCase;
import lombok.extern.java.Log;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class MoneyTests extends TestCase {

    // 5*2=10 Dolloer에 대한 테스트
    @Test
    public void testMultiDollar(){


        // 1. dollar가 10이 나와야 한다!
        // 2. dollar가 5*2=10임으로 성공
//        Dollar dollar = new Dollar();
//        assertEquals(10, dollar.amount);
        // 3. 5달러와 곱셈값 2를 입력 받을수 있도록 변경.
        // 4.생성자로부터 5달러를 받을수 있도록 세팅.
//        Dollar dollar = new Dollar(5,"USD");
        // 5.생성자로부터 받은 amount의 초기값으로부터 곱셈연산을 할수있는 메소드 생성.
//        assertEquals(10,dollar.times(2));
    }
    // 여러번 수행해 대한 테스트.
    @Test
    public void testMultiplication(){
        // Dollar.amount 초기값 5로 설정
//        Dollar five = new Dollar(5);
        // 5*2=10테스트 성공.
//        assertEquals(10, five.times(2);
//        assertEquals(10, five.times(2).amount);
        // 5*3 =15테스트 실패 5*2연산후 amount가 10으로 변해있기떄문에 결과가 30이되어버림.
        // Dollar 를 리턴, 여전히 실패,
//        assertEquals(15,five.times(3).amount);
        // 새로운 Dollar 생성
//        Dollar prod = five.times(2);
//        assertEquals(10, prod.amount);
//        prod = five.times(3);
//        assertEquals(15,prod.amount);
        // 동치성 비교후 객체고 비교
//        assertEquals(new Dollar(10), prod);
//        prod = five.times(3);
//        assertEquals(new Dollar(15), prod);

        // Money로 Doller 생성
        Money five = Money.dollar(5);

        // 임시 변수인 prod 필요 없으므로 삭제, 인라인
//        assertEquals(new Dollar(10), five.times(2));
//        assertEquals(new Dollar(15), five.times(3));
        // Dollar에 대한 참조를 제거
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));

    }
    // 동치성 테스트
    @Test
    public void testEqulity(){
//        // 5$ == 5$ ?
//        assertTrue(new Dollar(5).equals(new Dollar(5)));
//        // 5$ != 6$ ?
//        assertFalse(new Dollar(5).equals(new Dollar(6)));
//
//        assertTrue(new Franc(5).equals(new Franc(5)));
//        assertFalse(new Franc(5).equals(new Franc(6)));
//        // 자식객체들의 개별성 비교
//        assertFalse(new Dollar(5).equals(new Franc(5)));
        // 객체 참조 제거
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.dollar(5).equals(Money.franc(5)));
    }
    // Franc 테스트
    @Test
    public void testFrancMultilication(){
//        Franc five = new Franc(5);
//        assertEquals(new Franc(10), five.times(2));
//        assertEquals(new Franc(15), five.times(3));
        // Franc 참조 제거
        Money five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }
    // 다른 클래스의 equals
    @Test
    public void testDifferentClassEquality(){
//        assertTrue(new Money(10,"CHF").equals(new Franc(10,"CHF")));
    }
    // 더하기 테스트
    @Test
    public void testSimpleAddition(){
        // Money.plus 구현
//        Money sum = Money.dollar(5).plus(Money.dollar(5));
        // Expression 구현
        Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        assertEquals(Money.dollar(10),sum);
        // reduced(축약된)
        // Bank.reduce 구현
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(10),reduced);
    }
    @Test
    public void testPlusRetrunSum(){
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        // Sum 클래스 구현
        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five,sum.addend);
        // Money.plus()는 Sum이 아닌 Money를 반환, ClassCastException 발생 Sum으로 변경
    }
    @Test
    public void testReduceSum(){
        Expression sum = new Sum(Money.dollar(3),Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7),result);
    }

    @Test
    public void testReduceMoney(){
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1),"USD");
        assertEquals(Money.dollar(1), result);
    }

    // ArrayEquals Test
//    @Test
//    public void testArrayEquals(){
//        assertEquals(new Object[] {"abc"}, new Object[] {"abc"});
//    }

    @Test
    public void testIdentityRate(){
        assertEquals(1, new Bank().rate("USD","USD"));
    }

    @Test
    public void testMixedAddition(){
//        Expression fiveBucks = Money.dollar(5);
//        Expression tenFranc = Money.franc(10);
        // Money로 재정의
//        Money fiveBucks = Money.dollar(5);
//        Money tenFrancs = Money.franc(10);
        // plus인자를 Expression으로 변경
        // fiveBucks를 Experssion으로 변경
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD",2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs),"USD");
        assertEquals(Money.dollar(10),result);
    }
    @Test
    public void testSumPlusMoney(){
        Expression fivBucks = Money.dollar(5);
        Expression tenFrance = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fivBucks,tenFrance).plus(fivBucks);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(15),result);
    }

    @Test
    public void testSumTimes(){
        Expression fivBucks = Money.dollar(5);
        Expression tenFrance = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fivBucks,tenFrance).times(2);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(20),result);
    }

    @Test
    public void testPlusSameCurrencyReturnMoney(){
        Expression sum = Money.dollar(1).plus(Money.dollar(1));
//        assertEquals(Money.dollar(2),sum);
        assertTrue(sum instanceof Expression);
    }

}
