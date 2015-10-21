package info.subsonic.picketlink;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.picketlink.annotations.PicketLink;

/**
 * This class is for resources.
 *
 * @author HOSHI Seigo
 */
public class Resources {

	/**
	 * The EntityManager.
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Produces the EntityManager.
	 * 
	 * @return the EntityManager.
	 */
	@Produces
	@PicketLink
	public EntityManager producePicketLinkEntityManager() {
		return em;
	}

	/**
	 * Produces the FacesContext.
	 * 
	 * @return the FacesContext.
	 */
	@Produces
	@RequestScoped
	public FacesContext produceFacesContext() {
		return FacesContext.getCurrentInstance();
	}

}
