package com.socialbook.upload.api.v1.resources;

import com.socialbook.upload.services.ImagesBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/uploads")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UploadsResource {

    @Inject
    ImagesBean imagesBean;

    @GET
    public Response getAllImages() {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
