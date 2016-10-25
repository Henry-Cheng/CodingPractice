
import java.io.*;
import java.util.*;
import java.math.*;

public class ConnectedCellsInGrid {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        final int n = Integer.parseInt(scanner.nextLine()); // get rows length n
        final int m = Integer.parseInt(scanner.nextLine()); // get columns length m
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) { // get matrix
            String[] row = scanner.nextLine().split("\\s+");
            for (int j = 0; j < m; j++) {
                a[i][j] = Integer.parseInt(row[j]);
            } 
        }
        // start visiting matrix
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) { // get matrix
            for (int j = 0; j < m; j++) {
                if(a[i][j] != -1) { // not visited
                    max = Math.max(BFD(a, n, m, i, j), max);
                }
            }
        }
        System.out.println(max);
    }
    
    public static int BFD(int[][] a, int row, int col, int valueRow, int valueCol) {
        int connectedNum = 0;
        Queue<String> queue = new LinkedList<String>();
        queue.add(valueRow+" "+valueCol); // "i j" is enqueued
        while(!queue.isEmpty()) {
            String[] value = queue.poll().split("\\s+");
            int i = Integer.parseInt(value[0]);
            int j = Integer.parseInt(value[1]);
            if (i >= 0 && i < row && j >= 0 && j < col) {
                if (a[i][j] == 1) {
                    connectedNum++;
                    queue.add((i-1)+" "+(j-1));
                    queue.add((i-1)+" "+(j));
                    queue.add((i-1)+" "+(j+1));
                    queue.add((i)+" "+(j-1));
                    queue.add((i)+" "+(j+1));
                    queue.add((i+1)+" "+(j-1));
                    queue.add((i+1)+" "+(j));
                    queue.add((i+1)+" "+(j+1));
                }
                a[i][j] = -1; // mark it as visited
            }
        }
        return connectedNum;
    }
}