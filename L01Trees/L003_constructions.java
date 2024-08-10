package L01Trees;

import java.util.ArrayList;
import java.util.HashSet;

public class L003_constructions extends Tree {
    static int idx = 0;

    public TreeNode bstFromPreorder(int[] preorder) {
        idx = 0;
        return bstFromPreorder(preorder, -(int) 1e9, (int) 1e9);
    }

    private TreeNode bstFromPreorder(int[] preorder, int lr, int rr) {
        if (idx == preorder.length || preorder[idx] < lr || preorder[idx] > rr) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[idx++]);
        root.left = bstFromPreorder(preorder, lr, root.val);
        root.right = bstFromPreorder(preorder, root.val, rr);
        return root;
    }

    public TreeNode constructFromInorder(int inorder[]) {
        return constructFromInorder(inorder, 0, inorder.length - 1);
    }

    private TreeNode constructFromInorder(int[] inorder, int si, int ei) {
        if (si > ei)
            return null;
        int mid = (si + ei) / 2;
        TreeNode root = new TreeNode(inorder[mid]);
        root.left = constructFromInorder(inorder, si, mid - 1);
        root.right = constructFromInorder(inorder, mid + 1, ei);
        return root;
    }

    public Node constructTreeFromPostOrder(int post[], int n) {
        idx = post.length - 1;
        return bstFromPostorder(post, -(int) 1e9, (int) 1e9);
    }

    private Node bstFromPostorder(int[] post, int lr, int rr) {
        if (idx < 0 || post[idx] < lr || post[idx] > rr) {
            return null;
        }
        Node root = new Node(post[idx--]);
        root.right = bstFromPostorder(post, root.data, rr);
        root.left = bstFromPostorder(post, lr, root.data);
        return root;
    }

    Node buildBinaryTreeFromInLevel(int inord[], int level[]) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int e : level) {
            arr.add(e);
        }
        return constructInLevel(inord, arr, 0, inord.length - 1);
    }

    public Node constructInLevel(int[] in, ArrayList<Integer> level, int inSt, int inEnd) {
        if (level.size() == 0)
            return null;
        Node root = new Node(level.get(0));
        int idx = inSt;
        HashSet<Integer> set = new HashSet<>();
        while (idx < in.length && level.get(0) != in[idx]) {
            set.add(in[idx]);
            idx++;
        }
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        for (int i = 1; i < level.size(); i++) {
            if (set.contains(level.get(i))) {
                left.add(level.get(i));
            } else {
                right.add(level.get(i));
            }
        }
        root.left = constructInLevel(in, left, inSt, idx - 1);
        root.right = constructInLevel(in, right, idx + 1, inEnd);
        return root;
    }
}
