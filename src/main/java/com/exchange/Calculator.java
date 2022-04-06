package com.exchange;

import com.exchange.model.Money;
import com.exchange.model.Pair;
import com.exchange.model.RateUnavailableException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * @author Devskiller
 */
class Calculator {

    private final ForexEngine forexEngine;

    Calculator(ForexEngine forexEngine) {
        this.forexEngine = forexEngine;
    }

    /**
     * Calculates exchanged currency rate.
     *
     * @param amount    amount to convert
     * @param convertTo currency to convert to
     * @return exchanged amount
     */
    Money exchange(Money amount, Currency convertTo) throws RateUnavailableException {
        var exchangedRate = getExchangedRate(amount.getCurrency(), convertTo);
        var newMoneyValue = getAmountOfMoneyByExchangedRate(amount, exchangedRate);

        return new Money(newMoneyValue, convertTo);
    }

    private BigDecimal getAmountOfMoneyByExchangedRate(Money amount, BigDecimal exchangedRate) {
        if (exchangedRate.compareTo(BigDecimal.ZERO) > 0) {
            return amount.getAmount()
                         .multiply(exchangedRate);
        }

        return amount.getAmount()
                     .divide(exchangedRate.multiply(BigDecimal.valueOf(-1L)),
                             RoundingMode.HALF_UP);

    }

    BigDecimal getExchangedRate(Currency convertFrom, Currency convertTo) throws RateUnavailableException {
        try {
            return forexEngine.getExchangeRate(new Pair(convertFrom.getCurrencyCode(),
                    convertTo.getCurrencyCode()));
        } catch (RateUnavailableException e) {
            try {
                return forexEngine.getExchangeRate(new Pair(convertTo.getCurrencyCode(),
                                          convertFrom.getCurrencyCode()))
                                  .multiply(BigDecimal.valueOf(-1L));
            } catch (RateUnavailableException ex) {
                throw new RateUnavailableException();
            }
        }
    }
}
