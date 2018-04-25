package ui;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import javax.imageio.ImageIO;

public class Initialization {
    public static BufferedImage src;
    public static Integer width;
    public static Integer height;
    public static String testDataPath;
    public static String trainDataPath;
    public static int numComponents;
    public static String rfClassificationFilePath;
    public Initialization()    {
        src = null;
        width = 0;
        height = 0;
        numComponents = 0;
    }
    
//    public void init(String path)   {
//        try {
//            src = ImageIO.read(new File(path));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
    
    public static void setup()  {
        File configFile = new File("config.ini");
 
        try {
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);
            testDataPath = props.getProperty("TestDataPath","");
            trainDataPath = props.getProperty("TrainDataPath","");
            rfClassificationFilePath = props.getProperty("RFClassificationFile","");
            reader.close();
        }   catch (IOException ex) {
            // I/O error
        }
    }
    
    public static void update() {
        File configFile = new File("config.ini");
        try {
            Properties props = new Properties();
            props.setProperty("TestDataPath", testDataPath);
            props.setProperty("TrainDataPath", trainDataPath);
            props.setProperty("RFClassificationFile",rfClassificationFilePath);
            FileWriter writer = new FileWriter(configFile);
            props.store(writer, "LeukemiaPrediction Settings");
            writer.close();
        }   catch (IOException ex) {
            // I/O error
        }
    }
    
}
