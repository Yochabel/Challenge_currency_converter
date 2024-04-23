package com.aluracursos.CurrencyConverter.models;

public class Money {
    private double amount;
    private String currency;

    //Constructor
    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    //Getters
    public double getAmount() {
        return amount;
    }
    public String getCurrency() {
        return currency;
    }

    //toString
    @Override
    public String toString() {
        return String.format("%.2f %s", this.amount, this.currency);
    }
}
