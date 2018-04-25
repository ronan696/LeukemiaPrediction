package preprocessing;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class FeatureExtraction {
    Integer w;
    Integer h;
    Double area = 0.0;
    Double perimeter = 0.0;
    Double std = 0.0;
    Double var = 0.0;
    Double entropy = 0.0;
    Double energy = 0.0;
    BufferedImage binary = null, grayscale = null;
    Integer [][] imgArray;
    Map<Integer,Double> levels = new HashMap<>();

    public Double getArea()
    {
        return area;
    }
    
    public Double getStd()
    {
        return std;
    }
    
    public Double getVar()
    {
        return var;
    }
    
    public Double getEnergy()
    {
        return energy;
    }
    
    public Double getEntropy()
    {
        return entropy;
    }
    
    public void computeFeatures(String op,String gr)
    {
        try{
            if(op == null)
                op = "Images\\Opened.png";
            File f = new File(op);
            binary = ImageIO.read(f);
            if(gr == null)
                gr = "Images\\Grayscale.jpg";
            f = new File(gr);
            grayscale = ImageIO.read(f);
            w = binary.getWidth();
            h = binary.getHeight();
            imgArray = new Integer[w][h];
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        int count = 0, sum = 0;
        for(int i=0;i<w;i++)
            for(int j=0;j<h;j++)
            {   
                //find white pixels
                Color c = new Color(binary.getRGB(i,j));
                if(c.getRed() == 255)
                {
                    //get correspding gray level for white pixel
                    Color g = new Color(grayscale.getRGB(i,j));
                    sum += g.getRed();
                    //count of white pixels
                    count++;
                }
            }
        //PreprocessingUI.extractedProgress.setValue(25);
        Double v = 0.0, mean = (double) sum/count;
        for(int i=0;i<w;i++)
            for(int j=0;j<h;j++)
            {   
                //find white pixels
                Color c = new Color(binary.getRGB(i,j));
                if(c.getRed() == 255)
                {
                    //get correspding gray level for white pixel
                    Integer g = new Color(grayscale.getRGB(i,j)).getRed();
                    //find numebr of occurances of graylevel for energy and entropy
                    if(levels.containsKey(g))
                        levels.put(g,levels.get(g)+1.0);
                    else
                        levels.put(g,1.0);
                    //for std dev computation
                    v += (g - mean)*(g-mean);
                }
            }
        var = v/(count-1);
        std = Math.sqrt(var);
        //PreprocessingUI.extractedProgress.setValue(50);
        for(int x:levels.keySet())
        {
            double p=levels.get(x)/count;
            energy += (p*p);
            entropy -= p*Math.log(p)/Math.log(2.0);
        }
        area = count * 0.264;
        //PreprocessingUI.extractedProgress.setValue(70);
    }
    
    public void computePerimeter(String op)
    {
        new EdgeDetection().findEdge(op);
//        ArrayList<Point> visited = new ArrayList<>();
        Point start = new Point();
        Point end;
        int sLinks=0,dLinks=0;
        boolean isFirst;
        try{
            File f = new File("Images\\FinalEdge.png");
            binary = ImageIO.read(f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        outer:
        for(int i=0;i<binary.getWidth();i++)
            for(int j=binary.getHeight()-1;j>0;j--)
                {   
                    Color c = new Color(binary.getRGB(i,j));
                    if(c.getRed() == 255)
                    {
                        start.move(i,j);
                        break outer;
                    }
                }
        end = new Point(start);
//        visited.add(new Point(-999,-999));
//        visited.add(new Point(start));
        isFirst = true;
        int d = 1;
        while(isFirst || !end.equals(start))
        {
            Point p1 = new Point(start.x-1,start.y-1);
            Point p2 = new Point(start.x,start.y-1);
            Point p3 = new Point(start.x+1,start.y-1);
            Point p4 = new Point(start.x+1,start.y);
            Point p5 = new Point(start.x+1,start.y+1);
            Point p6 = new Point(start.x,start.y+1);
            Point p7 = new Point(start.x-1,start.y+1);
            Point p8 = new Point(start.x-1,start.y);
            int i=1;
            while(i<9)
            {
                if(d==1)
                {
                    if(new Color(binary.getRGB(p6.x,p6.y)).getRed() == 255) 
                    {
                        //System.out.println(p6);
                        start.move(p6.x,p6.y);   sLinks++;  
                        d=6;
                        break;
                    }
                    else
                        d=2;
                }
                i++;
                if(d==2)
                {
                    if(new Color(binary.getRGB(p7.x,p7.y)).getRed() == 255) 
                    {
                        //System.out.println(p7);
                        start.move(p7.x,p7.y);   dLinks++;  
                        d=7;
                        break;
                    }
                    else
                        d=3;
                }
                i++;
                if(d==3)
                {
                    if(new Color(binary.getRGB(p8.x,p8.y)).getRed() == 255) 
                    {
                        //System.out.println(p8);
                        start.move(p8.x,p8.y);   sLinks++;  
                        d=8;
                        break;
                    }
                    else
                        d=4;
                }
                i++;
                if(d==4)
                {
                    if(new Color(binary.getRGB(p1.x,p1.y)).getRed() == 255) 
                    {
                        //System.out.println(p1);
                        start.move(p1.x,p1.y);   dLinks++;  
                        d=1;
                        break;
                    }
                    else
                        d=5;
                }
                i++;
                if(d==5)
                {
                    if(new Color(binary.getRGB(p2.x,p2.y)).getRed() == 255) 
                    {
                        //System.out.println(p2);
                        start.move(p2.x,p2.y);   sLinks++;  
                        d=2;
                        break;
                    }
                    else
                        d=6;
                }
                i++;
                if(d==6)
                {
                    if(new Color(binary.getRGB(p3.x,p3.y)).getRed() == 255) 
                    {
                        //System.out.println(p3);
                        start.move(p3.x,p3.y);   dLinks++;  
                        d=3;
                        break;
                    }
                    else
                        d=7;
                }
                i++;
                if(d==7)
                {
                    if(new Color(binary.getRGB(p4.x,p4.y)).getRed() == 255) 
                    {
                        //System.out.println(p4);
                        start.move(p4.x,p4.y);   sLinks++;  
                        d=4;
                        break;
                    }
                    else
                        d=8;
                }
                i++;
                if(d==8)
                {
                    if(new Color(binary.getRGB(p5.x,p5.y)).getRed() == 255) 
                    {
                        //System.out.println(p5);
                        start.move(p5.x,p5.y);   dLinks++;  
                        d=5;
                        break;
                    }
                    else
                        d=1;
                }
                i++;      
            }
            isFirst=false;
        }
        perimeter = Math.sqrt(2)*dLinks + sLinks;
        //System.out.println(perimeter);
        //PreprocessingUI.extractedProgress.setValue(100);
    }
    
    public Double getFormFactor()
    {
        return (4*Math.PI*area)/(perimeter*perimeter);
    }
    
    public Double getPerimeter()
    {
        return perimeter;
    }
}
