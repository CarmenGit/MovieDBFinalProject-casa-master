package es.cice.moviedbfinalproject.model;

import com.example.Images;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class setupDB {

    private Images images;
    private List<String> changeKeys = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
