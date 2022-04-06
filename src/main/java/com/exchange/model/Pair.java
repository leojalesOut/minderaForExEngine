package com.exchange.model;

import java.util.Currency;
import java.util.Objects;

/**
 * @author Devskiller
 */
public class Pair {

	private final Currency from;
	private final Currency to;

	public Pair(String from, String to) {
		this.from = Currency.getInstance(from);
		this.to = Currency.getInstance(to);
	}

	public Pair(Currency from, Currency to) {
		this.from = from;
		this.to = to;
	}

	public Currency getFrom() {
		return from;
	}

	public Currency getTo() {
		return to;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pair pair = (Pair) o;
		return Objects.equals(from, pair.from) &&
				Objects.equals(to, pair.to);
	}

	@Override
	public int hashCode() {
		return Objects.hash(from, to);
	}
}
