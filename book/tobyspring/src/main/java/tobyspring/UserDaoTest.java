package tobyspring;

import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import tobyspring.user.dao.DaoFactory;
import tobyspring.user.dao.IndependentUserDao;
import tobyspring.user.dao.SimpleConnectionMaker;
import tobyspring.user.dao.UserDao;
import tobyspring.user.domain.User;

@SpringBootApplication
public class UserDaoTest {
	//
	// public static void main(String[] args) {
	// 	SpringApplication.run(
	// 		TobyspringApplication.class,
	// 		args
	// 	);
	// }

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		IndependentUserDao userDao = new DaoFactory().userDao();
		User user = new User();
		user.setId("wsadffdip");
		user.setName("김도현");
		user.setPassword("123456");

		userDao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = userDao.get(user.getId());
		System.out.println("user2 = " + user2.getName());
	}
}
