/**
 * Course: EECS 114 Fall 2015
 *
 * First Name: Derek
 * Last Name: Zhang
 * Lab Section: 1A
 * email address: derekz@uci.edu
 *
 *
 * Assignment: assn5
 * Filename : Jug.java
 *
 * I hereby certify that the contents of this file represent
 * my own original individual work. Nowhere herein is there 
 * code from any outside resources such as another individual,
 * a website, or publishings unless specifically designated as
 * permissible by the instructor or TA.
 */ 
//package assn5;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class Jug {
	
	private static int Ca;
	private static int Cb;
	private static int N;
	private static int fA;
	private static int fB;
	private static int eA;
	private static int eB;
	private static int pAB;
	private static int pBA;
	
	private static class Vertex{
		int curA;
		int curB;
		private int distance;
		private int key;
		private Vertex parent;
		private LinkedList<Vertex> neighbors;
		public Vertex(int A, int B, int D){
			this.key = (A+B)*((A+B+1)/2)+A;
			this.curA = A;
			this.curB = B;
			this.distance = D;
			this.parent = null;
			this.neighbors = new LinkedList<Vertex>();
		}
		public Vertex(Vertex clone){
			this.key = clone.key;
			this.curA = clone.curA;
			this.curB = clone.curB;
			this.distance = clone.distance;
			this.parent = clone.parent;
			this.neighbors = new LinkedList<Vertex>();
		}
	}
	
	private LinkedList<Vertex> vertices = new LinkedList<Vertex>();

	public Jug(int Ca, int Cb, int N, int fA, int fB, int eA, int eB, int pAB, int pBA){
		this.Ca = Ca;
		this.Cb = Cb;
		this.N = N;
		this.fA = fA;
		this.fB = fB;
		this.eA = eA;
		this.eB = eB;
		this.pAB = pAB;
		this.pBA = pBA;
		build_graph();
	}

	public void build_graph(){
		Vertex source = new Vertex(0,0,0);
		LinkedBlockingQueue<Vertex> queue = new LinkedBlockingQueue<Vertex>();
		vertices.add(source);
		queue.add(source);
		while(!queue.isEmpty()){
			Vertex parent = queue.poll();
			Vertex temp = new Vertex(parent);
			if(temp.curA != Ca){
				temp.curA = Ca;						//fill A
				Vertex newVertex = new Vertex(temp.curA,temp.curB,temp.distance + fA);
				if(!this.contain(newVertex)){		//if Node does not already exist
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
			if(temp.curB != Cb){
				temp.curB = Cb;						//fill B
				Vertex newVertex = new Vertex(temp.curA,temp.curB,temp.distance + fB);
				if(!this.contain(newVertex)){
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
			if(temp.curA != 0){
				temp.curA = 0;							//empty A
				Vertex newVertex = new Vertex(temp.curA,temp.curB,temp.distance+eA);
				if(!this.contain(newVertex)){
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
			if(temp.curB != 0){
				temp.curB = 0;							//empty B
				Vertex newVertex = new Vertex(temp.curA, temp.curB,  temp.distance + eB);
				if(!this.contain(newVertex)){
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
			if(temp.curA != Ca){
				temp.curA = temp.curA + temp.curB;			//pour B into A
				temp.curB = 0;
				if(temp.curA > Ca){
					int remainder = temp.curA - Ca;
					temp.curA = Ca;
					temp.curB = remainder;
				}
				Vertex newVertex = new Vertex(temp.curA, temp.curB,temp.distance + pBA);
				if(!this.contain(newVertex)){
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
			if(temp.curB != Cb){
				temp.curB = temp.curA + temp.curB;			//pour A into B
				temp.curA = 0;
				if(temp.curB > Cb){
					int remainder = temp.curB - Cb;
					temp.curA = remainder;
					temp.curB = Cb;
				}
				Vertex newVertex = new Vertex(temp.curA, temp.curB, temp.distance + pAB);
				if(!this.contain(newVertex)){
					vertices.add(newVertex);
					queue.add(newVertex);
				}
				if(newVertex.curA != parent.curA || newVertex.curB != parent.curB){
					parent.neighbors.add(newVertex);
					newVertex.parent = parent;
				}
				temp = new Vertex(parent);
			}
		}
		queue.clear();
	}
	
	public boolean contain(Vertex v){
		for(Vertex V:vertices){
			if(v.key == V.key){
				return true;
			}
		}
		return false;
	}
	
	public void display_graph(){
		for(Vertex v:vertices){
			System.out.print(v.curA + "," + v.curB + " -> ");
			for(Vertex V:v.neighbors){
				System.out.print(V.curA + "," + V.curB + "||");
			}
			System.out.println("");
		}
	}
	
	public void solve(){
		LinkedBlockingQueue<Vertex> visited = new LinkedBlockingQueue<Vertex>();
		Vertex source = vertices.get(0);
		source.distance = 0;
		int i = 0;
		for(Vertex temp:vertices){
			if(temp.curA == 0 && temp.curB == N){
				while(!temp.equals(source)){
					visited.add(temp);
					temp = temp.parent;
					i++;
				}
				visited.add(source);
				i++;
				break;
			}
		}
		int count = visited.size();
		Vertex[] path = new Vertex[visited.size()];
		while(!visited.isEmpty()){
			Vertex temp = visited.poll();
			path[count-i] = temp;
			i--;
		}
		for(int j=path.length-1;j>=0;j--){
			System.out.println("A=" + path[j].curA + ",B=" + path[j].curB + "  distance: "+ path[j].distance);
		}
		visited.clear();
	}
}
