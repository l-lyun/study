package tobyspirng.hellospring.exrate;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import tobyspirng.hellospring.api.ApiTemplate;
import tobyspirng.hellospring.api.ErApiExRateExtractor;
import tobyspirng.hellospring.api.HttpClientApiExecutor;
import tobyspirng.hellospring.api.SimpleApiExecutor;
import tobyspirng.hellospring.payment.ExRateProvider;

public class WebApiExRateProvider implements ExRateProvider {

	// 여러 사용자가 멀티스레드 환경에서 동시에 사용해도 상태가 없기 때문에
	// 인스턴스가 만들어질 때 재사용하도록 유도
	ApiTemplate apiTemplate = new ApiTemplate();

	// 클라이언트 -> 콜백 -> 템플릿
	// 클라이언트가 콜백을 만들어서 템플릿을 실행
	@Override
	public BigDecimal getExRate(String currency) {

		String url = "https://open.er-api.com/v6/latest/" + currency;
		// 콜백: new SimpleApiExecutor()
		// 변하는 속성을 가진 코드는 콜백으로 메서드 파라미터 형태로 전달
		return apiTemplate.getExRate(url, new HttpClientApiExecutor(), new ErApiExRateExtractor());
	}

}
