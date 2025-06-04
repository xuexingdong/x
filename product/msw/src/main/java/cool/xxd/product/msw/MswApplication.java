package cool.xxd.product.msw;

import com.tencent.devops.leaf.plugin.annotation.EnableLeafServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableLeafServer
@SpringBootApplication
@MapperScan(basePackages = {"cool.xxd.product.msw.**.mapper"})
@ComponentScan(basePackages = {"cool.xxd.infra", "cool.xxd.service", "cool.xxd.product.msw"})
public class MswApplication {
    public static void main(String[] args) {
        SpringApplication.run(MswApplication.class, args);
    }
}