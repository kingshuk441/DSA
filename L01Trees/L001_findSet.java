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

    // burningTree
    public static ArrayList<ArrayList<Integer>> burningTree(Node root, int fireNode) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        burningTree(root, fireNode, ans);
        return ans;
    }

    private static int burningTree(Node root, int fireNode, ArrayList<ArrayList<Integer>> ans) {
        if (root == null)
            return -1;
        if (root.data == fireNode) {
            addKDownNodes(root, 0, null, ans);
            return 1;
        }
        int left = burningTree(root.left, fireNode, ans);
        if (left > 0) {
            addKDownNodes(root, left, root.left, ans);
            return left + 1;
        }
        int right = burningTree(root.right, fireNode, ans);
        if (right > 0) {
            addKDownNodes(root, right, root.right, ans);
            return right + 1;
        }
        return -1;
    }

    private static void addKDownNodes(Node root, int k, Node blockNode, ArrayList<ArrayList<Integer>> ans) {
        if (root == null || blockNode == root)
            return;
        if (ans.size() == k)
            ans.add(new ArrayList<>());

        ans.get(k).add(root.data);
        addKDownNodes(root.left, k + 1, blockNode, ans);
        addKDownNodes(root.right, k + 1, blockNode, ans);
    }

    public ArrayList<ArrayList<Integer>> burningTreeWithWater(Node root, int fireNode, List<Integer> waterNodes) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();

        Set<Integer> waterSet = new HashSet<>(waterNodes);
        if (waterSet.contains(fireNode)) {
            return ans;
        }
        burningTreeWithWater(root, fireNode, waterSet, ans);
        System.out.println(ans);
        return ans;
    }

    private int burningTreeWithWater(Node root, int fireNode, Set<Integer> waterSet,
            ArrayList<ArrayList<Integer>> ans) {
        if (root == null)
            return -1;
        if (root.data == fireNode) {
            addKDownNodesForWater(root, 0, null, ans, waterSet);
            return 1;
        }
        int left = burningTree(root.left, fireNode, ans);
        if (left > 0) {
            if (waterSet.contains(root.data)) {
                return -2;
            }
            addKDownNodesForWater(root, left, root.left, ans, waterSet);
            return left + 1;
        }
        if (left == -2) {
            return -2;
        }

        int right = burningTree(root.right, fireNode, ans);
        if (right > 0) {
            if (waterSet.contains(root.data)) {
                return -2;
            }
            addKDownNodesForWater(root, right, root.right, ans, waterSet);
            return right + 1;
        }
        if (right == -2) {
            return -2;
        }
        return -1;

    }

    private void addKDownNodesForWater(Node root, int k, Node blockNode, ArrayList<ArrayList<Integer>> ans,
            Set<Integer> waterSet) {
        if (root == null || blockNode == root || waterSet.contains(root.data))
            return;
        if (ans.size() == k)
            ans.add(new ArrayList<>());

        ans.get(k).add(root.data);
        addKDownNodesForWater(root.left, k + 1, blockNode, ans, waterSet);
        addKDownNodesForWater(root.right, k + 1, blockNode, ans, waterSet);
    }

    private TreeNode LCA;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        LCA = null;
        lca(root, p, q);

        return LCA != null && find(LCA, p.val) && find(LCA, q.val) ? LCA : null;
    }

    private boolean lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return false;
        boolean mySelf = false;
        if (root == p || root == q)
            mySelf = true;
        if (LCA != null) {
            return false;
        }
        boolean left = lca(root.left, p, q);
        boolean right = lca(root.right, p, q);
        if ((mySelf && left) || (mySelf && right) || (right && left)) {
            LCA = root;
        }
        return mySelf || left || right;

    }

}