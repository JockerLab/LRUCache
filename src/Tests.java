import org.junit.jupiter.api.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void test00() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            LRUCache<Integer, Integer> cache = new LRUCache<>(-1);
        });
        String expectedMessage = "Capacity must be positive";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void test01() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(0);
        cache.put(1, 1);
        Integer value = cache.get(1);
        assertNull(value);
    }

    @Test
    void test02() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(1, 1);
        Integer value = cache.get(1);
        assertEquals(1, value);
    }

    @Test
    void test03() {
        LRUCache<String, String> cache = new LRUCache<>(1);
        cache.put("1", "1");
        String value = cache.get("1");
        assertEquals("1", value);
    }

    @Test
    void test04() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(1, 1);
        cache.put(2, 2);
        Integer value = cache.get(1);
        assertNull(value);
    }

    @Test
    void test05() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(1, 1);
        cache.put(2, 2);
        Integer value = cache.get(2);
        assertEquals(2, value);
    }

    @Test
    void test06() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(1, 1);
        cache.put(1, 2);
        Integer value = cache.get(1);
        assertEquals(2, value);
    }

    @Test
    void test07() {
        final int STEPS = 100_000, MAX_VALUE = 100, CAPACITY = 100;
        Random r = new Random();
        LRUCache<Integer, Integer> cache = new LRUCache<>(CAPACITY);
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(CAPACITY, .75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > CAPACITY;
            }
        };
        for (int i = 0; i < STEPS; i++) {
            int key = r.nextInt(MAX_VALUE);
            int value = r.nextInt();
            int action = r.nextInt(2);
            switch (action) {
                case 0:
//                    System.out.printf("put(%s, %s)%n", key, value);
                    cache.put(key, value);
                    map.put(key, value);
                    break;
                case 1:
//                    System.out.printf("get(%s)%n", key);
                    Integer actual = cache.get(key);
                    Integer expected = map.get(key);
                    assertEquals(expected, actual);
                    break;
            }
        }
    }

}