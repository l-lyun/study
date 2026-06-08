package tobyspring.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	@Bean
	public IndependentUserDao userDao() {
		return new IndependentUserDao(connectionMaker());
	}

	public IndependentUserDao accountDao() {
		return new IndependentUserDao(connectionMaker());
	}

	public IndependentUserDao messageDao() {
		return new IndependentUserDao(connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		// 분리하여 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
		return new CountingConnectionMaker(realConnectionMaker());
	}

	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new CountingConnectionMaker(new SimpleConnectionMaker());
	}
}
