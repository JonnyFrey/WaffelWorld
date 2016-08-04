package com.waffel.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Jonny on 7/31/16.
 */
public enum Images {

    ERROR("misc/error.png"),
    ARTIFACT("misc/Artifact.gif"),

    //Backgrounds
    CLOUD_BACKGROUND("backgrounds/clouds.gif"),
    MOUNTAINS_BACKGROUND("backgrounds/mountains.gif"),
    SKY_BACKGROUND("backgrounds/sky.gif"),

    //animals
    Player("animals/player.gif");

    private String location;
    private int width;
    private int height;

    Images(String location) {
        this.location = location;
        this.width = 1;
        this.height = 1;
    }


    public BufferedImage loadImage() throws IOException {
        return ImageIO.read(Files.newInputStream(Paths.get("src/main/resources/", location)));
    }

    public BufferedImage[][] loadSpriteSheet() throws IOException {
        BufferedImage fullImage = loadImage();
        int w = fullImage.getWidth() / width;
        int h = fullImage.getHeight() / height;
        BufferedImage[][] spriteSheet = new BufferedImage[h][w];
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                spriteSheet[i][j] = fullImage.getSubimage(j * width, i * height, width, height);
        return spriteSheet;
    }

}
