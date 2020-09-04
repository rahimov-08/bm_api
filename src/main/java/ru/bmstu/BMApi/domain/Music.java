package ru.bmstu.BMApi.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int year;
    private int duration;
    private String genre;
    private String imageLink;
    private String musicLink;
    private Long idAlbum;
    private Long idArtist1;
    private Long idArtist2;
    private Long idArtist3;
    private Long idArtist4;
    private Long idArtist5;
    private Long idArtist6;
    private Long idArtist7;

    public Music(){}

    public  Music(String title, Long idArtist1,Long idArtist2,Long idArtist3,Long idArtist4,Long idArtist5,Long idArtist6,Long idArtist7){
        this.title = title;
        this.idArtist1 = idArtist1;
        this.idArtist2 = idArtist2;
        this.idArtist3 = idArtist3;
        this.idArtist4 = idArtist4;
        this.idArtist5 = idArtist5;
        this.idArtist6 = idArtist6;
        this.idArtist7 = idArtist7;

    }

    public Long getIdArtist1() {
        return idArtist1;
    }

    public void setIdArtist1(Long idArtist1) {
        this.idArtist1 = idArtist1;
    }

    public Long getIdArtist2() {
        return idArtist2;
    }

    public void setIdArtist2(Long idArtist2) {
        this.idArtist2 = idArtist2;
    }

    public Long getIdArtist3() {
        return idArtist3;
    }

    public void setIdArtist3(Long idArtist3) {
        this.idArtist3 = idArtist3;
    }

    public Long getIdArtist4() {
        return idArtist4;
    }

    public void setIdArtist4(Long idArtist4) {
        this.idArtist4 = idArtist4;
    }

    public Long getIdArtist5() {
        return idArtist5;
    }

    public void setIdArtist5(Long idArtist5) {
        this.idArtist5 = idArtist5;
    }

    public Long getIdArtist6() {
        return idArtist6;
    }

    public void setIdArtist6(Long idArtist6) {
        this.idArtist6 = idArtist6;
    }

    public Long getIdArtist7() {
        return idArtist7;
    }

    public void setIdArtist7(Long idArtist7) {
        this.idArtist7 = idArtist7;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getMusicLink() {
        return musicLink;
    }

    public void setMusicLink(String musicLink) {
        this.musicLink = musicLink;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

}