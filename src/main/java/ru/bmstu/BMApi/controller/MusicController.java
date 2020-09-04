package ru.bmstu.BMApi.controller;

import libs.MultipartFileSender;
import libs.Song;
import libs.Translit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bmstu.BMApi.domain.Music;
import ru.bmstu.BMApi.repo.MusicRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/music")
public class MusicController {
    private final MusicRepo musicRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${custom.address}")
    private String address;

    @Autowired
    public MusicController(MusicRepo musicRepo) {
        this.musicRepo = musicRepo;
    }

    @GetMapping
    public List<Music> list() {
        return musicRepo.findAll();
    }

    @GetMapping("{id}")
    public Music getOne(@PathVariable("id") Music music) {
        return music;
    }

    @GetMapping("artist/{id}")
    public List<Music> getMusicsByArtist(@PathVariable("id") Long id) {
        return musicRepo.findAllByArtist(id);
    }

    @GetMapping("album/{id}")
    public List<Music> getMusicByAlbum(@PathVariable("id") Long id) {
        return musicRepo.findAllByAlbum(id);
    }

    @GetMapping("title/{titleName}")
    public List<Music> getByTitle(@PathVariable("titleName") String title) {
        String transliteratedString;

        if (Pattern.matches(".*\\p{InCyrillic}.*", title)) {
            transliteratedString = Translit.cyr2lat(title);
        } else {
            transliteratedString = Translit.lat2cyr(title);
        }

        return musicRepo.findAllByTitle(title, transliteratedString);
    }


    @RequestMapping(value = "mp3/{fileName}", method = RequestMethod.GET)
    public void getFile(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MultipartFileSender.fromFile(new File(uploadPath+"/music/"+fileName)).with(request).with(response).serveResource();
    }


    @PostMapping
    public Music create(@RequestParam("title") String title,
                        @RequestParam("idArtist1") Long idArtist1,
                        @RequestParam("idArtist2") Long idArtist2,
                        @RequestParam("idArtist3") Long idArtist3,
                        @RequestParam("idArtist4") Long idArtist4,
                        @RequestParam("idArtist5") Long idArtist5,
                        @RequestParam("idArtist6") Long idArtist6,
                        @RequestParam("idArtist7") Long idArtist7,
                        @RequestParam("file") MultipartFile file,
                        @RequestParam("idAlbum") Long idAlbum,
                        @RequestParam("genre") String genre,
                        @RequestParam("year") int year,
                        @RequestParam("imageLink") String imageLink
                        ) throws IOException, UnsupportedAudioFileException {

        Music music = new Music(title,idArtist1,idArtist2,idArtist3,idArtist4,idArtist5,idArtist6,idArtist7);

        if (file !=null){

            File uploadDir = new File(uploadPath + "/music");

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile;

            file.transferTo(new File(uploadPath + "/music/"+ resultFileName));

            File mp3 = new File(uploadPath + "/music/"+ resultFileName);
            music.setDuration(Song.getDuration(mp3));

            music.setMusicLink("http://"+address+":9000/music/mp3/"+resultFileName);



        }
        music.setGenre(genre);
        music.setYear(year);
        music.setIdAlbum(idAlbum);
        music.setImageLink(imageLink);
        return musicRepo.save(music);
    }

    @PutMapping("{id}")
    public Music update(@PathVariable("id") Music musicFromDB, @RequestBody Music music){
        BeanUtils.copyProperties(music,musicFromDB,"id");
        return musicRepo.save(music);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Music music){

        String musicLink = music.getMusicLink();
        String mp3 = musicLink.substring(musicLink.lastIndexOf('/')+1, musicLink.length());

        String musicPath = uploadPath + "/music/" + mp3;

        File musicDir = new File(musicPath);



        if(musicDir.exists()){
            musicDir.delete();
        }


        musicRepo.delete(music);
    }
}
