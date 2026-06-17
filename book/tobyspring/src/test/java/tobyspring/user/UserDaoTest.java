package tobyspring.user;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspring.user.dao.DaoFactory;
import tobyspring.user.dao.IndependentUserDao;
import tobyspring.user.domain.User;

public class UserDaoTest {
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		IndependentUserDao userDao = context.getBean("userDao", IndependentUserDao.class);

		User user = new User();
		user.setId("asdf");
		user.setName("김도현");
		user.setPassword("asdf");

		userDao.add(user);
		User user2 = userDao.get(user.getId());

		Assertions.assertThat(user.getId()).isEqualTo(user2.getId());
		Assertions.assertThat(user.getName()).isEqualTo(user2.getName());
		Assertions.assertThat(user.getPassword()).isEqualTo(user2.getPassword());
	}
}
