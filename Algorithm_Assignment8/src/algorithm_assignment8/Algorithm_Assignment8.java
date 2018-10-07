/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_assignment8;
import java.io.*;
import java.util.Arrays;
/**
 *
 * @author joker
 */
public class Algorithm_Assignment8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String str;
        String[] splited, sa, sb;
        int degree;
        int i;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            str=br.readLine();
            splited=str.trim().split("\\s+");
            degree=Integer.parseInt(splited[0]);
            str=br.readLine();
            sa=str.trim().split("\\s+");
            str=br.readLine();
            sb=str.trim().split("\\s+");
            int[] a=new int[degree+1];
            int[] b=new int[degree+1];
            for(i=0;i<=degree;i++){
                a[i]=Integer.parseInt(sa[i]);
                b[i]=Integer.parseInt(sb[i]);
            }
            int[] c=new int[2*degree+1];
            c=kara(a,b,degree+1);
            for(i=0;i<2*degree;i++){
                System.out.print(c[i]);
                System.out.print(" ");
            }
            System.out.println(c[2*degree]);
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static int[] kara(int[] a, int[] b, int size){
        if(size<=4){
            return simple(a,b,size);
        }else{
            int new_size=size/2;
            int[] f_low=new int[new_size];
            int[] f_high=new int[new_size];
            int[] g_low=new int[new_size];
            int[] g_high=new int[new_size];
            int[] F=new int[new_size];
            int[] G=new int[new_size];
            int[] c=new int[2*size-1];
            int i;
            for(i=0;i<size;i++){
                if(i<new_size){
                    f_low[i]=a[i];
                    g_low[i]=b[i];
                }else{
                    f_high[i-new_size]=a[i];
                    g_high[i-new_size]=b[i];
                }
            }
            for(i=0;i<new_size;i++){
                F[i]=f_low[i]+f_high[i];
                G[i]=g_low[i]+g_high[i];
            }
            int[] U=kara(f_high,g_high,new_size);
            int[] V=kara(f_low,g_low,new_size);
            int[] W=kara(F,G,new_size);
            for(i=0;i<2*new_size-1;i++){
                c[i]=c[i]+V[i];
                c[i+new_size]=c[i+new_size]+W[i]-U[i]-V[i];
                c[i+2*new_size]=c[i+2*new_size]+U[i];
            }
            return c;
        }
    }
    
    public static int[] simple(int[] a, int[] b, int size){
        int[] c=new int[2*size-1];
        int i,j;
        for(i=0;i<size;i++){
            for(j=0;j<size;j++){
                c[i+j]=c[i+j]+a[i]*b[j];
            }
        }
        return c;
    }
}
