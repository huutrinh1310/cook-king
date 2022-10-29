package fptu.prm.cookcook.entities;

import java.util.Map;

public class Step {
    private int id;
    private String description;
    private Map<String, String> images;

    public Step() {
    }

    public Step(int id, String description, Map<String, String> images) {
        this.id = id;
        this.description = description;
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", description=" + description +
                ", images=" + images +
                '}';
    }
}
