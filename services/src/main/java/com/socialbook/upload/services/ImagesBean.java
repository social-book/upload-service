package com.socialbook.upload.services;


import com.kumuluz.ee.discovery.annotations.DiscoverService;
import com.socialbook.upload.services.configuration.AppProperties;
import com.socialbook.uploads.entities.Image;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

@RequestScoped
public class ImagesBean {

    private static final String TAG = ImagesBean.class.getName();
    private Logger logger = Logger.getLogger(TAG);

    @PersistenceContext(unitName = "upload-service-jpa")
    private EntityManager entityManager;


    private Client httpClient;

    @PostConstruct
    private void init() {
        logger.info("Initialization of bean");
        httpClient = ClientBuilder.newClient();
    }


    @PreDestroy
    private void closure() {
        logger.info("Destroying of bean");
    }


    @Inject
    @DiscoverService("catalog-services")
    private Optional<String> baseUrl;

    @Inject
    AppProperties appProperties;

    //READ
    public Image getImage(Integer imageId) {
        logger.info(TAG + " Retrieving image");
        ArrayList<Image> images = (ArrayList<Image>) entityManager.createNamedQuery("Image.getImage").setParameter("image_id", imageId).getResultList();
        return images.get(0);
    }

    //CREATE
    @Transactional
    public void addImage(Image image) {
        logger.info("adding new image");
        if (image != null) {
            entityManager.getTransaction().begin();
            entityManager.persist(image);
            entityManager.flush();
            entityManager.getTransaction().commit();
        }
        logger.info("checking if upload image url enabled");
        if (appProperties.isUploadImageUrlEnable()) {
            logger.info("creating upload image url");
            createUploadImageUrl(image.getUser_id(), image.getAlbum_id(), image.getImage_id());
        } else {
            logger.info("upload image url disabled!!!");
        }
    }


    private void createUploadImageUrl(String userId, String albumId, Integer imageId) {
        try {
            httpClient
                    .target(baseUrl.get() + "/v1/albums/add/" + userId + "/" + albumId + "/" + imageId)
                    .request().get(new GenericType<String>() {
            });
        } catch (WebApplicationException | ProcessingException e) {
            logger.severe(e.getMessage());
        }
    }

    //DELETE
    @Transactional
    public void deleteImage(Integer id) {
        logger.info("deleting image with id: " + id);
        entityManager.getTransaction().begin();
        Image imageToDel = entityManager.find(Image.class, id);
        entityManager.remove(imageToDel);
        entityManager.getTransaction().commit();
    }
}
