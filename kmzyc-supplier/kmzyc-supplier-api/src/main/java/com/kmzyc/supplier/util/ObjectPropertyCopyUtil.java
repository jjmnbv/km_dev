package com.kmzyc.supplier.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectPropertyCopyUtil {

	public static void propertyCopy(Object source,Object target)throws NoSuchMethodException, SecurityException
			, IllegalAccessException, IllegalArgumentException, InvocationTargetException,Exception{
		Class sourceClass = source.getClass();
		Class targetClass = target.getClass();
		
		Field[] sourceFields = sourceClass.getDeclaredFields();
		Field[] targetFields = targetClass.getDeclaredFields();
		
		String name = null;
		String methodName = null;
		String targetName = null;
		for(Field sourcefield : sourceFields){
			name = sourcefield.getName();//属性名
			Class type = sourcefield.getType();//属性类型
			
			methodName = name.substring(0,1).toUpperCase()+name.substring(1);
			
			if("SerialVersionUID".equals(methodName)) continue;
			
			Method getMethod = sourceClass.getMethod("get"+methodName);
			Object value = getMethod.invoke(source);////执行源对象的get方法得到属性值
			
			for(Field targetfield : targetFields){
				targetName = targetfield.getName();
				if(targetName.equals(name)){
					Method setMethod = targetClass.getMethod("set" + methodName, type);//属性对应的set方法
					setMethod.invoke(target, value);//执行目标对象的set方法
				}
			}
			
		}
	}
}
