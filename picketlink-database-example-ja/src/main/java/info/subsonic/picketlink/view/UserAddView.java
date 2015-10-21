package info.subsonic.picketlink.view;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.picketlink.idm.model.basic.User;

import info.subsonic.picketlink.constant.Constants;
import info.subsonic.picketlink.service.UserService;

/**
 * This class is for user add view.
 * 
 * @author HOSHI Seigo
 */
@Named("userAddView")
@RequestScoped
public class UserAddView {

	/**
	 * The FacesContext.
	 */
	@Inject
	private FacesContext facesContext;

	/**
	 * The UserService.
	 */
	@Inject
	private UserService userService;

	/**
	 * The User.
	 */
	private User user;

	/**
	 * The password.
	 */
	private String password;

	/**
	 * The ResourceBundle.
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Initializes.
	 */
	@PostConstruct
	public void init() {
		user = new User();
	}

	/**
	 * Saves the User.
	 * 
	 * @return the outcome.
	 */
	public String save() {
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		resourceBundle = ResourceBundle.getBundle(
				"info.subsonic.picketlink.web.messages.Messages",
				uiViewRoot.getLocale());

		boolean isLoginNameValid = isLoginNameValid();
		boolean isNameValid = isNameValid();
		boolean isPasswordValid = isPasswordValid();

		if (!isLoginNameValid || !isNameValid || !isPasswordValid) {
			return "";
		}

		userService.add(user, password);

		String message = resourceBundle.getString("userAdd.added");
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
				message, message);
		facesContext.addMessage(null, facesMessage);

		return "/protected/home";
	}

	/**
	 * Gets the User.
	 *
	 * @return the User.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the User.
	 *
	 * @param user the User.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the password.
	 * 
	 * @return the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * 
	 * @param password the password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Tests if a loginName is valid.
	 * 
	 * @return true if a loginName is valid; false otherwise.
	 */
	private boolean isLoginNameValid() {
		String loginName = user.getLoginName();

		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		UIInput loginNameUiInput = (UIInput) uiViewRoot
				.findComponent("form:loginName");
		loginNameUiInput.setValid(true);

		if (StringUtils.isBlank(loginName)) {
			loginNameUiInput.setValid(false);

			String message = resourceBundle
					.getString("user.loginName.required");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:loginName", facesMessage);
			return false;
		}

		if (!loginName.matches(Constants.LOGIN_NAME_REGEXP)) {
			loginNameUiInput.setValid(false);

			String message = resourceBundle.getString("user.loginName.invalid");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:loginName", facesMessage);
			return false;
		}

		if (loginName.length() < Constants.LOGIN_NAME_MIN_LENGTH) {
			loginNameUiInput.setValid(false);

			String message = resourceBundle
					.getString("user.loginName.minLength");
			message = message.replace("{min}",
					String.valueOf(Constants.LOGIN_NAME_MIN_LENGTH));

			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:loginName", facesMessage);
			return false;
		}

		if (loginName.length() > Constants.LOGIN_NAME_MAX_LENGTH) {
			loginNameUiInput.setValid(false);

			String message = resourceBundle
					.getString("user.loginName.maxLength");
			message = message.replace("{max}",
					String.valueOf(Constants.LOGIN_NAME_MAX_LENGTH));
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:loginName", facesMessage);
			return false;
		}

		if (userService.existsLoginName(loginName)) {
			loginNameUiInput.setValid(false);

			String message = resourceBundle.getString("user.loginName.exists");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:loginName", facesMessage);
			return false;
		}

		return true;
	}

	/**
	 * Tests if name fields are valid.
	 * 
	 * @return true if name fields are valid; false otherwise.
	 */
	private boolean isNameValid() {
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		UIViewRoot uiViewRoot = facesContext.getViewRoot();

		UIInput firstNameUiInput = (UIInput) uiViewRoot
				.findComponent("form:firstName");
		firstNameUiInput.setValid(true);

		UIInput lastNameUiInput = (UIInput) uiViewRoot
				.findComponent("form:lastName");
		lastNameUiInput.setValid(true);

		boolean isValid = true;

		if (StringUtils.isBlank(firstName)) {
			firstNameUiInput.setValid(false);
			isValid = false;
		}

		if (StringUtils.isBlank(lastName)) {
			lastNameUiInput.setValid(false);
			isValid = false;
		}

		if (!isValid) {
			String message = resourceBundle.getString("user.name.required");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:firstName", facesMessage);
			return false;
		}

		if (firstName.length() > Constants.NAME_MAX_LENGTH) {
			firstNameUiInput.setValid(false);
			isValid = false;
		}

		if (lastName.length() > Constants.NAME_MAX_LENGTH) {
			lastNameUiInput.setValid(false);
			isValid = false;
		}

		if (!isValid) {
			String message = resourceBundle.getString("user.name.maxLength");
			message = message.replace("{max}",
					String.valueOf(Constants.NAME_MAX_LENGTH));
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:firstName", facesMessage);
			return false;
		}

		return true;
	}

	/**
	 * Tests if a password is valid.
	 * 
	 * @return true if a password is valid; false otherwise.
	 */
	private boolean isPasswordValid() {
		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		UIInput passwordUiInput = (UIInput) uiViewRoot
				.findComponent("form:password");
		passwordUiInput.setValid(true);

		if (StringUtils.isBlank(password)) {
			passwordUiInput.setValid(false);

			String message = resourceBundle.getString("user.password.required");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:password", facesMessage);
			return false;
		}

		if (!password.matches(Constants.PASSWORD_REGEXP)) {
			passwordUiInput.setValid(false);

			String message = resourceBundle.getString("user.password.invalid");
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:password", facesMessage);
			return false;
		}

		if (password.length() < Constants.PASSWORD_MIN_LENGTH) {
			passwordUiInput.setValid(false);

			String message = resourceBundle
					.getString("user.password.minLength");
			message = message.replace("{min}",
					String.valueOf(Constants.PASSWORD_MIN_LENGTH));
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:password", facesMessage);
			return false;
		}

		if (password.length() > Constants.PASSWORD_MAX_LENGTH) {
			passwordUiInput.setValid(false);

			String message = resourceBundle
					.getString("user.password.maxLength");
			message = message.replace("{max}",
					String.valueOf(Constants.PASSWORD_MAX_LENGTH));
			FacesMessage facesMessage = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, message, message);
			facesContext.addMessage("form:password", facesMessage);
			return false;
		}

		return true;
	}

}
