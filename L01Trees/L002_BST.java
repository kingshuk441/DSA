package L01Trees;

import java.util.ArrayList;

public class L002_BST extends Tree {

    public int max(TreeNode root) {
        TreeNode curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.val;
    }

    public int min(TreeNode root) {
        TreeNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.val;
    }

    public boolean find(TreeNode root, int data) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.val == data) {
                return true;
            }
            if (curr.val < data)
                curr = curr.right;
            else
                curr = curr.left;
        }
        return false;
    }

    public void ntrp(TreeNode root, int data, ArrayList<TreeNode> ans) {
        TreeNode curr = root;
        if (!find(root, data))
            return;
        while (curr != null) {
            ans.add(curr);
            if (curr.val == data) {
                break;
            }
            if (curr.val < data)
                curr = curr.right;
            else
                curr = curr.left;
        }
    }

    public TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode curr = root;
        TreeNode lcaNode = null;
        while (curr != null) {
            if (curr.val < p.val && curr.val < q.val) {
                curr = curr.right;
            } else if (curr.val > p.val && curr.val > q.val) {
                curr = curr.left;
            } else {
                lcaNode = curr;
                break;
            }
        }
        return lcaNode != null && find(lcaNode, p.val) && find(lcaNode, q.val) ? lcaNode : null;

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

    public TreeNode sortedListToBST(TreeNode head) {
        if (head == null || head.right == null)
            return head;
        TreeNode mid = middleOfLinkedList(head);
        TreeNode prev = mid.left;
        TreeNode fwd = mid.right;

        mid.left = mid.right = fwd.left = null;
        if (prev != null)
            prev.right = null;

        TreeNode root = mid;
        TreeNode leftHead = prev != null ? head : null;
        TreeNode rightHead = fwd;
        TreeNode leftSide = sortedListToBST(leftHead);
        TreeNode rightSide = sortedListToBST(rightHead);
        root.left = leftSide;
        root.right = rightSide;
        return root;
    }

    private TreeNode middleOfLinkedList(TreeNode head) {
        if (head == null || head.right == null)
            return head;

        TreeNode slow = head, fast = head.right;
        while (fast != null && fast.right != null) {
            slow = slow.right;
            fast = fast.right.right;
        }
        return slow;
    }
}
