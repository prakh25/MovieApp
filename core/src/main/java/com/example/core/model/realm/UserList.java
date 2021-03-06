package com.example.core.model.realm;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Prakhar on 3/25/2017.
 */

public class UserList extends RealmObject {

    @PrimaryKey
    private Integer id;

    @Required
    private String name;

    private String description;

    private RealmList<MovieItem> itemList = new RealmList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public RealmList<MovieItem> getItemList() {
        return itemList;
    }

    public void setItemList(RealmList<MovieItem> itemList) {
        this.itemList = itemList;
    }
}
