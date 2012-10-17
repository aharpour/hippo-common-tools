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
package com.tdclighthouse.commons.utils.beans;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.hippoecm.hst.core.component.HstRequest;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class QueryBean {
	private HttpServletRequest request;
	private String nameSpace = "";
	private final Map<String, String[]> map = new TreeMap<String, String[]>();
	private final URLCodec urlCode = new URLCodec();

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	@SuppressWarnings("unchecked")
	public void setRequest(HttpServletRequest request) {
		this.request = request;
		if (request instanceof HstRequest) {
			HstRequest hstRequest = (HstRequest) request;
			String contextNamespaceReference = hstRequest.getRequestContext().getContextNamespace();
			contextNamespaceReference = (contextNamespaceReference == null ? "" : contextNamespaceReference);
			map.putAll(hstRequest.getParameterMap(contextNamespaceReference));
		} else {
			map.putAll(request.getParameterMap());
		}

	}

	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}

	/**
	 * @param nameSpace
	 *            the nameSpace to set
	 */
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * @return the map
	 */
	public Map<String, String[]> getMap() {
		return map;
	}

	public void putAll(Map<String, String[]> map) {
		this.map.putAll(map);
	}

	public void set(String key, String value) {
		String[] temp = { value };
		this.map.put(key, temp);
	}

	public void set(String key, String[] value) {
		this.map.put(key, value);
	}

	public String[] get(String key) {
		return this.map.get(key);
	}

	public String getFirst(String key) {
		String result = "null";
		String[] values = this.map.get(key);
		if (values.length > 0) {
			result = values[0];
		}
		return result;
	}

	public void remove(String key) {
		this.map.remove(key);
	}

	public String getQuery() throws EncoderException {
		StringBuffer result = new StringBuffer("?");
		Iterator<String> iterator = this.map.keySet().iterator();
		boolean firstTime = true;
		while (iterator.hasNext()) {
			String key = iterator.next();
			String[] values = this.map.get(key);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					if (firstTime) {
						firstTime = false;
					} else {
						result.append("&amp;");
					}
					result.append(urlCode.encode(key));
					result.append('=');
					result.append(urlCode.encode(values[i]));
				}
			}
		}
		return result.toString();
	}

}
