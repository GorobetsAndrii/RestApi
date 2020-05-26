package app.services;

import app.dbObjects.Movie;
import app.implementation.DBMovieService;
import app.interfaces.IDBService;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/movies")
public class MovieService {

    private IDBService idbService;

    public MovieService(){
        idbService = new DBMovieService();
    }

    @POST
    @Consumes("application/json")
    public void create(Movie movie){
        idbService.INSERT(movie);
    }

    @GET
    @Path("/{title}")
    @Produces("application/json")
    public Movie getMovie(@PathParam("title") String title){
        ArrayList<Movie> movies = (ArrayList<Movie>) idbService.getAll();
        return movies
                .stream()
                .filter(movie -> movie.getTitle().equals(title))
                .findFirst()
                .get();
    }

    @GET
    @Produces({"application/json","text/uri-list"})
    @Consumes({"application/json","text/uri-list"})
    public Response readMovies(@HeaderParam("Accept") String header){
        List<Movie> movies = idbService.getAll();
        if(header.equals("text/uri-list")){
            return Response
                    .ok(movies
                            .stream()
                            .map(movie -> movie.getUrl())
                            .collect(Collectors.toList()))
                    .build();
        }else{
            return Response
                    .ok(movies)
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public void update(Movie movie,@PathParam("id") long id){
        movie.setId(id);
        idbService.UPDATE(movie);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") final long id){
        idbService.DELETE(id);
    }
}
