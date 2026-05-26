package tobyspirng.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import tobyspirng.hellospring.data.OrderRepository;
import tobyspirng.hellospring.order.Order;
import tobyspirng.hellospring.order.OrderService;

public class OrderClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
		OrderService service = beanFactory.getBean(OrderService.class);

		Order order = service.createOrder(
			"0100",
			BigDecimal.TEN
		);
		System.out.println(order);

	}

}
