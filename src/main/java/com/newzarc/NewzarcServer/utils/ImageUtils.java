package com.newzarc.NewzarcServer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static final String FOLDER_PATH="D:/Me/Downloads/NewzarcServer/NewzarcServer/src/main/java/com/newzarc/NewzarcServer/videos/";

    public static Boolean uploadImageToFileSystem(MultipartFile file, String name, java.io.File newsFolder) throws IOException {
        try {
//            String filePath=newsFolder+"/"+name;
            file.transferTo(new File(newsFolder.toString()));
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
