package tobyspirng.hellospring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

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
}
