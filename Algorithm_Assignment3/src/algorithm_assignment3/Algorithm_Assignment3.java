/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment3;
import java.io.*;
import java.util.*;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String str;
        String[] splited;
        int army_size, query_size, query_type;
        long score, standard;
        String name;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            army_size=Integer.parseInt(br.readLine());
            heaphash h=new heaphash();
            data soldier;
            while(army_size>0){
                army_size--;
                str=br.readLine();
                splited=str.trim().split("\\s+");
                name=splited[0];
                score=Long.parseLong(splited[1]);
                soldier=new data(name, score, h.len());
                h.ht_insert(soldier);
                h.insert(soldier);
            }
            query_size=Integer.parseInt(br.readLine());
            while(query_size>0){
                query_size--;
                str=br.readLine();
                splited=str.trim().split("\\s+");
                query_type=Integer.parseInt(splited[0]);
                if(query_type==1){
                    name=splited[1];
                    score=Long.parseLong(splited[2]);
                    if(h.ht_contains(name)){
                        soldier=h.ht_get(name);
                        soldier.score+=score;
                        h.update(soldier.pos);
                    }else{
                        //do nothing
                    }
                }else if(query_type==2){
                    score=Long.parseLong(splited[1]);
                    System.out.println(h.keep_above(score));
                }else{
                    //no query_type match, do nothing
                }
            }
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}

class heaphash{
    ArrayList<data> arr;
    Hashtable<String, data> ht;

    public heaphash() {
        this.arr=new ArrayList<data>();
        this.ht=new Hashtable<String, data>();
    }
    
    public int len(){
        return arr.size();
    }
    
    public void ht_insert(data item){
        ht.put(item.name, item);
    }
    
    public boolean ht_contains(String key){
        return ht.containsKey(key);
    }
    
    public data ht_get(String key){
        return ht.get(key);
    }
    
    public void update(int pos){
        sink_down(pos+1);
    }
    
    public int keep_above(long q){
        if(arr.isEmpty()){
            return 0;
        }else{
            if(arr.get(0).score<q){
                remove();
                return keep_above(q);
            }else{
                return arr.size();
            }
        }
    }
    public int insert(data d){
        arr.add(d);
        return float_up(arr.size());
    }
    
    public int float_up(int index){
        data parent_node, current_node;
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
            if(parent_node.score>current_node.score){
                parent_node.pos=current_pos;
                current_node.pos=parent_pos;
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
    
    public void remove(){
        if (arr.isEmpty()){
            //do nothing
            return;
        }else if(arr.size()==1){
            data target=arr.get(arr.size()-1);
            ht.remove(target.name, target);
            arr.remove(0);
        }else{
            data target=arr.get(0);
            ht.remove(target.name, target);
            target=arr.get(arr.size()-1);
            target.pos=0;
            arr.set(0, target);
            arr.remove(arr.size()-1);
            sink_down(1);
        }
    }
    
    public void sink_down(int index){
        int current_pos, left_child_pos, right_child_pos, min_pos;
        data node, left_child, right_child, min;
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
            if(left_child.score<=right_child.score){
                min_pos=left_child_pos;
                min=arr.get(min_pos);
            }else{
                min_pos=right_child_pos;
                min=arr.get(min_pos);
            }
        }
        //compare current_node with min_node
        if(node.score>min.score){
            min.pos=current_pos;
            node.pos=min_pos;
            arr.set(min_pos, node);
            arr.set(current_pos, min);
            sink_down(min_pos+1);
        }
    }
}
class data{
    String name;
    long score;
    int pos;

    public data(String name, long score, int pos) {
        this.name = name;
        this.score = score;
        this.pos = pos;
    }
    
}


