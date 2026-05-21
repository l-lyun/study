package tobyspirng.hellospring;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tobyspirng.hellospring.api.ApiTemplate;
import tobyspirng.hellospring.api.ErApiExRateExtractor;
import tobyspirng.hellospring.api.SimpleApiExecutor;
import tobyspirng.hellospring.payment.PaymentService;
import tobyspirng.hellospring.exrate.CachedExRateProvider;
import tobyspirng.hellospring.payment.ExRateProvider;
import tobyspirng.hellospring.exrate.WebApiExRateProvider;

@Configuration
public class PaymentConfig {

	// 구성 정보 안에는 실제 런타임 환경에서 어떤 빈이 생성되고
	// 어떠한 의존관계가 맺어져있는지 다 들어가있다.
	@Bean
	public PaymentService paymentService() {
		return new PaymentService(exRateProvider(), clock());
	}

	@Bean
	public ExRateProvider cachedExRateProvider() {
		return new CachedExRateProvider(exRateProvider());
	}

	// ApiTemplate도 하나의 빈으로 등록
	@Bean
	public ApiTemplate apiTemplate() {
		return new ApiTemplate(new SimpleApiExecutor(), new ErApiExRateExtractor());
	}

	@Bean
	public ExRateProvider exRateProvider() {
		return new WebApiExRateProvider(apiTemplate());
	}

	@Bean
	public Clock clock() {
		return Clock.systemDefaultZone();
	}
}
