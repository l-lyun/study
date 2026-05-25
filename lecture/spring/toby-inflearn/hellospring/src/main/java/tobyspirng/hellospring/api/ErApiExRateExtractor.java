package tobyspirng.hellospring.api;

import java.math.BigDecimal;

import tobyspirng.hellospring.exrate.ExRateData;
import tools.jackson.databind.ObjectMapper;

public class ErApiExRateExtractor implements ExRateExtractor {
	@Override
	public BigDecimal extractExRate(String response) {
			ObjectMapper mapper = new ObjectMapper();
			ExRateData data = mapper.readValue(response, ExRateData.class);
			return data.rates().get("KRW");
	}
}
