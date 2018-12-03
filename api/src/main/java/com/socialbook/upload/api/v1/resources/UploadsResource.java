package com.socialbook.upload.api.v1.resources;

import com.google.common.io.ByteStreams;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.socialbook.upload.services.ImageString;
import com.socialbook.upload.services.ImagesBean;
import com.socialbook.uploads.entities.Image;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

@RequestScoped
@Path("/uploads")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin
public class UploadsResource {

    @Inject
    ImagesBean imagesBean;

    @GET
    public Response uploadImage(ImageString imageString) {
        byte[] decodedBytes = Base64.getDecoder().decode(imageString.getImage());

        if (imageString.getUserId() == null || imageString.getAlbumId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Image image = new Image();
        image.setAlbum_id(imageString.getAlbumId());
        image.setUser_id(imageString.getUserId());
        image.setImageData(decodedBytes);
        imagesBean.addImage(image);
        return Response.status(Response.Status.CREATED).build();
    }
}
