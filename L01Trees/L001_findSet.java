package L01Trees;

import java.util.*;

public class L001_findSet extends Tree {

    public int size(TreeNode root) {
        if (root == null)
            return 0;
        return size(root.left) + size(root.right);
    }

    public int maximum(TreeNode root) {
        if (root == null)
            return (int) -1e9;
        return Math.max(maximum(root.left), Math.max(root.val, maximum(root.right)));
    }

    public boolean find(TreeNode root, int data) {
        if (root == null)
            return false;
        if (root.val == data)
            return true;
        boolean result = find(root.left, data) || find(root.right, data);
        return result;
    }

    public boolean nodeToRootPath(TreeNode root, int data, ArrayList<TreeNode> ans) {
        if (root == null)
            return false;
        if (root.val == data) {
            ans.add(root);
            return true;
        }
        // boolean ls = find(root.left, data);
        // if (ls) {
        // ans.add(root);
        // return true;
        // }

        // boolean rs = find(root.right, data);
        // if (rs) {
        // ans.add(root);
        // return true;
        // }
        boolean result = nodeToRootPath(root.left, data, ans) || nodeToRootPath(root.right, data, ans);
        if (result)
            ans.add(root);

        return result;
    }

    public static ArrayList<ArrayList<Integer>> Paths(Node root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> sAns = new ArrayList<>();
        rootToAllLeafPath(root, ans, sAns);
        return ans;
    }

    private static void rootToAllLeafPath(Node root, ArrayList<ArrayList<Integer>> ans, ArrayList<Integer> sAns) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            ArrayList<Integer> base = new ArrayList<>(sAns);
            base.add(root.data);
            ans.add(base);
            return;
        }
        sAns.add(root.data);
        rootToAllLeafPath(root.left, ans, sAns);
        rootToAllLeafPath(root.right, ans, sAns);
        sAns.remove(sAns.size() - 1);
    }

    public void exactlyOneChild(TreeNode root, ArrayList<Integer> ans) {
        if (root == null || (root.left == null && root.right == null))
            return;
        if (root.left == null || root.right == null) {
            ans.add(root.val);
        }
        exactlyOneChild(root.left, ans);
        exactlyOneChild(root.right, ans);
    }

    public int countExactlyOneChild(TreeNode root) {
        if (root == null || (root.left == null && root.right == null))
            return 0;
        int mySelf = 0;
        if (root.left == null || root.right == null) {
            mySelf++;
        }
        int leftSingleChildCount = countExactlyOneChild(root.left);
        int rightSingleChildCount = countExactlyOneChild(root.right);
        return leftSingleChildCount + rightSingleChildCount + mySelf;
    }

    public void kDown(TreeNode node, int k, TreeNode block, List<Integer> ans) {
        if (node == null || node == block || k < 0)
            return;
        if (k == 0) {
            ans.add(node.val);
            return;
        }
        kDown(node.left, k - 1, block, ans);
        kDown(node.right, k - 1, block, ans);
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        ArrayList<TreeNode> ntrp = new ArrayList<>();
        nodeToRootPath(root, target.val, ntrp);

        TreeNode block = null;
        for (int i = 0; i < ntrp.size(); i++) {
            TreeNode nodeForK = ntrp.get(i);
            kDown(nodeForK, k - i, block, ans);
            block = nodeForK;
        }
        return ans;
    }

    public List<Integer> distanceK_02(TreeNode root, TreeNode target, int k) {
        List<Integer> ans = new ArrayList<>();
        distanceK_02(root, target.val, k, ans);
        return ans;
    }

    private int distanceK_02(TreeNode root, int target, int k, List<Integer> ans) {
        if (root == null)
            return -1;
        if (root.val == target) {
            kDown(root, k, null, ans);
            return 1;
        }
        int left = distanceK_02(root.left, target, k, ans);
        if (left > 0) {
            kDown(root, k - left, root.left, ans);
            return left + 1;
        }

        int right = distanceK_02(root.right, target, k, ans);
        if (right > 0) {
            kDown(root, k - right, root.right, ans);
            return right + 1;
        }
        return -1;

    }

}