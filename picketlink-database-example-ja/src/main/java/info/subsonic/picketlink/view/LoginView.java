package info.subsonic.picketlink.view;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;

/**
 * This class is for login view.
 *
 * @author HOSHI Seigo
 */
@Named("loginView")
@RequestScoped
public class LoginView {

	/**
	 * The FacesContext.
	 */
	@Inject
	private FacesContext facesContext;

	/**
	 * The Identity.
	 */
	@Inject
	private Identity identity;

	/**
	 * Processes login.
	 * 
	 * @return the outcome.
	 */
	public String login() {

		if (identity.isLoggedIn()) {
			return "/protected/home?faces-redirect=true";
		}

		AuthenticationResult result = identity.login();

		if (AuthenticationResult.FAILED.equals(result)) {
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
					"info.subsonic.picketlink.web.messages.Messages",
					uiViewRoot.getLocale());
			String message = resourceBundle.getString("login.failed");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage(null, facesMessage);
			return "";
		}

		return "/protected/home?faces-redirect=true";
	}

}
