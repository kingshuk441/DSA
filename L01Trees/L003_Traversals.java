package L01Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public boolean isValidBST_MorrisTraversal(TreeNode root) {
        TreeNode curr = root;
        TreeNode prev = null;
        while (curr != null) {
            TreeNode leftNode = curr.left;
            if (leftNode == null) {
                if (prev != null && prev.val >= curr.val)
                    return false;
                prev = curr;
                curr = curr.right;
            } else {
                TreeNode rightMostNode = getRightMostNode(leftNode, curr);
                if (rightMostNode.right == null) {
                    rightMostNode.right = curr;
                    curr = curr.left;
                } else {
                    if (prev != null && prev.val >= curr.val)
                        return false;
                    prev = curr;
                    rightMostNode.right = null;
                    curr = curr.right;
                }
            }
        }
        return true;
    }

    public boolean isValidBST_Stack(TreeNode root) {
        Stack<TreeNode> st = new Stack<>();
        insertAllLeft(root, st);
        TreeNode prev = null;
        while (st.size() > 0) {
            TreeNode curr = st.pop();
            if (curr.right != null) {
                insertAllLeft(curr.right, st);
            }
            if (prev != null && prev.val >= curr.val)
                return false;
            prev = curr;
        }
        return true;
    }

    private void insertAllLeft(TreeNode root, Stack<TreeNode> st) {
        while (root != null) {
            st.add(root);
            root = root.left;
        }
    }

    class BSTIterator_MorrisTraversal {
        TreeNode curr;

        public BSTIterator_MorrisTraversal(TreeNode root) {
            this.curr = root;
        }

        public int next() {
            TreeNode nextNode = null;
            while (curr != null) {
                TreeNode leftNode = curr.left;
                if (leftNode == null) {
                    nextNode = curr;
                    curr = curr.right;
                    break;
                } else {
                    TreeNode rightMostNode = getRightMostNode(leftNode, curr);
                    if (rightMostNode.right == null) {
                        rightMostNode.right = curr;
                        curr = curr.left;
                    } else {
                        rightMostNode.right = null;
                        nextNode = curr;
                        curr = curr.right;
                        break;
                    }
                }
            }
            return nextNode.val;
        }

        public boolean hasNext() {
            return curr != null;
        }
    }

    class BSTIterator_Stack {

        Stack<TreeNode> st;

        private void insertAllLeft(TreeNode root, Stack<TreeNode> st) {
            while (root != null) {
                st.add(root);
                root = root.left;
            }
        }

        public BSTIterator_Stack(TreeNode root) {
            this.st = new Stack<>();
            insertAllLeft(root, st);
        }

        public int next() {
            TreeNode nextVal = st.pop();
            if (nextVal.right != null)
                insertAllLeft(nextVal.right, st);
            return nextVal.val;
        }

        public boolean hasNext() {
            return st.size() > 0;
        }
    }

}
