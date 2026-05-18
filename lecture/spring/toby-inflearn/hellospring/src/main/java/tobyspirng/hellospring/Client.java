package tobyspirng.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspirng.hellospring.payment.Payment;
import tobyspirng.hellospring.payment.PaymentService;

public class Client {

	public static void main(String[] args) throws IOException, InterruptedException {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
		PaymentService paymentService = beanFactory.getBean(PaymentService.class);

		Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
		System.out.println("payment1: "+ payment1);
	}

}
