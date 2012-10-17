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
package com.tdclighthouse.commons.utils.objectdump;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class ObjectDump {

	// the following String is used to mark the root Object in the Map
	private static final String OBJECT_KEY = "78c4df7e-deea-453a-a0c4-852b05a99e76";
	private static Set<String> filteredMethods = Collections.synchronizedSet(new HashSet<String>());
	private static List<Filter> filters = Collections.synchronizedList(new ArrayList<Filter>());

	static {
		addFilter(new ObjectFilter());
		addFilter(new HippoBeanFilter());
	}

	public static JSONObject dump(Object object, final int depth) {
		JSONObject result = new JSONObject();
		if (object != null) {
			if (isArray(object)) {
				for (int i = 0; i < Array.getLength(object); i++) {
					Object obj2 = Array.get(object, i);
					if (isPrimitiveOrString(obj2)) {
						result.accumulate(OBJECT_KEY, obj2.toString());
					} else {
						result.accumulate(OBJECT_KEY, dump(obj2, depth));
					}
				}
			} else {
				result.element(OBJECT_KEY, object.toString());
				if (!((object instanceof String) || (object instanceof Class)) && (depth != 0)) {
					Class<? extends Object> cls = object.getClass();
					Method[] methods = cls.getMethods();
					for (Method method : methods) {
						if (method.getName().startsWith("get") && (method.getParameterTypes().length == 0)
								&& !filteredMethods.contains(method.getName()) && (method.getName().length() >= 4)) {
							String key = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
							try {
								Object obj = method.invoke(object);

								if (isPrimitiveOrString(obj)) {
									result.accumulate(key, obj.toString());
								} else {
									result.accumulate(key, dump(obj, depth - 1));
								}
							} catch (IllegalArgumentException e) {
								throw new RuntimeException(e);
							} catch (IllegalAccessException e) {
								throw new RuntimeException(e);
							} catch (InvocationTargetException e) {
								throw new RuntimeException(e);
							}
						}
					}
				}
			}
		} else {
			result.accumulate(OBJECT_KEY, "null");
		}

		return result;
	}

	public static void addFilter(Filter filter) {
		Collection<String> methods = filter.getFilteredMethods();
		filteredMethods.addAll(methods);
		filters.add(filter);
	}

	public static List<Filter> getFilters() {
		List<Filter> clone = new ArrayList<Filter>(filters.size());
		Collections.copy(clone, filters);
		return clone;
	}

	public static boolean isPrimitiveOrString(final Object obj) {
		return (obj instanceof Boolean) || (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer)
				|| (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double)
				|| (obj instanceof String);
	}

	public static boolean isPrimitiveArray(final Object obj) {
		return (obj instanceof boolean[]) || (obj instanceof byte[]) || (obj instanceof short[])
				|| (obj instanceof char[]) || (obj instanceof int[]) || (obj instanceof long[])
				|| (obj instanceof float[]) || (obj instanceof double[]);
	}

	public static boolean isArray(final Object obj) {
		return obj.getClass().getName().startsWith("[");
	}

	public static void main(String... args) {
		String[] object = { "sdf", "sdf" };
		JSONObject jsonObject = dump(object, 5);
		System.out.println(PrintJSONObject(jsonObject, "test"));
	}

	public static String PrintJSONObject(JSONObject json, String path) {
		StringBuffer sb = new StringBuffer("<ul>\n");
		sb.append(recursiveJSONPrint(json, path));
		sb.append("</ul>\n");
		return sb.toString();
	}

	private static StringBuffer recursiveJSONPrint(JSONObject json, String path) {
		StringBuffer sb;
		Object value = json.get(OBJECT_KEY);
		if (!(value instanceof JSONArray)) {
			if (value instanceof String) {
				sb = new StringBuffer();
				sb.append(String.format("<li path=\"%s\">%s</li>\n", path, listItemMaker(value.toString(), path)));
				json.remove(OBJECT_KEY);
				@SuppressWarnings("unchecked")
				Iterator<String> keys = json.keys();
				if (keys.hasNext()) {
					sb.append("<ul>\n");
					while (keys.hasNext()) {
						String key = keys.next();
						Object object = json.get(key);
						String newPath = String.format("%s.%s", path, key);
						if (object instanceof String) {
							sb.append(String.format("<li path=\"%s\">%s</li>\n", newPath,
									listItemMaker(object.toString(), newPath)));
						} else if (object instanceof JSONObject) {
							JSONObject child = (JSONObject) object;
							sb.append(recursiveJSONPrint(child, newPath));
						} else {
							throw new IllegalStateException();
						}

					}
					sb.append("</ul>\n");
				}
			} else if (value == null) {
				sb = new StringBuffer("");// FIXME
			} else {
				sb = new StringBuffer("");// FIXME
				// throw new IllegalStateException(//FIXME
				// "we expect all the values to be either a String of a JSONObject.");
			}
		} else {
			JSONArray jsonArray = (JSONArray) value;
			sb = printJsonArray(jsonArray, path);
		}
		return sb;
	}

	private static String listItemMaker(String value, String path) {
		return String.format("%s = %s", path, value);
	}

	private static StringBuffer printJsonArray(JSONArray jsonArray, String path) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < jsonArray.size(); i++) {
			Object value = jsonArray.get(i);
			String newPath = String.format("%s[%d]", path, i);
			if (value instanceof String) {
				sb.append(String.format("<li path=\"%s\">%s</li>\n", newPath, listItemMaker(value.toString(), newPath)));
			} else if (value instanceof JSONObject) {
				sb.append(String.format("<li path=\"%s[%d]\">%s</li>\n", newPath,
						recursiveJSONPrint((JSONObject) value, newPath)));
			} else {
				throw new IllegalStateException("we expect all the values to be either a String of a JSONObject.");
			}
		}
		return sb;
	}

}
