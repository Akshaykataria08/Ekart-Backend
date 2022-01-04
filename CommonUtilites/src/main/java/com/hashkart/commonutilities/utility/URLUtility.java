package com.hashkart.commonutilities.utility;

import java.net.URI;
import java.net.URISyntaxException;

public class URLUtility {

	public static String createUrl(String domain, String path) throws URISyntaxException {
		return new URI(domain).resolve(path).normalize().toString();
	}
}
