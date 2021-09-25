import java.util.HashMap;

public class LRUCache<T, E> {
    final private int capacity;
    private int size;
    final private HashMap<T, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        map = new HashMap<>();
        head = tail = new Node(null, null);
        size = 0;
    }

    class Node {
        Node prev, next;
        T key;
        E value;

        Node(T key, E value) {
            this.key = key;
            this.value = value;
            next = prev = null;
        }
    }

    public void put(T key, E value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        } else if (size == capacity && capacity != 0) {
            map.remove(head.next.key);
            remove(head);
        }
        if (capacity != 0) {
            add(key, value);
            map.put(key, tail);
        }
    }

    public E get(T key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node node = map.get(key);
        assert node != null;
        E value = node.value;
        remove(node);
        add(key, value);
        map.put(key, tail);
        return value;
    }

    private void add(T key, E value) {
        assert tail != null;
        Node newNode = new Node(key, value);
        tail.next = newNode;
        newNode.prev = tail;
        tail = tail.next;
        size++;
        assert tail.value == value;
    }

    private void remove(Node node) {
        assert node.next != null || node.prev != null;

        size--;
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = node.prev = null;
            return;
        }
        if (node.prev == null) {
            node = node.next;
            node.prev.next = null;
            node.prev = null;
        } else {
            node = node.prev;
            node.next.prev = null;
            node.next = null;
        }
    }
}
