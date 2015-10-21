package info.subsonic.picketlink.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.User;

/**
 * This class is for user service.
 * 
 * @author HOSHI Seigo
 */
@Stateless
public class UserService {

	/**
	 * The PartitionManager.
	 */
	@Inject
	private PartitionManager partitionManager;

	/**
	 * Tests if loginName exists.
	 * 
	 * @param loginName the loginName.
	 * @return true if a loginName exists; false otherwise.
	 */
	public boolean existsLoginName(String loginName) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		User user = BasicModel.getUser(identityManager, loginName);

		if (user == null) {
			return false;
		}

		return true;
	}

	/**
	 * Adds the User.
	 * 
	 * @param user the User.
	 * @param password the password.
	 */
	public void add(User user, String password) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		identityManager.add(user);
		identityManager.updateCredential(user, new Password(password));
	}

	/**
	 * Updates the user.
	 * 
	 * @param user the user.
	 * @param password the password.
	 */
	public void update(User user, String password) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();

		User savedUser = BasicModel.getUser(identityManager,
				user.getLoginName());
		savedUser.setFirstName(user.getFirstName());
		savedUser.setLastName(user.getLastName());

		identityManager.update(savedUser);

		if (!StringUtils.isBlank(password)) {
			identityManager.updateCredential(savedUser, new Password(password));
		}

	}

	/**
	 * Removes the user.
	 * 
	 * @param user the user.
	 */
	public void remove(User user) {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		User savedUser = BasicModel.getUser(identityManager,
				user.getLoginName());
		identityManager.remove(savedUser);
	}

}
