package tobyspirng.hellospring;

import java.math.BigDecimal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tobyspirng.hellospring.exrate.CachedExRateProvider;
import tobyspirng.hellospring.exrate.WebApiExRateProvider;
import tobyspirng.hellospring.payment.ExRateProvider;
import tobyspirng.hellospring.payment.ExRateProviderStub;
import tobyspirng.hellospring.payment.PaymentService;

@Configuration
public class TestObjectFactory {

	@Bean
	public PaymentService paymentService() {
		return new PaymentService(cachedExRateProvider());
	}

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new ExRateProviderStub(BigDecimal.valueOf(1_000));
	}
}
