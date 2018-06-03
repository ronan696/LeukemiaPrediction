package preprocessing;

import ui.Initialization;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class MedianFilter {
     int [][]arrImage = null;
     File f;
     int []strarray = new int[9]; 
     int sz, median;
     public void performmf() {
     
      try{
            //reading image into the array
            Integer w = Initialization.src.getWidth();
            Integer h = Initialization.src.getHeight();
            arrImage= new int[w][h];   
            for(int i=0;i<w;i++)
                for(int j=0;j<h;j++)
                    {   
                        Color c= new Color(Initialization.src.getRGB(i,j));
                        arrImage[i][j]=c.getRed();
                    }
      //removing noise from all the pixels except the border pixels
            for(int i=1;i<w-1;i++)
                for(int j=1;j<h-1;j++)
                { sz=0;
                 //add elements to the structuring element
                    for(int k=i-1;k<i-1+3;k++)
                        for(int l=j-1;l<j-1+3;l++)
                            strarray[sz++]=arrImage[k][l];
                    Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                }
            
        
            for(int i=0;i<w;i++)
                for(int j=0;j<h;j++)
                { 
                    if(i==0 &&(j>=1 && j<h-1))
                    {//considers the top border pixels except the corners
                        //add elements to the structuring element
                        sz=0;
                        for(int k=i;k<i+2;k++)
                            for(int l=j-1;l<j-1+3;l++)
                            {
                             if(k==i)
                                { //to copy the same elements
                                    strarray[sz++]=arrImage[k][l];
                                    strarray[sz++]=arrImage[k][l];
                                }
                             else
                                 strarray[sz++]=arrImage[k][l];
                            }
                        Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                    }
                    
                    if(j==0 &&(i>=1 && i<w-1))
                    {//considers the left border pixels except the corners
                        //add elements to the structuring element
                        sz=0;
                        for(int k=i-1;k<i-1+3;k++)
                            for(int l=j;l<j+2;l++)
                            {
                             if(l==j)
                                { //to copy the same elements
                                    strarray[sz++]=arrImage[k][l];
                                    strarray[sz++]=arrImage[k][l];
                                }
                             else
                                 strarray[sz++]=arrImage[k][l];
                            }
                        Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                   }
                    
                    if(i==w-2 &&(j>=1 && j<h-1))
                    {//considers the bottom border pixels except the corners
                        //add elements to the structuring element
                        sz=0;
                          for(int k=i;k<i+2;k++)
                            for(int l=j-1;l<j-1+3;l++)
                            {
                             if(k==i+1)
                                { //to copy the same elements
                                    strarray[sz++]=arrImage[k][l];
                                    strarray[sz++]=arrImage[k][l];
                                }
                             else
                                 strarray[sz++]=arrImage[k][l];
                            }
                          Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                    }
                     if(j==h-2 &&(i>=1 && i<w-1))
                    {//considers the left border pixels except the corners
                        //add elements to the structuring element
                        sz=0;
                        for(int k=i-1;k<i-1+3;k++)
                            for(int l=j;l<j+2;l++)
                            {
                             if(l==j+1)
                                { //to copy the same elements
                                    strarray[sz++]=arrImage[k][l];
                                    strarray[sz++]=arrImage[k][l];
                                }
                             else
                                 strarray[sz++]=arrImage[k][l];
                            }
                        Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                    }
              //for the corners
                     if(i==0 && j==0)
                     {   for(sz=0;sz<=3;sz++)
                             strarray[sz]=arrImage[i][j];
                         strarray[sz++]=arrImage[0][1];
                         strarray[sz++]=arrImage[0][1];
                         
                         strarray[sz++]=arrImage[1][0];
                         strarray[sz++]=arrImage[1][0];
                         
                         strarray[sz++]=arrImage[1][1];
                         Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                     }
                     if(i==0&& j==h-1)
                     {  for(sz=0;sz<=3;sz++)
                             strarray[sz]=arrImage[i][j];
                         strarray[sz++]=arrImage[0][h-2];
                         strarray[sz++]=arrImage[0][h-2];
                         
                         strarray[sz++]=arrImage[1][j];
                         strarray[sz++]=arrImage[1][j];
                         
                         strarray[sz++]=arrImage[1][h-2];
                         Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                         
                     }
                     if(i==w-1&&j==0)
                     {for(sz=0;sz<=3;sz++)
                             strarray[sz]=arrImage[i][j];
                         strarray[sz++]=arrImage[i][1];
                         strarray[sz++]=arrImage[i][1];
                         
                         strarray[sz++]=arrImage[i-1][0];
                         strarray[sz++]=arrImage[i-1][0];
                         
                         strarray[sz++]=arrImage[i-1][1];
                         Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                     }
                     if(i==w-1&&j==h-1)
                     {for(sz=0;sz<=3;sz++)
                             strarray[sz]=arrImage[i][j];
                         strarray[sz++]=arrImage[i][h-2];
                         strarray[sz++]=arrImage[i][h-2];
                         
                         strarray[sz++]=arrImage[i-1][h-1];
                         strarray[sz++]=arrImage[i-1][h-1];
                         
                         strarray[sz++]=arrImage[i-1][h-2];
                         Arrays.sort(strarray);
                    median=strarray[4];
                    arrImage[i][j]=median;
                     }
                     
                    
            }
        
//writing to an image
        for(int i=0;i<w;i++)
             for(int j=0;j<h;j++)
             {    int b=arrImage[i][j];
                  Color newc = new Color(b,b,b);
                   Initialization.src.setRGB(i, j, newc.getRGB());
             }
                f= new File("Images//Filtered.jpg");
             ImageIO.write(Initialization.src,"jpg",f);
             //System.out.println("Write Successfull Filtered");
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
    }
}
