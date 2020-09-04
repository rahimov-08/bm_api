package ru.bmstu.BMApi.controller;

import libs.ImageResize;
import libs.Translit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.bmstu.BMApi.domain.Artist;
import ru.bmstu.BMApi.repo.ArtistRepo;

import javax.imageio.ImageIO;
import javax.management.StandardEmitterMBean;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.List;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${custom.address}")
    private String address;

    private final ArtistRepo artistRepo;

    @Autowired
    public ArtistController(ArtistRepo artistRepo) {
        this.artistRepo = artistRepo;
    }

    @GetMapping
    public List<Artist> list(){
        return artistRepo.findAll();
    }

    @GetMapping("{id}")
    public Artist getOne(@PathVariable("id") Artist artist){
        return artist;
    }

    @GetMapping("title/{titleName}")
    public List<Artist> getByTitle(@PathVariable("titleName") String title){
        String transliteratedString;

        if(Pattern.matches(".*\\p{InCyrillic}.*", title)){
            transliteratedString = Translit.cyr2lat(title);
        }else {
            transliteratedString = Translit.lat2cyr(title);
        }

        return artistRepo.findAllByTitle(title,transliteratedString);
    }

    //раздача фото артиста
    @GetMapping("image/1000x1000/{imageName}")
    public ResponseEntity<Resource> getImage1000(@PathVariable("imageName") String imageName) throws IOException {

        File image = new File(uploadPath + "/img/artist/1000x1000/" + imageName);
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

    @GetMapping("image/200x200/{imageName}")
    public ResponseEntity<Resource> getImage200(@PathVariable("imageName") String imageName) throws IOException {

        File image = new File(uploadPath + "/img/artist/200x200/" + imageName);
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
    public Artist create(
            @RequestParam String name,
            @RequestParam("file") MultipartFile file) throws IOException {

        Artist artist = new Artist(name);

        if(file != null){
            File uploadDir200 = new File(uploadPath + "/img/artist/200x200");
            File uploadDir1000 = new File(uploadPath + "/img/artist/1000x1000");

            if(!uploadDir200.exists()){
                uploadDir200.mkdir();
            }
            if(!uploadDir1000.exists()){
                uploadDir1000.mkdir();
            }


            String uuidFile =  UUID.randomUUID().toString();
            String resultFileName200 = "200x200_" + uuidFile ;
            String resultFileName1000 = "1000x1000_" + uuidFile ;

            file.transferTo(new File( uploadPath + "/img/artist/1000x1000/" + resultFileName1000));

            BufferedImage image1000 = ImageIO.read(new File(uploadPath + "/img/artist/1000x1000/" + resultFileName1000));
            BufferedImage image200 = null;
            int type = image1000.getType() == 0? BufferedImage.TYPE_INT_ARGB : image1000.getType();
            int x = 0,y=0,w=0,h=0;
            /*
            x - the X coordinate of the upper-left corner of the specified rectangular region
            y - the Y coordinate of the upper-left corner of the specified rectangular region
            w - the width of the specified rectangular region
            h - the height of the specified rectangular region
            */
            int width = image1000.getWidth();
            int height = image1000.getHeight();
            int delta = (width - height)/2;

            if (delta != 0){
                if(delta > 0){
                    x = delta;
                    y = 0;
                    w = height;
                    h = height;
                }
                if(delta < 0){
                    x = 0;
                    y = Math.abs(delta);
                    w = width;
                    h = width;
                }

                image1000 = image1000.getSubimage(x+1,y+1,w-2,h-2);

            }

           image200 = ImageResize.resizeImage(image1000,type,200,200);
            ImageIO.write(image200,"png",new File( uploadPath + "/img/artist/200x200/" + resultFileName200));

            artist.setImage200("http://"+address+":9000/artist/image/200x200/" + resultFileName200);
            artist.setImage1000("http://"+address+":9000/artist/image/1000x1000/" + resultFileName1000);
        }
        return artistRepo.save(artist);
    }

    @PutMapping("{id}")
    public Artist update(@PathVariable("id") Artist artistFromDB, @RequestBody Artist artist){
        BeanUtils.copyProperties(artist,artistFromDB,"id");
        return artistRepo.save(artist);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Artist artist){

        String imageLink200 = artist.getImage200();
        String imageLink1000 = artist.getImage1000();
        String image200 = imageLink200.substring(imageLink200.lastIndexOf('/')+1, imageLink200.length());
        String image1000 = imageLink1000.substring(imageLink1000.lastIndexOf('/')+1, imageLink1000.length());
        String imagePath200 = uploadPath + "/img/artist/200x200/" + image200;
        String imagePath1000 = uploadPath + "/img/artist/1000x1000/" + image1000;
        File imageDir200 = new File(imagePath200);
        File imageDir1000 = new File(imagePath1000);

        if(imageDir200.exists()){
            imageDir200.delete();
        }
        if(imageDir1000.exists()){
            imageDir1000.delete();
        }

        artistRepo.delete(artist);
    }
}
