package preprocessing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ui.Initialization;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Grayscale{

    public void convert() {
        try
        {
            Initialization.width = Initialization.src.getWidth();
            Initialization.height = Initialization.src.getHeight();
            for(int i=0;i<Initialization.width;i++)
                for(int j=0;j<Initialization.height;j++)
                {  
                    Color c = new Color(Initialization.src.getRGB(i,j));
                    int p=Initialization.src.getRGB(i,j);
                    int a = (p>>24)&0xff;
                    int r = (p>>16)& 0xff;
                    int g = (p>>8)& 0xff;
                    int b = (p)& 0xff;
                            
                    int grayLevel = (int)( 0.3*r+0.59*g+0.11*b);
                    grayLevel = a<<24|grayLevel<<16|grayLevel<<8|grayLevel;
                    Initialization.src.setRGB(i,j,grayLevel);
                }
            File f = new File("Images\\Grayscale.jpg");
            ImageIO.write(Initialization.src,"jpg",f);
            //System.out.println("Write Successful");
        }
        catch(Exception e)
         {
               System.out.println(e);      
         }
    }   
}
