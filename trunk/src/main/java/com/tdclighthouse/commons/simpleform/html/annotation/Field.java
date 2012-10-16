package com.tdclighthouse.commons.simpleform.html.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.tdclighthouse.commons.simpleform.html.FormItem.Type;

/**
 * @author Ebrahim Aharpour
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

	
	/**
	 * if not specify the fieldName will be used
	 */
	public String name() default "";
	
	public Type type();
	
	public String label() default "";
	
	public String hint() default "";
	
	public boolean mandatory() default false;
	
	public double weight() default 0.0;
}
