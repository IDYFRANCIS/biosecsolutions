package com.francis.biosectest;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.mchange.v2.c3p0.ComboPooledDataSource;



@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class ServletInitializer extends SpringBootServletInitializer {
	
	
	@Value("${spring.datasource.max-size}")
    private int maxSize;

    @Value("${spring.datasource.initial-size}")
    private int initialSize;

    @Value("${spring.datasource.min-size}")
    private int minSize;

    @Value("${spring.datasource.acquireIncrement}")
    private int acquireIncrement;

    @Value("${spring.datasource.idleConnectionTestPeriod}")
    private int idleConnectionTestPeriod;

    @Value("${spring.datasource.max-statements}")
    private int maxStatements;

    @Value("${spring.datasource.max-statements-per-connection}")
    private int maxStatementsPerConnection;

    @Value("${spring.datasource.max-idle}")
    private int maxIdleTime;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.commit-on-close}")
    private boolean commitOnClose;

    @Value("${spring.datasource.checkout-timeout}")
    private int checkoutTimeout;

    @Value("${spring.datasource.acquire-retry-attempts}")
    private int acquireRetryAttempts;

    @Value("${spring.datasource.acquire-retry-delay}")
    private int acquireRetryDelay;

    @Value("${spring.datasource.maximum-connection-age}")
    private int maxConnectionAge;

    @Value("${spring.datasource.maximum-idle-time-excess-connections}")
    private int maxIdleTimeExcessConnections;

    @Value("${spring.datasource.test-connection-on-checkin}")
    private boolean testConnectionOnCheckin;

    @Value("${spring.datasource.test-connection-on-checkout}")
    private boolean testConnectionOnCheckout;
    


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BiosectestApplication.class);
	}
	
	
	@Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        Collection<String> mappings = new ArrayList<String>();
        mappings.add("/*");
        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet());
        registration.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
        registration.setUrlMappings(mappings);
        return registration;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
    
    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setMaxPoolSize(maxSize);
        dataSource.setMinPoolSize(minSize);
        dataSource.setAcquireIncrement(acquireIncrement);
        dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
        dataSource.setMaxStatements(maxStatements);
        dataSource.setMaxStatementsPerConnection(maxStatementsPerConnection);
        dataSource.setMaxIdleTime(maxIdleTime);
        dataSource.setInitialPoolSize(initialSize);
        dataSource.setAutoCommitOnClose(commitOnClose);
        dataSource.setCheckoutTimeout(checkoutTimeout);
        dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
        dataSource.setAcquireRetryDelay(acquireRetryDelay);
        dataSource.setMaxConnectionAge(maxConnectionAge);
        dataSource.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
        dataSource.setTestConnectionOnCheckin(testConnectionOnCheckin);
        dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout);
        dataSource.setJdbcUrl(url);
        dataSource.setPassword(password);
        dataSource.setUser(username);
        dataSource.setDriverClass(driverClassName);
        return dataSource;
    }

}
