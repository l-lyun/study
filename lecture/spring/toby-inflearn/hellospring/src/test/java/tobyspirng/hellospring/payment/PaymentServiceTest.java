package tobyspirng.hellospring.payment;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

	Clock clock;

	@BeforeEach
	void beforeEach() {
		this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
	}


	@Test
	void convertedAmount() throws IOException {
		testAmount(valueOf(500), valueOf(5_000), this.clock);
		testAmount(valueOf(1_000), valueOf(10_000), this.clock);
		testAmount(valueOf(3_000), valueOf(30_000), this.clock);
	}

	@Test
	void validUntil() throws IOException {
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1_000)), clock);

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// valid until이 prepare() 30분 뒤로 설정됐는가?
		LocalDateTime now = LocalDateTime.now(this.clock);
		LocalDateTime expectedValidUntil = now.plusMinutes(30);

		Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

	}

	private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
		// PaymentService 입장에서는 어떠한 값이 들어오는지 알 필요가 없다
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

		// prepare 메서드가 IOException을 던지는데 캐치하거나 throw가 없다
		// 테스트 메서드에서 throws 걸어서 Exception 던지면 괜찮을까?
		// 테스트가 실패하는 경우 1. 예외가 던져짐 2. 검증 메서드를 통과하지 못했을 때
		Payment payment = paymentService.prepare(
			1L,
			"USD",
			TEN
		);

		// BigDecimal은 숫자만이 아닌 자릿수까지도 비교한다
		// isEqualTo는 소수점 2자리까지만 같다고 한다
		assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
		assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
		return payment;
	}

}