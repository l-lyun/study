package tobyspirng.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;

import tobyspirng.hellospring.data.JpaOrderRepository;
import tobyspirng.hellospring.order.OrderRepository;
import tobyspirng.hellospring.order.OrderService;

@Configuration
// OrderConfig를 로딩할 때 DataConfig에 있는 모든 빈 설정까지 가져올 수 있음
@Import(DataConfig.class)
public class OrderConfig {

	@Bean
	public OrderRepository orderRepository() {
		return new JpaOrderRepository();
	}

	@Bean
	public OrderService orderService(JpaTransactionManager transactionManager) {
		return new OrderService(orderRepository(), transactionManager);
	}

}
