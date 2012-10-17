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
package com.tdclighthouse.commons.utils.hippo;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;

/**
 * @author Ebrahim Aharpour
 * 
 */
public class Essentials {

	public static boolean areTheSameNode(HippoBean b1, HippoBean b2) {
		return b1.getCanonicalUUID().equals(b2.getCanonicalUUID());

	}

	public static HstLink createHstLink(HippoBean bean, final HstRequest request) {
		HstRequestContext reqContext = request.getRequestContext();
		return reqContext.getHstLinkCreator().create(bean, reqContext);
	}
}
