package L01Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class L007_GTree extends Tree {
    public class Node {

        int val = 0;
        ArrayList<Node> children;

        Node(int val) {
            this.val = val;
            this.children = new ArrayList<>();
        }
    }

    public int height(Node root) {
        int maxHeight = 1;
        for (Node children : root.children) {
            maxHeight = Math.max(maxHeight, height(children));
        }
        return maxHeight;
    }

    public int size(Node root) {
        int totalSize = 1;
        for (Node children : root.children) {
            totalSize += size(children);
        }
        return totalSize;
    }

    public int maxEle(Node root) {
        int max = -(int) 1e9;
        for (Node children : root.children) {
            max = Math.max(max, maxEle(children));
        }
        return max;
    }

    public boolean findData(Node root) {
        boolean dataFound = false;
        for (Node children : root.children) {
            dataFound = dataFound || findData(children);
        }
        return dataFound;
    }

    public boolean nodeToRootPath(Node root, int data, List<Integer> ntrp) {
        boolean dataFound = false;
        if (root.val == data) {
            ntrp.add(root.val);
            return true;
        }
        for (Node children : root.children) {
            dataFound = dataFound || nodeToRootPath(children, data, ntrp);
        }
        if (dataFound) {
            ntrp.add(root.val);
        }
        return dataFound;
    }

    // d,h
    public int diameter(Node root) {
        return diameter_(root)[0];
    }

    private int[] diameter_(Node root) {
        int maxH = -1, secMaxH = -1;
        int myRes[] = new int[2];
        for (Node children : root.children) {
            int pair[] = diameter_(children);
            int h = pair[1], d = pair[0];
            if (h > maxH) {
                secMaxH = maxH;
                maxH = h;
            } else if (h > secMaxH) {
                secMaxH = h;
            }
            myRes[0] = Math.max(d, myRes[0]);

        }
        myRes[0] = Math.max(myRes[0], maxH + secMaxH + 2);
        myRes[1] = maxH + 1;
        return myRes;

    }

    public class Codec {

        public void serialize(Node root, StringBuilder sb) {
            sb.append(root.val).append(" ");
            for (Node children : root.children) {
                serialize(children, sb);
            }
            sb.append("null").append(" ");
        }

        public String serialize(Node root) {
            if (root == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            serialize(root, sb);
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public Node deserialize(String data) {
            if (data.length() == 0) {
                return null;
            }
            String[] parts = data.split(" ");
            Stack<Node> st = new Stack<>();
            Node root = new Node(Integer.parseInt(parts[0]));

            st.push(root);
            int idx = 1;
            while (st.size() > 0 && idx < parts.length) {
                if (!parts[idx].equals("null")) {
                    Node newNode = new Node(Integer.parseInt(parts[idx++]));
                    st.peek().children.add(newNode);
                    st.push(newNode);
                } else {
                    idx++;
                    st.pop();
                }
            }
            return root;
        }
    }

    public Node linerizeTree(Node root) {
        if (root.children.size() == 0) {
            return root;
        }
        Node lastNode = root.children.get(root.children.size() - 1);
        Node gTail = linerizeTree(lastNode);
        for (int i = root.children.size() - 2; i >= 0; i--) {
            Node child = root.children.get(i);
            Node tail = linerizeTree(child);
            tail.children.add(root.children.get(i + 1));
            root.children.remove(root.children.size() - 1);
        }

        return gTail;
    }

    public void flatten(TreeNode root) {
        flatten_(root);
    }

    private TreeNode flatten_(TreeNode root) {
        if (root == null)
            return null;
        if (root.left == null && root.right == null)
            return root;

        TreeNode leftTail = flatten_(root.left);
        TreeNode rightTail = flatten_(root.right);
        if (leftTail != null) {
            leftTail.right = root.right;
            TreeNode left = root.left;
            root.left = null;
            root.right = left;
        }

        return rightTail != null ? rightTail : leftTail;
    }

}
