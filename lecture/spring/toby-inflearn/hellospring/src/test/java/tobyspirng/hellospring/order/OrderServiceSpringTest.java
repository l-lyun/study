package tobyspirng.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tobyspirng.hellospring.OrderConfig;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {

	@Autowired
	OrderService orderService;
	@Autowired
	DataSource dataSource;

	@Test
	void createOrder() {
		Order order = orderService.createOrder(
			"0100",
			BigDecimal.ONE
		);

		Assertions.assertThat(order.getId()).isGreaterThan(0);
	}

	@Test
	void createOrders() {
		List<OrderReq> orderReqs = List.of(
			new OrderReq(
				"200",
				BigDecimal.ONE
			),
			new OrderReq(
				"201",
				BigDecimal.TWO
			)
		);

		List<Order> orders = orderService.createOrders(orderReqs);

		Assertions.assertThat(orders).hasSize(2);
		orders.forEach(
			order -> Assertions.assertThat(order.getId()).isGreaterThan(0)
		);
	}

	@Test
	void createDuplicateOrders() {
		List<OrderReq> orderReqs = List.of(
			new OrderReq(
				"0300",
				BigDecimal.ONE
			),
			new OrderReq(
				"0300",
				BigDecimal.TWO
			)
		);

		Assertions.assertThatThrownBy(() -> orderService.createOrders(orderReqs))
			.isInstanceOf(DataIntegrityViolationException.class);

		JdbcClient client = JdbcClient.create(dataSource);
		Long count = client.sql("select count(*) from orders where no ='0300'").query(Long.class).single();
		Assertions.assertThat(count).isEqualTo(0);
	}
}
