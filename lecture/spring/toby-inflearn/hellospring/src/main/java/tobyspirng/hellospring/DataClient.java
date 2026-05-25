package tobyspirng.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import tobyspirng.hellospring.order.Order;

public class DataClient {

	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
		EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);

		// em
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();


		// em.persist <- 메서드 작업이 끝나고, 요청이 끝나고 시간이 지나면 영속화 해달라
		Order order = new Order(
			"100",
			BigDecimal.TEN
		);

		// 스프링부트 JPA에서 많이 쓰던 save 내부에서 persist 호출
		em.persist(order);

		System.out.println(order);

		em.getTransaction().commit();
		em.close();
	}

}
