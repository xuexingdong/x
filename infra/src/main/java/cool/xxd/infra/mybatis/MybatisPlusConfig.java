package cool.xxd.infra.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(XMybatisPlusProperties.class)
public class MybatisPlusConfig implements MetaObjectHandler {

    private final XMybatisPlusProperties properties;

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        var interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(properties.getDbType()));
        return interceptor;
    }

//    @Bean
//    public GlobalConfig globalConfig() {
//        var globalConfig = new GlobalConfig();
//        var dbConfig = new GlobalConfig.DbConfig();
//
//        // 设置逻辑删除配置
//        dbConfig.setLogicDeleteField(properties.getLogicDeleteField());
//        dbConfig.setLogicDeleteValue(properties.getLogicDeleteValue());
//        dbConfig.setLogicNotDeleteValue(properties.getLogicNotDeleteValue());
//
//        // 设置ID类型
//        dbConfig.setIdType(properties.getIdType());
//
//        globalConfig.setDbConfig(dbConfig);
//
//        return globalConfig;
//    }

    @Override
    public void insertFill(MetaObject metaObject) {
        var now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        var now = LocalDateTime.now();
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, now);
    }
}
