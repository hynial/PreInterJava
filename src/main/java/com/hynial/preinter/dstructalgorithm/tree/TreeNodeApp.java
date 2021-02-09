package com.hynial.preinter.dstructalgorithm.tree;

import java.util.*;

public class TreeNodeApp {

    public String serialize(TreeNode rootNode) {
        StringBuilder stringBuilder = new StringBuilder("[");
        if (rootNode == null) return stringBuilder.append("]").toString();

        TreeNode currentNode = rootNode;

        int front = -1, rear = 0, last = 0;
        List<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(rear, currentNode);
        int nodeCount = 0;
        // 树高
        int h = high(rootNode);
        // 满二叉树前h-1层个数
        int h_1Count = Double.valueOf(Math.pow(2, h - 1) - 1).intValue();
        int h_2Count = Double.valueOf(Math.pow(2, h - 2) - 1).intValue();
        while (front < rear) {
            currentNode = treeNodes.get(++front);
            if (currentNode != null) {
                stringBuilder.append(currentNode.val);

                treeNodes.add(++rear, currentNode.left);
                treeNodes.add(++rear, currentNode.right);
            } else {
                stringBuilder.append("null");
            }
            stringBuilder.append(",");
        }

        if (',' == (stringBuilder.charAt(stringBuilder.length() - 1))) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.append("]").toString();
    }

    public TreeNode deserialize(String treeString) {
        if (treeString == null) return null;
        if (treeString.length() == 0) return null;

        treeString = treeString.replaceAll("[\\[\\]]", "");
        String[] nodes = treeString.split(",");
        if (nodes == null || nodes.length == 0) return null;

        TreeNode rootNode = null, currentNode;
        Map<Integer, TreeNode> finishedMap = new HashMap<>();
        Map<TreeNode, Integer> children = new HashMap<>();
        for (int i = 0; i < nodes.length; i++) {
            currentNode = "null".equals(nodes[i]) ? null : new TreeNode(Integer.parseInt(nodes[i]));
            finishedMap.put(i, currentNode);
            children.put(currentNode, 0);

            if (i == 0) {
                rootNode = currentNode;
            } else {
                int fatherIdx = (i + 1) / 2 - 1;
                TreeNode fatherNode = finishedMap.get(fatherIdx);
                while (fatherNode == null && fatherIdx < i) {
                    fatherNode = finishedMap.get(++fatherIdx);
                    if (fatherNode != null) {
                        if (children.get(fatherNode).intValue() > 1) {
                            fatherNode = null;
                        }
                    }
                }

                if (isLeft(i)) {
                    fatherNode.left = (currentNode);
                } else {
                    fatherNode.right = (currentNode);
                }

                children.put(fatherNode, children.get(fatherNode) == null ? 1 : children.get(fatherNode).intValue() + 1);
            }

        }

        return rootNode;
    }

    private boolean isLeft(int i) {
        return i % 2 == 1 ? true : false;
    }

    /**
     * 按层次打印元素
     *
     * @param node
     */
    public void printByLevel(TreeNode node) {
        if (node == null) return;
        TreeNode p = node;
        int front = -1, rear = 0;
        List<TreeNode> lists = new ArrayList<>();
        lists.add(rear, p);
        StringBuilder stringBuilder = new StringBuilder("[");
        while (front < rear) {
            p = lists.get(++front);
            stringBuilder.append((p == null ? "null" : p.val)).append(",");
            if (p != null) {
                lists.add(++rear, p.left);
                lists.add(++rear, p.right);
            }
        }

        if (',' == stringBuilder.charAt(stringBuilder.length() - 1)) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        System.out.println(stringBuilder.append("]").toString());
    }

    private int amount = 0;

    public int count(TreeNode node) {
        if (node == null) return this.amount;
        this.amount++;

        count(node.left);
        count(node.right);

        return this.amount;
    }

    /**
     * 算个数
     *
     * @param rootNode
     * @return
     */
    public int count2(TreeNode rootNode) {
        if (rootNode == null) return 0;

        TreeNode p = rootNode;
        int front = -1, rear = 0;
        List<TreeNode> lists = new ArrayList<>();
        lists.add(rear, p);
        while (front < rear) {
            p = lists.get(++front);
            if (p.left != null) {
                lists.add(++rear, p.left);
            }

            if (p.right != null) {
                lists.add(++rear, p.right);
            }
        }

        return front + 1;
    }

    /**
     * 恢复完全二叉树个数
     *
     * @param rootNode
     * @return
     */
    public int countFull(TreeNode rootNode) {
        int actualCount = count2(rootNode);

        if (rootNode == null) return 0;

        TreeNode p = rootNode;
        int front = -1, rear = 0, emptyCount = 0, elementCount = 0;
        List<TreeNode> lists = new ArrayList<>();
        lists.add(rear, p);
        while (front < rear) {
            p = lists.get(++front);

            lists.add(++rear, p == null ? null : p.left);
            lists.add(++rear, p == null ? null : p.right);

            if (p != null) {
                elementCount++;
                if (elementCount >= actualCount) {
                    break;
                }
            } else {
                emptyCount++;
            }
        }

        System.out.println("Empty:" + emptyCount + ", Actual:" + actualCount + ", Full:" + (front + 1));

        return front + 1;
    }


    /**
     * 求树高-递归
     *
     * @param node
     * @return
     */
    public int high(TreeNode node) {
        if (node == null) return 0;

        int leftH = high(node.left);
        int rightH = high(node.right);

        return leftH > rightH ? leftH + 1 : rightH + 1;
    }

    /**
     * 层次遍历求树高
     *
     * @param rootNode
     * @return
     */
    public int height(TreeNode rootNode) {
        int level = 0;
        if (rootNode == null) return level;

        TreeNode p = rootNode;
        int front = -1, rear = 0, last = 0;
        List<TreeNode> lists = new ArrayList<>();
        lists.add(rear, p);
        while (front < rear) {
            p = lists.get(++front);
            if (p.left != null) {
                lists.add(++rear, p.left);
            }

            if (p.right != null) {
                lists.add(++rear, p.right);
            }

            if (front == last) {
                last = rear;
                level++;
            }
        }

        return level;
    }

    /**
     * 后续遍历求树高
     *
     * @param rootNode
     * @return
     */
    public int height2(TreeNode rootNode) {
        Stack<TreeNode> stack = new Stack<>();

        TreeNode p = rootNode, r = null;

        int h = 0;
        while (!(p == null) || !stack.empty()) {
            if (p != null) {
                stack.push(p);
                p = p.left;
            } else {
                p = stack.peek();
                if (p.right != null && p.right != r) {
                    p = p.right;
                } else {
                    if (stack.size() > h) {
                        h = stack.size();
                    }

                    r = p;
                    stack.pop();
                    p = null;
                }
            }
        }

        return h;
    }

    /**
     * 先序遍历
     *
     * @param rootNode
     * @return
     */
    public String serialize1(TreeNode rootNode) {
        if (rootNode == null) return "null";

        return s1(rootNode, "");
    }

    private String s1(TreeNode node, String v) {
        if (node == null) {
            v += "null,";
        } else {
            v += node.val + ",";
            v = s1(node.left, v);
            v = s1(node.right, v);
        }

        return v;
    }

    public TreeNode deserialize1(String v) {
        if (v == null || v.length() == 0) return null;

        String[] vArr = v.split(",");

        List<String> lists = new LinkedList(Arrays.asList(vArr));

        return d1(lists);
    }

    private TreeNode d1(List<String> lists) {
        if (lists == null || lists.size() == 0) return null;
        if (lists.get(0).equals("null") || lists.get(0).equals("")) {
            lists.remove(0);
            return null;
        }

        TreeNode rootNode = new TreeNode(Integer.parseInt(lists.get(0)));
        lists.remove(0);
        rootNode.left = d1(lists);
        rootNode.right = d1(lists);

        return rootNode;
    }

    /**
     * 中序遍历
     *
     * @return
     */
    public String serialize2(TreeNode rootNode) {
        if (rootNode == null) return "null";

        return s2(rootNode, "");
    }

    private String s2(TreeNode node, String v) {
        if (node == null) return v += "null,";
        else {
            v = s2(node.left, v);
            v = v + node.val + ",";
            v = s2(node.right, v);
        }

        return v;
    }

    /**
     * BFS
     */
    public String bfs_serialize(TreeNode root) {
        String result = "";
        if (root == null) return "X,";
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();
            if (currentNode != null) {
                result += currentNode.val + ",";

                TreeNode currentLeft = currentNode.left;
                TreeNode currentRight = currentNode.right;
                queue.add(currentLeft);
                queue.add(currentRight);
            } else {
                result += "X,";
            }
        }

        return result;
    }

    public TreeNode bfs_rserialize(String serializedString) {
        if (serializedString == null || serializedString.startsWith("X")) {
            return null;
        }
        String[] splitArr = serializedString.split(",");
        int curPlace = 1;
        TreeNode rootNode = new TreeNode(), fatherNode;
        rootNode.val = Integer.parseInt(splitArr[0]);
        List<TreeNode> list = new ArrayList<>();
        list.add(rootNode);
        while (curPlace < splitArr.length - 1) {
            fatherNode = list.get(0);
            list.remove(0);
            if (!"X".equals(splitArr[curPlace])) {
                TreeNode left = new TreeNode();
                left.val = Integer.parseInt(splitArr[curPlace]);
                fatherNode.setLeft(left);
                list.add(left);
            }

            if (!"X".equals(splitArr[curPlace + 1])) {
                TreeNode right = new TreeNode();
                right.val = Integer.parseInt(splitArr[curPlace + 1]);
                fatherNode.setRight(right);
                list.add(right);
            }

            curPlace += 2;
        }

        return rootNode;
    }
}