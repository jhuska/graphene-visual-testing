package sk.najkrajsie.videoApp.rest;

import java.util.logging.Logger;
import javax.ejb.Stateless;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sk.najkrajsie.videoApp.model.resources.MediaItem;

@Path("/mediaItems")
@Stateless
public class MediaItemService extends BaseEntityService<MediaItem> {

	private static final Logger LOGGER = Logger
			.getLogger(MediaItemService.class.getName());

	@Inject
	private EntityManager entityManager;

	public MediaItemService() {
		super(MediaItem.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createMediaItem(MediaItem video) {
		try {
                    //manager create video
                } catch (PersistenceException ex) {
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity("There was an error thrown while persisting the entity: "
							+ ex.getMessage()).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex)
					.build();
		}
		return Response.ok().build();
	}

	/**
	 * <p>
	 * A method for deleting individual entity instances.
	 * </p>
	 * 
	 * @param id
	 *            entity id
	 * @return
	 */
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMediaItem(@PathParam("id") Long id) {
		boolean result = false;
		try {
                    //delete video
                } catch (Exception ex) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ex)
					.build();
		}
		if (!result) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		} else {
			return Response.ok().build();
		}
	}

	/**
	 * <p>
	 * A method for updating individual entity instances.
	 * </p>
	 * 
	 * @param id
	 *            entity id
	 * @return
	 */
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMediaItem(@PathParam("id") Long id,
			MediaItem video) {
		return Response.ok().build();
	}
}