package preprocessing;

import ui.*;
import java.awt.Color;
import java.io.File;
import java.util.*;
import javax.imageio.ImageIO;

public class ContrastStretching {
    
    Map<Integer,Integer> intensity = new HashMap<>();
    ArrayList<Integer> levels = null;
    public void performcs()
    {   
        try
        {
            Initialization.src = ImageIO.read(new File("Images\\Grayscale.jpg"));
            //System.out.println(Initialization.width + " , " + Initialization.height);
            for(int i=0;i<Initialization.src.getWidth();i++)
               for(int j=0;j<Initialization.src.getHeight();j++)
                {
                    Color c = new Color(Initialization.src.getRGB(i,j));
                    Integer level = c.getRed();
                    intensity.put(level,0);
                }
            levels = new ArrayList<>(intensity.keySet());
            Collections.sort(levels);
            Integer min = levels.get(0);
            Integer max = levels.get(levels.size()-1);
            for(Integer level : intensity.keySet())
            {
                intensity.put(level,(int) Math.round(255.0*(level-min)/(max-min)));
            }
            for(int i=0;i<Initialization.width;i++)
                for(int j=0;j<Initialization.height;j++)
                {
                     Color c = new Color(Initialization.src.getRGB(i,j));
                     int result = intensity.get(c.getRed());
                     Color newc = new Color(result,result,result);
                     Initialization.src.setRGB(i, j, newc.getRGB());
                }
            File f= new File("Images\\ContrastSt.jpg");
            ImageIO.write(Initialization.src,"jpg",f);
            //System.out.println("Write Successful Constrast");   
        }
        catch(Exception e)
        {
            System.out.println(e);
        }    
    }
}
