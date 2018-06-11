package com.david;

import javax.swing.text.html.FormSubmitEvent;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.lang.reflect.Method;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebRoute {
    String value();
    FormSubmitEvent.MethodType method() default FormSubmitEvent.MethodType.GET;


}
