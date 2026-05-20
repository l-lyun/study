package tobyspirng.hellospring.exrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import org.springframework.boot.json.JsonParseException;

import tobyspirng.hellospring.api.ApiExecutor;
import tobyspirng.hellospring.api.SimpleApiExecutor;
import tobyspirng.hellospring.payment.ExRateProvider;
import tools.jackson.databind.ObjectMapper;


public class WebApiExRateProvider implements ExRateProvider {

	// 클라이언트 -> 콜백 -> 템플릿
	// 클라이언트가 콜백을 만들어서 템플릿을 실행
	@Override
	public BigDecimal getExRate(String currency) {
		String url = "https://open.er-api.com/v6/latest/" + currency;
		// 콜백: new SimpleApiExecutor()
		// 변하는 속성을 가진 코드는 콜백으로 메서드 파라미터 형태로 전달
		return runApiForExRate(url, new SimpleApiExecutor());
	}

	// 템플릿: 내부 기능 실행
	private static BigDecimal runApiForExRate(String url, ApiExecutor apiExecutor) {
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
			return extractExRate(response);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		}
	}

	private static BigDecimal extractExRate(String response) {
		ObjectMapper mapper = new ObjectMapper();
		ExRateData data = mapper.readValue(response, ExRateData.class);
		return data.rates().get("KRW");
	}

}
