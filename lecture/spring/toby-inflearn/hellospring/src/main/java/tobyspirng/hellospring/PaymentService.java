package tobyspirng.hellospring;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;

public class PaymentService {

	private final ExRateProvider exRateProvider;

	public PaymentService(ExRateProvider exRateProvider) {
		this.exRateProvider = exRateProvider;
	}

	public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {

		// 환율을 가져오는 정책이나 방식이 변경되어도 외부에서 변경 가능
		BigDecimal exRate = exRateProvider.getExRate(currency);
		BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
		LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

		return new Payment(orderId, currency,	foreignCurrencyAmount, exRate, convertedAmount, validUntil);
	}

}
