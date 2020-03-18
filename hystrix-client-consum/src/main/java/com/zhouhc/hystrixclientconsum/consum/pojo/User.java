package com.zhouhc.hystrixclientconsum.consum.pojo;

public class User {

    private String id;

    private String name;

    private String moblie;

    public User() {
    }

    public User(String id, String name, String moblie) {
        this.id = id;
        this.name = name;
        this.moblie = moblie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoblie() {
        return moblie;
    }

    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", moblie='" + moblie + '\'' +
                '}';
    }
}
