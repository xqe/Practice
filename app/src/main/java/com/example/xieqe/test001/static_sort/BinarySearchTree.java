package com.example.xieqe.test001.static_sort;

/**
 * Created by xieqe on 2017/6/29.
 */

public class BinarySearchTree<T extends Comparable<? super T>> {

    public static class Node<T> {

        T element;
        Node<T> left;
        Node<T> right;

        Node(T element){
            this(element,null, null);
        }

        Node(T element, Node<T> left, Node<T> right){
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    private Node<T> root;

    public BinarySearchTree(){
        root = null;
    }

    public void makeEmpty(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }
    
    public boolean contains(T key){
        return contains(key,root);
    }

    public T findMin(){

        return findMin(root).element;
    }

    public T findMax(){

        return findMax(root).element;
    }

    public void insert(T newElement){
        insert(newElement,root);
    }

    public void remove(T key){
        remove(key,root);
    }

    private boolean contains(T key, Node<T> root){
        if (root == null) {
            return false;
        }

        int compareResult = key.compareTo(root.element);
        if (compareResult < 0){
            return contains(key, root.left);
        }else if (compareResult > 0){
            return contains(key, root.right);
        }else {     //如果相同，比较结果 = 0
            return true;
        }
    }

    private Node<T> findMin(Node<T> root){
        if (root == null){
            return null;
        }else if (root.left == null){
            return root;
        }

        return findMin(root.left);

    }

    private Node<T> findMax(Node<T> root){
        if (root == null){
            return null;
        }

        Node<T> max = null;
        Node<T> current;
        current = root;
        while (current!=null){
            max = current;
            current = current.right;
        }
        return max;
    }

    private void insert(T newElement, Node<T> root){

    }

    private void remove(T newElement, Node<T> root){

    }
}
