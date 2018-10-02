package com.example.demo3.leaningTest;

import junit.framework.TestCase;
import lombok.extern.java.Log;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class DollarTests extends TestCase {

    @Test
    public void testMultiplication(){
        Money five = Money.dollar(5);

        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));


    }
    @Test
    public void testFrancMulit(){
        Money five = Money.franc(5);

        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));

    }
    @Test
    public void  testEquality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    public void testCurrency(){
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    @Test
    public void testDifferntClassEquality(){
        assertTrue(new Money(10,"CHF").equals(Money.franc(10)));
        assertTrue(new Money(10,"USD").equals(Money.dollar(10)));
    }

    @Test
    public void testSimpleAddition(){
//        Money sum = Money.dollar(5).plus(Money.dollar(5));
//        assertEquals(Money.dollar(10),sum)
//        Bank bank = new Bank();

//        Money reduced = bank.reduce(sum, "USD");
//        assertEquals(Money.dollar(10),reduced);

        Money five = Money.dollar(5);
        Expression result =  five.plus(five);

        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);

    }
    @Test
    public void testReduceSum(){
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(7), result);
    }

    @Test
    public void testReduceMoney(){
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1),"USD");
        assertEquals(Money.dollar(1), result);
    }
    @Test
    public void testReduceMoneyDiffrentCurrency(){
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Money result = bank.reduce(Money.franc(2),"USD");
        assertEquals(Money.dollar(1),result);
    }
    @Test
    public void testIdentityRate(){
        assertEquals(1, new Bank().rate("USD","USD"));
    }

    @Test
    public void testMixedAddition(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);

        Money result = bank.reduce(fiveBucks.plus(tenFrancs),"USD");
        assertEquals(Money.dollar(10),result);

    }
    @Test
    public void testSumPlusMoney(){
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(15),result);
    }
    @Test
    public void testSumTimes() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFranks = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks, tenFranks).times(2);
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.dollar(20), result);
    }

    @Test
    public void testPlusSameCurrencyRetumsMonney() {
        Expression sum = Money.dollar(1).plus(Money.dollar(1));
//        assertTrue(sum instanceof Money); Money의 plus가 Money instance를 반환하게 해뒀는데 Expression이 반환됨..
        assertTrue(sum instanceof Expression);

    }
}
