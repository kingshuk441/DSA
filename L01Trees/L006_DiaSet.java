package L01Trees;

import java.util.ArrayList;
import java.util.List;

public class L006_DiaSet extends Tree {
    private int height(TreeNode root) {
        if (root == null)
            return -1;
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    public int diameterOfBinaryTree1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDia = diameterOfBinaryTree1(root.left);
        int rightDia = diameterOfBinaryTree1(root.right);
        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        int myRes = Math.max(rightDia, Math.max(leftDia, leftHeight + rightHeight + 2));
        return myRes;
    }

    // {dia,H}
    public int[] diameterOfBinaryTree2(TreeNode root) {
        if (root == null) {
            return new int[] { 0, -1 };
        }
        int leftPair[] = diameterOfBinaryTree2(root.left);
        int rightPair[] = diameterOfBinaryTree2(root.right);
        int[] myRes = new int[2];
        myRes[0] = Math.max(leftPair[0], Math.max(rightPair[0], leftPair[1] + rightPair[1] + 2));
        myRes[1] = Math.max(leftPair[1], rightPair[1]) + 1;
        return myRes;
    }

    static int diameter = 0;

    public int diameterOfBinaryTree3(TreeNode root) {
        if (root == null) {
            return -1;
        }
        int leftHeight = diameterOfBinaryTree3(root.left);
        int rightHeight = diameterOfBinaryTree3(root.right);

        diameter = Math.max(diameter, leftHeight + rightHeight + 2);

        return Math.max(leftHeight, rightHeight) + 1;

    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> sAns = new ArrayList<>();
        pathSum(root, targetSum, sAns, ans);
        return ans;
    }

    private void pathSum(TreeNode root, int targetSum, List<Integer> sAns, List<List<Integer>> ans) {
        if (root == null)
            return;

        if (root.left == null && root.right == null) {
            if (targetSum - root.val == 0) {
                List<Integer> base = new ArrayList<>(sAns);
                base.add(root.val);
                ans.add(base);
            }
            return;
        }
        sAns.add(root.val);
        pathSum(root.left, targetSum - root.val, sAns, ans);
        pathSum(root.right, targetSum - root.val, sAns, ans);
        sAns.remove(sAns.size() - 1);
    }

    int maxPathSumLTL1(Node root) {
        return maxPathSumLeafToLeaf(root)[0];
    }

    // ltl,ntl
    private int[] maxPathSumLeafToLeaf(Node root) {
        if (root == null)
            return new int[] { -(int) 1e9, -(int) 1e9 };
        if (root.left == null && root.right == null) {
            return new int[] { -(int) 1e9, root.data };
        }
        int leftPair[] = maxPathSumLeafToLeaf(root.left);
        int rightPair[] = maxPathSumLeafToLeaf(root.right);
        int myRes[] = new int[2];
        myRes[0] = Math.max(leftPair[0], rightPair[0]);
        if (root.left != null && root.right != null) {
            myRes[0] = Math.max(myRes[0], leftPair[1] + rightPair[1] + root.data);
        }
        myRes[1] = Math.max(leftPair[1], rightPair[1]) + root.data;

        return myRes;
    }

    static int maxLeafToLeafPathSum;

    int maxPathSumLTL2(Node root) {
        maxLeafToLeafPathSum = -(int) 1e9;
        return maxPathSumLeafToLeaf2(root);
    }

    // ltl,ntl
    private int maxPathSumLeafToLeaf2(Node root) {
        if (root == null) {
            return -(int) 1e9;
        }
        if (root.left == null && root.right == null) {
            return root.data;
        }
        int leftNTN = maxPathSumLeafToLeaf2(root.left);
        int rightNTN = maxPathSumLeafToLeaf2(root.right);

        Math.max(maxLeafToLeafPathSum, leftNTN + rightNTN + root.data);
        return Math.max(leftNTN, rightNTN) + root.data;
    }

    int maxNodeToNodePathSum = 0;

    int findMaxSum(Node node) {
        maxNodeToNodePathSum = -(int) 1e9;
        maxPathSumNodeToNode(node);
        return maxNodeToNodePathSum;
    }

    private int maxPathSumNodeToNode(Node root) {
        if (root == null)
            return 0;
        int leftRTN = maxPathSumNodeToNode(root.left);
        int rightRTN = maxPathSumNodeToNode(root.right);
        int rootToNodeMaxPathSum = root.data + Math.max(leftRTN, rightRTN);
        maxNodeToNodePathSum = Math.max(root.data,
                Math.max(rootToNodeMaxPathSum, Math.max(root.data + leftRTN + rightRTN, maxNodeToNodePathSum)));

        return Math.max(rootToNodeMaxPathSum, root.data);
    }

}
