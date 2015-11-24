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

    @POST
    @Path("/test")
    public Response test(byte[] body, @HeaderParam("Content-Type") String contentType, @HeaderParam("key") String key) {
        if (null == key)
            return generateBadResponse(403, "No api key set in request");

        String inputBody = new String(body);
        String testJson = "{ \"status\":200, \"message\":\"okay\" }";
        if (contentType.equals("application/json")) {
            return generateResponse(200, testJson);
        } else {
            return generateBadResponse(404, "Unrecognised content-type");
        }
    }


    private Response generateBadResponse(int status, String message) {
        return generateResponse(status, "{\"status\":" + status + ",\"message\":\"" + message + "\"}");
    }

    private Response generateResponse(int status, String body) {
        return Response.status(status).entity(body).header("Content-Type", "application/json").header("Server", "Rocoder").build();
    }

    private Response generateResponse(int status, String body, String contentType) {
        return Response.status(status).entity(body).header("Content-Type", contentType).header("Server", "Rocoder").build();
    }


}
