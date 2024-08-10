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

    private TreeNode getRightMostNode(TreeNode node, TreeNode curr) {
        if (node == null) {
            return null;
        }
        while (node.right != null && node.right != curr) {
            node = node.right;
        }
        return node;

    }

    TreeNode bToDLL(TreeNode root) {
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        TreeNode curr = root;
        while (curr != null) {
            TreeNode leftNode = curr.left;
            if (leftNode == null) {
                prev.right = curr;
                curr.left = prev;
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode rightMostNode = getRightMostNode(leftNode, curr);
                if (rightMostNode.right == null) {
                    rightMostNode.right = curr;
                    curr = curr.left;
                } else {
                    rightMostNode.right = null;
                    prev.right = curr;
                    curr.left = prev;
                    prev = curr;
                    curr = curr.right;
                }
            }
        }
        TreeNode head = dummy.right;
        dummy.right = head.left = null;
        return head;
    }

    public TreeNode binaryTreeToBST(TreeNode root) {
        if (root == null)
            return null;
        TreeNode dllNode = bToDLL(root);
        TreeNode sortedNode = mergeSort(dllNode);
        return sortedListToBST(sortedNode);
    }

    private TreeNode mergeSort(TreeNode head) {
        if (head == null || head.right == null)
            return head;
        TreeNode mid = middleOfLinkedList(head);
        TreeNode nHead = mid.right;
        mid.right = nHead.left = null;
        TreeNode head1 = mergeSort(head);
        TreeNode head2 = mergeSort(nHead);
        return mergeSortedList(head1, head2);

    }

    private TreeNode mergeSortedList(TreeNode head1, TreeNode head2) {
        if (head1 == null || head2 == null) {
            return head1 == null ? head2 : head1;
        }
        TreeNode dummy = new TreeNode(-1);
        TreeNode prev = dummy;
        TreeNode c1 = head1, c2 = head2;
        while (c1 != null && c2 != null) {
            if (c1.val < c2.val) {
                prev.right = c1;
                c1.left = prev;
                prev = c1;
                c1 = c1.right;
            } else {
                prev.right = c2;
                c2.left = prev;
                prev = c2;
                c2 = c2.right;
            }
        }
        if (c1 != null) {
            prev.right = c1;
        } else {
            prev.right = c2;
        }
        TreeNode head = dummy.right;
        dummy.right = head.left = null;

        return head;
    }

}
