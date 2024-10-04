public class UnionFind {
    private int[] sets;
    private int[] children;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        sets = new int[N];
        children = new int[N];
        for (int i = 0; i < N; i++) {
            sets[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int rootID = find(v);
        return -sets[rootID];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return sets[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v > sets.length - 1 || v < 0) {
            throw new IllegalArgumentException();
        } else {
            int current = v;
            while (parent(current) >= 0) {
                children[current] = 1;
                current = parent(current);
            }
            for (int i = 0; i < children.length; i++) {
                if (children[i] == 1) {
                    sets[i] = current;
                    children[i] = 0;
                }
            }
            return current;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int parent1 = find(v1);
        int parent2 = find(v2);
        if (parent1 == parent2) return;
        if (sizeOf(v1) > sizeOf(v2)) {
            sets[parent2] = parent1;
            sets[parent1] = -sizeOf(v1) - sizeOf(v2);
        } else {
            sets[parent1] = parent2;
            sets[parent2] = -sizeOf(v1) - sizeOf(v2);
        }
    }

}
