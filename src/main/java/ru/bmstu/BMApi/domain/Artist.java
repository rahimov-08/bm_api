package ru.bmstu.BMApi.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String image200;
    private String image1000;

    public String getImage200() {
        return image200;
    }

    public void setImage200(String image200) {
        this.image200 = image200;
    }

    public String getImage1000() {
        return image1000;
    }

    public void setImage1000(String image1000) {
        this.image1000 = image1000;
    }



    public Artist(){
        this.name = null;
        this.image200  = null;
        this.image1000 = null;
    }

    public Artist(String name){
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
