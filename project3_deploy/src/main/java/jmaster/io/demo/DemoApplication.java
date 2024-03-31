package jmaster.io.demo;

import java.util.Locale;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import jmaster.io.demo.interceptor.LogInterceptor;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableCaching // redis key-value
public class DemoApplication implements WebMvcConfigurer {

	@Autowired
	LogInterceptor logInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(new Locale("vi"));
		return resolver;
	}

	// giong servlet filter
	@Bean
	LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(logInterceptor);
	}
	
	@Bean
	NewTopic userbill() {
		// topic name, partition numbers, replication number
		return new NewTopic("userbill", 1, (short) 1);
	}
	
	@Bean
	NewTopic billScan() {
		// topic name, partition numbers, replication number
		return new NewTopic("billScan", 2, (short) 1);
	}
	
	@Bean
	NewTopic password() {
		// topic name, partition numbers, replication number
		return new NewTopic("password", 3, (short) 1);
	}

}
