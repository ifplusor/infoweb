package psn.ifplusor.core.flyway;

import java.io.IOException;
import java.util.Properties;

import org.flywaydb.core.Flyway;

public class FlywayMigration {
    // 读取数据库配置参数
    private static Properties config = new Properties();
    static {
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("infoweb.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    // 执行数据库版本升级
    public static void migration() {
        // Create the Flyway instance
        Flyway flyway = new Flyway();
            
        // Point it to the database
        flyway.setDataSource(config.getProperty("db.url"), 
                config.getProperty("db.username"), 
                config.getProperty("db.password"));
        
        // Start the migration
        flyway.migrate();
    }
}
