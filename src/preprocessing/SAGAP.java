package preprocessing;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class SAGAP {

    File f,g;
    BufferedImage opened,grayscale;
    Integer w,h;
    long time_o;
    Map<Point,List<Point>> sTree = new HashMap<>();
    Map<Point,int []> coordinates = new HashMap<>();
    List<Point> topNodes = new ArrayList<>();
    Point next,current,bottom;
    
    List<Point> getNeighbours(Point p)   {
        List<Point> neighbours = new ArrayList<>();
        for(int i=p.y-1;i<p.y+2;i++)
            for(int j=p.x-1;j<p.x+2;j++)    {
                Point newP = new Point(j,i);
                if(i==-1 || j==-1 || i==w || j==h || (p.equals(newP)))  continue;
                Color c = new Color(opened.getRGB(i,j));
                if(c.getRed()==255)
                    neighbours.add(newP);
            }
        return neighbours;
    }
    
    public int perform()  {
        try {
            
            //Clean the folder
            File[] listFiles = new File("Components").listFiles();
		for(File file : listFiles){
			System.out.println("Deleting "+file.getName());
			file.delete();
		}
            
            f = new File("Images\\Opened.png");
            g = new File("Images\\Grayscale.jpg");
            
            opened = ImageIO.read(f);
            grayscale = ImageIO.read(g);
            w = opened.getWidth();
            h = opened.getHeight();
            
            StartTimer();
            for(int i=0;i<w;i++)
                for(int j=0;j<h;j++)    {
                    Color c = new Color(opened.getRGB(i,j));
                    if(c.getRed() == 255)   {
                        Point p = new Point(j,i);
                        List<Point> n = getNeighbours(p);
                        topNodes.add(p);
                        sTree.put(p,n);
                    }
                }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("Tree built in " + TimeElapsed(time_o));
        
        int nextPtr = 0, currentPtr = 0, bottomPtr;        
        while(currentPtr < topNodes.size()-1) {
            bottomPtr = 0;
            current = topNodes.get(currentPtr);
            List<Point> bottomList = sTree.get(current);
            int [] cd = new int[4];
            cd[0] = current.x; cd[1] = current.y;
            cd[2] = current.x; cd[3] = current.y;
            while(bottomPtr < bottomList.size())    {
                bottom = bottomList.get(bottomPtr);
                if(bottom.x < cd[0])
                    cd[0] = bottom.x;
                if(bottom.x > cd[2])
                    cd[2] = bottom.x;
                if(bottom.y < cd[1])
                    cd[1] = bottom.y;
                if(bottom.y > cd[3])
                    cd[3] = bottom.y;
                nextPtr = currentPtr + 1;
                while(nextPtr < topNodes.size())    {
                    next = topNodes.get(nextPtr);
                    if(bottom.equals(next)) {
                        List<Point> tempList = sTree.get(next);
                        sTree.remove(next);
                        topNodes.remove(next);
                        for(Point n:tempList)    {
                            if(!bottomList.contains(n))
                                bottomList.add(n);
                        }
                    }
                    nextPtr++;
                }
                bottomPtr++;
            }
            bottomList.remove(current);
            sTree.put(current,bottomList);
            coordinates.put(current,cd);
            currentPtr++;
        }
        
        System.out.println("SAGAP completed in " + TimeElapsed(time_o));
        
        System.out.println("\nFinal Tree");
//        for(Point n : sTree.keySet()) {
//            System.out.print(n + " ==> ");
//            for(Point c : sTree.get(n))   {
//                System.out.print(c + " ");
//            }
//            int [] cd = coordinates.get(n);
//            System.out.println("\nminX : " + cd[0] + ", minY : " + cd[1] + ", maxX : " + cd[2] + ", maxY : " + cd[3]);
//            System.out.println();
//        }
        int c = 1;

        for(Point t : sTree.keySet())   {
            int [] cd = coordinates.get(t);
            BufferedImage bin = new BufferedImage(cd[3]-cd[1]+3,cd[2]-cd[0]+3,BufferedImage.TYPE_BYTE_BINARY);
            BufferedImage gr = new BufferedImage(cd[3]-cd[1]+3,cd[2]-cd[0]+3,BufferedImage.TYPE_INT_RGB);
            int x = t.x - cd[0] + 1;
            int y = t.y - cd[1] + 1;
            Color g  = new Color(grayscale.getRGB(t.y,t.x));
            bin.setRGB(y,x,Color.WHITE.getRGB());
            gr.setRGB(y,x,g.getRGB());
            for(Point b : sTree.get(t)) {
                x = b.x - cd[0] + 1;
                y = b.y - cd[1] + 1;
                g  = new Color(grayscale.getRGB(b.y,b.x));
                bin.setRGB(y,x,Color.WHITE.getRGB());
                gr.setRGB(y,x,g.getRGB());
            }
            if(bin.getWidth()>60 && bin.getHeight()>60) {
                try {
                    ImageIO.write(bin, "png", new File("Components\\Component" + c + ".png"));
                    ImageIO.write(gr, "png", new File("Components\\GComponent" + c + ".png"));
                }
                catch(IOException e)    {
                    e.printStackTrace();
                }
                c++;
            }
        }

        System.out.println("Completed Writing in " + TimeElapsed(time_o));
        return (c-1);
    }
    
        private void StartTimer(){
		time_o=System.currentTimeMillis();
	}
	
	private static String TimeElapsed(long timeinms){
            double s=(double)(System.currentTimeMillis()-timeinms)/1000;
            int h=(int)Math.floor(s/((double)3600));
            s-=(h*3600);
            int m=(int)Math.floor(s/((double)60));
            s-=(m*60);
            return ""+h+"hr "+m+"m "+s+"sec";
	}
    
    public static void main(String[] args) {
            SAGAP t =  new SAGAP();
            t.perform();
    }
    
}
