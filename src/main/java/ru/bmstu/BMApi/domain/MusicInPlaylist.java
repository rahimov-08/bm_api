package ru.bmstu.BMApi.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode
public class MusicInPlaylist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long idPlaylsit;
    private Long idMusic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlaylsit() {
        return idPlaylsit;
    }

    public void setIdPlaylsit(Long idPlaylsit) {
        this.idPlaylsit = idPlaylsit;
    }

    public Long getIdMusic() {
        return idMusic;
    }

    public void setIdMusic(Long idMusic) {
        this.idMusic = idMusic;
    }
}
