package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void notAsyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum rcs = new RolColSum();
        assertEquals(rcs.sum(matrix)[0].getRowSum(), 6);
        assertEquals(rcs.sum(matrix)[1].getColSum(), 15);
        assertEquals(rcs.sum(matrix)[2].getRowSum(), 24);
    }

    @Test
    public void asyncSum() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        RolColSum rcs = new RolColSum();
        assertEquals(rcs.asyncSum(matrix)[0].getColSum(), 12);
        assertEquals(rcs.asyncSum(matrix)[1].getRowSum(), 15);
        assertEquals(rcs.asyncSum(matrix)[2].getColSum(), 18);
    }
}