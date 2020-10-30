/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robothieves;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author zhuan
 */
public class RoboThieves {

    

    static char[][] map=null;
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
        for (int i=0;i<n;i++) {
            for (int j=0;j<m;j++) {
                if (map[i][j]=='.') {
                    if (status[i][j]>0)
                        System.out.println(status[i][j]);
                    else 
                        System.out.println("-1");
                }
            }
        }
    }
    
    private static void readIn() {
        Scanner sc=new Scanner(System.in);
        n=sc.nextInt();
        m=sc.nextInt();
        sc.nextLine();
        map=new char[n][m];
        status=new int[n][m];
        for (int i=0;i<n;i++) {
            String l=sc.nextLine();
            for (int j=0;j<m;j++)
            {
                map[i][j]=l.charAt(j);
                if (map[i][j]=='W')
                    status[i][j]=-2;
                else if(map[i][j]=='C'){
                    Integer[] temp = new Integer[2];
                    temp[0] = i;
                    temp[1] = j;
                    cams.add(temp);
                    status[i][j]=-2;
                }
                else if (map[i][j]=='S') {
                    start[0]=i;
                    start[1]=j;
                    status[i][j]=0;
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
            if(map[i][j]=='C'){
                status[i][j] = -2;
                for(int u=i-1;u>=0;u--){
                    if(map[u][j]=='.')
                        status[u][j] = -2;
                    else if (map[u][j]=='W')
                        break;
                }
                for(int d=i+1;d<n;d++){
                    if(map[d][j]=='.')
                        status[d][j] = -2;
                    else if (map[d][j]=='W')
                        break;
                }
                for(int l=j-1;l>=0;l--){
                    if(map[i][l]=='.')
                        status[i][l] = -2;
                    else if (map[i][l]=='W')
                        break;
                }
                for(int r=j+1;r<m;r++){
                    if(map[i][r]=='.')
                        status[i][r] = -2;
                    else if (map[i][r]=='W')
                        break;
                }
            }
        }
    }
    
    private static void process()  {
        //typical BFS algorithm
        ArrayList<Integer[]> currentNodes=new ArrayList();
        ArrayList<Integer[]> nextNodes=new ArrayList();
        for(int i=0;i<cams.size();i++){
            if(cams.get(i)[0]==start[0] || cams.get(i)[1]==start[1])
                return;
        }
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

    private static Integer[] move(Integer[] node, int i, int j) {
        
        int x = node[0] + j;
        int y = node[1] + i;
        if (x<0 || x>=n || y<0 || y>=m) return null;
        if (status[x][y]==-2 || status[x][y]>=0) return null;
        Integer[] rt=new Integer[2];
        rt[0]=x;
        rt[1]=y;
        switch (map[x][y]) {
            case 'L':
                return move(rt,-1,0);
            case 'R':
                return move(rt,1,0);
            case 'U':
                return move(rt,0,-1);
            case 'D':
                return move(rt,0,1);
            default:
                break;
        }
        
        return rt;
        
        /*while(true){
            if(map[temp[0]][temp[1]]=='L') {
                temp[1]--;
                if (temp[1]<0 || status[temp[0]][temp[1]==-2) return null;
            }
            else if(map[temp[0]][temp[1]]=='R')
                temp[1]++;
                if (temp[1]>=n || status[temp[0]][temp[1]==-2) return null;
            else if(map[temp[0]][temp[1]]=='U')
                temp[0]++;
                if (temp[0]<0 || status[temp[0]][temp[1]]==-2) return null;
            else if(map[temp[0]][temp[1]]=='D')
                temp[0]--;
                if (temp[0]>=n || status[temp[0]][temp[1]==-2) return null;
            else if (status[temp[0]][temp[1]]==-1) {
                return temp;
            }
            else return null;
            
        }
        return temp;
        */
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
