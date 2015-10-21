package info.subsonic.picketlink.view;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

/**
 * This class is for home view.
 *
 * @author HOSHI Seigo
 */
@Named("homeView")
@RequestScoped
public class HomeView {

	/**
	 * The Identity.
	 */
	@Inject
	private Identity identity;

	/**
	 * The PartitionManager.
	 */
	@Inject
	private PartitionManager partitionManager;

	/**
	 * The users.
	 */
	private List<User> users;

	/**
	 * Initializes.
	 */
	@PostConstruct
	public void init() {
		IdentityManager identityManager = partitionManager
				.createIdentityManager();
		IdentityQueryBuilder builder = identityManager.getQueryBuilder();
		IdentityQuery<User> query = builder.createIdentityQuery(User.class);
		users = query.getResultList();
	}

	/**
	 * Processes logout.
	 * 
	 * @return the outcome.
	 */
	public String logout() {
		identity.logout();
		return "/loggedOut?faces-redirect=true";
	}

	/**
	 * Gets users.
	 * 
	 * @return the users.
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Sets users.
	 * 
	 * @param users the users.
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

}
