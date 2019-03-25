package com.github.kevin.library.annocations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //属性之上
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {

    //int类型的控件
    int value();

}
