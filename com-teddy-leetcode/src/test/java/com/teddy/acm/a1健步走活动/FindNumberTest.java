package com.teddy.acm.a1健步走活动;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FindNumberTest {

    @Test
    public void find() {
        assertEquals(7, FindNumber.find(new int[]{3,4,1,2,5,6,5,2,3,1,7,6,4}));
        assertEquals(4, FindNumber.find(new int[]{3,3,4}));
    }

    @Test
    void findCourse() {
        assertEquals(-1, FindNumber.findCourse(1, new int[][]{{1}}));
        assertEquals(2, FindNumber.findCourse(2, new int[][]{{1,2}}));
        assertEquals(3, FindNumber.findCourse(3, new int[][]{{1,3},{2,3}}));
        assertEquals(-1, FindNumber.findCourse(3, new int[][]{{1,2},{2,3}}));
        assertEquals(2, FindNumber.findCourse(3, new int[][]{{1,2},{3,2}}));
        assertEquals(3, FindNumber.findCourse(4, new int[][]{{1,3},{2,3},{4,3}}));
        assertEquals(-1, FindNumber.findCourse(4, new int[][]{{1,3},{3,2},{4,3}}));
        assertEquals(-1, FindNumber.findCourse(7, new int[][]{{1,3},{2,3},{4,3}, {5,4}, {4,7}, {7, 3}}));
        assertEquals(-1, FindNumber.findCourse(4, new int[][]{{1,3},{2,3},{4,2}, {3,4}}));
    }

    @Test
    void maxDestorySum() {
        assertEquals(20, FindNumber.maxDestorySum(new int[][]{
                {3,7,2},
                {3,3,1},
                {6,5,2},
        }));
        assertEquals(6, FindNumber.maxDestorySum(new int[][]{
                {6}
        }));
    }

    @Test
    void maxScores() {
        assertEquals(22, FindNumber.maxScores(new int[]{8,1,3,3,5,2,7,9,1}));
    }

    @Test
    void findAllUser() {
//        System.out.println(FindNumber.findAllUser1(6, new int[][]{{1,2,5}, {2,3,5}, {1, 5, 10}},2));
//        System.out.println(FindNumber.findAllUser(8, new int[][]{{1,2,5}, {2,3,5}, {8,3,5}, {1, 5, 10}, {7, 5, 11}, {3, 6, 3}},2));
//        System.out.println(FindNumber.findAllUser1(8, new int[][]{{1,2,5}, {2,3,5}, {8,3,5}, {1, 5, 10}, {7, 5, 11}, {3, 6, 3}, {4, 6, 5}},2));
//        System.out.println(FindNumber.findAllUser1(8, new int[][]{{1,2,5}, {2,3,5}, {8,3,5}, {1, 5, 10}, {7, 5, 11}, {3, 6, 3}, {4, 6, 5}},3));
        System.out.println(FindNumber.findAllUser1(4, new int[][]{{1,2,2}, {2,3,3}},3));
        System.out.println(FindNumber.findAllUser1(4, new int[][]{{1,2,22}, {2,3,3}},3));
//        System.out.println(FindNumber.findAllUser1(2, new int[][]{{1,2,5}},1));
    }
}