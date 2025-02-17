package com.ebe.qrscanner.model.data.dto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items")
public class QRItemDTO {

    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    private Long Id;
    @ColumnInfo(name = "Content")
    private String content;
    @ColumnInfo(name = "Date")
    private String date;
    @ColumnInfo(name = "Type")
    private String type;
    @ColumnInfo(name = "Favorite")
    private Boolean favorite;
    @ColumnInfo(name = "Image")
    private byte[] image;

    public QRItemDTO() {
    }

    @Ignore
    public QRItemDTO(String content, String date, String type, Boolean favorite, byte[] image) {
        this.content = content;
        this.date = date;
        this.type = type;
        this.favorite = favorite;
        this.image = image;

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
