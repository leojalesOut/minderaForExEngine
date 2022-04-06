package com.exchange;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.exchange.model.Pair;
import com.exchange.model.QuoteOutdatedException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

/**
 * @author Devskiller
 */
public class ForexEngineTest {

	private ForexEngine forexEngine = new ForexEngine();

	@Test
	public void shouldSetRate() throws Exception {
		// when
		BigDecimal update = forexEngine.update(new Pair("USD", "JPY"), BigDecimal.valueOf(109.82), ZonedDateTime.now());

		// then
		assertThat(update).isEqualTo(BigDecimal.ZERO);
	}

	@Test
	public void shouldUpdateRate() throws Exception {
		ZonedDateTime firstDate = ZonedDateTime.of(2017, 6, 2, 20, 21, 21, 0, ZoneId.systemDefault());
		ZonedDateTime newerDate = firstDate.plusMinutes(1);

		// when
		forexEngine.update(new Pair("USD", "JPY"), BigDecimal.valueOf(109.82), firstDate);
		BigDecimal update = forexEngine.update(new Pair("USD", "JPY"), BigDecimal.valueOf(109.83), newerDate);

		// then
		assertThat(update).isEqualTo(BigDecimal.valueOf(0.01));
	}

	@Test
	public void shouldNotUpdateOutdatedRate() throws Exception {
		// given
		ForexEngine forexEngine = new ForexEngine();
		ZonedDateTime firstDate = ZonedDateTime.of(2017, 6, 2, 20, 21, 21, 0, ZoneId.systemDefault());
		ZonedDateTime earlierDate = firstDate.minusMinutes(1);

		// when
		forexEngine.update(new Pair("USD", "JPY"), BigDecimal.valueOf(109.82), firstDate);
		Throwable thrown = catchThrowable(() -> forexEngine.update(new Pair("USD", "JPY"), BigDecimal.valueOf(109.83), earlierDate));

		// then
		assertThat(thrown).isInstanceOf(QuoteOutdatedException.class);
	}
}