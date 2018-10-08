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
//        Money five = Money.dollar(5);
        Money five = Money.moneyCurrency(5,"USD");
//        assertEquals(Money.dollar(10), five.times(2));
//        assertEquals(Money.dollar(15), five.times(3));
        assertEquals(Money.moneyCurrency(10,"USD"), five.times(2));
        assertEquals(Money.moneyCurrency(15,"USD"), five.times(3));


    }
    @Test
    public void testFrancMulit(){
//        Money five = Money.dollar(5);
        Money five = Money.moneyCurrency(5,"CHF");
//        assertEquals(Money.dollar(10), five.times(2));
//        assertEquals(Money.dollar(15), five.times(3));
        assertEquals(Money.moneyCurrency(10,"CHF"), five.times(2));
        assertEquals(Money.moneyCurrency(15,"CHF"), five.times(3));

    }
    @Test
    public void  testEquality() {
//        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
//        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
//        assertFalse(Money.franc(5).equals(Money.dollar(5)));
        assertTrue(Money.moneyCurrency(5,"USD").equals(Money.moneyCurrency(5,"USD")));
        assertFalse(Money.moneyCurrency(5,"USD").equals(Money.moneyCurrency(6,"USD")));
        assertFalse(Money.moneyCurrency(5,"CHF").equals(Money.moneyCurrency(5,"USD")));
    }

    @Test
    public void testCurrency(){
//        assertEquals("USD", Money.dollar(1).currency());
//        assertEquals("CHF", Money.franc(1).currency());
        assertEquals("USD", Money.moneyCurrency(1,"USD").currency());
        assertEquals("CHF", Money.moneyCurrency(1,"CHF").currency());
    }

    @Test
    public void testDifferntClassEquality(){
//        assertTrue(new Money(10,"CHF").equals(Money.franc(10)));
//        assertTrue(new Money(10,"USD").equals(Money.dollar(10)));
        assertTrue(new Money(10,"CHF").equals(Money.moneyCurrency(10,"CHF")));
        assertTrue(new Money(10,"USD").equals(Money.moneyCurrency(10,"CHF")));
    }

    @Test
    public void testSimpleAddition(){
//        Money sum = Money.dollar(5).plus(Money.dollar(5));
//        assertEquals(Money.dollar(10),sum)
//        Bank bank = new Bank();

//        Money reduced = bank.reduce(sum, "USD");
//        assertEquals(Money.dollar(10),reduced);

//        Money five = Money.dollar(5);
        Money five = Money.moneyCurrency(5,"USD");
        Expression result =  five.plus(five);

        Sum sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);

    }
    @Test
    public void testReduceSum(){
//        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Expression sum = new Sum(Money.moneyCurrency(3,"USD"), Money.moneyCurrency(4,"USD"));
        Bank bank = new Bank();
        Money result = bank.reduce(sum,"USD");
        assertEquals(Money.moneyCurrency(7,"USD"), result);
    }

    @Test
    public void testReduceMoney(){
        Bank bank = new Bank();
//        Money result = bank.reduce(Money.dollar(1),"USD");
//        assertEquals(Money.dollar(1), result);
        Money result = bank.reduce(Money.moneyCurrency(1,"USD"),"USD");
        assertEquals(Money.moneyCurrency(1,"USD"), result);
    }
    @Test
    public void testReduceMoneyDiffrentCurrency(){
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
//        Money result = bank.reduce(Money.franc(2),"USD");
//        assertEquals(Money.dollar(1),result);
        Money result = bank.reduce(Money.moneyCurrency(2,"CHF"),"USD");
        assertEquals(Money.moneyCurrency(1,"USD"),result);
    }
    @Test
    public void testIdentityRate(){
        assertEquals(1, new Bank().rate("USD","USD"));
    }

    @Test
    public void testMixedAddition(){
//        Expression fiveBucks = Money.dollar(5);
//        Expression tenFrancs = Money.franc(10);
        Expression fiveBucks = Money.moneyCurrency(5,"USD");
        Expression tenFrancs = Money.moneyCurrency(10,"CHF");
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);

        Money result = bank.reduce(fiveBucks.plus(tenFrancs),"USD");
//        assertEquals(Money.dollar(10),result);
        assertEquals(Money.moneyCurrency(10,"USD"),result);

    }
    @Test
    public void testSumPlusMoney(){
//        Expression fiveBucks = Money.dollar(5);
//        Expression tenFrancs = Money.franc(10);
        Expression fiveBucks = Money.moneyCurrency(5,"USD");
        Expression tenFrancs = Money.moneyCurrency(10,"CHF");
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        Money result = bank.reduce(sum,"USD");
//        assertEquals(Money.dollar(15),result);
        assertEquals(Money.moneyCurrency(15,"USD"),result);
    }
    @Test
    public void testSumTimes() {
//        Expression fiveBucks = Money.dollar(5);
//        Expression tenFranks = Money.franc(10);
        Expression fiveBucks = Money.moneyCurrency(5,"USD");
        Expression tenFranks = Money.moneyCurrency(10,"CHF");
        Bank bank = new Bank();
        bank.addRate("CHF","USD",2);
        Expression sum = new Sum(fiveBucks, tenFranks).times(2);
        Money result = bank.reduce(sum,"USD");
//        assertEquals(Money.dollar(20), result);
        assertEquals(Money.moneyCurrency(20,"USD"), result);
        log.info("Result : "+result.amount+", "+result.currency);
    }

    @Test
    public void testPlusSameCurrencyRetumsMonney() {
//        Expression sum = Money.dollar(1).plus(Money.dollar(1));
        Expression sum = Money.moneyCurrency(1,"USD").plus(Money.moneyCurrency(1,"USD"));
//        assertTrue(sum instanceof Money); Money의 plus가 Money instance를 반환하게 해뒀는데 Expression이 반환됨..
        assertTrue(sum instanceof Expression);

    }
}
