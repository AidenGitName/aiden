package com.example.demo3.leaningTest;



public class Money implements Expression {
    protected int amount;
    protected String currency;

    protected Money (int amount, String currency){
        this.amount = amount;
        this.currency = currency;

    }

    public Money() {
    }

    static  Money dollar(int amount){
        return new Money(amount, "USD");
    }
    static  Money franc(int amount){
        return new Money(amount, "CHF");
    }
    @Override
    public Expression times(int multiplier){
         return new Money(amount * multiplier, currency);
     }

    String currency() {
        return currency;
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount
                && currency().equals(money.currency);
    }

    @Override
    public String toString() {
        return amount + "   " + currency;
    }

    @Override
     public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }
    @Override
    public Money reduce(Bank bank,String to){
//        int rate = (currency.equals("CHF") && to.equals("USD"))? 2 : 1 ;
        int rate = bank.rate(currency,to);
        return new Money(amount/rate, to);
    }
}
