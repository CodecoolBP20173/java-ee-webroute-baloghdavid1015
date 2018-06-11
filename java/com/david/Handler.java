package com.david;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsExchange;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Handler implements HttpHandler {


    @Override
    public void handle(HttpExchange reqData) throws IOException {
        Class route = Route.class;
        Method[] methods = route.getMethods();

        boolean wrongPage = true;

        for (Method method : methods) {

            Annotation[] annotations = method.getDeclaredAnnotations();

            for (Annotation annotation : annotations) {

                if (annotation instanceof WebRoute) {
                    WebRoute annot = (WebRoute) annotation;
                    if (annot.value().equals(reqData.getRequestURI().getRawPath())) {
                        try {
                            method.invoke(route.newInstance(), reqData);
                            wrongPage = false;
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        if (wrongPage) {

            try {
                Method wrongPagem = route.getDeclaredMethod("onError", HttpExchange.class);
                try {
                    wrongPagem.invoke(route.newInstance(), reqData);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
    }
}