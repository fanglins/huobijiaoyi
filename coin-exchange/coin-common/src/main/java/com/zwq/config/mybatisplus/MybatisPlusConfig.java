package com.zwq.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Acer
 * @Date 2021/08/21 16:32
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDbType(DbType.MYSQL);
        return paginationInterceptor;

    }
    /**
     * 乐观锁
     * @Version
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor(){
        OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
        return optimisticLockerInterceptor;
    }
    /**
     * 主键序列生成
     * IDE_WORK 数字
     * ID_WORK_STR 字符串
     */
    @Bean
    public IKeyGenerator iKeyGenerator(){
        H2KeyGenerator h2KeyGenerator = new H2KeyGenerator();
        return h2KeyGenerator;
    }
}
