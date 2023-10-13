package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.service.base.STFService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@Service
public class STFServiceImpl implements STFService {
    @Override
    public void createThumbnailImage(String pathImage) {
        try {
            BufferedImage img = ImageIO.read(new File(Constant.BOOK_IMAGE_STORAGE_PATH + pathImage));
            double scale = (double) img.getWidth(null) / (double) 300;
            int height = Double.valueOf(img.getHeight(null) / scale).intValue();
            java.awt.Image newImg = img.getScaledInstance(300, height, java.awt.Image.SCALE_SMOOTH);
            BufferedImage bsi = new BufferedImage(newImg.getWidth(null), newImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            bsi.getGraphics().drawImage(newImg, 0, 0, null);
            File destFile = new File(Constant.BOOK_THUMBNAIL_STORAGE_PATH + pathImage);
            destFile.getParentFile().mkdirs();
            ImageIO.write(bsi, "jpg", destFile);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void createChapterText(String chapterContent, String fileName) {
        File file = new File(Constant.CHAPTER_TEXT_STORAGE_PATH, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(chapterContent);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public String getChapterContent(String fileName) {
        StringBuilder content = new StringBuilder("");
        try {
            FileReader fileReader = new FileReader(Constant.CHAPTER_TEXT_STORAGE_PATH + fileName);
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

}
