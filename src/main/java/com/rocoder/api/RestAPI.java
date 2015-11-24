package com.rocoder.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class RestAPI {

    @GET
    @Path("/stream")
    public Response stream(@QueryParam("token") String token) {
        if (null != token && token.equals("test"))
            return Response.status(200).entity(token).header("Content-Type", "application/json").build();
        else
            return Response.status(403).entity("No token has been set or illegal token").build();
    }


}
