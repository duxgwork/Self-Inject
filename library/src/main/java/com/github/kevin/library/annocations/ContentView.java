package com.github.kevin.library.annocations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //该注解作用在类之上
@Retention(RetentionPolicy.RUNTIME) //jvm加载时通过反射获取注解值
public @interface ContentView {

    //int类型的布局
    int value();

}
