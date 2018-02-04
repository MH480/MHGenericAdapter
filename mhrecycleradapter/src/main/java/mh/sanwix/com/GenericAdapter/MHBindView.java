package mh.sanwix.com.GenericAdapter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by m.hoseini on 8/26/2017.
 */

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MHBindView
{
    /**
     * gets view id
     * @return view id
     */
    int value();

    /**
     * marks view as clickable
     * @return isClickable ? true : false;
     */
    boolean isClickable() default false;

    boolean isTextAppend() default false;

    boolean isHtml() default false;

    boolean hiddenIfNull() default false;

    boolean isPosition() default false;

    String defaultValue() default "";
    int defaultValueRes() default -1;
    boolean defaultValueBool() default false;
}
