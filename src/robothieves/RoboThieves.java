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
    static Integer[] start=new Integer[2];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readIn();
        preprocess();
        process();
    }
    
    private static void readIn() {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int m=sc.nextInt();
        map=new int[n][m];
        status=new int[n][m];
        for (int i=0;i<n;i++) {
            String l=sc.next();
            for (int j=0;j<m;j++)
            {
                map[i][j]=l.charAt(j);
                if (map[i][j]=='W')
                    status[i][j]=-2;
                else 
                    status[i][j]=-1;
            }
        }
        
    }

    private static void preprocess() {
        
        markStatusForWalls(); // mark all the W points with -2
        markStatusForAllCameras(); // mark all the . where camera can see with -2
        
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
