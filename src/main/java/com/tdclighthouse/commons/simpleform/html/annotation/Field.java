/*
 *  Copyright 2012 Finalist B.V.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
	
	public String[] buttonValues() default {"true", "false"};
	
	public boolean[] defaultValues() default {false, true};
	
	public String[] optionsValues() default {"true", "false"};
	
	public String[] optionsText() default {"true", "false"};
}