package info.subsonic.picketlink;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import javax.enterprise.event.Observes;

/**
 * This class is for http security configuration.
 *
 * @author HOSHI Seigo
 */
public class HttpSecurityConfiguration {

	/**
	 * Configures the http Security.
	 * 
	 * @param event the SecurityConfigurationEvent.
	 */
	public void onInit(@Observes SecurityConfigurationEvent event) {
		SecurityConfigurationBuilder builder = event.getBuilder();
		builder.http().forPath("/protected/*").authenticateWith()
				.scheme(FormWithAjaxAuthenticationScheme.class);
	}

}
