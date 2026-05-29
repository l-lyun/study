package tobyspirng.hellospring.api;

import java.math.BigDecimal;

public interface ExRateExtractor {
	BigDecimal extractExRate(String response);
}
