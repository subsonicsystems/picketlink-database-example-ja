package info.subsonic.picketlink;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.picketlink.config.http.CustomAuthenticationConfiguration;
import org.picketlink.credential.DefaultLoginCredentials;
import org.picketlink.http.authentication.HttpAuthenticationScheme;

/**
 * This class is for FormAuthenticationScheme with Ajax.
 *
 * @author HOSHI Seigo
 */
public class FormWithAjaxAuthenticationScheme
		implements HttpAuthenticationScheme<CustomAuthenticationConfiguration> {

	/**
	 * The timeout page URL.
	 */
	private static final String TIMEOUT_PAGE_URL = "/timeout.xhtml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.picketlink.http.authentication.HttpAuthenticationScheme#initialize
	 * (org.picketlink.config.http.AuthenticationSchemeConfiguration)
	 */
	@Override
	public void initialize(CustomAuthenticationConfiguration config) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.picketlink.http.authentication.HttpAuthenticationScheme#
	 * extractCredential (javax.servlet.http.HttpServletRequest,
	 * org.picketlink.credential.DefaultLoginCredentials)
	 */
	@Override
	public void extractCredential(HttpServletRequest request,
			DefaultLoginCredentials creds) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.picketlink.http.authentication.HttpAuthenticationScheme#
	 * challengeClient (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void challengeClient(HttpServletRequest request,
			HttpServletResponse response) {

		if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
			sendXmlResponse(TIMEOUT_PAGE_URL, request, response);
			return;
		}

		forwardToPage(TIMEOUT_PAGE_URL, request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.picketlink.http.authentication.HttpAuthenticationScheme#
	 * onPostAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onPostAuthentication(HttpServletRequest request,
			HttpServletResponse response) {
	}

	/**
	 * Sends the XML response.
	 * 
	 * @param page the redirect page.
	 * @param request the HttpServletRequest.
	 * @param response the HttpServletResponse.
	 */
	private void sendXmlResponse(String page, HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");

		try {
			response.getWriter()
					.printf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>",
							request.getContextPath() + page)
					.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Forwards to page.
	 * 
	 * @param page the redirect page.
	 * @param request the HttpServletRequest.
	 * @param response the HttpServletResponse.
	 */
	private void forwardToPage(String page, HttpServletRequest request,
			HttpServletResponse response) {

		try {
			response.sendRedirect(request.getContextPath() + page);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
