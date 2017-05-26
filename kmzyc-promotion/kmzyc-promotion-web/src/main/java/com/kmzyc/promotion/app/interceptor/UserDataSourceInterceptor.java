package com.kmzyc.promotion.app.interceptor;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.km.framework.persistence.util.DBContextHolder;
import com.kmzyc.promotion.app.enums.DBType;

@Component
@Aspect
public class UserDataSourceInterceptor {

    @SuppressWarnings("unused")
    @Pointcut("execution(* com.kmzyc.promotion.app.dao.impl.*.*(..))")
    private void anyDao() {// 定义一个切入点
    }

    @Before("anyDao()")
    public void doAccessCheck(JoinPoint joinPoint) {
        DBType type = getDBType(joinPoint);
        DBContextHolder.setDBType(type.name());
    }

    /**
     * 获取数据源
     * 
     * @param joinPoint @return DBType @throws
     */
    private DBType getDBType(JoinPoint joinPoint) {
        DBType type = getDBTypeByMethodDBAnno(joinPoint);
        if (null == type) {
            type = getDBTypeByClassDBAnno(joinPoint);
        }
        if (null == type) { // 默认查询产品数据库
            type = DBType.productDataSource;
        }
        return type;
    }

    /**
     * 尝试通过方法名上DBAnno注解获取数据源
     * 
     * @param joinPoint @return DBType @throws
     */
    private DBType getDBTypeByMethodDBAnno(JoinPoint joinPoint) {
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        String methodName = joinPoint.getSignature().getName();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                DBAnno anno = m.getAnnotation(DBAnno.class);
                if (null != anno) {
                    return anno.value();
                }
            }
        }
        return null;
    }

    /**
     * 尝试通过类名上DBAnno注解获取数据源
     * 
     * @param joinPoint @return DBType @throws
     */
    private DBType getDBTypeByClassDBAnno(JoinPoint joinPoint) {
        DBAnno anno = joinPoint.getTarget().getClass().getAnnotation(DBAnno.class);
        if (null != anno) {
            return anno.value();
        }
        return null;
    }

}
