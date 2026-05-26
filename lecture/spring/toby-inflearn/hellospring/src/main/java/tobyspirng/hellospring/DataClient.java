package tobyspirng.hellospring;

import java.math.BigDecimal;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import tobyspirng.hellospring.data.OrderRepository;
import tobyspirng.hellospring.order.Order;

public class DataClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);

		OrderRepository repository = beanFactory.getBean(OrderRepository.class);
		JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

		try {

			new TransactionTemplate(transactionManager).execute(status ->  {
				// transaction begin
				Order order = new Order("100", BigDecimal.TEN);
				repository.save(order);
				System.out.println(order);
				Order order2 = new Order("100", BigDecimal.ONE);
				repository.save(order2);
					return null;
			});
		} catch (DataIntegrityViolationException e) {
			System.out.println("주문 번호 중복 복구 작업");
			// 이 외의 예외들은 RuntimeException이니까 밖으로 던져질 것, 이 익셉션에 한해서는 가드가 가능하다
			// 새로운 익셉션으로 캐치해서 밖에 던질 수 있다
			// 이 상황에서는 하이버네이트가 아닌 다른 jpa, jdbc, mybatis 등등 어떠한 기술을 사용하더라도 스프링에서 translation을 제공
		}
	}

}
