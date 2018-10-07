/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment4;
import java.io.*;
import java.util.*;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String str;
        String[] splited;
        int stage_size, edge_size, pre, suc;
        ArrayList<Integer> vertices=new ArrayList<Integer>();
        ArrayList<Integer> ordered_vertices=new ArrayList<Integer>();
        ArrayList<Integer> temp;
        ArrayList<ArrayList<Integer>> edges=new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            str=br.readLine();
            splited=str.trim().split("\\s+");
            stage_size=Integer.parseInt(splited[0]);
            for(int i=0;i<stage_size;i++){
                //initialize the array that tracks the in degree of each stage from 0 to n-1
                vertices.add(0);
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
                //initilize the in-degree for each node
                vertices.set(suc, vertices.get(suc)+1);
                //initilize the edges
                edges.get(pre).add(suc);
            }
            
            //check vertices of in-degree 0
            heap h=new heap();
            for(int i=0;i<stage_size;i++){
                if(vertices.get(i)==0){
                    h.insert(i);
                }
            }
            
            int next_v;
            while(h.len()>0){
                next_v=h.peek();
                h.remove();
                ordered_vertices.add(next_v+1);
                for(int w:edges.get(next_v)){
                    vertices.set(w, vertices.get(w)-1);
                    if(vertices.get(w)==0){
                        h.insert(w);
                    }
                }
            }
            
            if(ordered_vertices.size()<stage_size){
                System.out.println(-1);
            }else if(ordered_vertices.size()==stage_size){
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<stage_size;i++){
                    sb.append(ordered_vertices.get(i));
                    sb.append(" ");
                }
                System.out.println(sb.toString().trim());
            }
            
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
class heap{
    ArrayList<Integer> arr;

    public heap() {
        this.arr=new ArrayList<Integer>();
    }
    
    public int len(){
        return arr.size();
    }
    
    public void update(int pos){
        sink_down(pos+1);
    }
    
    public int insert(int d){
        arr.add(d);
        return float_up(arr.size());
    }
    
    public int float_up(int index){
        int parent_node, current_node;
        if(index==1){
            return 0;
        }
        int parent=index/2;
        int current_pos=index-1;
        int parent_pos=parent-1;
        parent_node=arr.get(parent_pos);
        current_node=arr.get(current_pos);
        //iterative implementation
        while(current_pos>0){
            if(parent_node>current_node){
                arr.set(parent_pos, current_node);
                arr.set(current_pos, parent_node);
                index=parent_pos+1;
                if(index<=1){
                    return 0;
                }
                parent=index/2;
                current_pos=index-1;
                parent_pos=parent-1;
                parent_node=arr.get(parent_pos);
                current_node=arr.get(current_pos); 
            }else{
                return current_pos;
            }
        }
        return 0;
    }
    
    public int peek(){
        if (arr.isEmpty()){
            //do nothing
            return -1;
        }else{
            return arr.get(0);
        }
    }
    
    public void remove(){
        if (arr.isEmpty()){
            //do nothing
            return;
        }else if(arr.size()==1){
            arr.remove(0);
        }else{
            int target=arr.get(arr.size()-1);
            arr.set(0, target);
            arr.remove(arr.size()-1);
            sink_down(1);
        }
    }
    
    public void sink_down(int index){
        int current_pos, left_child_pos, right_child_pos, min_pos;
        int node, left_child, right_child, min;
        int size=arr.size();
        current_pos=index-1;
        node=arr.get(current_pos);
        left_child_pos=2*index-1;
        right_child_pos=2*index;
        //recursive implementation, since iterative implementative is too complicated
        //find out the min_node first
        if(right_child_pos>=size){
            if(left_child_pos>=size){
                return;
            }else{
                min_pos=left_child_pos;
                min=arr.get(min_pos);
            }
        }else{
            left_child=arr.get(left_child_pos);
            right_child=arr.get(right_child_pos);
            if(left_child<=right_child){
                min_pos=left_child_pos;
                min=arr.get(min_pos);
            }else{
                min_pos=right_child_pos;
                min=arr.get(min_pos);
            }
        }
        //compare current_node with min_node
        if(node>min){
            arr.set(min_pos, node);
            arr.set(current_pos, min);
            sink_down(min_pos+1);
        }
    }
}
