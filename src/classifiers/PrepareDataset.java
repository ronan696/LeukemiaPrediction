package classifiers;

import java.io.*;
import java.util.*;

public class PrepareDataset {

	BufferedReader br = null;
	
	public ArrayList<ArrayList<String>> getData(String path)	{
		ArrayList<ArrayList<String>> data = new ArrayList<>();
		try {
			String line;
			br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null)	{
				String [] dataPoints = line.split(",");
				ArrayList<String> record = new ArrayList<String>(Arrays.asList(dataPoints));
				data.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
