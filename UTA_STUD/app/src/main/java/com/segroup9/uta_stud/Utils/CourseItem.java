package com.segroup9.uta_stud.Utils;

import java.io.Serializable;

/**
 * Created by kevinyanogo on 10/19/18.
 */

public class CourseItem implements Serializable {

    private int image;
    private String name;


    public CourseItem() {
        this.image = image;
        this.name = name;
    }

    public CourseItem(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
