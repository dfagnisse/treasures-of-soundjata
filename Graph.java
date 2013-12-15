import java.util.*;




public class Graph {
	Node[] theNodes;
	public Graph (String [] data) {
		int res=0;
		theNodes = new Node[data.length];
		for (int i=0; i<data.length; i++) {
			theNodes[i] = new Node(i);
		}
		for (int i=0; i<data.length; i++) {
			theNodes[i].setConnections(data[i]);
		}
	}
	public Graph (String [] data1, String [] data2) {
		theNodes = new Node[data1.length];
		for (int i=0; i<data1.length; i++) {
			theNodes[i] = new Node(i);
		}
		for (int i=0; i<data1.length; i++) {
			theNodes[i].setConnections(data1[i], data2[i]);
		}
	}
	
	public class Node {
		int id; //index position & name
		Node[] connections;
		Map<Node,Integer> neighbors; //store the weight;
		
		public Node (int n) {
			id=n;
			neighbors=new HashMap<Node,Integer>();
		}
		public Node(int n, String a) {
			id=n;
			setConnections(a);
		}
		
		public boolean setConnections(String b) {
			if(!b.equals("")) {
				String [] str=b.split(" ");
				connections = new Node[str.length];
				for (int k=0; k<str.length;k++) {
					int j=Integer.parseInt(str[k]);
					connections[k]=theNodes[j];//
					neighbors.put(theNodes[j],0);
//					System.out.println(myVals[k]);
				}
//				System.out.println(myVals);
			}
			return true;
		}
		public boolean setConnections(String b, String c) {
			if(!b.equals("")) {
				String [] str1=b.split(" ");
				String [] str2=c.split(" ");
				connections = new Node[str1.length];
				for (int k=0; k<str1.length;k++) {
					int j1=Integer.parseInt(str1[k]);
					int j2=Integer.parseInt(str2[k]);
					connections[k]=theNodes[j1];//
					neighbors.put(theNodes[j1],j2);
//					System.out.println(myVals[k]);
				}
//				System.out.println(myVals);
			}
			return true;
		}
		
		public boolean isConnectedto(Node other,Queue<Node> visited, Stack<Node> toTry) {
//			ArrayList<Modem> con = new ArrayList<Modem>();
			for (int i=0; i<other.connections.length; i++) {
				if(!visited.contains(other))
//					con.add(other.connections[i]);
					toTry.add(other.connections[i]);
			}
			if (toTry.contains(this)) {
//				System.out.println(visited);
//				visited.clear();
				return true;
			}
			if(toTry.isEmpty()){
				return false;
			}
			int k=0;
			visited.add(other);
			System.out.println(visited);
//			boolean[] res= new boolean[con.size()];
//			while(k<con.size()) {
//				return this.isConnectedto(con.get(k), visited);
//			}
			
			while(!toTry.isEmpty()) {
				return this.isConnectedto(toTry.pop(), visited, toTry);
			}
			
//			visited.poll();
			return false;
		}
		public boolean isConnectedtoAvoiding(Node other, Node broken, Queue<Node> visited, Queue<Node> toTry) {
//			ArrayList<Modem> con = new ArrayList<Modem>();
			for (int i=0; i<other.connections.length; i++) {
				if (!other.connections[i].equals(broken)&&!visited.contains(other))
//					con.add(other.connections[i]);
					toTry.add(other.connections[i]);
			}
//			System.out.println(con);
			if (toTry.contains(this)) {
//				System.out.println(visited);
//				visited.clear(); //
				return true;
			}
			if(toTry.isEmpty()){
				return false;
			}
			int k=0;
			visited.add(other);
//			System.out.println(visited);
//			boolean[] res= new boolean[con.size()];
//			while(k<con.size()) {
//				return this.isConnectedtoAvoiding(con.get(k), broken, visited, toTry);
//			}
			while(!toTry.isEmpty()) {
				return this.isConnectedtoAvoiding(toTry.poll(), broken, visited, toTry);
			}
//			visited.clear();
			return false;
		}
		public boolean isAP() { //AP means Articulation Point
			if (theNodes.length<=2)
				return false;// there needs at least 3 modems in the network for any to even be an AP
			boolean res=true;
			Queue<Node> visited=new LinkedList<Node>();
			Queue<Node> toTry= new LinkedList<Node>();
			for (int i=0; i<theNodes.length;i++) {
				for (int j=0; j<theNodes.length;j++){
					if (i!=this.id&&j!=this.id){
//					System.out.println(i+" "+j+" "+theModems[i].isConnectedtoAvoiding(theModems[j], this, visited,  toTry)+" avoiding "+this.id+" "+toTry+" "+visited);
					res=res&&theNodes[i].isConnectedtoAvoiding(theNodes[j], this, visited, toTry);
					toTry.clear(); visited.clear();
					}
				}
			}
			return !res;
		}
		public String toString() {
			String res = new String();
			StringBuilder theConnections= new StringBuilder();
			theConnections.append("[");
			if (connections.length!=0) {
				for (int a=0; a<connections.length; a++) {
					theConnections.append(connections[a].id+",");
				}
				theConnections.delete(theConnections.lastIndexOf(","),theConnections.lastIndexOf(",")+1);
			}
			theConnections.append("]");
			res = "{ "+this.id+" : "+theConnections.toString()+" }";
			return res;
		}
		
	}
	
	public boolean viewMatrix() {
		System.out.print("\\co");
		for (int i=0; i<theNodes.length; i++) {
			System.out.print("| "+i+" ");
		}
		System.out.println("|");
		for (int i=0; i<theNodes.length; i++) {
			System.out.print(i+"  ");
			for (int j=0; j<theNodes.length; j++) {
				if (theNodes[i].neighbors.containsKey(theNodes[j])){
					String toprint=theNodes[i].neighbors.get(theNodes[j]).toString();
					if(toprint.equals("0")) {toprint="X";}
					System.out.print("| "+toprint+" ");
				}
				else
					System.out.print("|   ");
			}
			System.out.println("|");
		}
		return false;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[]test={"","2 3 5","4 5","5 6","7","7 8","8 9","10",
	            "10 11 12","11","12","12",""};
		String[]test2={"","3 2 9","2 4","6 9","3","1 2","1 2","5",
	            "5 6 9","2","5","3",""};
		Graph tester=new Graph(test);
		Graph tester2=new Graph(test, test2);
		tester.viewMatrix();
		tester2.viewMatrix();
	}

}
