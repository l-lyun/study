package tobyspirng.hellospring.payment;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class PaymentServiceTest {

	@Test
	void convertedAmount() throws IOException {
		testAmount(valueOf(500), valueOf(5_000));
		testAmount(valueOf(1_000), valueOf(10_000));
		testAmount(valueOf(3_000), valueOf(30_000));

		// 원화환산금액의 유효시간 계산
		// assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
		// assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now().plusMinutes(29));
	}

	private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
		// PaymentService 입장에서는 어떠한 값이 들어오는지 알 필요가 없다
		PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate));

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