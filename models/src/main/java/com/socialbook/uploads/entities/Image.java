package com.socialbook.uploads.entities;

import javax.persistence.*;

@Entity(name = "image_binary_table")
@NamedQueries(value = {
        @NamedQuery(name = "Image.getAll",
                query = "SELECT image from image_binary_table image"),
        @NamedQuery(name = "Image.getImage",
                query = "SELECT image FROM image_binary_table image WHERE image.image_id = :image_id")
})
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer image_id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "album_id")
    private String album_id;

    @Lob
    @Column(name="image_data")
    private byte[] imageData;

    public Integer getImage_id() {
        return image_id;
    }

    public void setImage_id(Integer image_id) {
        this.image_id = image_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
