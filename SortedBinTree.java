package tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SortedBinTree<T extends Comparable> {
    static class Node{
        Object data;
        Node parent;
        Node left;
        Node right;

        public Node(Object data, Node parent, Node left, Node right){
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public String toString(){
            return "[data=" + data + "]";
        }

        public boolean equals(Object obj){
            if(this == obj)
                return true;
            if(obj.getClass() == Node.class){
                Node target = (Node) obj;
                return data.equals(target.data)
                        && left == target.left
                        && right == target.right
                        && parent == target.parent;
            }
            return false;
        }
    }

    private Node root;

    public SortedBinTree(){
        root = null;
    }

    public SortedBinTree(T data){
        root = new Node(data, null, null,null);
    }

    //添加节点
    public void add(T ele){
        if(root == null)
            root = new Node(ele,null,null,null);
        else {
            Node current = root;
            Node parent = null;
            int cmp = 0;
            //搜索合适的叶子节点，以该叶子节点为父节点添加新节点
            do{
                parent = current;
                cmp = ele.compareTo(current.data);
                //如果新节点的值大于当前节点的值
                if(cmp > 0)
                {
                    //以右节点作为当前节点的值
                    current = current.right;
                }else {
                    //以左节点作为当前节点的值
                    current = current.left;
                }
            }while (current != null);
            //创建新节点
            Node newNode = new Node(ele, parent, null,null);
            //如果新节点的值大于父节点的值
            if(cmp > 0){
                //新节点作为父节点的右子节点
                parent.right = newNode;
            }else {
                //新节点作为父节点的左子节点
                parent.left = newNode;
            }
        }
    }

    //删除节点
    public void remove(T ele){
        //获取要删除的节点
        Node target = getNode(ele);
        if(target == null)
            return;
        if(target.left == null && target.left == null){
            //被删除节点是根节点
            if(target == root)
                root = null;
            else {
                //被删除的节点是父节点的左子节点
                if(target == target.parent.left){
                    //将target的父节点的left设为null
                    target.parent.left = null;
                }else {
                    //将target的父节点的right设为null
                    target.parent.right = null;
                }
                target.parent = null;
            }
        }else if(target.left == null && target.right != null){
            //被删除节点是根节点
            if(target == root)
                root = target.right;
            else {
                //被删除节点是父节点的左子节点
                if(target == target.parent.left){
                    //让target的父节点的left指向target的右子树
                    target.parent.left = target.right;
                }else {
                    target.parent.right = target.parent;
                }
            }
        }else if(target.left != null && target.right == null){
            //被删除节点是根节点
            if(target == root)
                root = target.left;
            else {
                //被删除节点是父节点的左子节点
                if(target == target.parent.left){
                    target.parent.left = target.left;
                }else {
                    //让target的父节点的right指向target的左子树
                    target.parent.right = target.left;
                }
                target.left.parent = target.parent;
            }
        }else {
            //leftMaxNode用于保存target节点的左子树中值最大的节点
            Node leftMaxNode = target.left;
            //搜索target节点的左子树中值最大的节点
            while (leftMaxNode.right != null){
                leftMaxNode = leftMaxNode.right;
            }
            //从原来的子树中删除leftMaxNode节点
            leftMaxNode.parent.right = null;
            //让leftMaxNode的parent指向target的parent
            leftMaxNode.parent = target.parent;
            //被删除节点是父节点的左子节点
            if(target == target.parent.left){
                //让target的父节点的left指向leftMaxNode
                target.parent.left = leftMaxNode;
            }else {
                target.parent.right = leftMaxNode;
            }
            leftMaxNode.left = target.left;
            leftMaxNode.right = target.right;
            target.parent = target.left = target.right = null;
        }
    }

    //根据给定的值搜索节点
    public Node getNode(T ele) {
        Node p = root;
        while (p != null){
            int cmp = ele.compareTo(p.data);
            if(cmp < 0){
                p = p.left;
            }else if(cmp > 0){
                p = p.right;
            }else {
                return p;
            }
        }
        return null;
    }

    //广度优先遍历
    public List<Node> breadthFirst(){
        Queue<Node> queue = new ArrayDeque<>();
        List<Node> list = new ArrayList<>();
        if(root != null)
            queue.offer(root);
        while (!queue.isEmpty()){
            //将该队列的“队尾”的元素添加到List中
            list.add(queue.peek());
            Node p = queue.poll();
            //如果左子节点不为null，将它加入队列
            if(p.left != null)
                queue.offer(p.left);
            //如果右子节点不为null，将它加入队列
            if(p.right != null)
                queue.offer(p.right);
        }
        return list;
    }

    //测试
    public static void main(String[] args) {
        SortedBinTree<Integer> tree = new SortedBinTree<>();
        tree.add(5);
        tree.add(20);
        tree.add(10);
        tree.add(3);
        tree.add(8);
        tree.add(15);
        tree.add(30);
        System.out.println(tree.breadthFirst());
        tree.remove(20);
        System.out.println(tree.breadthFirst());
    }



}
