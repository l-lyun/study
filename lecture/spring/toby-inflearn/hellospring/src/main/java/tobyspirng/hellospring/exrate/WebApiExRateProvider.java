package tobyspirng.hellospring.exrate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.stream.Collectors;

import org.springframework.boot.json.JsonParseException;

import tobyspirng.hellospring.payment.ExRateProvider;
import tools.jackson.databind.ObjectMapper;


public class WebApiExRateProvider implements ExRateProvider {

	@Override
	public BigDecimal getExRate(String currency) {
		String url = "https://open.er-api.com/v6/latest/" + currency;
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// 기존에 발생했던 checked exception을 종류만 바꿔서 런타임 익셉션으로 throw
			throw new RuntimeException(e);
		}
		String response;
		try {
				HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();

					try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

						response = br.lines().collect(Collectors.joining());
					}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			try {
				ObjectMapper mapper = new ObjectMapper();
				ExRateData data = mapper.readValue(response, ExRateData.class);
				return data.rates().get("KRW");

			} catch (JsonParseException e) {
				throw new RuntimeException(e);
			}

	}
}
