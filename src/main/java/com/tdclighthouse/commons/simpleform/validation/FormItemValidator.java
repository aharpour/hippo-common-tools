package com.tdclighthouse.commons.simpleform.validation;

import com.tdclighthouse.commons.simpleform.html.FormItem;

/**
 * @author Ebrahim Aharpour
 *
 */
public interface FormItemValidator {
	
	public boolean validate(FormItem item, String param);

}
