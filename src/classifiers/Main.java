package classifiers;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		String testData,trainData;
		int numTrees,numAttrs,numAttrsSub;
		String record = "2284.3920000000003,351.1198410471445,0.2328465203013835,7.5112481092047565,56.41884815803203,0.09913495277138017,3.6595469749914944,test";
		numTrees = 100;
		numAttrs = 7;
		numAttrsSub = (int) Math.round(Math.log(numAttrs)/Math.log(2)+1);;
		
		PrepareDataset pd = new PrepareDataset();
		ArrayList<ArrayList<String>> trainingData = pd.getData("celldata.txt");
		//List<List<String>> testingData = pd.getData("LeuTest.txt");
		RandomForest rf = new RandomForest(numTrees,4,numAttrs,numAttrsSub,2,trainingData);
		rf.Start(new ArrayList<String>(Arrays.asList(record.split(","))));
//		for(List<String> s : trainingData) {
//			for(String p : s)	{
//				System.out.print(p + ", ");
//			}
//			System.out.println();
//		}
	}

}
