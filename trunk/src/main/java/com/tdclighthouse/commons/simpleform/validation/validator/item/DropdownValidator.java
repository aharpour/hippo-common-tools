package com.tdclighthouse.commons.simpleform.validation.validator.item;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.html.FormItem.Type;
import com.tdclighthouse.commons.simpleform.html.Option;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class DropdownValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		if (item.getType() != Type.SELECT) {
			throw new IllegalArgumentException("this validator is"
					+ " only applicable to FormItems of the type SELECT");
		}
		boolean result;
		if (StringUtils.isBlank(item.getValue())) {
			result = true;
		} else {
			result = false;
			for (Option option : item.getOptions()) {
				if (item.getValue().equals(option.getValue())) {
					result = true;
					break;
				}
			}
		}
		if (!result) {
			item.setErrorMessage(getMessage());
		} else {
			item.setAttributes(null);
		}
		return result;
	}

	protected String getMessage() {
		return "simpleform.validation.posted.value.not.valid";
	}

}
