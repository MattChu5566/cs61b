import java.util.List;
import java.util.ArrayList;

public class LinkedListDeque61B<T> implements Deque61B<T> {
  private Node sentinel;
  private int size;

  public LinkedListDeque61B() {
    sentinel = new Node(null);
    sentinel.next = sentinel;
    sentinel.prev = sentinel;
    size = 0;
  }

  private class Node {
    public T item;
    public Node next;
    public Node prev;

    public Node(T x) {
       item = x;
       next = null;
       prev = null;
    }

    private T getRecursive(int index) {
      if (index == 0) return next.item;
      return next.getRecursive(index - 1);
    }
  }

  @Override
  public void addFirst(T x) {
    size += 1;
    Node firstNode = new Node(x);
    Node pFirstNode = sentinel.next;
    firstNode.prev = sentinel;
    firstNode.next = pFirstNode;
    pFirstNode.prev = firstNode;
    sentinel.next = firstNode;
  }

  @Override
  public void addLast(T x) {
    size += 1;
    Node lastNode = new Node(x);
    Node pLastNode = sentinel.prev;
    lastNode.next = sentinel;
    lastNode.prev = pLastNode;
    pLastNode.next = lastNode;
    sentinel.prev = lastNode;
  }

  @Override
  public List<T> toList() {
    List<T> returnList = new ArrayList<>();
    Node nowNode = sentinel.next;
    while (nowNode != sentinel) {
      returnList.addLast(nowNode.item);
      nowNode = nowNode.next;
    }
    return returnList;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public T removeFirst() {
    Node prevFirstNode = sentinel.next;
    Node newFirstNode = prevFirstNode.next;
    sentinel.next = newFirstNode;
    newFirstNode.prev = sentinel;
    if (size > 0) size = size - 1;
    return prevFirstNode.item;
  }

  @Override
  public T removeLast() {
    Node prevLastNode = sentinel.prev;
    Node newLastNode = prevLastNode.prev;
    sentinel.prev = newLastNode;
    newLastNode.next = sentinel;
    if (size > 0) size = size - 1;
    return prevLastNode.item;
  }

  @Override
  public T get(int index) {
    Node targetNode = sentinel;
    if (index >= 0 && index <= size - 1) {
      for (int i = 0; i <= index; i ++) {
        targetNode = targetNode.next;
      }
    }
    return targetNode.item;
  }

  @Override
  public T getRecursive(int index) {
    if (index < 0 || index > size - 1) return null;
    return sentinel.getRecursive(index);
  }
  
}
