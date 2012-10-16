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
	private Map<String, String[]> map = new TreeMap<String, String[]>();
	private URLCodec urlCode = new URLCodec();

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
	        contextNamespaceReference = (contextNamespaceReference == null ? "": contextNamespaceReference);
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
