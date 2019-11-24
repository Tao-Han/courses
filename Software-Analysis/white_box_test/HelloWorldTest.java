package com.test.jacoco.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
// 此处引用junit
import com.test.jacoco.HelloWorld;

public class HelloWorldTest {
    @Test
    public void testMethod1() {
        HelloWorld hw = new HelloWorld();
        String a = hw.Method1();
        assertEquals("Hello World", a);
    }

    @Test
    public void testMethod2() {
        HelloWorld hw = new HelloWorld();
        int a = hw.Method2(2, 1);
        assertEquals(3, a);
    }

    @Test
    public void testMethod3() {
        HelloWorld hw = new HelloWorld();
        int a1 = hw.Method3(6, -1, 1);
        int a2 = hw.Method3(5, 0, 0);
        int a3 = hw.Method3(4, -1, 0);
        int b = hw.Method3(6, 0, 1);
        int c = hw.Method3(3, 0, -3);
    }
    @Test
    public void testbug() {
        HelloWorld hw = new HelloWorld();
        boolean a = hw.bug(5);
        boolean b = hw.bug(6);
    }
    /**
     * TODO: add the test function of other methods in HelloWorld.java
     */
    @Test
    public void testMethod4() {
        HelloWorld hw = new HelloWorld();
        int a = hw.Method4(0, 1, 1, 1, (float)1.0);
        try{
            int c1 = hw.Method4(5, 1, 0, 1,  (float)1.0);
        } catch(Exception ex) {
            System.out.println("Bad Divisor 0 !");
            ex.printStackTrace();
        }
        try {
            int c2 = hw.Method4(4, 1, 0, 0,  (float)1.0);
        } catch (Exception ex) {
            System.out.println("Bad Divisor 0 !");
            ex.printStackTrace();            
        }
        int c3 = hw.Method4(4, 4, 1, 1,  (float)1.0);
        int c4 = hw.Method4(5, 4, 1, 1,  (float)1.0);
        int c5 = hw.Method4(5, 5, 0, 1,  (float)1.0);
    }
    @Test
    public void testisTriangle() {
        HelloWorld hw = new HelloWorld();
        boolean a1 = hw.isTriangle(0, 1, 1);
        boolean a2 = hw.isTriangle(1, 0, 1);
        boolean a3 = hw.isTriangle(1, 1, 0);
        boolean b = hw.isTriangle(3, 4, 5);
        boolean c1 = hw.isTriangle(3, 3, 6);
        boolean c2 = hw.isTriangle(3, 6, 3);
        boolean c3 = hw.isTriangle(6, 3, 3);
        assertEquals(false, a1);
        assertEquals(false, a2);
        assertEquals(false, a3);
        assertEquals(false, c1);
        assertEquals(false, c2);
        assertEquals(false, c3);
        assertEquals(true, b);
    }
    @Test
    public void testisBirthday() {
        HelloWorld hw = new HelloWorld();
        boolean a1 = hw.isBirthday(0, 0, 0);
        boolean a2 = hw.isBirthday(2020, 0, 0);
        boolean b1 = hw.isBirthday(2008, 13, 0);
        boolean c1 = hw.isBirthday(2000, 2, 29);
        boolean d1 = hw.isBirthday(2019, 10, 2);
        boolean d2 = hw.isBirthday(2019, 11, 1);
        assertEquals(false, a1);
        assertEquals(false, a2);
        assertEquals(false, b1);
        assertEquals(false, c1);
        assertEquals(false, d1);
        assertEquals(false, d2);
    }
    @Test
    public void testminiCalculator() {
        HelloWorld hw = new HelloWorld();
        double a = hw.miniCalculator(6, -1, '+');
        double b = hw.miniCalculator(6, -1, '-');
        double c = hw.miniCalculator(6, -1, '*');
        double e1 = hw.miniCalculator(6, 0, '/');
        double e2 = hw.miniCalculator(6, 1, '|');
        double e3 = hw.miniCalculator(6, 1, '/');
    }
}
