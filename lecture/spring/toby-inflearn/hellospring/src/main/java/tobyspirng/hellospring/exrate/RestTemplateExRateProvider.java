package tobyspirng.hellospring.exrate;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import tobyspirng.hellospring.payment.ExRateProvider;

public class RestTemplateExRateProvider implements ExRateProvider {

	private final RestTemplate restTemplate;

	public RestTemplateExRateProvider(RestTemplate restTemplate) {
		// 스프링 빈으로 등록하고 활용하자
		this.restTemplate = restTemplate;
	}

	@Override
	public BigDecimal getExRate(String currency) {
		String url = "https://open.er-api.com/v6/latest/" + currency;
		return restTemplate.getForObject(url, ExRateData.class).rates().get("KRW");
	}
}
