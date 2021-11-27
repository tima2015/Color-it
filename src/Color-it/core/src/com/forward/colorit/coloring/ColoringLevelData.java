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

    public ColoringLevelData(ColoringMap[] map, String img, String img_thumbnail, String done, String done_thumbnail) {
        this.map = map;
        this.img = img;
        this.img_thumbnail = img_thumbnail;
        this.done = done;
        this.done_thumbnail = done_thumbnail;
    }

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
}
