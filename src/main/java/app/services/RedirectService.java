package app.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/osoby")
public class RedirectService {
    @GET
    @Produces("application/json")
    public Response goToUsers(){
        return Response.temporaryRedirect(URI.create("/users")).status(307).build();
    }
}
