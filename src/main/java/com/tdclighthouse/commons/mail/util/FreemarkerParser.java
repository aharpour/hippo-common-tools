package com.tdclighthouse.commons.mail.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tdclighthouse.commons.simpleform.html.Form;
import com.tdclighthouse.commons.simpleform.html.FormItem;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * FreemarkerParser
 *
 * @version $Id: FreemarkerParser.java 97 2010-05-14 13:13:17Z mmilicevic $
 */
public final class FreemarkerParser implements TemplateParser {

    private static Logger log = LoggerFactory.getLogger(FreemarkerParser.class);


    private static final FreemarkerParser instance = new FreemarkerParser();

    private FreemarkerParser() {
    }


    private String populateTemplate(final HstRequest request, final Form form, final Template template) {
        if (template == null) {
            log.error("Template was null");
            return "";
        }
        Map<String, Object> context = populateContext(request, form);
        return populateData(template, context);
    }

    private Map<String, Object> populateContext(final HstRequest request, final Form form) {
        Map<String, Object> context = new HashMap<String, Object>();

        final Map<String, String> fieldMap = new HashMap<String, String>();
        for (FormItem item : form.getItems()) {
            fieldMap.put(item.getName(), item.getValue());
        }
        
        context.put("form", form);
        context.put("request", request);
        context.put("fieldMap", fieldMap);
        context.put("formMap", form.getAttributesMap());
        return context;
    }

    public String populateHtmlTemplate(final HstRequest request, final Form form, final String templatePath) {
            log.info("Using custom velocity template: " + templatePath);
            return populateTemplate(request, form, createTemplate(templatePath));
    }


    public String populatePlainTextTemplate(final HstRequest request, final Form form, final String templatePath) {
            log.info("Using custom velocity template: " + templatePath);
            return populateTemplate(request, form, createTemplate(templatePath));
    }

    private Template createTemplate(final String templatePath) {
        try {
            ClassTemplateLoader ctl = new ClassTemplateLoader(FreemarkerParser.class, "/");
            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(ctl);
            Template template = cfg.getTemplate(templatePath);

            return template;

        } catch (IOException e) {
            log.error("Error loading freemarker template:" + templatePath, e);
        }
        return null;

    }

    public String populateFromString(final HstRequest request, final Form form, final String stringTemplate) {
        Template emailTemplate = createEmailTemplate(request, stringTemplate);

        return populateTemplate(request, form, emailTemplate);
    }


    /**
     * Create freemarker template for given String
     *
     * @param request         hst request
     * @param templateContent string containing ftl markup (freemarker template)  @return freemarker template or null if creation fails
     * @return template
     */
    private Template createEmailTemplate(final HstRequest request, final String templateContent) {

        StringTemplateLoader loader = new StringTemplateLoader();
        String name = "ef_email_template";
        loader.putTemplate(name, templateContent);
        Configuration config = new Configuration();
        config.setTemplateLoader(loader);
        try {
            return config.getTemplate(name);
        } catch (IOException e) {
            log.error("Error creating freemaker template", e);
        }
        return null;
    }


    /**
     * Creates body of email message which we are going to send
     *
     * @param template freemarker template
     * @param data     data which will be used to populate template variables
     * @return null if populating fails
     */
    private String populateData(final Template template, final Map<String, Object> data) {
        try {
            // NOTE: no cleanup needed
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            return writer.toString();
        } catch (TemplateException e) {
            log.error("Error populating template data", e);
        } catch (IOException e) {
            log.error("Error populating template data", e);
        }
        return null;
    }

    public static TemplateParser getInstance() {
        return instance;
    }
}
