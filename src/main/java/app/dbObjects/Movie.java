package app.dbObjects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @ManyToMany(mappedBy = "movies")
    private Set<User> users = new HashSet<User>();

    public Movie(){}

    public Movie(String title, String url){
        this.title = title;
        this.url = url;
    }

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


}
