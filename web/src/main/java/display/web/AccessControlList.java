package display.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import display.web.javabean.CustomerBean;

public class AccessControlList {
	private Map<String, List<String>> acl;
	
	private static AccessControlList instance;
	
	private AccessControlList () {
		acl = new HashMap<String, List<String>>();
		
		acl.put("/web", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/", Arrays.asList("ADMIN", "ASSISTANT"));
		acl.put("/web/index.jsp", Arrays.asList("ADMIN", "ASSISTANT"));
		// acl.put("/web/anywhere", Arrays.asList("ADMIN"));
	}
	
	public static AccessControlList getInstance() {
		if(instance != null) {
			instance = new AccessControlList();
		}
		
		return instance;
	}
	
	public boolean isAllowed(String route, CustomerBean user) {
		return acl.get(route).contains(user.getRoleUser());
	}
}
