package mh.sanwix.com.GenericAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by m.hoseini on 11/25/2017.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MHBindViewAction
{
    int value();
    boolean hiddenIfNull() default false;
    String defaultValue() default "";
    int defaultValueRes() default -1;
}
