package tobyspirng.hellospring.data;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tobyspirng.hellospring.order.Order;
import tobyspirng.hellospring.order.OrderRepository;

public class JpaOrderRepository implements OrderRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Order order) {
		entityManager.persist(order);
	}
}
