package com.tdclighthouse.commons.simpleform.validation.validator.item;

import java.text.MessageFormat;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class MaxLengthValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		int maxLength = Integer.parseInt(param);
		boolean result = item.getValue().length() <= maxLength;
		if (!result) {
			item.setErrorMessage(getMessage(maxLength));
		} else {
			item.setErrorMessage(null);
		}
		return result;
	}

	protected String getMessage(int max) {
		return MessageFormat.format("simpleform.validation.field.lenght.limit.exceeded.{0}", max);
	}
}
