package preprocessing;

import ui.Initialization;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;

public class HistogramEqualization{
    Map<Integer,Integer> intensity = new HashMap<>();
    ArrayList<Integer> levels = null;
    public void performHistEqualization() {
    
      try
        {   
            for(int i=0;i<Initialization.src.getWidth();i++)
             for(int j=0;j<Initialization.src.getHeight();j++)
               {
                Color c = new Color(Initialization.src.getRGB(i,j));
                Integer level = c.getRed();
                if(intensity.containsKey(level))
                {
                    intensity.put(level,intensity.get(level)+1);
                }
                else
                    intensity.put(level,1);
                
               }
                
        levels = new ArrayList<>(intensity.keySet());
        Collections.sort(levels);
        Integer cdf = intensity.get(levels.get(0));
        Integer cdfmin = intensity.get(levels.get(0));
        intensity.put(levels.get(0),(int) Math.round(255.0*(cdf-cdfmin)/( (Initialization.width*Initialization.height)-1)));
        for(int i=1;i<levels.size();i++)
        {
            cdf += intensity.get(levels.get(i));
            intensity.put(levels.get(i),(int) Math.round(255.0*(cdf-cdfmin)/( (Initialization.width*Initialization.height)-1)));
        }

            for(int i=0;i<Initialization.width;i++)
                for(int j=0;j<Initialization.height;j++)
                {  
                    Color c = new Color(Initialization.src.getRGB(i,j));         
                    Integer hValue = intensity.get(c.getRed());
                    Color result = new Color(hValue,hValue,hValue);          
                    Initialization.src.setRGB(i, j, result.getRGB());
                }
            
            File f= new File("Images\\HistogramEqualization.jpg");
            ImageIO.write(Initialization.src,"jpg",f);
            //System.out.println("Write Successfull");  
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
    }
}
