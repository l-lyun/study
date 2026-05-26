package tobyspirng.hellospring;

import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;
import tobyspirng.hellospring.data.OrderRepository;

public class DataConfig {

	// data source
	@Bean
	public DataSource dataSoutce() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
	}

	// entity manager factory
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSoutce());
		emf.setPackagesToScan("tobyspirng.hellospring");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {{
			setDatabase(Database.H2);
			setGenerateDdl(true);
			setShowSql(true);
		}});

		return emf;
	}

	@Bean
	public BeanPostProcessor pesistenceAnnotationBeanPostProcessor() {
		return new PersistenceAnnotationBeanPostProcessor();
	}

	@Bean
	// 스프링 컨테이너에서 Bean 메서드에 의해 생성되는 EntityManagerFactory를 가져온다
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public OrderRepository orderRepository() {
		return new OrderRepository();
	}
}
