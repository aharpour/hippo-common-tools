package com.tdclighthouse.commons.simpleform.validation.validator.item;

import java.text.MessageFormat;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class MinLengthValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		int minLength = Integer.parseInt(param);
		boolean result = item.getValue().length() >= minLength;
		if (!result) {
			item.setErrorMessage(getMessage(minLength));
		} else {
			item.setErrorMessage(null);
		}
		return result;
	}

	protected String getMessage(int min) {
		return MessageFormat.format("simpleform.validation.field.lenght.minimum.not.reached.{0}", min);
	}
}
