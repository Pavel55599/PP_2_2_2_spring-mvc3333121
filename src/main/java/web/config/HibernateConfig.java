package web.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement (proxyTargetClass = true)
@ComponentScan(basePackages = {"web.model"}) // Укажите правильные пакеты
public class HibernateConfig {

    @Autowired
    private Environment env;
   
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(getDataSource());
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty("db.entity.package"));
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(getHibernateProperties());
        return entityManagerFactoryBean;
    }


    @Bean
    public DataSource getDataSource() { //логика подключения к источнику данных
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));

        // Настройки пула соединений
        dataSource.setInitialSize(Integer.parseInt(env.getRequiredProperty("db.initialSize")));
        dataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("db.minIdle")));
        dataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("db.maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("db.testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }
}