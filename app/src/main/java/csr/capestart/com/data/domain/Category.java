package csr.capestart.com.data.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @Expose
    @SerializedName("c_id")
    private int id;

    @Expose
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Category) {
            Category category = (Category) obj;
            return (category.getName().equals(this.name) && category.getId() == this.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
