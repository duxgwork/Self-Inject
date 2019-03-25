package com.github.kevin.library.annocations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE) //该注解作用在其他注解之上，即注解的注解
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    //1、监听方法名：setOnClickListener
    String listenerSetter();

    //2、监听对象：View.OnClickListener
    Class<?> listenerType();

    //3、回调方法名：onClick(View v)
    String callBackListener();

}
