package app.services;

import app.dbObjects.Movie;
import app.dbObjects.User;
import app.implementation.DBMovieService;
import app.implementation.DBUserService;
import app.interfaces.IDBService;

import javax.ws.rs.*;
import java.util.List;

@Path("/users")
public class UserService {

    private IDBService idbService;

    public UserService(){
        idbService = new DBUserService();
    }

    @POST
    @Consumes("application/json")
    public void create(User user){
        idbService.INSERT(user);
    }

    @GET
    @Produces("application/json")
    public List<User> read(){
        return idbService.getAll();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void update(User user,@PathParam("id") long id){
        user.setId(id);
        idbService.UPDATE(user);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") final long id){
        idbService.DELETE(id);
    }

    @GET
    @Path("{id_user}/movies/{id_movie}")
    public void addMovie(@PathParam("id_user") long id_user, @PathParam("id_movie") long id_movie){
        IDBService temp = new DBMovieService();
        User user = (User) idbService.getByID(id_user);
        Movie movie = (Movie) temp.getByID(id_movie);
        user.addMovie(movie);
        idbService.UPDATE(user);
    }

    @DELETE
    @Path("{id_user}/movies/{id_movie}")
    public void deleteMovie(@PathParam("id_user") long id_user, @PathParam("id_movie") long id_movie){
        User user = (User) idbService.getByID(id_user);
        user.removeMovie(id_movie);
        idbService.UPDATE(user);
    }
}
