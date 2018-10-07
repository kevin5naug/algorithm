/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment6;
import java.io.*;
import java.util.*;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Try linked list representation
        String str;
        String[] splited;
        int vertice_size, edge_size, pre, suc, indicator;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            str=br.readLine();
            splited=str.trim().split("\\s+");
            vertice_size=Integer.parseInt(splited[0]);
            edge_size=Integer.parseInt(splited[1]);
            EdgeNode[] allEdges=new EdgeNode[vertice_size];
            for(int i=0;i<edge_size;i++){
                str=br.readLine();
                splited=str.trim().split("\\s+");
                pre=Integer.parseInt(splited[0])-1;
                suc=Integer.parseInt(splited[1])-1;
                indicator=Integer.parseInt(splited[2])-1;
                EdgeNode sucNode=new EdgeNode(suc, indicator);
                //linked list structure for efficiency
                sucNode.next=allEdges[pre];
                allEdges[pre]=sucNode;
            }
            
            EdgeNode[] G=new EdgeNode[vertice_size*2];
            EdgeNode cur, addEdge;
            int newWeight;
            for(int i=0;i<allEdges.length;i++){
                cur=allEdges[i];
                while(cur!=null){
                    newWeight=1;
                    if(cur.weight==0){
                        newWeight=0;
                        addEdge=new EdgeNode(cur.suc+vertice_size, newWeight);
                        //linked list structure for efficiency
                        addEdge.next=G[i];
                        G[i]=addEdge;
                    }
                    //linked list structure
                    addEdge=new EdgeNode(cur.suc, newWeight);
                    addEdge.next=G[i];
                    G[i]=addEdge;
                    addEdge=new EdgeNode(cur.suc+vertice_size, newWeight);
                    addEdge.next=G[i+vertice_size];
                    G[i+vertice_size]=addEdge;
                    cur=cur.next;
                }
            }
            dijkstra d=new dijkstra(G, 0);
            d.run();
            d.report();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

class dijkstra{
    EdgeNode[] G;
    int start;
    int[] d;
    VerticeNode[] buckets;
    int next;

    public dijkstra(EdgeNode[] G, int start) {
        this.G = G;
        this.start = start;
        this.d=new int[G.length];
        for(int i=0;i<d.length;i++){
            //-1 implies infinity in our implementation
            d[i]=-1;
        }
        this.buckets=new VerticeNode[G.length];
        this.next=0;
        add(0,start);
        d[start]=0;
    }
    
    public void report(){
        System.out.println(d[G.length-1]);
    }
    
    public void run(){
        int q_size=1;
        int u=-1;
        int v,i,du,d_new, d_old;
        EdgeNode cur;
        while(q_size!=0){
            u=ExtractMin();
            q_size--;
            du=d[u];
            cur=this.G[u];
            while(cur!=null){
                v=cur.suc;
                d_old=d[v];
                d_new=du+cur.weight;
                if(d_old==-1){
                    d[v]=d_new;
                    add(d_new, v);
                    q_size++;
                }else if(d_new<d_old){
                    d[v]=d_new;
                    add(d_new, v);
                }
                cur=cur.next;
            }
        }
    }
    
    public void add(int index, int vertice){
        VerticeNode v=new VerticeNode(vertice);
        v.next=this.buckets[index];
        this.buckets[index]=v;
    }
    
    public int ExtractMin(){
        while(this.buckets[this.next]==null){
            this.next++;
        }
        VerticeNode target=this.buckets[this.next];
        this.buckets[this.next]=target.next;
        return target.id;
    }
    
    
}
class VerticeNode{
    int id;
    VerticeNode next;

    public VerticeNode(int id) {
        this.id = id;
        this.next = null;
    }
    
    
}
class EdgeNode{
    int suc;
    int weight;
    EdgeNode next;

    public EdgeNode(int suc, int weight) {
        this.suc = suc;
        this.weight = weight;
        this.next=null;
    }
}
