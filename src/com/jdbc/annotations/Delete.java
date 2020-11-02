package com.jdbc.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//可以用在方法上
@Retention(RetentionPolicy.RUNTIME)//生命周期存在于虚拟机中，可以通过反射获取注解信息
@Inherited//表示注解可继承，表示为们在接口方法定义的注解，在实现类的方法上也可以获得。
public @interface Delete {
    public String value();
}
