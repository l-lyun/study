package tobyspirng.hellospring.data;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.JdbcClient;

import jakarta.annotation.PostConstruct;
import tobyspirng.hellospring.order.Order;
import tobyspirng.hellospring.order.OrderRepository;

public class JdbcOrderRepository implements OrderRepository {

	private final JdbcClient jdbcClient;

	public JdbcOrderRepository(DataSource dataSource) {
		this.jdbcClient = JdbcClient.create(dataSource);
	}

	// jpa를 쓸 때 편리한 옵션은 간단한 옵션을 통해 엔티티 메타정보를 읽어
	// 그걸 거꾸로 DB 테이블을 생성해준다
	// 스프링 부트 사용하면 초기화 작업할 때 만들지만
	// 여기서는 간단히 JdbcOrderRepository 빈이 만들어지고
	// JdbcClient가 준비가 되면 여기서 테이블 만들어도 괜찮을 것 같다

	// 생성자가 실행이 다 되고 빈의 초기화 작업까지 끝나면 컨테이너 자동 실행
	@PostConstruct
	void initDb() {
		jdbcClient.sql("""
		create table orders (
      id bigint not null,
      no varchar(255),
      total numeric(38,2),
      primary key (id)
  ); 
  
  alter table if exists orders
  drop constraint if exists UK43egxxciqr9ncgmxbdx2avi8n;
  
  alter table if exists orders
  add constraint UK43egxxciqr9ncgmxbdx2avi8n unique (no);
  
  create sequence orders_SEQ
  start with 1
  increment by 50;
""").update();
	}

	@Override
	public void save(Order order) {

		Long id = jdbcClient.sql("select next value for orders_SEQ")
			.query(Long.class)
			.single();

		order.setId(id);
		jdbcClient.sql("insert into orders (id, no, total) values (?, ?, ?)")
			.params(order.getId(), order.getNo(), order.getTotal())
			.update();


	}
}
