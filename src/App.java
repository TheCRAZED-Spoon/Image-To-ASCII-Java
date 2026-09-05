import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Loading image.");
        BufferedImage bi = null;
        BufferedWriter bw = new BufferedWriter(new FileWriter("Out.txt"));
        try {
            bi = ImageIO.read(new File("./Input Images/In.png"));
            System.out.println("File read successfully.");
        }
        catch (IOException ioe) {
            System.out.println("An error has occurred reading the image file:" + ioe.toString());
            System.exit(1);
        }

        int imWidth = bi.getWidth();
        int imHeight = bi.getHeight();
        System.out.println("Image has dimensions: " + imWidth + "x" + imHeight);
        if (imHeight > 500 || imWidth > 500) {
            float scaleFactor = 1;
            if (imHeight > imWidth) {
                scaleFactor = 500/(float)imHeight;
            }
            else {
                scaleFactor = 500/(float)imWidth;
            }
            imHeight = (int)(imHeight*scaleFactor);
            imWidth = (int)(imWidth*scaleFactor);
            Image scaledI = bi.getScaledInstance(imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);
            bi = new BufferedImage(imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);
            bi.getGraphics().drawImage(scaledI, 0, 0, null);
        }

        float pixBrightness;
        for (int row = 0; row < imHeight; row++) {
            for (int col= 0; col < imWidth; col++) {
                int rgb = bi.getRGB(col, row);
                int blue = rgb & 0xff;
                int green = (rgb & 0xff00) >> 8;
                int red = (rgb & 0xff0000) >> 16;
                pixBrightness = (red + green + blue)/4;
                if (pixBrightness > 204) {
                    bw.append(' ');
                }
                else if (pixBrightness > 153) {
                    bw.append('░');
                }
                else if (pixBrightness > 102) {
                    bw.append('▒');
                }
                else if (pixBrightness > 51) {
                    bw.append('▓');
                }
                else {
                    bw.append('█');
                }
            }
            bw.newLine();
        }
    
        bw.flush();
    }
}
