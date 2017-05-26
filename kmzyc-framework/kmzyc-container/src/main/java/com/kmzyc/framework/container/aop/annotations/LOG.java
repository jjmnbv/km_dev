package com.kmzyc.framework.container.aop.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kmzyc.framework.container.aop.Logger;


/**
 * 日志标识.
 * 
 * @author weishanyao
 * 
 * @see Logger
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LOG {
    enum POSITION {BEFORE, AFTER, ABEND}
    enum LEVEL {ERROR, WARN, IMPORTANCE, INFO, TRIVIAL}
    
    /**
     * 记录/显示的日志信息模板，格式：string{variable[<quoted-string/int/var>].path.value,type,format}，其中{}之间的内容变量是从上下文环境中提取:
     * 
     * 其中variable可能的值包括:
     *   1. arguments - 表示方法参数, 如:{arguments[1].name}表示从第二个调用参数中提取name属性.
     *   2. returnValue - 表示方法返回值.
     *   3. exception - 表示方法异常消息(Exception.getMessage).
     *   4. field - 表示方法所在对象的属性, 如:{field.path.value}或{field["path"].value}或{field['path'].value}.
     *   5. request.param - 表示请求参数(request.getParameter, WEB环境).
     *   6. request.params - 表示请求参数(数组，request.getParameterValues, WEB环境).
     *   7. request.attri - 表示请求对象的属性(request.getAttribute, WEB环境).
     *   8. request.uri - 表示请求对象的URI(request.getRequestURI, WEB环境).
     *   9. session - 表示保存于会话上下文中的属性(session.getAttribute, WEB环境).
     *   0. property - 表示保存于容器上下文中的属性(uossContext.property, 通过配置文件读取).
     * 基于上述要求,日志处理器({@link Logger})必须能够访问当前线程的request对象.
     * 中括号包括引号字符串,整数或变量.
     * 
     * type的可能值包括(可选):
     *   1. string(默认) - 字符串.
     *   2. number - 数值.
     *   3. date - 日期.
     *   4. time - 时间.
     *   5. choice - 选择范围.
     *   
     * format 表示格式化输出数据型或日期/时间类型.
     * 
     * 参见{@link java.text.MessageFormat}对象.
     * 
     */
    String value() default "";
    
    /** 在方法执行的时间位置，默认为在方法执行后记录 */
    POSITION[] position() default POSITION.AFTER;
    
    /** 日志级别 */
    LEVEL level() default LEVEL.IMPORTANCE;
}
