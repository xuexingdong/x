package cool.xxd.product.databox;

import com.tencent.devops.leaf.plugin.annotation.EnableLeafServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableLeafServer
@SpringBootApplication
@MapperScan(basePackages = {"cool.xxd.**.mapper"})
@ComponentScan(basePackages = {"cool.xxd.infra", "cool.xxd.service", "cool.xxd.product.databox"})
public class DataBoxApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataBoxApplication.class, args);
    }
}