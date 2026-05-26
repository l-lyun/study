package tobyspirng.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tobyspirng.hellospring.order.Order;

public class OrderRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(Order order) {
		entityManager.persist(order);
	}
}
