public class IntList {
  public int first;
  public IntList rest;

  public IntList(int f, IntList r) {
    first = f;
    rest = r;
  }

  /** Return the size of the list using... recursion! */
  public int size() {
    if (this.rest == null)
      return 1;
    return this.rest.size() + 1;
  }

  /** Return the size of the list using no recursion! */
  public int iterativeSize() {
    IntList current = this;
    int s = 0;
    while (current != null) {
      s++;
      current = current.rest;
    }
    return s;
  }

  /** Returns the ith value in this list. */
  public int get(int i) {
    if (i == 0)
      return this.first;
    return this.rest.get(i - 1);
  }

  public static void main(String[] args) {
    IntList L = new IntList(15, null);
    L = new IntList(10, L);
    L = new IntList(5, L);

    System.out.println(L.iterativeSize());
  }
}