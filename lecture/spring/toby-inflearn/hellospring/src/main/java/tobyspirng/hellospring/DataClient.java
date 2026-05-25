package tobyspirng.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspirng.hellospring.data.OrderRepository;
import tobyspirng.hellospring.order.Order;

public class DataClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);

		OrderRepository repository = beanFactory.getBean(OrderRepository.class);
		Order order = new Order("100", BigDecimal.TEN);
		repository.save(order);

		System.out.println(order);

		Order order2 = new Order("100", BigDecimal.ONE);
		repository.save(order2);

	}

}
