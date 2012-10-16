package com.tdclighthouse.commons.simpleform.validation.validator.item;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class BlankFieldValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		boolean result = !StringUtils.isBlank(item.getValue());
		if (!result) {
			item.setErrorMessage(getMessage());
		} else {
			item.setErrorMessage(null);
		}
		return result;
	}

	protected String getMessage() {
		return "simpleform.validation.required.field";
	}

}
