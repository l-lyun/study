package tobyspring;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tobyspring.user.dao.UserDao;
import tobyspring.user.domain.User;

@SpringBootApplication
public class TobyspringApplication {
	//
	// public static void main(String[] args) {
	// 	SpringApplication.run(
	// 		TobyspringApplication.class,
	// 		args
	// 	);
	// }

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao userDao = new UserDao();
		User user = new User();
		user.setId("whiteship");
		user.setName("김도현");
		user.setPassword("123456");

		userDao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = userDao.get(user.getId());
		System.out.println("user2 = " + user2.getName());
	}
}
