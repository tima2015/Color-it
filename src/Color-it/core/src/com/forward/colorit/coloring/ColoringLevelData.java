package com.forward.colorit.coloring;

/**
 * Информаци о изображении уровня.
 */
public class ColoringLevelData {
    /**
     * Данные о местах заливки в изображении.
     */
    private ColoringMap[] map;

    /**
     * Исходное изображение
     */
    private String img;

    /**
     * Иконка исходного изображения
     */
    private String img_thumbnail;

    /**
     * Раскрашенное изображение
     */
    private String done;

    /**
     * Иконка раскрашенного изображения
     */
    private String done_thumbnail;

    /**
     * Название уровня
     */
    private String name;

    /**
     * Идентификатор уровня
     */
    private String id;

    private String prev;

    public ColoringLevelData() {
    }

    public ColoringMap[] getMap() {

        return map;
    }

    public void setMap(ColoringMap[] map) {
        this.map = map;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg_thumbnail() {
        return img_thumbnail;
    }

    public void setImg_thumbnail(String img_thumbnail) {
        this.img_thumbnail = img_thumbnail;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getDone_thumbnail() {
        return done_thumbnail;
    }

    public void setDone_thumbnail(String done_thumbnail) {
        this.done_thumbnail = done_thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }
}
