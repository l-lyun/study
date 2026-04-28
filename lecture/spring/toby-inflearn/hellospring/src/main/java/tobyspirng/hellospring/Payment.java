package tobyspirng.hellospring;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
	private Long orderId;
	private String currency;

	// 기존 Double, Float -> 부동소수점 기반 계산은 오차 발생
	private BigDecimal foreignCurrencyAmount;
	private BigDecimal exRate;
	private BigDecimal convertedAmount;

	private LocalDateTime validUntil;

	public Payment(Long orderId, String currency, BigDecimal foreignCurrencyAmount, BigDecimal exRate,
		BigDecimal convertedAmount, LocalDateTime validUntil) {
		this.orderId = orderId;
		this.foreignCurrencyAmount = foreignCurrencyAmount;
		this.currency = currency;
		this.exRate = exRate;
		this.convertedAmount = convertedAmount;
		this.validUntil = validUntil;
	}

	public Long getOrderId() {
		return orderId;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public BigDecimal getExRate() {
		return exRate;
	}

	public BigDecimal getConvertedAmount() {
		return convertedAmount;
	}

	public LocalDateTime getValidUntil() {
		return validUntil;
	}

	@Override
	public String toString() {
		return "Payment{" +
			"orderId=" + orderId +
			", currency='" + currency + '\'' +
			", foreignCurrencyAmount=" + foreignCurrencyAmount +
			", exRate=" + exRate +
			", convertedAmount=" + convertedAmount +
			", validUntil=" + validUntil +
			'}';
	}
}
