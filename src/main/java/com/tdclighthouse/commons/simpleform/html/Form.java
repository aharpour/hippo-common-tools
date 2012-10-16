package com.tdclighthouse.commons.simpleform.html;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang.StringUtils;

import com.tdclighthouse.commons.simpleform.html.annotation.Field;
import com.tdclighthouse.commons.simpleform.html.annotation.Validator;
import com.tdclighthouse.commons.simpleform.validation.FormItemValidator;
import com.tdclighthouse.commons.simpleform.validation.FormValidator;
import com.tdclighthouse.commons.simpleform.validation.validator.item.BlankFieldValidator;

/**
 * @author Ebrahim Aharpour
 *
 */
public class Form {

	private final String formName;
	private final String action;
	private final FormItemGroup group;
	private String errorMessage;
	private final Map<Object, Object> attributes = new HashMap<Object, Object>();
	private Boolean validationSucceed; 
	private final List<FormValidator> validators = new ArrayList<FormValidator>();
	private static Class<? extends FormItemValidator> BlankFieldValidator = BlankFieldValidator.class;
	private Class<?> beanClass;
	private final AnnotationProssesor annotationProssesor = new AnnotationProssesor();
	
	public Form(String formName, String action) {
		super();
		if (StringUtils.isBlank(formName)) {
			throw new IllegalArgumentException("formName can not be blank");
		}
		this.formName = formName;
		this.action = action;
		this.group = new FormItemGroup("defaultGroup", null, null);
	}

	public Form(String formName, String action, List<FormItem> items) {
		super();
		if (StringUtils.isBlank(formName)) {
			throw new IllegalArgumentException("formName can not be blank");
		}
		this.formName = formName;
		this.action = action;
		this.group = new FormItemGroup("defaultGroup", null, null);
		this.group.addItems(items);
	}
	
	public Form(String formName, String action, Object bean) {
		this(formName, action);
		try {
			if (bean == null) {
				throw new IllegalArgumentException();
			}
			this.beanClass = bean.getClass();
			Collection<FormItem> formItems;
			formItems = annotationProssesor.getFormItems(bean);
			this.group.addItems(formItems);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public FormItemGroup addGroup(String groupName, String label) {
		return this.group.addSubgroup(groupName, label);
	}

	public void addItem(FormItem item) {
		this.group.addItem(item);
	}

	public void addValidator(FormValidator validator) {
		validators.add(validator);
	}

	public String generateErrorMessage(List<FormItem> invalidItems) {
		return "error.message.invalid.form";
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	
	public Object getAttribute(String key) {
		return this.attributes.get(key);
	}
	
	public Map<Object, Object> getAttributesMap() {
		return this.attributes;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public FormItemGroup getGroup() {
		return this.group;
	}
	
	/**
	 * @return the items
	 */
	public List<FormItem> getItems() {
		return this.group.getItems();
	}
	
	public Map<String, FormItem> getItemsMap() {
		return this.group.getItemsMap();
	}

	/**
	 * @return the formName
	 */
	public String getName() {
		return formName;
	}
	
	public FormItemGroup getSubgroupByName(String subgroupName) {
		return this.group.getSubgroupByName(subgroupName);
	}
	

	public Map<String, FormItemGroup> getSubgroups() {
		return this.group.getSubgroups();
	}
	
	/**
	 * @return the validationSucceed
	 */
	public Boolean getValidationSucceed() {
		return validationSucceed;
	}
	
	public List<FormValidator> getValidators() {
		return validators;
	}
	
	public void removeArribute(String key) {
		if (this.attributes.containsKey(key)) {
			this.attributes.remove(key);
		}
	}
	
	public void setAttribute(String key, String value) {
		this.attributes.put(key, value);
	}
	
	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @param validationSucceed the validationSucceed to set
	 */
	public void setValidationSucceed(Boolean validationSucceed) {
		this.validationSucceed = validationSucceed;
	}
	
	public static Class<? extends FormItemValidator> getBlankFieldValidator() {
		return BlankFieldValidator;
	}

	public synchronized static void setBlankFieldValidator(Class<? extends FormItemValidator> blankFieldValidator) {
		BlankFieldValidator = blankFieldValidator;
	}

	public class AnnotationProssesor {
		//TODO it needs to be refactored. Bear in mind that a incomplete but working software is better than no software
		public Collection<FormItem> getFormItems(Object bean) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchFieldException {
			@SuppressWarnings("unchecked")
			SortedMap<Double, FormItem> map = new TreeMap<Double, FormItem>(new ComparableComparator());
			
			if (bean == null) {
				throw new IllegalArgumentException();
			}
			beanClass = bean.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				if ((propertyDescriptor.getWriteMethod() != null) && (propertyDescriptor.getReadMethod() != null)) {
					java.lang.reflect.Field declaredField = beanClass.getDeclaredField(propertyDescriptor.getName());
					Field field = declaredField.getAnnotation(Field.class);
					if (field != null) {
						String name = getFieldName(propertyDescriptor, field);
						String StringValue = getStringValue(bean, propertyDescriptor);
						FormItem formItem = new FormItem(name, field.type(), StringValue, field.label(), field.hint(), field.mandatory());
						if(field.mandatory()) {
							formItem.addValidator(BlankFieldValidator.getName(), "");
						}
						Validator validatorAnnotation = declaredField.getAnnotation(Validator.class);
						if (validatorAnnotation != null) {
							Class<? extends FormItemValidator>[] validatorClass = validatorAnnotation.validatorClass();
							String[] parameter = validatorAnnotation.parameter();
							for (int i = 0; i < validatorClass.length; i++) {
								formItem.addValidator(validatorClass[i].getName(), parameter[i]);
							}
						}
						map.put(field.weight(), formItem);
						
					}
				}
			}
			return map.values();
		}

		private String getFieldName(PropertyDescriptor propertyDescriptor, Field field) {
			String name = field.name();
			if (StringUtils.isBlank(name)) {
				name = propertyDescriptor.getName();
			}
			return name;
		}

		private String getStringValue(Object bean, PropertyDescriptor propertyDescriptor)
				throws IllegalAccessException, InvocationTargetException {
			String result = null;
			Object valueObject = propertyDescriptor.getReadMethod().invoke(bean);
			if (valueObject != null) {
				result = valueObject.toString();
			}
			return result;
		}
		

	}



}
