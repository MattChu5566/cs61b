import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
  private int size;
  private T[] items;
  private int nextFirst;
  private int nextLast;
  private int RFACTOR = 2;

  public ArrayDeque61B() {
    size = 0;
    items = (T[]) new Object[8];
    nextFirst = 4;
    nextLast = 5;
  }

  public void resize(int newLength) {
    T[] newItems = (T[]) new Object[newLength];
    for (int i = 0; i < size; i++) {
      newItems[i] = get(i);
    }
    nextFirst = newLength - 1;
    nextLast = size;
    items = newItems;
  }

  @Override
  public void addFirst(T x) {
    if (size == items.length) {
      resize(size * RFACTOR);
    }
    items[nextFirst] = x;
    nextFirst = Math.floorMod(nextFirst - 1, items.length);
    size += 1;
  }

  @Override
  public void addLast(T x) {
    if (size == items.length) {
      resize(size * RFACTOR);
    }
    items[nextLast] = x;
    nextLast = Math.floorMod(nextLast + 1, items.length);
    size += 1;
  }

  @Override
  public List<T> toList() {
    List<T> returnList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      returnList.addLast(get(i));
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

  public void checkUsageFactor() {
    if (items.length < 16) return;
    float usageFactor = (float) (size - 1) / items.length;
    if (usageFactor < 0.25) {
      resize(items.length / RFACTOR);
    }
  }

  @Override
  public T removeFirst() {
    if (size == 0) return null;
    checkUsageFactor();
    int currentFirst = Math.floorMod(nextFirst + 1, items.length);
    T firstElement = items[currentFirst];
    size -= 1;
    nextFirst = currentFirst;
    items[currentFirst] = null;
    return firstElement;
  }

  @Override
  public T removeLast() {
    if (size == 0) return null;
    checkUsageFactor();
    int currentLast = Math.floorMod(nextLast - 1, items.length);
    T lastElement = items[currentLast];
    size -= 1;
    nextLast = currentLast;
    items[currentLast] = null;
    return lastElement;
  }

  @Override
  public T get(int index) {
    if (index < 0 || index > size - 1) return null;
    int shiftIndex = Math.floorMod(index + nextFirst + 1, items.length);
    return items[shiftIndex];
  }

  @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
  
}
