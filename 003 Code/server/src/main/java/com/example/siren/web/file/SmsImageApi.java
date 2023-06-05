package com.example.siren.web.file;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SmsImageApi {
    public BufferedImage getPickup(String paramForUrl) {
        try{
            String URL = "paramForUrl";

            java.net.URL url = new URL(URL); //URL 객체 생성

            return ImageIO.read(url); //URL의 이미지를 BufferedImage로 반환
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
