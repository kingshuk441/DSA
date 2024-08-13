package L01Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class L005_ViewSet extends Tree {
    ArrayList<Integer> leftView(Node root) {
        if (root == null)
            return new ArrayList<>();
        Queue<Node> que = new ArrayDeque<>();
        int lvl = 0;
        que.add(root);
        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() > 0) {
            int size = que.size();
            Node firstNode = que.peek();
            System.out.println(lvl);
            ans.add(firstNode.data);
            while (size-- > 0) {
                Node rem = que.remove();
                if (rem.left != null)
                    que.add(rem.left);
                if (rem.right != null)
                    que.add(rem.right);
            }
            lvl++;
        }
        return ans;
    }

    ArrayList<Integer> rightView(Node root) {
        if (root == null)
            return new ArrayList<>();
        Queue<Node> que = new ArrayDeque<>();
        que.add(root);
        ArrayList<Integer> ans = new ArrayList<>();
        while (que.size() > 0) {
            int size = que.size();
            Node firstNode = que.peek();
            ans.add(firstNode.data);
            while (size-- > 0) {
                Node rem = que.remove();
                if (rem.right != null)
                    que.add(rem.right);
                if (rem.left != null)
                    que.add(rem.left);
            }
        }
        return ans;
    }

    static void widthOfBT(Node root, int minMax[], int lvl) {
        if (root == null)
            return;
        minMax[0] = Math.min(lvl, minMax[0]);
        minMax[1] = Math.max(minMax[1], lvl);
        widthOfBT(root.left, minMax, lvl - 1);
        widthOfBT(root.right, minMax, lvl + 1);

    }

    static class VerticalPair {
        Node node;
        int vl;
        int hl;

        VerticalPair(Node node, int vl) {
            this.node = node;
            this.vl = vl;
        }

        VerticalPair(Node node, int hl, int vl) {
            this.node = node;
            this.vl = vl;
            this.hl = hl;
        }
    }

    static ArrayList<Integer> verticalOrder(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        int width = maxMin[1] - maxMin[0] + 1;
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, Math.abs(maxMin[0])));
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        for (int i = 0; i < width; i++)
            out.add(new ArrayList<>());
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.remove();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                out.get(vl).add(node.data);
                if (node.left != null)
                    que.add(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.add(new VerticalPair(node.right, vl + 1));
            }
        }
        out.forEach(ans::addAll);
        return ans;
    }

    public static int height(Node root) {
        return root == null ? 0 : Math.max(height(root.left), height(root.right)) + 1;

    }

    // !imp
    static List<Integer> rightView2(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        // lvl can be used
        int height = height(root);
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, 0, Math.abs(maxMin[0])));
        int[] result = new int[height];
        int lastVerticalLevel[] = new int[height];
        Arrays.fill(result, -1);
        Arrays.fill(lastVerticalLevel, -1);
        while (!que.isEmpty()) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.remove();
                int vl = verticalPair.vl;
                int hl = verticalPair.hl;
                Node node = verticalPair.node;
                if (result[hl] == -1) {
                    result[hl] = node.data;
                    lastVerticalLevel[hl] = vl;
                } else {
                    if (vl >= lastVerticalLevel[hl]) {
                        result[hl] = node.data;
                        lastVerticalLevel[hl] = vl;
                    }
                }
                if (node.left != null)
                    que.add(new VerticalPair(node.left, hl + 1, vl - 1));
                if (node.right != null)
                    que.add(new VerticalPair(node.right, hl + 1, vl + 1));
            }
        }
        return Arrays.stream(result)
                .boxed()
                .collect(Collectors.toList());
    }

    public ArrayList<Integer> bottomView(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> ans = new ArrayList<>();

        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        LinkedList<VerticalPair> que = new LinkedList<>();
        que.addLast(new VerticalPair(root, Math.abs(maxMin[0])));
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.removeFirst();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                ans.set(vl, node.data);

                if (node.left != null)
                    que.addLast(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.addLast(new VerticalPair(node.right, vl + 1));
            }
        }

        return ans;
    }

    static ArrayList<Integer> topView(Node root) {
        if (root == null) {
            return new ArrayList<>();
        }
        ArrayList<Integer> ans = new ArrayList<>();

        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        LinkedList<VerticalPair> que = new LinkedList<>();
        int width = maxMin[1] - maxMin[0] + 1;
        for (int i = 0; i < width; i++) {
            ans.add(-1);
        }
        que.addLast(new VerticalPair(root, Math.abs(maxMin[0])));
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.removeFirst();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                if (ans.get(vl) == -1)
                    ans.set(vl, node.data);

                if (node.left != null)
                    que.addLast(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.addLast(new VerticalPair(node.right, vl + 1));
            }
        }

        return ans;
    }

    static ArrayList<ArrayList<Integer>> bottomView_allValues(Node root) {
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        LinkedList<VerticalPair> que = new LinkedList<>();
        int width = maxMin[1] - maxMin[0] + 1;
        que.addLast(new VerticalPair(root, 0, Math.abs(maxMin[0])));
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> lastHorizontalLevel = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            ans.add(new ArrayList<>());
            lastHorizontalLevel.add(-1);
        }
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair remPair = que.removeFirst();
                Node node = remPair.node;
                int hl = remPair.hl, vl = remPair.vl;

                if (hl > lastHorizontalLevel.get(vl)) {
                    ans.get(vl).clear();

                }
                ans.get(vl).add(node.data);
                lastHorizontalLevel.set(vl, hl);

                if (node.left != null)
                    que.addLast(new VerticalPair(node.left, hl + 1, vl - 1));
                if (node.right != null)
                    que.addLast(new VerticalPair(node.right, hl + 1, vl + 1));
            }
        }
        return ans;
    }

    public ArrayList<Integer> verticalSum(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        int width = maxMin[1] - maxMin[0] + 1;
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, Math.abs(maxMin[0])));
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < width; i++)
            ans.add(0);
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.remove();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                ans.set(vl, ans.get(vl) + node.data);
                if (node.left != null)
                    que.add(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.add(new VerticalPair(node.right, vl + 1));
            }
        }
        return ans;
    }

    public ArrayList<ArrayList<Integer>> diagonalOrder(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        int width = 0 - maxMin[0] + 1;
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, Math.abs(maxMin[0])));
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        for (int i = 0; i < width; i++)
            out.add(new ArrayList<>());
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.remove();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                out.get(vl).add(node.data);
                if (node.left != null)
                    que.add(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.add(new VerticalPair(node.right, vl));
            }
        }
        out.forEach(ans::addAll);
        return out;
    }

    public ArrayList<Integer> diagonalOrder_geeks(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, Math.abs(maxMin[0])));
        ArrayList<Integer> ans = new ArrayList<>();

        while (que.size() > 0) {
            VerticalPair verticalPair = que.remove();
            int vl = verticalPair.vl;
            Node node = verticalPair.node;
            ans.add(node.data);
            while (node != null) {
                if (node.left != null) {
                    que.add(new VerticalPair(node.left, vl - 1));
                }
                node = node.right;
                if (node != null)
                    ans.add(node.data);
            }
        }
        return ans;
    }

    public ArrayList<Integer> diagonalOrderSum(Node root) {
        if (root == null)
            return new ArrayList<>();
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        int width = 0 - maxMin[0] + 1;
        Queue<VerticalPair> que = new ArrayDeque<>();
        que.add(new VerticalPair(root, Math.abs(maxMin[0])));
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < width; i++)
            ans.add(0);
        while (que.size() > 0) {
            int size = que.size();
            while (size-- > 0) {
                VerticalPair verticalPair = que.remove();
                int vl = verticalPair.vl;
                Node node = verticalPair.node;
                ans.set(vl, ans.get(vl) + node.data);
                if (node.left != null)
                    que.add(new VerticalPair(node.left, vl - 1));
                if (node.right != null)
                    que.add(new VerticalPair(node.right, vl));
            }
        }
        return ans;
    }

    static class VerticalPair2 {
        TreeNode node;
        int vl;
        int hl;

        VerticalPair2(TreeNode node, int hl, int vl) {
            this.node = node;
            this.vl = vl;
            this.hl = hl;
        }
    }

    static void widthOfBT(TreeNode root, int minMax[], int lvl) {
        if (root == null)
            return;
        minMax[0] = Math.min(lvl, minMax[0]);
        minMax[1] = Math.max(minMax[1], lvl);
        widthOfBT(root.left, minMax, lvl - 1);
        widthOfBT(root.right, minMax, lvl + 1);

    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        int maxMin[] = new int[2];
        widthOfBT(root, maxMin, 0);
        int width = maxMin[1] - maxMin[0] + 1;
        for (int i = 0; i < width; i++)
            ans.add(new ArrayList<>());
        PriorityQueue<VerticalPair2> pq = new PriorityQueue<>((a, b) -> {
            if (a.hl != b.hl)
                return a.hl - b.hl;
            return a.node.val - b.node.val;
        });
        pq.add(new VerticalPair2(root, 0, Math.abs(maxMin[0])));
        while (pq.size() > 0) {
            VerticalPair2 remPair = pq.remove();
            int vl = remPair.vl, hl = remPair.hl;
            TreeNode node = remPair.node;
            ans.get(vl).add(node.val);
            if (node.left != null)
                pq.add(new VerticalPair2(node.left, hl + 1, vl - 1));
            if (node.right != null)
                pq.add(new VerticalPair2(node.right, hl + 1, vl + 1));
        }
        return ans;
    }
}
