package classifiers;

import java.util.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomForest implements Serializable {

	private static int NUM_THREADS;
	public static int C;
	public static int M;
	public static int Ms;
	
	private ArrayList<DecisionTree> forestTrees;
	private long time_o;
	private int numTrees;
	private double update;
	private double progress;
	
	private ExecutorService treePool;
	private ArrayList<ArrayList<String>> data;
	//private ArrayList<ArrayList<String>> testData;
	//private ArrayList<ArrayList<String>> predictions;
	public int corretsPredictions;
	
	@SuppressWarnings("static-access")
	public RandomForest(int numTrees,int numThreads,int M,int Ms,int C, ArrayList<ArrayList<String>> train) {
		StartTimer();
		this.numTrees=numTrees;
		this.NUM_THREADS=numThreads;
		this.data=train;
		this.M=M;
		this.Ms=Ms;
		this.C=C;
		
		forestTrees = new ArrayList<DecisionTree>(numTrees);
		//update=100/((double)numTrees);
		//progress=0;
		corretsPredictions =0;
		System.out.println("creating "+numTrees+" trees in a random Forest. . . ");
		System.out.println("total data size is "+train.size());
		System.out.println("number of attributes "+M);
		System.out.println("number of selected attributes "+Ms);	
	}
	
	@SuppressWarnings("unchecked")
	public String Start(ArrayList<String> record) {
		// TODO Auto-generated method stub
		System.out.println("Number of threads started : "+NUM_THREADS);
		System.out.print("Starting trees");
		treePool = Executors.newFixedThreadPool(NUM_THREADS);
		for (int t=0;t<numTrees;t++){
			treePool.execute(new CreateTree(data,this,t+1));
		}
		treePool.shutdown();
		try {	         
			treePool.awaitTermination(10,TimeUnit.SECONDS); 
	    } catch (InterruptedException ignored){
	    	System.out.println("interrupted exception in Random Forests");
	    }
		System.out.println("Trees Production completed in "+TimeElapsed(time_o));
		
		System.out.println("Testing Forest for Labels without threads");
		ArrayList<DecisionTree> Tree4 = (ArrayList<DecisionTree>) forestTrees.clone();
		return TestForestForLabel(Tree4, data,record);
	}
	
        public void writeToFile()   {
            System.out.println("Number of threads started : "+NUM_THREADS);
		System.out.print("Starting trees");
		treePool = Executors.newFixedThreadPool(NUM_THREADS);
		for (int t=0;t<numTrees;t++){
			treePool.execute(new CreateTree(data,this,t+1));
		}
		treePool.shutdown();
		try {	         
			treePool.awaitTermination(10,TimeUnit.SECONDS); 
	    } catch (InterruptedException ignored){
	    	System.out.println("interrupted exception in Random Forests");
	    }
            List<DecisionTree> dt = (ArrayList<DecisionTree>) forestTrees.clone();
            try {
                File rfClassificationfile = new File("rf.dat");
                FileOutputStream outFileStream = new FileOutputStream(rfClassificationfile);
                ObjectOutputStream outObjectStream = new ObjectOutputStream(outFileStream);
                for(DecisionTree d : dt)    {
                    outObjectStream.writeObject(d);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	
        
	private String TestForestForLabel(ArrayList<DecisionTree> trees,ArrayList<ArrayList<String>> traindata, ArrayList<String> record) {
		// TODO Auto-generated method stub
		long time = System.currentTimeMillis();
		ArrayList<String> Val = new ArrayList<String>();
		System.out.println("Predicting Labels now");
		for(DecisionTree DTC : trees){
			Val.add(DTC.Evaluate(record, traindata));
		}
			String pred = ModeofList(Val);
			System.out.println("["+pred+"]: Class predicted for data point");
                        return pred;
	}
	
	public String ModeofList(ArrayList<String> predictions) {
		String MaxValue = null; int MaxCount = 0;
		for(int i=0;i<predictions.size();i++){
			int count=0;
			for(int j=0;j<predictions.size();j++){
				if(predictions.get(j).trim().equalsIgnoreCase(predictions.get(i).trim()))
					count++;
				if(count>MaxCount){
					MaxValue=predictions.get(i);
					MaxCount=count;
				}
			}
		}
		return MaxValue;
	}
	

	private class CreateTree implements Runnable{
		private ArrayList<ArrayList<String>> data;
		private RandomForest forest;
		private int treeNum;

		public CreateTree(ArrayList<ArrayList<String>> data,RandomForest forest,int num){
			this.data=data;
			this.forest=forest;
			this.treeNum=num;
		}
		public void run() {
			//trees.add(new DTreeCateg(data,forest,treenum));
			forestTrees.add(new DecisionTree(data, forest, treeNum));
			//progress += update;
		}
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

}
