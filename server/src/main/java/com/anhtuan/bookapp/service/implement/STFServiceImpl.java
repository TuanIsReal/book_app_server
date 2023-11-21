package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.service.base.STFService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.anhtuan.bookapp.config.Constant.*;

@Service
public class STFServiceImpl implements STFService {
    @Override
    public void createThumbnail(String folderPath, String pathImage) {
        try {
            BufferedImage img = ImageIO.read(new File(folderPath + pathImage));
            double scale = (double) img.getWidth(null) / (double) 300;
            int height = Double.valueOf(img.getHeight(null) / scale).intValue();
            java.awt.Image newImg = img.getScaledInstance(300, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage bsi = new BufferedImage(newImg.getWidth(null), newImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            bsi.getGraphics().drawImage(newImg, 0, 0, null);
            File destFile = new File(Constant.THUMBNAIL_STORAGE_PATH + pathImage);
            destFile.getParentFile().mkdirs();
            ImageIO.write(bsi, "jpg", destFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void createChapterText(String chapterContent, String fileName) {
        File file = new File(Constant.CHAPTER_TEXT_STORAGE_PATH, fileName);
        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(chapterContent);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String getChapterContent(String fileName) {
        StringBuilder content = new StringBuilder("");
        try {
            FileReader fileReader = new FileReader(Constant.CHAPTER_TEXT_STORAGE_PATH + fileName, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }

            bufferedReader.close();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return content.toString();
    }

    @Override
    public Map<String, String> getBookImagePathMap(List<Book> books) {
        if (books == null || books.isEmpty()) return null;

        return books.stream()
                .filter(book -> book.getBookImage() != null && !book.getBookImage().isBlank())
                .collect(Collectors.toMap(Book::getId, book -> THUMBNAIL_STORAGE_PATH_RESPONSE + book.getBookImage() + Constant.JPG));
    }

    @Override
    public Map<String, String> getUserAvatarPathMap(List<User> users) {
        if (users == null || users.isEmpty()){
            return null;
        }

        return users.stream()
                .filter(user -> user.getAvatarImage() != null && !user.getAvatarImage().isBlank())
                .collect(Collectors.toMap(User::getId, user -> (user.getIsGoogleLogin() == null || !user.getIsGoogleLogin()) ? THUMBNAIL_STORAGE_PATH_RESPONSE + user.getAvatarImage() + JPG : user.getAvatarImage()));
    }

}
