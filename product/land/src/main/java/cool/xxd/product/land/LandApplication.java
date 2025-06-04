package cool.xxd.product.land;

import com.tencent.devops.leaf.plugin.annotation.EnableLeafServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"cool.xxd"})
@EnableAsync
@EnableScheduling
@EnableLeafServer
@SpringBootApplication
public class LandApplication {
    public static void main(String[] args) {
        SpringApplication.run(LandApplication.class, args);
    }
}