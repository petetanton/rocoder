package com.rocoder.api;

import com.rocoder.api.pojo.CreateRequest;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
            return generateMessageResponse(403, "No api key set in request");

        String inputBody = new String(body);
        String testJson = "{ \"status\":200, \"message\":\"okay\" }";
        if (contentType.equals("application/json")) {
            return generateResponse(200, testJson);
        } else {
            return generateMessageResponse(404, "Unrecognised content-type");
        }
    }


    private Response generateMessageResponse(int status, String message) {
        return generateResponse(status, "{\"status\":" + status + ",\"message\":\"" + message + "\"}");
    }

    private Response generateResponse(int status, String body) {
        return Response.status(status).entity(body).header("Content-Type", "application/json").header("Server", "Rocoder").build();
    }

    private Response generateResponse(int status, String body, String contentType) {
        return Response.status(status).entity(body).header("Content-Type", contentType).header("Server", "Rocoder").build();
    }


    @POST
    @Path("/create")
    public Response createStream(byte[] body, @HeaderParam("Content-Type") String contentType, @HeaderParam("key") String key) {
        if (contentType.equals("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            CreateRequest createRequest;// = new CreateRequest();
            try {
                createRequest = mapper.readValue(new String(body), CreateRequest.class);
                System.out.println("New post request");
                System.out.println(createRequest.getRequestType());
                System.out.println(createRequest.getSourceFile());
                System.out.println(createRequest.getSourceType());
                return generateMessageResponse(201, "Created");
            } catch (UnrecognizedPropertyException e) {
                return generateMessageResponse(400, "Unrecognised JSON property");
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            return generateMessageResponse(415, "Illegal Content-Type or Content-Type not set");
        }
        return generateMessageResponse(500, "not implemented");
    }


}
