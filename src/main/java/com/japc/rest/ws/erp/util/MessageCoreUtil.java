package com.japc.rest.ws.erp.util;

import org.springframework.stereotype.Service;

@Service
public class MessageCoreUtil extends MessageBundleBase {

	public MessageCoreUtil() {
		bundle = "app-messages";
	}
}
