package L01Trees;

import java.util.ArrayList;
import java.util.List;

public class L003_Traversals extends Tree {

    private TreeNode getRightMostNode(TreeNode node, TreeNode curr) {
        if (node == null) {
            return null;
        }
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;

    }

    private TreeNode getLeftMostNode(TreeNode node, TreeNode curr) {
        if (node == null) {
            return null;
        }
        while (node.left != null && node.left != curr) {
            node = node.left;
        }
        return node;

    }

    public List<Integer> morrisInOrderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode leftNode = curr.left;
            if (leftNode == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMostNode = getRightMostNode(leftNode, curr);
                if (rightMostNode.right == null) {
                    rightMostNode.right = curr;
                    curr = curr.left;
                } else {
                    rightMostNode.right = null;
                    ans.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        return ans;
    }

    public List<Integer> morrisPreOrderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode leftNode = curr.left;
            if (leftNode == null) {
                ans.add(curr.val);
                curr = curr.right;
            } else {
                TreeNode rightMostNode = getRightMostNode(leftNode, curr);
                if (rightMostNode.right == null) {
                    ans.add(curr.val);
                    rightMostNode.right = curr;
                    curr = curr.left;
                } else {
                    rightMostNode.right = null;
                    curr = curr.right;
                }
            }
        }
        return ans;
    }

    public List<Integer> morrisReversePreOrderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        TreeNode curr = root;
        while (curr != null) {
            TreeNode rightNode = curr.right;
            if (rightNode == null) {
                ans.add(curr.val);
                curr = curr.left;
            } else {
                TreeNode leftMostNode = getLeftMostNode(rightNode, curr);
                if (leftMostNode.left == null) {
                    ans.add(curr.val);
                    leftMostNode.left = curr;
                    curr = curr.right;
                } else {
                    leftMostNode.left = null;
                    curr = curr.left;
                }
            }
        }
        return ans;
    }

    public List<Integer> morrisPostOrderTraversal(TreeNode root) {
        // 1 reverse preorder
        List<Integer> morrisReversePreOrderTraversal = morrisReversePreOrderTraversal(root);
        // 2 reverse the returned output
        return morrisReversePreOrderTraversal.reversed();
    }
}
