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

    static char[][] map = null;
    static int[][] status = null;
    static ArrayList<int[]> cams = new ArrayList();
    static int[] start = new int[2];
    static int n, m;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        readIn();
        markStatusForAllCameras();
        process();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == '.') {
                    if (status[i][j] > 0) {
                        System.out.println(status[i][j]);
                    } else {
                        System.out.println("-1");
                    }
                }
            }
        }
    }

    private static void readIn() {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        sc.nextLine();
        map = new char[n][m];
        status = new int[n][m];
        for (int i = 0; i < n; i++) {
            String l = sc.nextLine();
            for (int j = 0; j < m; j++) {
                char c = map[i][j] = l.charAt(j);
                switch (c) {
                    case 'W':
                        status[i][j] = -2;
                        break;
                    case 'C':
                        int[] temp = new int[2];
                        temp[0] = i;
                        temp[1] = j;
                        cams.add(temp);
                        status[i][j] = -2;
                        break;
                    case 'S':
                        start[0] = i;
                        start[1] = j;
                        status[i][j] = -1;
                        break;
                    default:
                        status[i][j] = -1;
                        break;
                }
            }
        }

    }

    private static void markStatusForAllCameras() {
        for (int[] cam : cams) {
            int i = cam[0];
            int j = cam[1];
            if (start[0]==i || start[1]==j) {
                status[start[0]][start[1]]=-2;
                break;
            }
            
            for (int k = i - 1; k >= 0; k--) {
                if (map[k][j] == '.') {
                    status[k][j] = -2;
                } else if (map[k][j] == 'W') {
                    break;
                }
            }
            for (int k = i + 1; k < n; k++) {
                if (map[k][j] == '.') {
                    status[k][j] = -2;
                } else if (map[k][j] == 'W') {
                    break;
                }
            }
            for (int k = j - 1; k >= 0; k--) {
                if (map[i][k] == '.') {
                    status[i][k] = -2;
                } else if (map[i][k] == 'W') {
                    break;
                }
            }
            for (int k = j + 1; k < m; k++) {
                if (map[i][k] == '.') {
                    status[i][k] = -2;
                } else if (map[i][k] == 'W') {
                    break;
                }
            }
            
        }
    }

    private static void process() {
        //typical BFS algorithm
        ArrayList<int[]> currentNodes = new ArrayList();
        ArrayList<int[]> nextNodes = new ArrayList();
        if (status[start[0]][start[1]] == -2) {
            return;
        }

        currentNodes.add(start);
        int step = 0;

        while (!currentNodes.isEmpty()) {
            for (int[] node : currentNodes) {
                status[node[0]][node[1]] = step;
                nextNodes.addAll(getNeighbour(node));
            }
            step++;
            currentNodes = nextNodes;
            nextNodes = new ArrayList();
        }
    }

    private static int[] move(int[] node, int i, int j) {

        int x = node[0] + i;
        int y = node[1] + j;
        if (x < 0 || x >= n || y < 0 || y >= m) {
            return null;
        }
        if (status[x][y]!=-1)
            return null;
        
        int[] rt = new int[2];
        rt[0] = x;
        rt[1] = y;
        switch (map[x][y]) {
            case 'L':
                status[x][y]=-2;
                return move(rt, 0, -1);
            case 'R':
                status[x][y]=-2;
                return move(rt, 0, 1);
            case 'U':
                status[x][y]=-2;
                return move(rt, -1, 0);
            case 'D':
                status[x][y]=-2;
                return move(rt, 1, 0);
            default:
                break;
        }
       
        return rt;
    }

    private static ArrayList<int[]> getNeighbour(int[] node) {
        ArrayList<int[]> rt = new ArrayList();
        int[] left = move(node, 0, -1);
        int[] right = move(node, 0, 1);
        int[] up = move(node, -1, 0);
        int[] down = move(node, 1, 0);

        if (left != null) {
            rt.add(left);
        }
        if (right != null) {
            rt.add(right);
        }
        if (up != null) {
            rt.add(up);
        }
        if (down != null) {
            rt.add(down);
        }
        return rt;
    }

}
