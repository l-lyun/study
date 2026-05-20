package tobyspirng.hellospring.api;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.json.JsonParseException;

public class ApiTemplate {
	// 템플릿: 내부 기능 실행
	public BigDecimal getExRate(String url, ApiExecutor apiExecutor, ExRateExtractor exRateExtractor) {
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
