package com.exchange.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Devskiller
 */
public class Bid {

	private final BigDecimal exchangeRate;
	private final ZonedDateTime quoteTime;

	public Bid(BigDecimal exchangeRate, ZonedDateTime quoteTime) {
		this.exchangeRate = exchangeRate;
		this.quoteTime = quoteTime;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public ZonedDateTime getQuoteTime() {
		return quoteTime;
	}
}
