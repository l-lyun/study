package tobyspring.user;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import tobyspring.user.dao.DaoFactory;
import tobyspring.user.dao.IndependentUserDao;
import tobyspring.user.dao.UserDao;
import tobyspring.user.domain.User;

public class UserDaoTest {
	@Test
	public void addAndGet() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		IndependentUserDao userDao = context.getBean("userDao", IndependentUserDao.class);

		User user1 = new User("aa", "김김", "spring1");
		User user2 = new User("bb", "이이", "spring2");

		userDao.deleteAll();
		assertThat(userDao.getCount()).isEqualTo(0);

		userDao.add(user1);
		userDao.add(user2);

		assertThat(userDao.getCount()).isEqualTo(2);

		// id를 활용해 user를 가져오는 테스트 보강
		User userGet1 = userDao.get(user1.getId());
		assertThat(userGet1.getId()).isEqualTo(user1.getId());
		assertThat(userGet1.getName()).isEqualTo(user1.getName());

		User userGet2 = userDao.get(user2.getId());
		assertThat(userGet2.getId()).isEqualTo(user2.getId());
		assertThat(userGet2.getName()).isEqualTo(user2.getName());

		// assertThat(userDao.getCount()).isEqualTo(1);
		// User user2 = userDao.get(user.getId());
		//
		// assertThat(user.getId()).isEqualTo(user2.getId());
		// assertThat(user.getName()).isEqualTo(user2.getName());
		// assertThat(user.getPassword()).isEqualTo(user2.getPassword());
	}

	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		IndependentUserDao userDao = context.getBean("userDao", IndependentUserDao.class);
		User user1 = new User("aa", "김김", "spring1");
		User user2 = new User("bb", "이이", "spring2");
		User user3 = new User("cc", "박박", "spring3");

		userDao.deleteAll();
		assertThat(userDao.getCount()).isEqualTo(0);

		userDao.add(user1);
		assertThat(userDao.getCount()).isEqualTo(1);

		userDao.add(user2);
		assertThat(userDao.getCount()).isEqualTo(2);

		userDao.add(user3);
		assertThat(userDao.getCount()).isEqualTo(3);
	}

	@Test
	public void getUserFailure() throws SQLException, ClassNotFoundException{
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		IndependentUserDao userDao = context.getBean("userDao", IndependentUserDao.class);

		userDao.deleteAll();
		assertThat(userDao.getCount()).isEqualTo(0);

		assertThatThrownBy(() -> userDao.get("unknown"))
			.isInstanceOf(EmptyResultDataAccessException.class);
	}
}
