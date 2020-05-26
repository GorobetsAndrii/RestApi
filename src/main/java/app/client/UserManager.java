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
import java.util.Set;

@ManagedBean(name = "userManager")
@SessionScoped
public class UserManager {
    private String name;
    private int age;
    private User user;
    private ResteasyClient client;
    private long id_movie;

    public UserManager(){
        client = new ResteasyClientBuilder().build();
    }

    public List<User> getAll(){
        return client
                .target("http://localhost:8080/RestApi_war_exploded/api/users")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class)
                .readEntity(new GenericType<List<User>>(){});
    }

    public String go2UserMovies(User u){
        user = u;
        return "usersMovie";
    }

    public String go2Update(User u){
        user = u;
        return "updateUser";
    }

    public Set<Movie> getMovies(){
        List<User> users = getAll();
        return users.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .get()
                .getMovies();
    }

    public void deleteMovieFromUserList(long id_movie){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/users/" + user.getId() + "/movies/" + id_movie)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public void deleteUser(long id){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/users/" + id)
                .request(MediaType.APPLICATION_JSON)
                .delete();
    }

    public String createUser(){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/users")
                .request()
                .post(Entity.entity(new User(name,age), MediaType.APPLICATION_JSON));
        return "user";
    }

    public String updateUser(){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/users/" + user.getId())
                .request()
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));
        return "user";
    }

    public void addMovie(){
        client
                .target("http://localhost:8080/RestApi_war_exploded/api/users/" + user.getId() + "/movies/" + id_movie)
                .request()
                .get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId_movie() {
        return id_movie;
    }

    public void setId_movie(long id_movie) {
        this.id_movie = id_movie;
    }
}
