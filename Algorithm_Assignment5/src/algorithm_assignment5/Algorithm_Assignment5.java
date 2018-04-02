/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment5;
import java.io.*;
import java.util.*;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String str;
        String[] splited;
        int room_size, edge_size, pre, suc;
        ArrayList<Integer> color_vectors=new ArrayList<Integer>();
        ArrayList<Integer> p=new ArrayList<Integer>();
        ArrayList<Integer> temp;
        ArrayList<ArrayList<Integer>> edges=new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            str=br.readLine();
            splited=str.trim().split("\\s+");
            room_size=Integer.parseInt(splited[0]);
            for(int i=0;i<room_size;i++){
                //initialize the array that tracks the color for each node
                color_vectors.add(0);
                //initilize the array that tracks the predecessor
                p.add(-1);
                //initialize the adjacency list representation
                temp=new ArrayList<Integer>();
                edges.add(temp);
            }
            edge_size=Integer.parseInt(splited[1]);
            while(edge_size>0){
                edge_size--;
                str=br.readLine();
                splited=str.trim().split("\\s+");
                pre=Integer.parseInt(splited[0])-1;
                suc=Integer.parseInt(splited[1])-1;
                //initilize the edges
                edges.get(pre).add(suc);
            }
            
            DFS(color_vectors, p, edges);
            
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void DFS(ArrayList<Integer> color_vectors, ArrayList<Integer> p, ArrayList<ArrayList<Integer>> edges){
        int result=-1;
        for(int i=0;i<color_vectors.size();i++){
            if(color_vectors.get(i)==0){
                result=RecDFS(color_vectors, p, edges, i);
                if(result!=-1){
                    break;
                }
            }
        }
        if(result!=-1){
            ArrayList<Integer> cycle=new ArrayList<Integer>();
            int cur=result;
            int pre=p.get(cur);
            do{
                cycle.add(0, cur+1);
                cur=pre;
                pre=p.get(cur);
            }while(cur!=result);
            
            
            System.out.println(1);
            StringBuilder sb=new StringBuilder();
            for(int node:cycle){
                sb.append(node);
                sb.append(" ");
            }
            System.out.println(sb.toString().trim());
        }else{
            System.out.println(0);
        }
    }
    
    public static int RecDFS(ArrayList<Integer> color_vectors, ArrayList<Integer> p, ArrayList<ArrayList<Integer>> edges, int u){
        int result=-1;
        color_vectors.set(u, 1);
        for (int v:edges.get(u)){
            if (color_vectors.get(v)==0){
                p.set(v, u);
                result=RecDFS(color_vectors, p, edges, v);
                if(result!=-1){
                    return result;
                }
            }else if(color_vectors.get(v)==1){
                p.set(v, u);
                return u;
            }
        }
        color_vectors.set(u,2);
        return -1;
    }
}
