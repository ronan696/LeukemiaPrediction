package classifiers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import ui.Initialization;

public class KNN {
    public static int kValue;
    class record {

        Double area;
        Double perimeter;
        Double form_factor;
        Double standard_deviation;
        Double variance;
        Double energy;
        double entropy;
        String classCell;
    };

    public String perform(String query) {
        record[] r = new record[500];
        int i = 0;
        double[] euclidean = new double[500];
        //HashMap<Integer,double> values = new HashMap<Integer,Float>();

        try {
            BufferedReader text = new BufferedReader(new FileReader(Initialization.trainDataPath));
            String line;
            line = text.readLine();
            //Fetching from the text File
            while (line != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                while (st.hasMoreTokens()) {
                    r[i] = new record();
                    r[i].area = Double.parseDouble(st.nextToken());
                    r[i].perimeter = Double.parseDouble(st.nextToken());
                    r[i].form_factor = Double.parseDouble(st.nextToken());
                    r[i].standard_deviation = Double.parseDouble(st.nextToken());
                    r[i].variance = Double.parseDouble(st.nextToken());
                    r[i].energy = Double.parseDouble(st.nextToken());
                    r[i].entropy = Double.parseDouble(st.nextToken());
                    r[i].classCell = st.nextToken();
                    i++;
                }
                line = text.readLine();
            }
            text.close();
        } catch (Exception e) {
            System.out.println(e);
        }
//Displaying fetched items
//for(int j=0;j<i;j++){
//System.out.println(r[j].area+" "+r[j].perimeter+" "+r[j].form_factor+" "+r[j].standard_deviation+" "+r[j].variance+" "+r[j].energy+" "+r[j].entropy+" "+r[j].classCell);
//}
//Enter the data sample
//System.out.println("Enter the query");
//Scanner sc=new Scanner(System.in);
//String query=sc.next();
        StringTokenizer st = new StringTokenizer(query, ",");
        while (st.hasMoreTokens()) {
            r[i] = new record();
            r[i].area = Double.parseDouble(st.nextToken());
            r[i].perimeter = Double.parseDouble(st.nextToken());
            r[i].form_factor = Double.parseDouble(st.nextToken());
            r[i].standard_deviation = Double.parseDouble(st.nextToken());
            r[i].variance = Double.parseDouble(st.nextToken());
            r[i].energy = Double.parseDouble(st.nextToken());
            r[i].entropy = Double.parseDouble(st.nextToken());
            r[i].classCell = "null";
            i++;
        }
//System.out.println("Current DataBase");
//for(int j=0;j<i;j++){
//System.out.println(r[j].area+" "+r[j].perimeter+" "+r[j].form_factor+" "+r[j].standard_deviation+" "+r[j].variance+" "+r[j].energy+" "+r[j].entropy+" "+r[j].classCell);
//}
//Calculating Euclidean Distance
        for (int k = 0; k < i - 1; k++) {
            euclidean[k] = (((r[i - 1].area - r[k].area) * (r[i - 1].area - r[k].area))
                    + ((r[i - 1].perimeter - r[k].perimeter) * (r[i - 1].perimeter - r[k].perimeter))
                    + ((r[i - 1].form_factor - r[k].form_factor) * (r[i - 1].form_factor - r[k].form_factor))
                    + ((r[i - 1].standard_deviation - r[k].standard_deviation) * (r[i - 1].standard_deviation - r[k].standard_deviation))
                    + ((r[i - 1].variance - r[k].variance) * (r[i - 1].variance - r[k].variance))
                    + ((r[i - 1].energy - r[k].energy) * (r[i - 1].energy - r[k].energy))
                    + ((r[i - 1].entropy - r[k].entropy) * (r[i - 1].entropy - r[k].entropy)));
            euclidean[k] = (Double) Math.sqrt(euclidean[k]);
        }
//Displaying
//for(int k=0;k<i-1;k++){
//    System.out.println("Euclidean Distance of record"+k+" is "+euclidean[k]);
//}
//K value
//System.out.println("Enter the kValue");
        //int kValue = 3;
        int y = 0;
        Double[] minarr = new Double[kValue];
        Double min = 0.0;
        int max = 50000;
//getting the k min values
        for (int m = 0; m < kValue; m++) {
            min = euclidean[0];
            for (int k = 0; k < i - 1; k++) {
                if (min > euclidean[k]) {
                    min = euclidean[k];
                    minarr[m] = min;
                    y = k;
                }
                if (k == i - 2) {
                    euclidean[y] = max;
                }
            }
        }
//displaying the min values obtained
        System.out.println("Min values");
        for (int m = 0; m < kValue; m++) {
            System.out.print(minarr[m] + " ");
        }
        System.out.println();
//Checking with majority voting for the records obtained 
        int good = 0, bad = 0;
        for (int k = 0; k < i - 1; k++) {
            if (euclidean[k] == max) {
                if (r[k].classCell.equals("false")) {
                    good++;
                } else {
                    bad++;
                }
            }
        }
        if (good > bad) {
            r[i - 1].classCell = "false";
        } else {
            r[i - 1].classCell = "true";
        }

        return r[i - 1].classCell;

//System.out.println("Final Result");
//for(int j=0;j<i;j++){
//System.out.println(r[j].area+" "+r[j].perimeter+" "+r[j].form_factor+" "+r[j].standard_deviation+" "+r[j].variance+" "+r[j].energy+" "+r[j].entropy+" "+r[j].classCell);
//  }
    }

}
