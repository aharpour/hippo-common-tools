package com.tdclighthouse.commons.simpleform.validation.validator.item;

import com.tdclighthouse.commons.simpleform.html.FormItem;

public class EmailAddressValidator extends PatternMatchValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		return super.validate(item, "^([0-9a-zA-Z]+[-._+&amp;amp;])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}$");
	}

	@Override
	protected String getMessage() {
		return "simpleform.validation.invalid.email";
	}

}
