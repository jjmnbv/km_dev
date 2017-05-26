package com.kmzyc.framework.persistence.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {

    String value() default CUSTOMER;

    public String CUSTOMER = "dataSourceCustomer";
    public String ORDER = "dataSourceOrder";
    public String PRODUCT = "dataSourceProduct";
    public String SEARCH = "dataSourceSearch";
    public String CMS = "dataSourceCMS";

}