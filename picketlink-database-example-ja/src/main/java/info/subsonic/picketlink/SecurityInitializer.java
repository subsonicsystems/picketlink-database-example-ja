package info.subsonic.picketlink;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.User;

/**
 * This class is for startup.
 *
 * @author HOSHI Seigo
 */
@Singleton
@Startup
public class SecurityInitializer {

	/**
	 * The PartitionManager.
	 */
	@Inject
	private PartitionManager partitionManager;

	/**
	 * Creates the User.
	 */
	@PostConstruct
	public void create() {
		IdentityManager identityManager = this.partitionManager
				.createIdentityManager();

		User user = BasicModel.getUser(identityManager, "admin");

		if (user != null) {
			return;
		}

		user = new User("admin");
		user.setFirstName("1");
		user.setLastName("管理者");

		identityManager.add(user);
		identityManager.updateCredential(user, new Password("abcd1234"));
	}

}
