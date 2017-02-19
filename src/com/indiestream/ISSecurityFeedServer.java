package com.indiestream;

import com.wowza.wms.logging.WMSLoggerFactory;
import com.wowza.wms.server.IServer;
import com.wowza.wms.server.IServerNotify2;
import com.wowza.wms.vhost.VHostSingleton;

public class ISSecurityFeedServer implements IServerNotify2 {

	ISSecurityFeedVHost listenerVHost;
	
	public void onServerConfigLoaded(IServer server) {
		WMSLoggerFactory.getLogger(null).info("onServerConfigLoaded");
	}

	public void onServerCreate(IServer server) {
		WMSLoggerFactory.getLogger(null).info("onServerCreate");
		listenerVHost = new ISSecurityFeedVHost();
		VHostSingleton.addVHostListener(listenerVHost);
	}

	public void onServerInit(IServer server) {
		WMSLoggerFactory.getLogger(null).info("onServerInit");
		listenerVHost.startApplication();
	}

	public void onServerShutdownStart(IServer server) {
		WMSLoggerFactory.getLogger(null).info("onServerShutdownStart");

		VHostSingleton.removeApplicationListener(listenerVHost);
		listenerVHost = null;
		
	}

	public void onServerShutdownComplete(IServer server) {
		WMSLoggerFactory.getLogger(null).info("onServerShutdownComplete");
	}

}
