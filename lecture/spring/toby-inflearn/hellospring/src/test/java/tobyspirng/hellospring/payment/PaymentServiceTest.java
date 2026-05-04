package tobyspirng.hellospring.payment;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tobyspirng.hellospring.exrate.WebApiExRateProvider;

class PaymentServiceTest {

	@Test
	void prepare() throws IOException {
		PaymentService paymentService = new PaymentService(new WebApiExRateProvider());

		// prepare 메서드가 IOException을 던지는데 캐치하거나 throw가 없다
		// 테스트 메서드에서 throws 걸어서 Exception 던지면 괜찮을까?
		// 테스트가 실패하는 경우 1. 예외가 던져짐 2. 검증 메서드를 통과하지 못했을 때
		Payment payment = paymentService.prepare(
			1L,
			"USD",
			BigDecimal.TEN
		);

		// 환율정보 가져온다
		assertThat(payment.getExRate()).isNotNull();

		// 원화환산금액 계산
		assertThat(payment.getConvertedAmount())
			.isEqualTo(payment.getExRate()
			.multiply(payment.getForeignCurrencyAmount()));

		// 원화환산금액의 유효시간 계산
		assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
		assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now().plusMinutes(29));
	}

}