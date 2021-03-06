package us.lacchain.crossborder.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Adrian Pareja
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EntityScan("us.lacchain.crossborder.management.model")
@EnableJpaRepositories("us.lacchain.crossborder.management.repository")
@ComponentScan(value = {"us.lacchain.crossborder"})
public class ManagementService {
    public static void main(String[] args) {
        SpringApplication.run(ManagementService.class, args);
    }
}