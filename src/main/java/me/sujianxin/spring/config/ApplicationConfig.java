package me.sujianxin.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>Created with IDEA
 * <p>Author: sujianxin
 * <p>Date: 2016/1/22
 * <p>Time: 16:25
 * <p>Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableSpringDataWebSupport
//@EnableWebMvc
@ComponentScan(basePackages = {"me.sujianxin.spring.config"})//"me.sujianxin.persistence",
public class ApplicationConfig {
}
