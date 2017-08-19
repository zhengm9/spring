package com.future.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhengming on 2017/8/19.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface LoginValidation {
    String value() default "user";
}
