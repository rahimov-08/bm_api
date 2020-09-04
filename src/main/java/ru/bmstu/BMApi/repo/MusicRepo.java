package ru.bmstu.BMApi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.bmstu.BMApi.domain.Music;

import java.util.List;


public interface MusicRepo extends JpaRepository<Music, Long> {

    @Query(value = "select * from music " +
            "where id_artist1 = :idArtist or id_artist2 = :idArtist or id_artist3 = :idArtist or id_artist4 = :idArtist " +
            "or id_artist5 = :idArtist or id_artist6 = :idArtist or id_artist7 = :idArtist or id_artist8 = :idArtist", nativeQuery = true)
    List<Music> findAllByArtist(@Param("idArtist") Long idArtist);

    @Query(value = "select * from music where id_Album = :idAlbum", nativeQuery = true)
    List<Music> findAllByAlbum(@Param("idAlbum") Long idAlbum);

    @Query(value = "select * from music where title = :title or title ilike '%'||:title||'%' or title = :transliteratedTitle or title ilike '%'||:transliteratedTitle||'%'", nativeQuery = true)
    List<Music> findAllByTitle(@Param("title") String title, @Param("transliteratedTitle") String transliteratedTitle);

}
