package org.example;

import org.junit.*;

import static org.junit.Assert.*;

public class SingletonTest {

    @Test
    public void testSingleton() {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        assertEquals(instance1, instance2);
    }
}
