package com.socialbook.upload.services;


import com.socialbook.uploads.entities.Image;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.logging.Logger;

@ApplicationScoped
public class ImagesBean {

    private static final String TAG = ImagesBean.class.getName();
    private Logger logger = Logger.getLogger(TAG);

    @PersistenceContext(unitName = "upload-service-jpa")
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        logger.info("Initialization of bean");
    }

    @PreDestroy
    private void closure() {
        logger.info("Destroying of bean");
    }

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
