package preprocessing;

import ui.Initialization;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class KMeans {
    
    double c1,c2;
    Integer [][] pixels = null;
    Integer w,h;
    BufferedImage binary = new BufferedImage(Initialization.src.getWidth(),Initialization.src.getHeight(),BufferedImage.TYPE_BYTE_BINARY);
    ArrayList<Integer> CLUSTER1 = new ArrayList<>();
    ArrayList<Integer> CLUSTER2 = new ArrayList<>();
    ArrayList<Integer> CLUSTER1_OLD = new ArrayList<>();
    ArrayList<Integer> CLUSTER2_OLD = new ArrayList<>();
    public void init()
    {
        w = Initialization.src.getWidth();
        h = Initialization.src.getHeight();
        pixels = new Integer[w][h];
        for(int i=0;i<w;i++)
            for(int j=0;j<h;j++)
            {
                Color c = new Color(Initialization.src.getRGB(i, j));
                pixels[i][j] = c.getRed();
            }
        Random r = new Random();
        c1 = r.nextDouble()*159;
        c2 = 160 + r.nextDouble()*(255-160);
        calc();
    }
    
    public void calc()
    {
        try
        {
        Integer max,count = 0;
        while(true)
        {
            for(int i=0;i<w;i++)
                for(int j=0;j<h;j++)
                {
                    double temp1 = Math.sqrt(Math.pow(c1-pixels[i][j],2));
                    double temp2 = Math.sqrt(Math.pow(c2-pixels[i][j],2));
                    if(temp1 < temp2)
                    {
                        CLUSTER1.add(pixels[i][j]);
                    }
                    else
                    {
                        CLUSTER2.add(pixels[i][j]);
                    }
                }
            double sum1 = 0, sum2 = 0;
            max = 0;
            for (Integer px : CLUSTER1) {
                sum1 += px;
                if(px > max)
                    max = px;
            }
            sum1 /= CLUSTER1.size();
            for (Integer px : CLUSTER2) {
                sum2 += px;
            }
            sum2 /= CLUSTER2.size();
            //System.out.println("Mean 1 : " + sum1 + " Mean 2 : " + sum2);
            
            if(Math.abs(sum1-c1) < 0.01 && Math.abs(sum2 - c2) < 0.01 )
            {
                //System.out.println("Broke from Means");
                break;
            }
            if(CLUSTER1.equals(CLUSTER1_OLD) && CLUSTER2.equals(CLUSTER2_OLD))
            {
                //System.out.println("Broke from Clusters");
                break;
            }
            if(count>10)
            {
                //System.out.println("Broke from Threshold");
                break;
            }
            CLUSTER1_OLD = new ArrayList<>(CLUSTER1);
            CLUSTER2_OLD = new ArrayList<>(CLUSTER2);
            CLUSTER1.clear();
            CLUSTER2.clear();
            c1 = sum1;
            c2 = sum2;
            count++;
        }
        
        
        //System.out.println("Max : " + max);
        for(int i=0;i<w;i++)
            for(int j=0;j<h;j++)
            {
                Color c = new Color(Initialization.src.getRGB(i, j));
                if(c.getRed() <= max)     
               {    
                   Initialization.src.setRGB(i, j, Color.WHITE.getRGB());
                   binary.setRGB(i, j, Color.WHITE.getRGB());
               }
                else
                {
                    Initialization.src.setRGB(i, j, Color.BLACK.getRGB());
                    binary.setRGB(i, j, Color.BLACK.getRGB());
                }
            }
//        File f = new File("Images\\kMeans.jpg");
//        ImageIO.write(Home.src,"jpg",f);
        ImageIO.write(binary,"png",new File("Images\\binary.png"));
        //System.out.println("Write Successfull kMeans");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
