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
package com.tdclighthouse.commons.mail.util;

import org.hippoecm.hst.core.component.HstRequest;

import com.tdclighthouse.commons.simpleform.html.Form;

public interface TemplateParser {

	/**
	 * get HTML email, populated with
	 * 
	 * @param request
	 *            hst request
	 * @param form
	 *            easyform instance
	 * @param formMap
	 *            (populated )HST FormMap
	 * @param templatePath
	 *            optional parameter, if given will override default velocity email template {@code easyforms_html.vm}
	 * @return null if parsing fails, html otherwise
	 */
	String populateHtmlTemplate(final HstRequest request, final Form form, final String templatePath);

	String populatePlainTextTemplate(final HstRequest request, final Form form, final String templatePath);

	String populateFromString(final HstRequest request, Form form, String template);
}
