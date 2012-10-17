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
package com.tdclighthouse.commons.simpleform.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tdclighthouse.commons.simpleform.html.Form;
import com.tdclighthouse.commons.simpleform.html.FormItem;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ValidatorEngine {

	private final Map<String, FormItemValidator> validators = Collections
			.synchronizedMap(new HashMap<String, FormItemValidator>());

	public boolean validate(Form form) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		boolean result;
		List<FormItem> invalidItems = new ArrayList<FormItem>();
		Collection<FormItem> items = form.getItems();
		// item validation
		for (FormItem item : items) {
			if (item.getDisabled() == false) {
				List<String> classNames = item.getValidatorClassNames();
				List<String> validationCriteria = item.getValidationCriteria();
				if (classNames.size() == validationCriteria.size()) {
					for (int i = 0; i < classNames.size(); i++) {
						FormItemValidator itemValidator = getItemValidator(classNames.get(i));
						if (!itemValidator.validate(item, validationCriteria.get(i))) {
							invalidItems.add(item);
							break;
						}
					}
				} else {
					throw new IllegalArgumentException("there should be a one to one"
							+ " correspondence between classNames and validation criteria");
				}
			}
		}
		if (invalidItems.isEmpty()) {
			// form validation
			result = true;
			for (FormValidator formValidator : form.getValidators()) {
				if (!formValidator.validate(form)) {
					result = false;
					form.setValidationSucceed(new Boolean(false));
					form.setErrorMessage(formValidator.getErrorMessage(form));
					break;
				}
			}
			if (result) {
				form.setValidationSucceed(new Boolean(true));
			}
		} else {
			result = false;
			form.setValidationSucceed(new Boolean(false));
			form.setErrorMessage(form.generateErrorMessage(invalidItems));
		}
		return result;
	}

	private FormItemValidator getItemValidator(String className) throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		FormItemValidator formItemValidator = validators.get(className);
		if (formItemValidator == null) {
			Class<?> candidateClass = Class.forName(className);
			Object candidateObject = candidateClass.newInstance();
			if (candidateObject instanceof FormItemValidator) {
				formItemValidator = (FormItemValidator) candidateObject;
				validators.put(className, formItemValidator);
			} else {
				throw new IllegalArgumentException("the given class name does not belong to an "
						+ "implementation of the interface \"FormItemValidator\"");
			}
		}
		return formItemValidator;
	}

}
