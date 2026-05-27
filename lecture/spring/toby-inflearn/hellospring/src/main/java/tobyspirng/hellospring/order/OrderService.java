package tobyspirng.hellospring.order;

import java.math.BigDecimal;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final PlatformTransactionManager transactionManager;

	public OrderService(OrderRepository orderRepository, PlatformTransactionManager transactionManager) {
		this.orderRepository = orderRepository;
		this.transactionManager = transactionManager;
	}

	public Order createOrder(String no, BigDecimal total) {

		Order order = new Order(no, total);
		// 템플릿의 조건으로 JPA 트랜잭션 매니저를 넣고 트랜잭션이라는 워크플로우 안에서 코드 실행
		return new TransactionTemplate(transactionManager).execute(status -> {
			this.orderRepository.save(order);
			return order;
		});
	}
}
