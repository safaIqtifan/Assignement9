package com.example.assignement9.Model;

public class StudentModel {

    private int id;
    private String name;
    private double average;
    private String img;

    public static final String TABLE_NAME = "student";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_AVERAGE = "average";
    public static final String COL_IMAGE = "image";

    public static final String TABLE_CREATE = "create table "+TABLE_NAME+" ("+COL_ID+
            " integer primary key autoincrement, "+COL_NAME+" text not null, "+
            COL_AVERAGE+" Double, "+COL_IMAGE+" text not null)";

    public StudentModel() {
    }

    public StudentModel(int id, String name, double average, String image) {
        this.id = id;
        this.name = name;
        this.average = average;
        this.img = image;
    }

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

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", average=" + average +
                ", img='" + img + '\'' +
                '}';
    }
}
