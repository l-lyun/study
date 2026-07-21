package tobyspring.user.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class DaoFactory {
	@Bean
	public IndependentUserDao userDao() {
		return new IndependentUserDao(jdbcContext(), connectionMaker());
	}

	public IndependentUserDao accountDao() {
		return new IndependentUserDao(jdbcContext(), connectionMaker());
	}

	public IndependentUserDao messageDao() {
		return new IndependentUserDao(jdbcContext(), connectionMaker());
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		// 분리하여 중복을 제거한 ConnectionMaker 타입 오브젝트 생성 코드
		return new CountingConnectionMaker(realConnectionMaker());
	}

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
		dataSource.setUrl("jdbc:mysql://localhost/springbook");
		dataSource.setUsername("spring");
		dataSource.setPassword("book");
		return dataSource;
	}

	@Bean
	public JdbcContext jdbcContext() {
		JdbcContext jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(dataSource());
		return jdbcContext;
	}
	@Bean
	public ConnectionMaker realConnectionMaker() {
		return new CountingConnectionMaker(new SimpleConnectionMaker());
	}
}
