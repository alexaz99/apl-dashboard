package alex.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * if we need to create a DB from the script
 */
@Configuration
public class DataSourceConfig {

    /*@Bean
    public DataSource dataSource(){
        //jdbc:hsqldb:mem:testdb
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.HSQL)
                .addScript("db/hsqldb/db.sql")
                .build();
        return db;
    }*/

    /**
     * Note
     * If you start the DatabaseManagerSwing via a command prompt or terminal,
     * the Swing HSQL database manager will still be prompted,
     * but it is UNABLE to connect to the embedded database that started by Spring, because both are different JVM.
     */

    //default username : sa, password : ''
/*    @PostConstruct
    public void getDbManager(){
        DatabaseManagerSwing.main(
                new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""});
    }*/
}
