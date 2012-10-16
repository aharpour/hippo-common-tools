package com.tdclighthouse.commons.simpleform.validation.validator.item;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

public class IntegerValidator implements FormItemValidator {

	public String getBadFromatedMessage() {
		return "simpleform.validation.badly.formated.integer";
	}

	public String getGreaterMessage() {
		return "simpleform.validation.integer.greater.limit";
	}

	@Override
	public boolean validate(FormItem item, String param) {
		boolean result;
		Integer max = null;
		if (!StringUtils.isBlank(param)) {
			try {
				int par = Integer.parseInt(param);
				max = par;
			} catch (NumberFormatException e) {
			}
		}
		String value = item.getValue();
		try {
			if (!StringUtils.isBlank(value)) {
				int intValue = Integer.parseInt(value);
				if (max == null) {
					result = setValid(item);
				} else {
					if (intValue <= max) {
						result = setValid(item);
					} else {
						result = false;
						item.setErrorMessage(getGreaterMessage());
					}
				}
			} else {
				result = setValid(item);
			}
		} catch (NumberFormatException e) {
			result = false;
			item.setErrorMessage(getBadFromatedMessage());
		}
		return result;
	}

	private boolean setValid(FormItem item) {
		item.setErrorMessage(null);
		return true;
	}
}
