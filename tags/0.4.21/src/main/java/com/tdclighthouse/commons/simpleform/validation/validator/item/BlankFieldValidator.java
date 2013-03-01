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
