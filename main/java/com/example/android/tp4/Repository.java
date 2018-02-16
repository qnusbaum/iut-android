package com.example.android.tp4;

/**
 * Created by Android on 15/02/2018.
 */

public class Repository {
    private String name;
    private String description;
    private int forkCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }
}
