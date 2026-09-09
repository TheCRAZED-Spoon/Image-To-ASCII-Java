import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Loading image.");
        BufferedImage bi = null;
        Scanner sc =  new Scanner(System.in);
        BufferedWriter bw = new BufferedWriter(new FileWriter("Out.txt"));
        JFileChooser fC = new JFileChooser();
        fC.setDialogTitle("Select and image file");

        int maxScale = 0;
        try {
            int result = fC.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                bi = ImageIO.read(fC.getSelectedFile());
                System.out.println("File read successfully.");
                System.out.println("What do you want your max image dimension to be? (in integer format)");
                maxScale = sc.nextInt();
            }
            else {
                System.out.println("File Read Unsuccessful");
            }
        }
        catch (IOException ioe) {
            System.out.println("An error has occurred opening the image file:" + ioe.toString());
            System.exit(1);
        }

        int imWidth = bi.getWidth();
        int imHeight = bi.getHeight();
        System.out.println("Image has dimensions: " + imWidth + "x" + imHeight);
        if (imHeight > maxScale || imWidth > maxScale) {
            float scaleFactor = 1;
            if (imHeight > imWidth) {
                scaleFactor = maxScale/(float)imHeight;
            }
            else {
                scaleFactor = maxScale/(float)imWidth;
            }
            imHeight = (int)(imHeight*scaleFactor);
            imWidth = (int)(imWidth*scaleFactor);
            Image scaledI = bi.getScaledInstance(imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);
            bi = new BufferedImage(imWidth, imHeight, BufferedImage.TYPE_INT_ARGB);
            bi.getGraphics().drawImage(scaledI, 0, 0, null);
            System.out.println("Image successfully scaled to " + imWidth + "x" + imHeight);
        }


        float pixBrightness;
        int rgb;
        int blue;
        int green;
        int red;

        for (int row = 0; row < imHeight; row++) {
            for (int col= 0; col < imWidth; col++) {
                rgb = bi.getRGB(col, row);
                blue = rgb & 0xff;
                green = (rgb & 0xff00) >> 8;
                red = (rgb & 0xff0000) >> 16;
                pixBrightness = (red + green + blue)/3;
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
        System.out.println("Image converted to text and found at ./Out.txt");
    }
}
