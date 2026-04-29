package tobyspirng.hellospring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import tools.jackson.databind.ObjectMapper;

public abstract class PaymentService {

	public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
		BigDecimal exRate = getExRate(currency);
		BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
		LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

		return new Payment(orderId, currency,	foreignCurrencyAmount, exRate, convertedAmount, validUntil);
	}

	abstract BigDecimal getExRate(String currency) throws IOException;
}
