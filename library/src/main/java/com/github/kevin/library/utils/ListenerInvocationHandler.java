package com.github.kevin.library.utils;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

//拦截本应执行的回调onClick，而去执行我们自定义的方法
public class ListenerInvocationHandler implements InvocationHandler {
    private Object target;
    private HashMap<String, Method> methodHashMap = new HashMap();


    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            //获取要拦截的方法名
            String methodName = method.getName();
            Log.e("invoke ==> method1:",methodName);
            //执行自定义的方法
            method = methodHashMap.get(methodName);
            Log.e("invoke ==> method2:",method.getName());
            if (method != null){
                return method.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 添加要拦截的方法
     *
     * @param methodName 要拦截的方法，如OnClick()
     * @param method     将执行的方法，如show()
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }

}
