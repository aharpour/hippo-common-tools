package com.tdclighthouse.commons.simpleform.validation;

import com.tdclighthouse.commons.simpleform.html.Form;

public interface FormValidator {
	
	public boolean validate(Form form);

	public String getErrorMessage(Form form);

}
