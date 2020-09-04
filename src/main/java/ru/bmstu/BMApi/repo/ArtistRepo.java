package ru.bmstu.BMApi.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bmstu.BMApi.domain.Artist;

import java.util.List;

public interface ArtistRepo extends JpaRepository<Artist, Long> {

    @Query(value = "select * from artist where name = :title or name ilike '%'||:title||'%' or name = :transliteratedTitle or name ilike '%'||:transliteratedTitle||'%'", nativeQuery = true)
    List<Artist> findAllByTitle(@Param("title") String title, @Param("transliteratedTitle") String transliteratedTitle);
}
