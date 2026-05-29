package tobyspirng.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public Order createOrder(String no, BigDecimal total) {

		Order order = new Order(
			no,
			total
		);
		// 템플릿의 조건으로 JPA 트랜잭션 매니저를 넣고 트랜잭션이라는 워크플로우 안에서 코드 실행
		// return new TransactionTemplate(transactionManager).execute(status -> {
		this.orderRepository.save(order);
		return order;
		// });
	}

	@Override
	public List<Order> createOrders(List<OrderReq> reqs) {
		return  reqs.stream()
			.map(req -> createOrder(req.no(), req.total()))
			.toList();
	}
}
