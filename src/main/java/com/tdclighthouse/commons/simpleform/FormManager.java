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
package com.tdclighthouse.commons.simpleform;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.commons.simpleform.html.Form;
import com.tdclighthouse.commons.simpleform.html.FormItem;
import com.tdclighthouse.commons.simpleform.html.FormItem.Type;
import com.tdclighthouse.commons.simpleform.validation.ValidatorEngine;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class FormManager {
	public static Logger log = LoggerFactory.getLogger(FormManager.class);

	public static final String FORM_NAME = "formname";

	private static ValidatorEngine formValidator = new ValidatorEngine();

	public static void setForm(HttpServletRequest request, Form form, String formOwner) {
		HttpSession session = request.getSession(true);
		synchronized (session) {
			session.setAttribute(makeKey(form, formOwner), form);
		}
	}

	public static void removeForm(HttpServletRequest request, String formName, String formOwner) {
		HttpSession session = request.getSession(true);
		synchronized (session) {
			session.removeAttribute(makeKey(formName, formOwner));
		}
	}

	public static Form getForm(HttpServletRequest request, String formName, String formOwner) {
		Form result;
		HttpSession session = request.getSession(true);
		synchronized (session) {
			Object object = session.getAttribute(makeKey(formName, formOwner));
			if (object instanceof Form) {
				result = (Form) object;
				updateForm(request, result);
			} else {
				log.warn("the form you are trying to retrive form from the"
						+ " session which has never been set there.");
				result = null;
			}
			return result;
		}
	}

	private static String makeKey(Form form, String formOwner) {
		return makeKey(form.getName(), formOwner);
	}

	private static String makeKey(String formName, String formOwner) {
		return formOwner + "-" + formName;
	}

	public static void updateForm(HttpServletRequest request, String formName, String formOwner) {
		getForm(request, formName, formOwner);

	}

	protected static boolean validate(Form form) {
		boolean result;
		try {
			result = formValidator.validate(form);
		} catch (InstantiationException e) {
			validationExceptionHandler(e, form);
			result = false;
		} catch (IllegalAccessException e) {
			validationExceptionHandler(e, form);
			result = false;
		} catch (ClassNotFoundException e) {
			validationExceptionHandler(e, form);
			result = false;
		}
		return result;
	}

	private static void validationExceptionHandler(Throwable t, Form form) {
		log.error(String.format("an %s was thrown while trying to validate a form.", t.getClass().getSimpleName()), t);
		form.setValidationSucceed(false);
		form.setErrorMessage("form validation was failed due to a server error. please contact the site administrator.");
	}

	protected static void updateForm(HttpServletRequest request, Form form) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> parameterMap = request.getParameterMap();
		String formName = stringCast(parameterMap.get(FORM_NAME));
		if (form.getName().equals(formName)) {
			parameterMap.remove(FORM_NAME);
			Collection<FormItem> items = form.getItems();
			for (FormItem item : items) {
				if (item.getDisabled() == false) {
					String param = stringCast(parameterMap.get(item.getName()));
					if (param != null) {
						parameterMap.remove(item.getName());
						item.setValue(param);
					}//hold the checkbox value 
					else if (item.getType() == Type.SIMPLECHECKBOX) {
						item.setValue("off");
					}
				}
			}
			form.getAttributesMap().putAll(parameterMap);
			validate(form);
		}

	}

	protected static String stringCast(Object object) {
		String result = null;
		if (object instanceof String[]) {
			String[] temp = (String[]) object;
			if (temp.length > 0) {
				result = temp[0];
			}
		}
		return result;
	}

	@SuppressWarnings("unused")
	private static String[] stringArrayCast(Object object) {
		String[] result;
		if (object instanceof String[]) {
			result = (String[]) object;
		} else {
			result = null;
		}
		return result;
	}

}
