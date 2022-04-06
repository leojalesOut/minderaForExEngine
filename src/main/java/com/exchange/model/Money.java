package com.exchange.model;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Devskiller
 */
public class Money {

	private final BigDecimal amount;
	private final Currency currency;

	public Money(BigDecimal amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}
}
