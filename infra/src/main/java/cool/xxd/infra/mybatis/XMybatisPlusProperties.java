package cool.xxd.infra.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "x.mybatis-plus")
@Data
public class XMybatisPlusProperties {

    private DbType dbType = DbType.POSTGRE_SQL;

    private IdType idType = IdType.INPUT;

    private String tablePrefix = "x_";
    
    private String logicDeleteField = "deleted";
    
    private String logicDeleteValue = "true";
    
    private String logicNotDeleteValue = "false";
    
}