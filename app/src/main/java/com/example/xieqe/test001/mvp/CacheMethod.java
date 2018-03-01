package com.example.xieqe.test001.mvp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheMethod {
    boolean isCached() default true;
    //isCached不是代表一个方式，而是代表一个属性，默认值为true
}
