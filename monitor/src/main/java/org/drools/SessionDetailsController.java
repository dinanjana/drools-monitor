package org.drools;

import org.drools.App;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dinanjanag
 * on 2/29/2016.
 */

@Path("session")
public class SessionDetailsController {
    @GET
    @Path("/currentSession")
    public String getCurrentSession ()
    {
//        App app = new App();
        return "Hello!";
    }
}
