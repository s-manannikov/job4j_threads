package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            for (int j = 0; j < matrix.length; j++) {
                int sumRow = matrix[i][j] + sums[i].getRowSum();
                int sumCol = matrix[j][i] + sums[i].getColSum();
                sums[i].setRowSum(sumRow);
                sums[i].setColSum(sumCol);
            }
        }
        return sums;
    }

    public Sums[] asyncSum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            map.put(i, getTask(matrix, i));
        }
        for (Integer key : map.keySet()) {
            try {
                sums[key] = map.get(key).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return sums;
    }

    public CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
            Sums sums = new Sums();
                for (int i = 0; i < matrix.length; i++) {
                    int sumRow = matrix[index][i] + sums.getRowSum();
                    int sumCol = matrix[i][index] + sums.getColSum();
                    sums.setRowSum(sumRow);
                    sums.setColSum(sumCol);
                }
            return sums;
        });
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum + ", colSum=" + colSum + '}';
        }
    }
}