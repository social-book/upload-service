package com.socialbook.upload.api.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.socialbook.upload.services.ErrorDto;
import com.socialbook.upload.services.ImagesBean;
import com.socialbook.uploads.entities.Image;
import javassist.bytecode.ByteArray;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {
    @Inject
    ImagesBean imagesBean;

//    /images?imageId=2
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Integer imageId = Integer.parseInt(req.getParameter("imageId"));
            Image image = imagesBean.getImage(imageId);
            resp.getOutputStream().write(image.getImageData());
        } catch (Exception e) {
            log(e.getMessage());
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(toJsonError());
            out.flush();
        }
    }

//    /images?albumId=1&userId=3
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InputStream inputStream = req.getInputStream();
            byte[] bytes = ByteStreams.toByteArray(inputStream);
            String albumId = req.getParameter("albumId");
            String userId = req.getParameter("userId");
            if (albumId == null || userId == null) throw new Exception("Missing params");
            Image image = new Image();
            image.setAlbum_id(albumId);
            image.setUser_id(userId);
            image.setImageData(bytes);
            imagesBean.addImage(image);
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            out.print(toJsonError());
            out.flush();
        }
    }

    private String toJsonError() {

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Missing params!");
        try {
            return objectMapper.writeValueAsString(errorDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error occurred";
    }
}
