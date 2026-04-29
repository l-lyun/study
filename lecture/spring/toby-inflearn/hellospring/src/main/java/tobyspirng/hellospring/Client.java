package tobyspirng.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public class Client {

	public static void main(String[] args) throws IOException {
		PaymentService paymentService = new PaymentService(new WebApiExRateProvider()); // 관계 설정의 책임을 클라이언트가 가져갔다
		// PaymentService paymentService = new PaymentService(new SimpleExRateProvider());
		Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
		System.out.println(payment);
	}

}
