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
package com.tdclighthouse.commons.simpleform.validation.validator.item;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Ebrahim Aharpour
 * 
 */
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
