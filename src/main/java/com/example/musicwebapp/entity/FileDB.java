package com.example.musicwebapp.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileDB {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileOwner;

    private String name;

    private String type;

    private Boolean isApproved;

    @Column(name = "category")
    private String category;

    @Lob
    private byte[] data;

    public FileDB(){
    }

    public FileDB(String name, String type, byte[] data, String category) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.isApproved = false;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean status) {
        this.isApproved = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        var words = type.split("/");
        return words[1];
    }

    public byte[] getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
