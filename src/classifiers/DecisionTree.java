package classifiers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

public class DecisionTree implements Serializable{
	private static final int INDEX_SKIP=3;
	private static final int MIN_SIZE_TO_CHECK_EACH=10;
	private static final int MIN_NODE_SIZE=5;
	private int N;
	
	public ArrayList<String> predictions;
	private TreeNode root;
	//private RandomForest forest;
	
	public DecisionTree(ArrayList<ArrayList<String>> data, int treenum) {
		// TODO Auto-generated constructor stub
		//this.forest=forest;
		N=data.size();
		
		ArrayList<ArrayList<String>> train = new ArrayList<ArrayList<String>>(N);
		ArrayList<ArrayList<String>> test = new ArrayList<ArrayList<String>>();
		
		BootStrapSample(data,train,test,treenum);
		
		root=CreateTree(train,treenum);
		FlushData(root, treenum);
	}
	

	@SuppressWarnings("unchecked")
	private void BootStrapSample(ArrayList<ArrayList<String>> data,ArrayList<ArrayList<String>> train,ArrayList<ArrayList<String>> test,int numb){
		ArrayList<Integer> indices=new ArrayList<Integer>();
		for (int n=0;n<N;n++)
			indices.add((int)Math.floor(Math.random()*N));
//		ArrayList<Boolean> in=new ArrayList<Boolean>();
//		for (int n=0;n<N;n++)
//			in.add(false); //have to initialize it first
		for (int num:indices){
			ArrayList<String> k = data.get(num);
			train.add((ArrayList<String>) k.clone());
			//in.set(num,true);
		}//System.out.println("created training-data for tree : "+numb);
//		for (int i=0;i<N;i++)
//			if (!in.get(i))
//				test.add(data.get(i));
		
//		System.out.println("bootstrap N:"+N+" size of bootstrap:"+bootstrap.size());
	}
	

	private TreeNode CreateTree(ArrayList<ArrayList<String>> train, int ntree){
		TreeNode root = new TreeNode();
		root.label = "|ROOT|";
		root.data = train;
		//System.out.println("creating ");
		RecursiveSplit(root,ntree);
		return root;
	}

	public class TreeNode implements Cloneable,Serializable {
		public boolean isLeaf;
		public ArrayList<TreeNode> ChildNode ;
		//public HashMap<String, String> Missingdata;
		public int splitAttributeM;
		public String Class;
		public ArrayList<ArrayList<String>> data;
		public String splitValue;
		public String label;
		public int generation;
		
		public TreeNode(){
			splitAttributeM=-99;
			splitValue="-99";
			generation=1;
		}
		public TreeNode clone(){ 
			TreeNode copy = new TreeNode();
			copy.isLeaf = isLeaf;
			for(TreeNode tn : ChildNode){
				if(tn != null){
					copy.ChildNode.add(tn.clone());
				}
			}
			copy.splitAttributeM=splitAttributeM;
			copy.Class=Class;
			copy.splitValue=splitValue;
			copy.label=label;
			return copy;
		}
	}

	private class DoubleWrap implements Serializable{
		public double d;
		public DoubleWrap(double d){
			this.d=d;
		}		
	}


	public String Evaluate(ArrayList<String> record, ArrayList<ArrayList<String>> tester){
		TreeNode evalNode=root;		
			while (true) {				
				if(evalNode.isLeaf)
					return evalNode.Class;
				else{
						double Compare = Double.parseDouble(evalNode.splitValue);
						double Actual = Double.parseDouble(record.get(evalNode.splitAttributeM));
						if(Actual <= Compare){
							if(evalNode.ChildNode.get(0).label.equalsIgnoreCase("Left"))
								evalNode=evalNode.ChildNode.get(0);
							else
								evalNode=evalNode.ChildNode.get(1);
//							evalNode=evalNode.left;
//							System.out.println("going in child :"+evalNode.label);
						}else{
							if(evalNode.ChildNode.get(0).label.equalsIgnoreCase("Right"))
								evalNode=evalNode.ChildNode.get(0);
							else
								evalNode=evalNode.ChildNode.get(1);
//							evalNode=evalNode.right;
//							System.out.println("going in child :"+evalNode.label);
						}

				}
		}
	}

	private void RecursiveSplit(TreeNode parent, int Ntreenum){
		
		if (!parent.isLeaf){
			
			
			//-------------------------------
			String Class=CheckIfLeaf(parent.data);
			if (Class != null){
				parent.isLeaf=true;
				parent.Class=Class;
				return;
			}
			
			
			//-------------------------------
			int Nsub=parent.data.size();			
			
			parent.ChildNode = new ArrayList<DecisionTree.TreeNode>();
			for(TreeNode TN: parent.ChildNode){
				TN = new TreeNode();
				TN.generation = parent.generation+1;
			}
			
			ArrayList<Integer> vars=GetVarsToInclude();
			DoubleWrap lowestE=new DoubleWrap(Double.MAX_VALUE);
			
			
			//-------------------------------
			for (int m:vars){
				ArrayList<Integer> DataPointToCheck=new ArrayList<Integer>();
				
				SortAtAttribute(parent.data,m);//sorts on a particular column in the row
				for (int n=1;n<Nsub;n++){
					String classA=GetClass(parent.data.get(n-1));
					String classB=GetClass(parent.data.get(n));
					if(!classA.equalsIgnoreCase(classB))
						DataPointToCheck.add(n);
				}
				
				if (DataPointToCheck.size() == 0){//if all the Y-values are same, then get the class directly
					parent.isLeaf=true;
					parent.Class=GetClass(parent.data.get(0));
					continue;
				}
				
				if (DataPointToCheck.size() > MIN_SIZE_TO_CHECK_EACH){
					for (int i=0;i<DataPointToCheck.size();i+=INDEX_SKIP){
						CheckPosition(m, DataPointToCheck.get(i), Nsub, lowestE, parent, Ntreenum);
						if (lowestE.d == 0)//lowestE now has the minimum conditional entropy so IG is max there
							break;
					}
				}else{
					for (int k:DataPointToCheck){
						CheckPosition(m,k, Nsub, lowestE, parent, Ntreenum);
						if (lowestE.d == 0)//lowestE now has the minimum conditional entropy so IG is max there
							break;
					}
				}
				if (lowestE.d == 0)
					break;
			}
			//System.out.println("Adding "+parent.ChildNode.size()+" children at level: "+parent.generation);
			//-------------------------------Step D
			for(TreeNode Child:parent.ChildNode){
				if(Child.data.size()==1){
					Child.isLeaf=true;
					Child.Class=GetClass(Child.data.get(0));
				}else if(Child.data.size()<MIN_NODE_SIZE){
					Child.isLeaf=true;
					Child.Class=GetMajorityClass(Child.data);
				}else{
					Class=CheckIfLeaf(Child.data);
					if(Class==null){
						Child.isLeaf=false;
						Child.Class=null;
					}else{
						Child.isLeaf=true;
						Child.Class=Class;
					}
				}
				if(!Child.isLeaf){
					RecursiveSplit(Child, Ntreenum);
				}
			}
		}
	}
	
	private void SortAtAttribute(ArrayList<ArrayList<String>> data, int m) {
			Collections.sort(data,new AttributeComparatorReal(m));
		
	}
	

	private class AttributeComparatorReal implements Comparator<ArrayList<String>>{		
		/** the specified attribute */
		private int m;

		public AttributeComparatorReal(int m){
			this.m=m;
		}

		@Override
		public int compare(ArrayList<String> arg1, ArrayList<String> arg2) {//compare value of strings
			// TODO Auto-generated method stub
			double a2 = Double.parseDouble(arg1.get(m));
			double b2 = Double.parseDouble(arg2.get(m));
			if(a2<b2)
				return -1;
			else if(a2>b2)
				return 1;
			else
				return 0;
		}		
	}


	private String GetMajorityClass(ArrayList<ArrayList<String>> data){
		// find the max class for this data.
		ArrayList<String> ToFind = new ArrayList<String>();
		for(ArrayList<String> s:data){
			ToFind.add(s.get(s.size()-1));
		}
		String MaxValue = null; int MaxCount = 0;
		for(String s1:ToFind){
			int count =0;
			for(String s2:ToFind){
				if(s2.equalsIgnoreCase(s1))
					count++;
			}
			if(count > MaxCount){
				MaxValue = s1;
				MaxCount = count;
			}
		}return MaxValue;
	}

	private double CheckPosition(int m,int n,int Nsub,DoubleWrap lowestE,TreeNode parent, int nTre){
	
		double entropy =0;
		
		if (n < 1) //exit conditions
			return 0;
		if (n > Nsub)
			return 0;
		
			//real value
			
			HashMap<String, ArrayList<ArrayList<String>>> UpLo = GetUpperLower(parent.data, n, m);
			
			ArrayList<ArrayList<String>> lower = UpLo.get("lower"); 
			ArrayList<ArrayList<String>> upper = UpLo.get("upper"); 
			
			ArrayList<Double> pl=getClassProbs(lower);
			ArrayList<Double> pu=getClassProbs(upper);
			double eL=CalEntropy(pl);
			double eU=CalEntropy(pu);
		
			entropy =(eL*lower.size()+eU*upper.size())/((double)Nsub);
			
			if (entropy < lowestE.d){
				lowestE.d=entropy;
				parent.splitAttributeM=m;
				//parent.spiltonCateg=false;
				parent.splitValue = parent.data.get(n).get(m).trim();

				ArrayList<TreeNode> Children2 = new ArrayList<TreeNode>();
				TreeNode Child_left = new TreeNode();
				Child_left.data=lower;
				Child_left.label="Left";
				Children2.add(Child_left);
				TreeNode Child_Right = new TreeNode();
				Child_Right.data=upper;
				Child_Right.label="Right";
				Children2.add(Child_Right);
				parent.ChildNode=Children2;
			}
		return entropy;
	}
	
	private HashMap<String, ArrayList<ArrayList<String>>> GetUpperLower(ArrayList<ArrayList<String>> data, int n2,int m) {
		
		HashMap<String, ArrayList<ArrayList<String>>> UpperLower = new HashMap<String, ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> lowerr = new ArrayList<ArrayList<String>>(); 
		ArrayList<ArrayList<String>> upperr = new ArrayList<ArrayList<String>>(); 
		for(int n=0;n<n2;n++)
			lowerr.add(data.get(n));
		for(int n=n2;n<data.size();n++)
			upperr.add(data.get(n));
		UpperLower.put("lower", lowerr);
		UpperLower.put("upper", upperr);
		
		return UpperLower;
	}


	private ArrayList<Double> getClassProbs(ArrayList<ArrayList<String>> record){
		double N=record.size();
		HashMap<String, Integer > counts = new HashMap<String, Integer>();
		for(ArrayList<String> s : record){
			String clas = GetClass(s);
			if(counts.containsKey(clas))
				counts.put(clas, counts.get(clas)+1);
			else
				counts.put(clas, 1);
		}
		ArrayList<Double> probs = new ArrayList<Double>();
		for(Entry<String, Integer> entry : counts.entrySet()){
			double prob = entry.getValue()/N;
			probs.add(prob);
		}return probs;
	}
	
	private static final double logoftwo=Math.log(2);

	private double CalEntropy(ArrayList<Double> ps){
		double e=0;		
		for (double p:ps){
			if (p != 0) 
				e+=p*Math.log(p)/logoftwo;
		}
		return -e;
	}
	
	private ArrayList<Integer> GetVarsToInclude() {
		boolean[] whichVarsToInclude=new boolean[RandomForest.M];

		for (int i=0;i<RandomForest.M;i++)
			whichVarsToInclude[i]=false;
		
		while (true){
			int a=(int)Math.floor(Math.random()*RandomForest.M);
			whichVarsToInclude[a]=true;
			int N=0;
			for (int i=0;i<RandomForest.M;i++)
				if (whichVarsToInclude[i])
					N++;
			if (N == RandomForest.Ms)
				break;
		}
		
		ArrayList<Integer> shortRecord=new ArrayList<Integer>(RandomForest.Ms);
		
		for (int i=0;i<RandomForest.M;i++)
			if (whichVarsToInclude[i])
				shortRecord.add(i);
		return shortRecord;
	}

	public static String GetClass(ArrayList<String> record){
		return record.get(RandomForest.M).trim();
	}

	private String CheckIfLeaf(ArrayList<ArrayList<String>> data){
//		System.out.println("checkIfLeaf");
		boolean isCLeaf=true;
		String ClassA=GetClass(data.get(0));
		for(ArrayList<String> record : data){
			if(!ClassA.equalsIgnoreCase(GetClass(record))){
				isCLeaf = false;
				return null;
			}
		}
		if (isCLeaf)
			return GetClass(data.get(0));
		else
			return null;
	}

	private void FlushData(TreeNode node, int treenum){
		node.data=null;
		if(node.ChildNode!=null){
			for(TreeNode TN : node.ChildNode){
				if(TN != null)
					FlushData(TN,treenum);
			}
		}
	}

}
