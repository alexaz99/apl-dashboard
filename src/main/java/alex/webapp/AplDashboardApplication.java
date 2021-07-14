package alex.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * After the application starts spring batch is kicked off automatically and
 * the HSQL table is created. You don't even need to add DB settings to application.properties file.
 * It's in memory HSQL.
 */
@SpringBootApplication
public class AplDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplDashboardApplication.class, args);
	}

}
