import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

  private BST entry;
  private int size = 0;

  private class BST {
    private K key;
    private V value;
    private BST left;
    private BST right;

    public BST(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public BST find(BST T, K key) {
      if (T == null) {
        return null;
      }
      if (key.equals(T.key)) {
        return T;
      } else if (key.compareTo(T.key) < 0) {
        return find(T.left, key);
      } else {
        return find(T.right, key);
      }
    }

    public BST findParent(BST T, K key) {
      if (T == null || (T.left == null && T.right == null)) {
        return null;
      }
      if ((T.left != null && key.equals(T.left.key)) || (T.right != null && key.equals(T.right.key))) {
        return T;
      } else if (key.compareTo(T.key) < 0) {
        return findParent(T.left, key);
      } else {
        return findParent(T.right, key);
      }
    }

    public BST insert(BST T, K key, V value) {
      if (T == null) {
        return new BST(key, value);
      }
      if (key.compareTo(T.key) < 0) {
        T.left = insert(T.left, key, value);
      } else if (key.compareTo(T.key) > 0) {
        T.right = insert(T.right, key, value);
      }
      return T;
    }

    private BST hibbardDelete(BST start) {
      BST result = start.left;
      while (result.right != null) {
        result = result.right;
      }
      return result;
    }

    private void assignNewChild(BST parent, K key, BST newChild) {
      if (parent == null) {//entry removed
        entry = newChild;
      } else {
        if (key.compareTo(parent.key) < 0) {
          parent.left = newChild;
        } else {
          parent.right = newChild;
        }
      }
    }

    public BST delete(BST T, K key) {
      BST target = find(T, key);

      if (target == null) return null;

      BST parent = findParent(T, key);

      if (target.left == null && target.right == null) {
        assignNewChild(parent, key, null);
      } else if (target.left == null && target.right != null) {
        assignNewChild(parent, key, target.right);
      } else if (target.left != null && target.right == null) {
        assignNewChild(parent, key, target.left);
      } else {
        BST largestLeft = hibbardDelete(target);
        delete(T, largestLeft.key);
        assignNewChild(parent, key, largestLeft);
        assignNewChild(largestLeft, target.left.key, target.left);
        assignNewChild(largestLeft, target.right.key, target.right);
      }
      size -= 1;
      return target;
    }
  }

  @Override
  public void put(K key, V value) {
    if (entry == null) {
      entry = new BST(key, value);
      size += 1;
    } else {
      BST lookup = entry.find(entry, key);
      if (lookup == null) {
        entry.insert(entry, key, value);
        size += 1;
      } else {
        lookup.value = value;
      }
    }
  }

  @Override
  public V get(K key) {
    if (entry == null) {
      return null;
    }
    BST lookup = entry.find(entry, key);
    if (lookup == null) {
        return null;
    }
    return lookup.value;
  }

  @Override
  public boolean containsKey(K key) {
    if (entry == null) {
      return false;
    }
    return entry.find(entry, key) != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void clear() {
    entry = null;
    size = 0;
  }

  @Override
  public Set<K> keySet() {
    Set<K> set = new TreeSet<>();
    for(K key : this) {
      set.add(key);
    };
    return set;
  }

  @Override
  public V remove(K key) {
    BST removed = entry.delete(entry, key);
    if (removed == null) {
      return null;
    }
    return removed.value;
  }

  private class keyIterator implements Iterator<K> {
    private int current = 0;
    private ArrayList<K> keyList = new ArrayList<>(size);

    public keyIterator() {
      if (entry != null) {
        keyList.add(entry.key);
        putChildInList(entry);
      }
    }

    @Override
    public boolean hasNext() {
      return current < keyList.size();
    }

    private void putChildInList(BST bst) {
      if (bst.left != null) {
        keyList.add(bst.left.key);
        putChildInList(bst.left);
      }
      if (bst.right != null) {
        keyList.add(bst.right.key);
        putChildInList(bst.right);
      }
    }

    @Override
    public K next() {
      K ret = keyList.get(current);
      current += 1;
      return ret;
    }

  }

  @Override
  public Iterator<K> iterator() {
    return new keyIterator();
  }
}
