package preprocessing;

import ui.Initialization;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class ImageArithmetic{
    
    BufferedImage temp1= null,temp2=null;
    File f= null, g = null;
    
    public void compute()
    {
        
       try
         {  
            //to get image I1
            f= new File("Images\\HistogramEqualization.jpg");
            g= new File("Images\\ContrastSt.jpg");
            
            temp1 = ImageIO.read(f);
            temp2 = ImageIO.read(g);
            Integer w = Initialization.src.getWidth();
            Integer h = Initialization.src.getHeight();
           
           
           for(int i=0;i<w;i++)
             for(int j=0;j<h;j++)
             {
                 Color c = new Color(temp1.getRGB(i,j));
                 Color c1 = new Color(temp2.getRGB(i,j));
                 int b = c.getRed()+c1.getRed();
                 if(b>255)
                     b=255;
                 Color newc = new Color(b,b,b);
                  temp1.setRGB(i, j, newc.getRGB());
            }
            f = new File("Images\\I1.jpg");
            ImageIO.write(temp1,"jpg",f);
            //System.out.println("Write Successfull I1");
             
             //to get get I2
             temp1=ImageIO.read(f);
             g = new File("Images\\HistogramEqualization.jpg");
             temp2=ImageIO.read(g);
             
              for(int i=0;i<w;i++)
             for(int j=0;j<h;j++)
             {
                 Color c1= new Color(temp1.getRGB(i,j));
                 Color c= new Color(temp2.getRGB(i,j));
                
                 int b=c1.getRed()-c.getRed();
                 b=Math.abs(b);
                 Color newc = new Color(b,b,b);
                 temp1.setRGB(i, j, newc.getRGB());
             }
           
            f= new File("Images\\I2.jpg");
            ImageIO.write(temp1,"jpg",f);
            //System.out.println("Write Successfull I2");
            
            
            //to get I3
            f=new File("Images\\I1.jpg");
            g=new File("Images\\I2.jpg");
             temp1=ImageIO.read(f);
            temp2=ImageIO.read(g);
            for(int i=0;i<w;i++)
             for(int j=0;j<h;j++)
             {
                 Color c1= new Color(temp1.getRGB(i,j));
                 Color c= new Color(temp2.getRGB(i,j));
                
                 int b=c1.getRed()+c.getRed();
                 
                   if(b>255)b=255;
                 Color newc = new Color(b,b,b);
                  Initialization.src.setRGB(i, j, newc.getRGB());
             }
           
            f = new File("Images\\I3.jpg");
            ImageIO.write(Initialization.src,"jpg",f);
            //System.out.println("Write Successfull I3");
        }
       
       catch(Exception e)
         {
             System.out.println(e);
         }
       
    }
}
