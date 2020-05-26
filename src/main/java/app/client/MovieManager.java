package app.client;

import app.dbObjects.Movie;
import app.dbObjects.User;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "movieManager")
@SessionScoped
public class MovieManager {
    private String title;
    private String uri;
    private ResteasyClient client;
    private Movie movie;

    public MovieManager(){
        client = new ResteasyClientBuilder().build();
    }

    public List<Movie> getAll(){
        return client
                .target("http://localhost:8080/RestApi_war_exploded/api/movies")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class)
                .readEntity(new GenericType<List<Movie>>(){});
    }

    public void deleteMovie(long id){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/movies/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public String createMovie(){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/movies")
                .request()
                .post(Entity.entity(new Movie(title,uri), MediaType.APPLICATION_JSON));
        return "movie";
    }

    public String updateMovie(){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/movies/" + movie.getId())
                .request()
                .put(Entity.entity(movie, MediaType.APPLICATION_JSON));
        return "movie";
    }

    public String go2Update(Movie m){
        movie = m;
        return "updateMovie";
    }

    public List<String> getMoviesTitle(){
        return getAll()
                .stream()
                .map(m -> m.getTitle())
                .collect(Collectors.toList());
    }

    public List<Long> getMoviesId(){
        return getAll()
                .stream()
                .map(m -> m.getId())
                .collect(Collectors.toList());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
