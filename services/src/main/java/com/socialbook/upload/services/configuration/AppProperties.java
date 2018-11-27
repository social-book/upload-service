package com.socialbook.upload.services.configuration;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;

@RequestScoped
@ConfigBundle("app-properties")
public class AppProperties {
    @ConfigValue(value = "upload-imgurl.enabled", watch = true)
    private boolean uploadImageUrlEnable;

    public boolean isUploadImageUrlEnable() {
        return uploadImageUrlEnable;
    }

    public void setUploadImageUrlEnable(boolean uploadImageUrlEnable) {
        this.uploadImageUrlEnable = uploadImageUrlEnable;
    }
}
