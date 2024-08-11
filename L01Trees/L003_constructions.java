package L01Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

    class LvlPair {
        TreeNode parent;
        int lr;
        int rr;

        LvlPair(TreeNode parent, int lr, int rr) {
            this.parent = parent;
            this.lr = lr;
            this.rr = rr;
        }
    }

    public TreeNode constructBstFromLevelOrder(int level[]) {
        int n = level.length;
        if (n == 0)
            return null;
        int idx = 0;
        Queue<LvlPair> que = new ArrayDeque<>();
        TreeNode root = new TreeNode(level[idx++]);
        que.add(new LvlPair(root, -(int) 1e9, root.val));
        que.add(new LvlPair(root, root.val, (int) 1e9));
        while (que.size() > 0 && idx < n) {
            LvlPair remPair = que.remove();
            TreeNode parentNode = remPair.parent;
            int lr = remPair.lr, rr = remPair.rr;
            Integer nextVal = level[idx];
            if (lr > nextVal || rr < nextVal)
                continue;
            TreeNode newNode = new TreeNode(nextVal);
            if (nextVal < parentNode.val) {
                parentNode.left = newNode;
            } else {
                parentNode.right = newNode;
            }
            que.add(new LvlPair(newNode, lr, nextVal));
            que.add(new LvlPair(newNode, nextVal, rr));

            idx++;

        }
        return root;
    }

    public boolean validateBstFromPreOrder(int preorder[]) {
        Stack<Integer> st = new Stack<>();
        int root = Integer.MIN_VALUE;
        for (int i = 0; i < preorder.length; i++) {
            int val = preorder[i];
            while (st.size() > 0 && st.peek() <= val) {
                root = st.pop();
            }
            if (val <= root) {
                return false;
            }
            st.push(val);
        }
        return true;
    }

    public TreeNode buildTreePreIn(int[] preorder, int[] inorder) {
        return buildTreePreIn(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreePreIn(int[] preorder, int psi, int pei, int[] inorder, int isi, int iei) {
        if (psi > pei)
            return null;
        TreeNode root = new TreeNode(preorder[psi]);
        int idx = isi;
        while (idx < inorder.length && inorder[idx] != root.val)
            idx++;
        int te = idx - isi;
        root.left = buildTreePreIn(preorder, psi + 1, psi + te, inorder, isi, idx - 1);
        root.right = buildTreePreIn(preorder, psi + te + 1, pei, inorder, idx + 1, iei);
        return root;
    }

    public TreeNode buildTreePostIn(int[] inorder, int[] postorder) {
        return buildTreePostIn(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreePostIn(int[] postorder, int psi, int pei, int[] inorder, int isi, int iei) {
        if (psi > pei)
            return null;
        TreeNode root = new TreeNode(postorder[pei]);
        int idx = isi;
        while (idx < inorder.length && inorder[idx] != root.val)
            idx++;
        int te = idx - isi;
        root.left = buildTreePostIn(postorder, psi, psi + te - 1, inorder, isi, idx - 1);
        root.right = buildTreePostIn(postorder, psi + te, pei - 1, inorder, idx + 1, iei);
        return root;
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        return buildTreePrePost(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }

    private TreeNode buildTreePrePost(int[] preorder, int psi, int pei, int[] postorder, int posi, int poei) {
        if (psi > pei)
            return null;
        TreeNode root = new TreeNode(preorder[psi]);
        if (psi == pei)
            return root;
        int idx = posi;
        while (idx < postorder.length && postorder[idx] != preorder[psi + 1])
            idx++;
        int te = idx - posi + 1;
        root.left = buildTreePrePost(preorder, psi + 1, psi + te, postorder, posi, posi + te - 1);
        root.right = buildTreePrePost(preorder, psi + te + 1, pei, postorder, posi + te, poei - 1);
        return root;
    }

    public class Codec_Rec {
        int idx;

        public String serialize(TreeNode root) {
            if (root == null)
                return "";
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();

        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                sb.append("#").append(" ");
                return;
            }
            sb.append(root.val).append(" ");
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        public TreeNode deserialize(String data) {
            idx = 0;
            if (data == null || data.length() == 0) {
                return null;
            }
            String[] arr = data.split(" ");
            return deserialize(arr);
        }

        private TreeNode deserialize(String[] arr) {
            if (idx >= arr.length)
                return null;
            if (arr[idx].equals("#")) {
                idx++;
                return null;
            }
            TreeNode root = new TreeNode(Integer.parseInt(arr[idx++]));
            root.left = deserialize(arr);
            root.right = deserialize(arr);
            return root;
        }
    }

    public class Codec_LvlOrder {

        public String serialize(TreeNode root) {
            return serialize_(root);
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            String[] arr = data.split(" ");
            return deserialize_(arr);
        }

        public String serialize_(TreeNode root) {
            if (root == null)
                return "";
            LinkedList<TreeNode> que = new LinkedList<>();
            que.add(root);
            StringBuilder sb = new StringBuilder();
            while (que.size() > 0) {
                TreeNode rem = que.remove();
                if (rem == null) {
                    sb.append("#").append(" ");
                    continue;
                }
                sb.append(rem.val).append(" ");
                que.add(rem.left);
                que.add(rem.right);
            }

            return sb.toString();
        }

        public TreeNode deserialize_(String[] arr) {
            LinkedList<TreeNode> que = new LinkedList<>();
            TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
            que.add(root);
            int idx = 1;
            while (que.size() > 0 && idx < arr.length) {
                TreeNode parentNode = que.remove();
                if (!arr[idx].equals("#")) {
                    TreeNode left = new TreeNode(Integer.parseInt(arr[idx]));
                    parentNode.left = left;
                    que.add(left);
                }
                idx++;
                if (!arr[idx].equals("#")) {
                    TreeNode rNode = new TreeNode(Integer.parseInt(arr[idx]));
                    parentNode.right = rNode;
                    que.add(rNode);
                }
                idx++;

            }
            return root;
        }
    }

    public class Codec {
        static int idx = 0;

        public String serialize(TreeNode root) {
            if (root == null)
                return "";
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();

        }

        private void serialize(TreeNode root, StringBuilder sb) {
            if (root == null) {
                return;
            }
            sb.append(root.val).append(" ");
            serialize(root.left, sb);
            serialize(root.right, sb);
        }

        public TreeNode deserialize(String data) {
            if (data == null || data.length() == 0) {
                return null;
            }
            idx = 0;
            String[] arr = data.split(" ");
            ArrayList<Integer> preorder = new ArrayList<>();
            for (String s : arr) {
                preorder.add(Integer.parseInt(s));
            }
            int a[] = preorder.stream().mapToInt(Integer::intValue).toArray();
            return bstFromPreorder(a, -(int) 1e9, (int) 1e9);
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

    }

}