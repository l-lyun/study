package tobyspring.user;

import static org.assertj.core.api.Assertions.*;

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

		userDao.deleteAll();
		assertThat(userDao.getCount()).isEqualTo(0);

		User user = new User();
		user.setId("asdf");
		user.setName("김도현");
		user.setPassword("asdf");

		userDao.add(user);
		assertThat(userDao.getCount()).isEqualTo(1);
		User user2 = userDao.get(user.getId());

		assertThat(user.getId()).isEqualTo(user2.getId());
		assertThat(user.getName()).isEqualTo(user2.getName());
		assertThat(user.getPassword()).isEqualTo(user2.getPassword());
	}
}
