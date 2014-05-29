package com.paradiseoctopus.happysquirrel.helpers;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.web.client.RestClientException;

/*
 * Custom handler for RestClientError
 */
@EBean
public class CustomRestErrorHandler implements RestErrorHandler {

	/*
	 * (non-Javadoc)
	 * @see org.androidannotations.api.rest.RestErrorHandler#onRestClientExceptionThrown(org.springframework.web.client.RestClientException)
	 * Just say to the networkUtils that server is unreachable.
	 */
	@Override
	public void onRestClientExceptionThrown(RestClientException e) {
		NetworkUtils.setServerReachable(false);
	}
}
