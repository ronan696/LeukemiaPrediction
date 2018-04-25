package preprocessing;

import ui.Initialization;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Opening {

    Integer w = Initialization.src.getWidth();
    Integer h = Initialization.src.getHeight();
    Integer s = 7;
    int [][] arrImage = new int[w][h];
    int [][] result = new int[w][h];
    BufferedImage temp = null;
    BufferedImage opened = new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
    public void open() {
    try{
        File f = new File("Images\\binary.png");
        temp = ImageIO.read(f);
        if(w>300)
            s = 17;
        //reading image into the array
        for(int i=0;i<w;i++)
            for(int j=0;j<h;j++)
            {   
                Color c = new Color(Initialization.src.getRGB(i,j));
                arrImage[i][j] = c.getRed();
            }
        
        //Erosion
        boolean erode = true;
        for(int i=0;i<w;i++)
        {
            for(int j=0;j<h;j++)
            {
                    if(i<s/2 || j<s/2  || i>w-1-s/2 || j>h-1-s/2)
                    {
                        result[i][j] = 0;
                    }
                    else
                    {    
                        int x = i - s/2, y = j - s/2;
                        for(int c=0;c<=s/2;c++)
                        {
                            int space = s/2-c;
                            for(int d=space;d<(space+ (2*c+1));d++)
                            {

                                if(arrImage[x+c][y+d] < 127)
                                {
                                    result[i][j] = 0;
                                    erode = false;
                                }
                            }
                        }
                        for(int c=s/2+1;c<s;c++)
                        {
                            int space = c - s/2;
                            for(int d=space;d<(space+ (2*(s/2-space)+1));d++)
                            {

                                if(arrImage[x+c][y+d] < 127)
                                {
                                    result[i][j] = 0;
                                    erode = false;
                                }
                            }
                        }
                        if(erode)
                        {
                            result[i][j] = 255;
                        }
                    }
                erode = true;
            }
        }
            
        for(int i=0;i<w;i++)
               for(int j=0;j<h;j++)
               {    
                   arrImage[i][j] = result[i][j];
               }
        
        //Dilation
        boolean dilate = true;
        for(int i=0;i<w;i++)
        {
            for(int j=0;j<h;j++)
            {
                        int x = i - s/2, y = j - s/2;
                        for(int c=0;c<=s/2;c++)
                        {
                            int space = s/2-c;
                            for(int d=space;d<(space+ (2*c+1));d++)
                            {
                                if((x+c)>=0 && (x+c)<w && (y+d)>=0 && (y+d)<h)
                                    if(arrImage[x+c][y+d] > 127)
                                    {
                                        result[i][j] = 255;
                                        dilate = false;
                                    }
                            }
                        }
                        for(int c=s/2+1;c<s;c++)
                        {
                            int space = c - s/2;
                            for(int d=space;d<(space+ (2*(s/2-space)+1));d++)
                            {
                                if((x+c)>=0 && (x+c)<w && (y+d)>=0 && (y+d)<h)
                                    if(arrImage[x+c][y+d] > 127)
                                    {
                                        result[i][j] = 255;
                                        dilate = false;
                                    }
                            }
                        }
                        if(dilate)
                        {
                            result[i][j] = 0;
                        }
                dilate = true;
            }
        }
        
//        boolean dilate = true;
//        for(int i=0;i<w;i++)
//        {
//            for(int j=0;j<h;j++)
//            {
//                    for(int c=i-s/2;c<i+s/2+1 && c>=0 && c<w;c++)
//                    {
//                        for(int d=j-s/2;d<j+s/2+1 && d>=0 && d<h;d++)
//                        {
//                            
//                            if(arrImage[c][d] > 127)
//                            {
//                                result[i][j] = 255;
//                                dilate = false;
//                            }
//                        }
//                    }
//                    if(dilate)
//                    {
//                        result[i][j] = 0;
//                    }
//                dilate = true;
//            }
//        }
        
        for(int i=0;i<w;i++)
               for(int j=0;j<h;j++)
               {    
                    int b = result[i][j];
                    Color newc = new Color(b,b,b);
                    Initialization.src.setRGB(i, j, newc.getRGB());
               }
        f= new File("Images\\Opened.png");
        ImageIO.write(Initialization.src,"png",f);
        //System.out.println("Write Successfull Opened");

       }
        catch(Exception e)
        {
            e.printStackTrace();
        }
      }
}
