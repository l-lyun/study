package tobyspirng.hellospring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import tobyspirng.hellospring.data.JdbcOrderRepository;
import tobyspirng.hellospring.order.OrderRepository;
import tobyspirng.hellospring.order.OrderService;
import tobyspirng.hellospring.order.OrderServiceImpl;
import tobyspirng.hellospring.order.OrderServiceTxProxy;

@Configuration
// OrderConfig를 로딩할 때 DataConfig에 있는 모든 빈 설정까지 가져올 수 있음
@Import(DataConfig.class)
public class OrderConfig {

	@Bean
	public OrderRepository orderRepository(DataSource dataSource) {
		return new JdbcOrderRepository(dataSource);
	}

	@Bean
	public OrderService orderService(
		PlatformTransactionManager transactionManager,
		OrderRepository orderRepository
		 ) {
		return new OrderServiceTxProxy(
			new OrderServiceImpl(orderRepository),
			transactionManager
		);
	}

}
