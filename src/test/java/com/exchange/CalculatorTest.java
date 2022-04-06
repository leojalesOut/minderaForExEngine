package com.exchange;

import java.math.BigDecimal;
import java.util.Currency;

import com.exchange.model.Money;
import com.exchange.model.Pair;
import com.exchange.model.RateUnavailableException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;

/**
 * @author Devskiller
 */
public class CalculatorTest {
	private ForexEngine forexEngine = Mockito.mock(ForexEngine.class);
	private Calculator calculator = new Calculator(forexEngine);

	@Test
	public void shouldExchangeWithDirectRate() throws Exception {
		// given
		Mockito.when(forexEngine.getExchangeRate(new Pair("USD", "JPY"))).thenReturn(BigDecimal.TEN);

		// when
		Money jpy = calculator.exchange(new Money(BigDecimal.valueOf(20), Currency.getInstance("USD")), Currency.getInstance("JPY"));

		// then
		assertThat(jpy.getAmount()).isCloseTo(BigDecimal.valueOf(200), withinPercentage(1));
		assertThat(jpy.getCurrency()).isEqualTo(Currency.getInstance("JPY"));
	}

	@Test
	public void shouldExchangeWithInvertedRate() throws Exception {
		// given
		Mockito.when(forexEngine.getExchangeRate(new Pair("JPY", "USD"))).thenThrow(new RateUnavailableException());
		Mockito.when(forexEngine.getExchangeRate(new Pair("USD", "JPY"))).thenReturn(BigDecimal.TEN);

		// when
		Money usd = calculator.exchange(new Money(BigDecimal.valueOf(200), Currency.getInstance("JPY")), Currency.getInstance("USD"));

		// then
		assertThat(usd.getAmount()).isCloseTo(BigDecimal.valueOf(20), withinPercentage(1));
		assertThat(usd.getCurrency()).isEqualTo(Currency.getInstance("USD"));
	}

}