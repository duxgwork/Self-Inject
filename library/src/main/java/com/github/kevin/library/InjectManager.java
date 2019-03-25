package com.github.kevin.library;

import android.app.Activity;
import android.view.View;

import com.github.kevin.library.annocations.ContentView;
import com.github.kevin.library.annocations.EventBase;
import com.github.kevin.library.annocations.InjectView;
import com.github.kevin.library.utils.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {

    public static void inject(Activity activity) {
        //布局的注入
        injectLayout(activity);
        //控件的注入
        injectView(activity);
        //事件的注入
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            int layoutId = contentView.value();
            try {
//                //方式一：
//                activity.setContentView(layoutId);
                //方式二：利用反射技术。
                Method setContentView = clazz.getMethod("setContentView", int.class);
                setContentView.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void injectView(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        //获取当前类的所有属性（包括私有的）
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            InjectView annotation = field.getAnnotation(InjectView.class);
            if (annotation != null) {
                int viewId = annotation.value();
                try {
//                    //方式一：
//                    View view = activity.findViewById(viewId);
                    //方式二：利用反射技术。
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);
                    //属性的赋值过程
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        //获取当前类的所有方法（包括私有的）
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            //获取每个方法的注解（一个方法可能有多个注解）
            Annotation[] annotations = method.getDeclaredAnnotations();
            //遍历该方法的所有注解
            for (Annotation annotation : annotations) {
                //获取OnClick注解上的注解类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        //获取了事件的重要3成员
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        String callBackListener = eventBase.callBackListener();

                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        handler.addMethod(callBackListener, method);
                        //通过代理的方式操作这个对象，并且中间拦截onClick方法执行自定义的方法
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                        try {
                            //通过annotationType获取OnClick注解的value值
                            Method declaredMethod = annotationType.getDeclaredMethod("value");
                            //执行value方法获取注解值
                            int[] viewIds = (int[]) declaredMethod.invoke(annotation);
                            for (int viewId : viewIds) {
                                //获取当前Activity的View
                                View view = activity.findViewById(viewId);
                                //获取指定的方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                //执行方法
                                setter.invoke(view, listener);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    }

}
