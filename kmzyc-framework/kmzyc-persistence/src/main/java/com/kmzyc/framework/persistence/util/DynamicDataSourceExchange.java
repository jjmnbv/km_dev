package com.kmzyc.framework.persistence.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;


/**
 * 拦截@annotation(dataSource)
 * <p>
 * 注意：order =1 , 配置事务拦截的order 必须大于 1，切换数据源一定要比事务更先执行
 *
 * @author KM
 */
//spring context:component-scan 默认不扫描@Aspect标签，增加@component来支持扫描
@Component
@Aspect
public class DynamicDataSourceExchange implements Ordered {

    @Around("@annotation(dataSource)")
    public Object proceed(ProceedingJoinPoint point, DataSource dataSource) throws Throwable {
        String oldDataSource = DBContextHolder.getDBType();
        try {
            DBContextHolder.setDBType(dataSource.value());
            Object result = point.proceed();
            //DBContextHolder.clearDBType();
            DBContextHolder.setDBType(oldDataSource);
            return result;
        } finally {
            DBContextHolder.setDBType(oldDataSource);
            //DBContextHolder.clearDBType();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}