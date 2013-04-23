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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class DateValidator extends ConditionalValidator implements FormItemValidator {

	@Override
	public boolean validate(FormItem item, String param) {
		if (ConditionalValidator.isConditionalValidator(param, CONDITIONAL_VALIDATION)){
			if(ConditionalValidator.isConditionSatisfied(item, param, CONDITIONAL_VALIDATION)){
				return performValidation(item, param);
			}else{
				item.setErrorMessage(null);
				return true;
			}
		}else{
			return performValidation(item, param);
		}
	}
	
	private boolean performValidation(FormItem item, String param) {
		if (StringUtils.isBlank(param)) {
			throw new IllegalArgumentException("\"param\" argment must contain a date fromat");
		}
		boolean result = true;
		item.setErrorMessage(null);
		String value = item.getValue();
		if (!StringUtils.isBlank(value)) {
			value = value.trim();
			SimpleDateFormat dateFormat = new SimpleDateFormat(param);
			try {
				Date date = dateFormat.parse(value);
				if (!dateFormat.format(date).equals(value)) {
					// in this case the date is of the right format but such a
					// date dose not exist in the calendar
					result = false;
					item.setErrorMessage(getInvalidDateMessage());
				}
			} catch (ParseException e) {
				// the given string is not of the right format
				result = false;
				item.setErrorMessage(getBadFormatMessage(param));
			}
		}
		return result;
	}

	protected String getBadFormatMessage(String format) {
		return "simpleform.validation.badly.formated.date";
	}

	protected String getInvalidDateMessage() {
		return "simpleform.validation.invalid.date";
	}

}
