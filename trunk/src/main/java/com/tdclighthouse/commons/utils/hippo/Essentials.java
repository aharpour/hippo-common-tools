package com.tdclighthouse.commons.utils.hippo;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;

public class Essentials {
	
	public static boolean areTheSameNode(HippoBean b1, HippoBean b2) {
		return b1.getCanonicalUUID().equals(b2.getCanonicalUUID());

	}
	
	public static HstLink createHstLink(HippoBean bean, final HstRequest request) {
		HstRequestContext reqContext = request.getRequestContext();
		return reqContext.getHstLinkCreator().create(bean, reqContext);
	}
}
