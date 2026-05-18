package tobyspirng.hellospring.payment;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tobyspirng.hellospring.TestPaymentConfig;

@ExtendWith(SpringExtension.class)
// 이 테스트가 실행될 때 클래스의 구성정보를 읽어 스프링의 구성정보를 읽어 컨테이너 생성
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {

	// 내가 갖고 있는 오브젝트 중에 이 타입을 가져와서 대체
	// 현재 테스트에서 만들어진 컨테이너를 BeanFactory 타입으로 주입 받았는데,
	// 이럴 바에 컨테이너에서 직접 꺼내서 PaymentService를 받으면 되지 않나?
	// @Autowired
	// BeanFactory beanFactory;

	@Autowired
	PaymentService paymentService;
	// 테스트용으로 사용할 때는 인터페이스가 아니라 클래스 직접 호출해도 좋다
	@Autowired
	ExRateProviderStub exRateProviderStub;
	@Autowired
	Clock clock;


	@Test
	void convertedAmount() throws IOException {
		// PaymentService paymentService = beanFactory.getBean(PaymentService.class);

		// exRate: 1000
		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1_000));
		assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

		// exRate: 500
		exRateProviderStub.setExRate(valueOf(500));
		Payment payment2= paymentService.prepare(1L, "USD", BigDecimal.TEN);

		assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(1_000));
		assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

	}

	@Test
	void validUntil() throws IOException {

		Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

		// valid until이 prepare() 30분 뒤로 설정됐는가?
		LocalDateTime now = LocalDateTime.now(this.clock);
		LocalDateTime expectedValidUntil = now.plusMinutes(30);

		Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

	}

	//
	// private static Payment testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
	//
	// }

}