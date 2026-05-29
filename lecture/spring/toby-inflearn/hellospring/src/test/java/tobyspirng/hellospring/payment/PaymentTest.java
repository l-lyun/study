package tobyspirng.hellospring.payment;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentTest {

	@Test
	void createPrepared() {
		Clock clock = Clock.fixed(Instant.now(),
			ZoneId.systemDefault()
		);

		Payment payment = Payment.createPrepared(
			1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1_000),
			LocalDateTime.now(clock)
		);

		Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
		Assertions.assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
	}


	// 시간이 정확하게 지났는지 지나지않았는지 Payment 도메인에서 확신을 가지고 빠르게 개발이 가능하다
	// 따라서 도메인 오브젝트 테스트는 필히 권장
	@Test
	void isValid() {
		Clock clock = Clock.fixed(
			Instant.now(),
			ZoneId.systemDefault()
		);

		Payment payment = Payment.createPrepared(
			1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1_000), LocalDateTime.now(clock)
		);

		Assertions.assertThat(payment.isValid(clock)).isTrue();
		Assertions.assertThat(payment.isValid(Clock.offset(clock,
			Duration.of(
				30,
				ChronoUnit.MINUTES
			)
		))).isFalse();
	}
}
