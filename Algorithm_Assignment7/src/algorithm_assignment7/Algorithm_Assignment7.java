/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment7;
import java.io.*;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String a,b,str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            str=br.readLine();
            a=str.trim();
            str=br.readLine();
            b=str.trim();
            best_match(a,b);
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static void best_match(String a, String b){
        //iterative implementation
        int[][] max_score=new int[a.length()+1][b.length()+1];
        
        int i,j, max, score;
        char x,y;
        
        //base case;
        for(j=0;j<b.length()+1;j++){
            max_score[0][j]=-1*j;
        }
        for(i=0;i<a.length()+1;i++){
            max_score[i][0]=-1*i;
        }
        
        //main loop
        for(i=1;i<a.length()+1;i++){
            for(j=1;j<b.length()+1;j++){
                //we pair last two characters first and compute the score
                x=a.charAt(i-1);
                y=b.charAt(j-1);
                max=max_score[i-1][j-1]+match_score(x,y);
                //next we pair the last character of a with "-"
                score=max_score[i-1][j]-1;
                if(score>max){
                    max=score;
                }
                //next we pair the last character of b with "-"
                score=max_score[i][j-1]-1;
                if(score>max){
                    max=score;
                }
                max_score[i][j]=max;
            }
        }
        
        //reconstruct solution
        i=a.length();
        j=b.length();
        max=max_score[i][j];
        String a_sol="";
        String b_sol="";
        while((i>0)&&(j>0)){
            x=a.charAt(i-1);
            y=b.charAt(j-1);
            if(max==(max_score[i-1][j-1]+match_score(x,y))){
                //pair the last two characters
                max=max_score[i-1][j-1];
                a_sol=Character.toString(x).concat(a_sol);
                b_sol=Character.toString(y).concat(b_sol);
                i--;
                j--;
            }else if(max==(max_score[i][j-1]-1)){
                //pair the last character of b with "-"
                max=max_score[i][j-1];
                a_sol="_".concat(a_sol);
                b_sol=Character.toString(y).concat(b_sol);
                j--;
            }else if(max==(max_score[i-1][j]-1)){
                //pair the last character of a with "-"
                max=max_score[i-1][j];
                a_sol=Character.toString(x).concat(a_sol);
                b_sol="_".concat(b_sol);
                i--;
            }
        }
        
        //like merge sort, if there is remainder, we pair it with "_"
        while(i>0){
            x=a.charAt(i-1);
            a_sol=Character.toString(x).concat(a_sol);
            b_sol="_".concat(b_sol);
            i--;
        }
        while(j>0){
            y=b.charAt(j-1);
            a_sol="_".concat(a_sol);
            b_sol=Character.toString(y).concat(b_sol);
            j--;
        }
        
        System.out.println(max_score[a.length()][b.length()]);
        System.out.println(a_sol);
        System.out.println(b_sol);
    }
    
    public static int match_score(char x, char y){
        if(x==y){
            return 2;
        }else{
            return -1;
        }
    }
}
