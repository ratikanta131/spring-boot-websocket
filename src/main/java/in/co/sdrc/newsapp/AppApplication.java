package in.co.sdrc.newsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import in.co.sdrc.newsapp.config.RabbitMQConfig;

@SpringBootApplication
@ComponentScan(basePackages = { "in.co.sdrc.app", "org.sdrc.usermgmt.core"})
@EntityScan(basePackages = "in.co.sdrc.app.domain")
@EnableJpaRepositories(basePackages = { "in.co.sdrc.app.repository" })
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration(exclude= {MongoAutoConfiguration.class,MongoRepositoriesAutoConfiguration.class,MongoDataAutoConfiguration.class})
@EnableBinding(RabbitMQConfig.class)
public class AppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppApplication.class);
	}

}
