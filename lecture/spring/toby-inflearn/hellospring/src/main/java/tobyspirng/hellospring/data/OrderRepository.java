package tobyspirng.hellospring.data;

import java.math.BigDecimal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import tobyspirng.hellospring.order.Order;

public class OrderRepository {

	private final EntityManagerFactory emf;

	public OrderRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void save(Order order) {
		// em
		EntityManager em = emf.createEntityManager();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		// em.persist <- 메서드 작업이 끝나고, 요청이 끝나고 시간이 지나면 영속화 해달라
		// 스프링부트 JPA에서 많이 쓰던 save 내부에서 persist 호출
		try {
			em.persist(order);
			em.flush();
			transaction.commit();
		}
		catch (RuntimeException e) {
			if (transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			if (em.isOpen())
				em.close();
		}
	}
}
