/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robothieves;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class RoboThieves {

    

    static int[][] map=null;
    static int[][] status=null;
    static ArrayList<Integer[]> cams =new ArrayList();
    static Integer[] start=new Integer[2];
    static int n, m;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readIn();
        markStatusForAllCameras();
        process();
    }
    
    private static void readIn() {
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        m=sc.nextInt();
        map=new int[n][m];
        status=new int[n][m];
        for (int i=0;i<n;i++) {
            String l=sc.next();
            for (int j=0;j<m;j++)
            {
                map[i][j]=l.charAt(j);
                if (map[i][j]=='W')
                    status[i][j]=-2;
                else if(map[i][j]=='C'){
                    Integer[] temp = new Integer[2];
                    temp[0] = i;
                    temp[1] = j;
                }
                else 
                    status[i][j]=-1;
            }
        }
        
    }
    
    private static void markStatusForAllCameras() {
        for(Integer[] cam:cams){
            int i = cam[0];
            int j = cam[1];
            if(map[i][j]=='c'){
                map[i][j] = -2;
                for(int u=i-1;u>=0;u--){
                    if(map[u][j]!=-2)
                        map[u][j] = -2;
                    else
                        break;
                }
                for(int d=i+1;d<n;d++){
                    if(map[d][j]!=-2)
                        map[d][j] = -2;
                    else
                        break;
                }
                for(int l=j-1;l>=0;l--){
                    if(map[i][l]!=-2)
                        map[i][l] = -2;
                    else
                        break;
                }
                for(int r=j+1;r<m;r++){
                    if(map[i][r]!=-2)
                        map[i][r] = -2;
                    else
                        break;
                }
            }
        }
    }
    
    private static void process()  {
        //typical BFS algorithm
        ArrayList<Integer[]> currentNodes=new ArrayList();
        ArrayList<Integer[]> nextNodes=new ArrayList();
        currentNodes.add(start);
        int step=0;
        
        while (!currentNodes.isEmpty()) {
            for (Integer[] node:currentNodes) {
                status[node[0]][node[1]]=step;
                nextNodes.addAll(getNeighbour(node));
            }
            step++;
            currentNodes=nextNodes;
            nextNodes=new ArrayList();
        }
    }

    private static Integer[] move(Integer[] node, int i, int i0) {
        Integer[] temp = new Integer[2];
        temp[0] = node[0] + i;
        temp[1] = node[1] + i0;
        while(map[temp[0]][temp[1]]!=-1){
            if(map[temp[0]][temp[1]]==-2)
                return null;
            if(map[temp[0]][temp[1]]=='L')
                temp[1]--;
            if(map[temp[0]][temp[1]]=='R')
                temp[1]++;
            if(map[temp[0]][temp[1]]=='U')
                temp[0]++;
            if(map[temp[0]][temp[1]]=='D')
                temp[0]--;
        }
        return temp;
    }
    
    private static ArrayList<Integer[]> getNeighbour(Integer[] node) {
        ArrayList<Integer[]> rt=new ArrayList();
        Integer[] left=move(node,-1,0);
        Integer[] right=move(node,1,0);
        Integer[] up=move(node,0,-1);
        Integer[] down=move(node,0,1);
        
        if (left!=null) rt.add(left);
        if (right!=null) rt.add(right);
        if (up!=null) rt.add(up);
        if (down!=null) rt.add(down);
        return rt;
    }

}
