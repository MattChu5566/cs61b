public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /**
         * Creates a RBTreeNode with item ITEM and color depending on ISBLACK
         * value.
         * @param isBlack
         * @param item
         */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /**
         * Creates a RBTreeNode with item ITEM, color depending on ISBLACK
         * value, left child LEFT, and right child RIGHT.
         * @param isBlack
         * @param item
         * @param left
         * @param right
         */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Creates an empty RedBlackTree.
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Flips the color of node and its children. Assume that NODE has both left
     * and right children
     * @param node
     */
    void flipColors(RBTreeNode<T> node) {
        node.left.isBlack = true;
        node.right.isBlack = true;
        node.isBlack = false;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> parent = findParent(node);
        RBTreeNode<T> newRootNodeSub = node.left;
        node.left = newRootNodeSub.right;
        newRootNodeSub.right = node;
        boolean nodeIsBlack = node.isBlack;
        node.isBlack = newRootNodeSub.isBlack;
        newRootNodeSub.isBlack = nodeIsBlack;
        if (parent == null) {
            root = newRootNodeSub;
        } else if (parent.left == node) {
            parent.left = newRootNodeSub;
        } else {
            parent.right = newRootNodeSub;
        }
        return newRootNodeSub;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> parent = findParent(node);
        RBTreeNode<T> newRootNodeSub = node.right;
        node.right = newRootNodeSub.left;
        newRootNodeSub.left = node;
        boolean nodeIsBlack = node.isBlack;
        node.isBlack = newRootNodeSub.isBlack;
        newRootNodeSub.isBlack = nodeIsBlack;
        if (parent == null) {
            root = newRootNodeSub;
        } else if (parent.left == node) {
            parent.left = newRootNodeSub;
        } else {
            parent.right = newRootNodeSub;
        }
        return newRootNodeSub;
    }

    /**
     * Helper method that returns whether the given node is red. Null nodes (children or leaf
     * nodes) are automatically considered black.
     * @param node
     * @return
     */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     * @param item
     */
    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    private RBTreeNode<T> findParent(RBTreeNode<T> node, RBTreeNode<T> now) {
        if (node.item.compareTo(now.item) < 0) {
            if (now.left == node || now.left == null) {
                return now;
            } else {
                return findParent(node, now.left);
            }
        } else {
            if (now.right == node || now.right == null) {
                return now;
            } else {
                return findParent(node, now.right);
            }
        }
    }

    private RBTreeNode<T> findParent(RBTreeNode<T> node) {
        if (root == null || (root == node)) return null;
        return findParent(node, root);
    }

    /**
     * Inserts the given node into this Red Black Tree. Comments have been provided to help break
     * down the problem. For each case, consider the scenario needed to perform those operations.
     * Make sure to also review the other methods in this class!
     * @param node
     * @param item
     * @return
     */
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // TODO: Insert (return) new red leaf node.
        RBTreeNode<T> newNode = new RBTreeNode<T>(false, item);

        // TODO: Handle normal binary search tree insertion.
        RBTreeNode<T> parentNode = findParent(newNode);
        if (parentNode == null) {
            root = newNode;
        } else if (item.compareTo(parentNode.item) < 0) {
            parentNode.left = newNode;
        } else {
            parentNode.right = newNode;
        }

        RBTreeNode<T> subRoot = parentNode;
        while (subRoot != null) {
            // TODO: Rotate left operation
            if (isRed(subRoot.right) && !isRed(subRoot.left)) {
                subRoot = rotateLeft(subRoot);
            }

            // TODO: Rotate right operation
            if (isRed(subRoot) && isRed(subRoot.left)) {
                subRoot = rotateRight(findParent(subRoot));
            }

            // TODO: Color flip
            if (subRoot.left != null && subRoot.right != null && isRed(subRoot.left) && isRed(subRoot.right)) {
                flipColors(subRoot);
                subRoot = findParent(subRoot);
            } else {
                subRoot = null;
            }
        }

        return root; //fix this return statement
    }
}
