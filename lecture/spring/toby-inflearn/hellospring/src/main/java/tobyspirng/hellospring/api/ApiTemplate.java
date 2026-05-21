package tobyspirng.hellospring.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.json.JsonParseException;

public class ApiTemplate {

	private final ApiExecutor apiExecutor;
	private final ExRateExtractor exRateExtractor;

	public ApiTemplate() {
		this.apiExecutor = new HttpClientApiExecutor();
		this.exRateExtractor = new ErApiExRateExtractor();
	}

	public ApiTemplate(ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
		this.apiExecutor = apiExecutor;
		this.exRateExtractor = exRateExtractor;
	}

	public BigDecimal getForExRate(String url) {
		return this.getForExRate(
			url,
			this.apiExecutor,
			this.exRateExtractor
		);
	}

	public BigDecimal getForExRate(String url, ApiExecutor apiExecutor) {
		return this.getForExRate(
			url,
			apiExecutor,
			this.exRateExtractor
		);
	}

	public BigDecimal getForExRate(String url, ExRateExtractor exRateExtractor) {
		return this.getForExRate(
			url,
			this.apiExecutor,
			exRateExtractor
		);
	}

	// 템플릿: 내부 기능 실행
	public BigDecimal getForExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// 기존에 발생했던 checked exception을 종류만 바꿔서 런타임 익셉션으로 throw
			throw new RuntimeException(e);
		}
		String response;
		try {
			response = apiExecutor.execute(uri);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			return exRateExtractor.extractExRate(response);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		}
	}
}
