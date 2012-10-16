package com.tdclighthouse.commons.mail.util;

import org.hippoecm.hst.core.component.HstRequest;

import com.tdclighthouse.commons.simpleform.html.Form;

/**
 * TemplateParser
 * 
 * @version $id$
 */
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
	 *            optional parameter, if given will override default velocity
	 *            email template {@code easyforms_html.vm}
	 * @return null if parsing fails, html otherwise
	 */
	String populateHtmlTemplate(final HstRequest request, final Form form,
			final String templatePath);

	String populatePlainTextTemplate(final HstRequest request, final Form form,
			final String templatePath);

	String populateFromString(final HstRequest request, Form form,
			String template);
}
