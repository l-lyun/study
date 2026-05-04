package tobyspirng.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectFactory {

	// 구성 정보 안에는 실제 런타임 환경에서 어떤 빈이 생성되고
	// 어떠한 의존관계가 맺어져있는지 다 들어가있다.
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
		return new WebApiExRateProvider();
	}
}
