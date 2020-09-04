package ru.bmstu.BMApi.controller;

import libs.Translit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bmstu.BMApi.domain.Album;
import ru.bmstu.BMApi.domain.Artist;
import ru.bmstu.BMApi.repo.AlbumRepo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final AlbumRepo albumRepo;

    @Value("${upload.path}")
    private  String uploadPath;

    @Value("${custom.address}")
    private String address;

    @Autowired
    public AlbumController(AlbumRepo albumRepo) {
        this.albumRepo = albumRepo;
    }

    @GetMapping
    public List<Album> getAll(){
        return albumRepo.findAll();
    }

    @GetMapping("{id}")
    public Album getOne(@PathVariable("id") Album album){
        return album;
    }

    @GetMapping("artist/{id}")
    public List<Album> getAlbumsByArtist(@PathVariable("id") Long id){
        return albumRepo.findAllByArtist(id);
    }

    @GetMapping("title/{titleName}")
    public List<Album> getAlbumsByTitle(@PathVariable("titleName") String title){
        String transliteratedString;

        if(Pattern.matches(".*\\p{InCyrillic}.*", title)){
            transliteratedString = Translit.cyr2lat(title);
        }else {
            transliteratedString = Translit.lat2cyr(title);
        }

        return albumRepo.findAllByTitle(title,transliteratedString);
    }

    @GetMapping("image/{imageName}")
    public ResponseEntity<Resource> getImage200(@PathVariable("imageName") String imageName) throws IOException {

        File image = new File(uploadPath + "/img/album/" + imageName);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(image));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(image.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PostMapping
    public Album create(@RequestParam("title") String title,
                        @RequestParam("genre") String genre,
                        @RequestParam("single") boolean single,
                        @RequestParam("year") int year,
                        @RequestParam("idArtist1") Long idArtist1,
                        @RequestParam("idArtist2") Long idArtist2,
                        @RequestParam("idArtist3") Long idArtist3,
                        @RequestParam("idArtist4") Long idArtist4,
                        @RequestParam("idArtist5") Long idArtist5,
                        @RequestParam("idArtist6") Long idArtist6,
                        @RequestParam("idArtist7") Long idArtist7,
                        @RequestParam("idArtist8") Long idArtist8,
                        @RequestParam("idArtist9") Long idArtist9,
                        @RequestParam("idArtist10") Long idArtist10,
                        @RequestParam("file") MultipartFile file) throws IOException {

        Album album = new Album(title,genre,single,year,idArtist1,idArtist2,idArtist3,idArtist4,idArtist5,idArtist6,idArtist7,idArtist8,idArtist9,idArtist10);

        if(file != null){
            File uploadDir = new File(uploadPath + "/img/album");


            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = "200x200_" + uuidFile;

            file.transferTo(new File(uploadPath + "/img/album/" + resultFileName));
            album.setImageLink("http://"+address+":9000/album/image/"+ resultFileName);
        }


        return albumRepo.save(album);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Album album){

        String imageLink = album.getImageLink();
        String image = imageLink.substring(imageLink.lastIndexOf('/')+1, imageLink.length());

        String imagePath200 = uploadPath + "/img/album/" + image;

        File imageDir = new File(imagePath200);



        if(imageDir.exists()){
            imageDir.delete();
        }

        albumRepo.delete(album);
    }

}
