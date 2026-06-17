package tobyspring;

import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import tobyspring.user.dao.CountingConnectionMaker;
import tobyspring.user.dao.DaoFactory;
import tobyspring.user.dao.IndependentUserDao;
import tobyspring.user.dao.SimpleConnectionMaker;
import tobyspring.user.dao.UserDao;
import tobyspring.user.domain.User;

@SpringBootApplication
public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		// 아직까지는 기존에 DaoFactory가 더 깔끔한 것 같음
		// 기능적으로도 차이가 없다.
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		IndependentUserDao userDao = context.getBean("userDao", IndependentUserDao.class);

		User user = new User();
		user.setId("saddip");
		user.setName("김도현");
		user.setPassword("123456");

		userDao.add(user);

		System.out.println(user.getId() + " 등록 성공");

		User user2 = userDao.get(user.getId());
		if(!user.getName().equals(user2.getName())) {
			System.out.println("테스트 실패 (name)");
		}
		else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 (password)");
		}
		else {
			System.out.println("조회 테스트 성공");
		}




	}
}
