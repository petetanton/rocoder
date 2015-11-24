package com.rocoder.gui;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by tantop01 on 21/11/15.
 */
@Path("/encoder")
public class Encoder {

    @GET
    @Path("/")
    public Response index() {
        String result = "<h1>Rocoder | Encoder</h1>Home screen";
        return Response.status(200).entity(result).build();
    }


}
