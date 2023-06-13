package com.newzarc.NewzarcServer.controller;

import com.newzarc.NewzarcServer.model.NewsPost;
import com.newzarc.NewzarcServer.repository.NewsRepo;
import com.newzarc.NewzarcServer.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.PrintException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.newzarc.NewzarcServer.utils.ImageUtils.FOLDER_PATH;

@RestController
public class NewsController {

    @Autowired
    private NewsRepo newsRepo;

    @GetMapping("/allnews")
    List<NewsPost> getAllNews() {
        return newsRepo.findAll();
    }

    @GetMapping("/news/{id}")
    Optional<NewsPost> getNewsById(@PathVariable Long id) {
        return newsRepo.findById(id);
    }

    @PostMapping("/news/create")
    NewsPost addNews(@RequestBody NewsPost news) {
        return newsRepo.save(news);
    }


//    @PostMapping("/create")
//    public ResponseEntity<?> createZule(
//            @RequestParam Map<String,String> body,
//            @RequestParam("videoNews") MultipartFile videoNews,
//            @RequestParam("imageNews")MultipartFile imageNews
//
//    ) throws IOException {
////        Optional<Users> user = userRepository.findById(body.get("userId"));
////        Optional<Zulespot> zulespot = zulespotRepository.findById(body.get("zulespotId"));
//
//
//
////        if(user.isEmpty()||zulespot.isEmpty()) {
////            return ResponseEntity.badRequest().body("Invalid request");
////        }
//
////        String zuleId = UUID.randomUUID().toString();
//
////        Zule newZule = new Zule(
////                zuleId,
////                body.get("title"),
////                body.get("description"),
////                List.of(body.get("tags")),
////                body.get("genre"),
////                body.get("cbfc_rating"),
////                body.get("zulespotId")
////        );
//
//        java.io.File newsFolder = new java.io.File(FOLDER_PATH+"abc");
//
//        if (!newsFolder.exists()) {
//            newsFolder.mkdirs();
//        }
//        if(
//                !(ImageUtils.uploadImageToFileSystem(videoNews,"video.mp4",newsFolder) &&
//                        ImageUtils.uploadImageToFileSystem(imageNews,"image.jpg",newsFolder))
//        ){
//            if (newsFolder.exists()) {
//                newsFolder.delete();
//            }
//            return ResponseEntity.badRequest().body("Something went wrong");
//        }
//
////        String thumbnail_16_9_file = host+"/fetch/"+zulespot.get().getzulespotId()+"/"+user.get().getUserId()+"/"+zuleId+"_zule-thumbnail.jpg";
////        String thumbnail_9_16_file = host+"/fetch/"+zulespot.get().getzulespotId()+"/"+user.get().getUserId()+"/"+zuleId+"_teaser-thumbnail.jpg";;
////        String zule_file = host+"/fetch/"+zulespot.get().getzulespotId()+"/"+user.get().getUserId()+"/"+zuleId+"_zule.mp4";;
////        String teaser_file = host+"/fetch/"+zulespot.get().getzulespotId()+"/"+user.get().getUserId()+"/"+zuleId+"_teaser.mp4";;
//
////        newZule.setThumbnail_16_9(thumbnail_16_9_file);
////        newZule.setThumbnail_9_16(thumbnail_9_16_file);
////        newZule.setZule(zule_file);
////        newZule.setTeaser(teaser_file);
////
////        newZule=zuleRepository.save(newZule);
////        List<String> zules=zulespot.get().getZules();
////        zules.add(newZule.getZuleId());
////        zulespot.get().setZules(zules);
////        zulespotRepository.save(zulespot.get());
////
//            return ResponseEntity.ok("ok");
//
//        }

    @PutMapping("/news/update/{id}")
    NewsPost updateNews(@RequestBody NewsPost addNews, @PathVariable Long id) {
        try {
            return newsRepo.findById(id)
                    .map(news -> {
                        news.setContent(addNews.getContent());
                        news.setDescription(addNews.getDescription());
                        news.setTitle(addNews.getTitle());
                        news.setImage_url(addNews.getImage_url());
                        news.setPubDate(addNews.getPubDate());
                        news.setVideoUrl(addNews.getVideoUrl());
                        return newsRepo.save(news);
                    }).orElseThrow(() -> new PrintException("News Update Failed"));
        } catch (PrintException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/news/delete/{id}")
    String deleteNews(@PathVariable Long id) throws PrintException {
        if (!newsRepo.existsById(id)) {
            throw new PrintException("News not found");
        };
        newsRepo.deleteById(id);
        return "News deleted successfully " + id;
    }
}
