package ru.bmstu.BMApi.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private boolean single;
    private String genre;
    private String title;
    private int year;
    private Long id_artist1;
    private Long id_artist2;
    private Long id_artist3;
    private Long id_artist4;
    private Long id_artist5;
    private Long id_artist6;
    private Long id_artist7;
    private Long id_artist8;
    private Long id_artist9;
    private Long id_artist10;
    private String imageLink;


    public Album() {
    }

    public Album(String title, String genre, boolean single, int year, Long id_artist1, Long id_artist2, Long id_artist3, Long id_artist4, Long id_artist5, Long id_artist6, Long id_artist7, Long id_artist8,Long id_artist9, Long id_artist10){
        this.title = title;
        this.genre = genre;
        this.single = single;
        this.year = year;
        this.id_artist1 = id_artist1;
        this.id_artist2 = id_artist2;
        this.id_artist3 = id_artist3;
        this.id_artist4 = id_artist4;
        this.id_artist5 = id_artist5;
        this.id_artist6 = id_artist6;
        this.id_artist7 = id_artist7;
        this.id_artist8 = id_artist8;
        this.id_artist9 = id_artist9;
        this.id_artist10 = id_artist10;
    }


    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public Long getId_artist1() {
        return id_artist1;
    }

    public void setId_artist1(Long id_artist1) {
        this.id_artist1 = id_artist1;
    }

    public Long getId_artist2() {
        return id_artist2;
    }

    public void setId_artist2(Long id_artist2) {
        this.id_artist2 = id_artist2;
    }

    public Long getId_artist3() {
        return id_artist3;
    }

    public void setId_artist3(Long id_artist3) {
        this.id_artist3 = id_artist3;
    }

    public Long getId_artist4() {
        return id_artist4;
    }

    public void setId_artist4(Long id_artist4) {
        this.id_artist4 = id_artist4;
    }

    public Long getId_artist5() {
        return id_artist5;
    }

    public void setId_artist5(Long id_artist5) {
        this.id_artist5 = id_artist5;
    }

    public Long getId_artist6() {
        return id_artist6;
    }

    public void setId_artist6(Long id_artist6) {
        this.id_artist6 = id_artist6;
    }

    public Long getId_artist7() {
        return id_artist7;
    }

    public void setId_artist7(Long id_artist7) {
        this.id_artist7 = id_artist7;
    }

    public Long getId_artist8() {
        return id_artist8;
    }

    public void setId_artist8(Long id_artist8) {
        this.id_artist8 = id_artist8;
    }

    public Long getId_artist9() {
        return id_artist9;
    }

    public void setId_artist9(Long id_artist9) {
        this.id_artist9 = id_artist9;
    }

    public Long getId_artist10() {
        return id_artist10;
    }

    public void setId_artist10(Long id_artist10) {
        this.id_artist10 = id_artist10;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
