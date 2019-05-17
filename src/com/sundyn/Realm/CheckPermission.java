package com.sundyn.Realm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})//适用的地方 有 方法上  类上等
public @interface CheckPermission {
    String [] permission();//可以传多个权限标示
}