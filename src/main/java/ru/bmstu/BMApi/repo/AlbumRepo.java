package ru.bmstu.BMApi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bmstu.BMApi.domain.Album;

import java.util.List;

public interface AlbumRepo  extends JpaRepository<Album, Long> {

    @Query(value = "select * from album where id_artist1 = :idArtist", nativeQuery = true)
    List<Album> findAllByArtist(@Param("idArtist") Long idArtist);

    @Query(value = "select * from album where title = :title or title ilike '%'||:title||'%' or title = :transliteratedTitle or title ilike '%'||:transliteratedTitle||'%'", nativeQuery = true)
    List<Album> findAllByTitle(@Param("title") String title, @Param("transliteratedTitle") String transliteratedTitle);
}
