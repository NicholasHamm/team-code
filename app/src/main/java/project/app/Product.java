package project.app;

import java.util.Set;

public class Product {

    private int id;
    private String title;
    private Set<Users> users;
    private Float price;


    private adminController.Visibility visibility = adminController.Visibility.PRIVATE;

    private String description;

    public static boolean isEmpty(String title) {
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price == null ? 0.0f : price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public adminController.Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(adminController.Visibility visibility) {
        this.visibility = visibility;
    }
}
