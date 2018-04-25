package preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class EdgeDetection {

    public void findEdge(String op) {
        int [][]arrImage ;
        int [][]maskImage;
        int [][]tempImage;
        int [][]lImage;
        int [][]rImage;
        int [][]tImage;
        int [][]bImage;
        
        BufferedImage temp1,temp2,temp3;
        int b,res = 0;
        Color c;
        
        try{
                if(op == null)
                    op = "Images\\Opened.png";
                File f= new File(op);
                temp1 = ImageIO.read(f);
                Integer w = temp1.getWidth();
                Integer h = temp1.getHeight();
                temp2=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
                temp3=new BufferedImage(w,h,BufferedImage.TYPE_BYTE_BINARY);
                arrImage= new int[w][h];
                maskImage= new int[w][h];
                tempImage=new int[w][h];
                lImage=new int[w][h];
                tImage=new int[w][h];
                bImage=new int[w][h];
                rImage=new int[w][h];
                
             //reading image into the array  and getting the masked image;
     
      
                for(int i=0;i<w;i++)
                    for(int j=0;j<h;j++)
                    {   
                        c= new Color(temp1.getRGB(i,j));
                        arrImage[i][j]=c.getRed();
                        if(arrImage[i][j]==255)
                                    maskImage[i][j]=0;
                        if(arrImage[i][j]==0)
                                    maskImage[i][j]=255;
                                
                        b=maskImage[i][j];
                        c = new Color(b,b,b);
                        temp2.setRGB(i,j,c.getRGB());           
                    }
                 //writing the masked image
                f= new File("Images\\Masked.png");
                ImageIO.write(temp2,"png",f);
                //System.out.println("Write Successfull Masked ");
              
         
             //move the masked image to the right to get left edge
             for(int i=0;i<w;i++)
              for(int j=0;j<h;j++)
              { if(j!=0)
                res=arrImage[i][j]&maskImage[i][j-1];
                    if(j==0)
                    res=arrImage[i][j];  
                lImage[i][j]=res;
                c = new Color(res,res,res);
                temp2.setRGB(i,j,c.getRGB());
              }
                f= new File("Images\\leftedge.png");
                ImageIO.write(temp2,"png",f);
                //System.out.println("Write Successfull Left Edge");
                
               //move the masked image to the left to get right edge   
             for(int i=0;i<w;i++)
              for(int j=0;j<h;j++)
              { if(j!=h-1)
                res=arrImage[i][j]&maskImage[i][j+1];
                    if(j==h-1)
                    res=arrImage[i][j];  
                rImage[i][j]=res;
                c = new Color(res,res,res);
                temp2.setRGB(i,j,c.getRGB());
              }
                f= new File("Images\\rightedge.png");
                ImageIO.write(temp2,"png",f);
                //System.out.println("Write Successfull Right Edge");
                
                
                 //move the masked image to the top to get bottom edge   
             for(int i=0;i<w;i++)
              for(int j=0;j<h;j++)
              { if(i!=w-1)
                res=arrImage[i][j]&maskImage[i+1][j];
                    if(i==w-1)
                    res=arrImage[i][j];  
                bImage[i][j]=res;
                c = new Color(res,res,res);
                temp2.setRGB(i,j,c.getRGB());
              }
                f= new File("Images\\bottomedge.png");
                ImageIO.write(temp2,"png",f);
                //System.out.println("Write Successful Bottom Edge");
                
                
                 //move the masked image to the bottom to get top edge   
             for(int i=0;i<w;i++)
              for(int j=0;j<h;j++)
              { if(i!=0)
                res=arrImage[i][j]&maskImage[i-1][j];
                    if(i==0)
                    res=arrImage[i][j];  
                tImage[i][j]=res;
                c = new Color(res,res,res);
                temp2.setRGB(i,j,c.getRGB());
                //reusing variable
                res=tImage[i][j] | lImage[i][j] | rImage[i][j] | bImage[i][j];
                 c = new Color(res,res,res);
                temp2.setRGB(i,j,c.getRGB());
                
              }
                f= new File("Images\\topedge.png");
                ImageIO.write(temp2,"png",f);
                //System.out.println("Write Successfull Top Edge");
             
              
                f= new File("Images\\FinalEdge.png");
                ImageIO.write(temp2,"png",f);
               // System.out.println("Write Successfull Final Edge");
                //PreprocessingUI.extractedProgress.setValue(85);
      
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}
