package deque;
import java.util.ArrayList; // import the ArrayList class

import java.util.List;

public class LinkedListDeque61B<TempHold> implements Deque61B<TempHold> {
    private class IntNode {
        private TempHold item;
        private IntNode prev;
        private IntNode next;

        public IntNode(IntNode p, TempHold i, IntNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private IntNode sentinel;
    private int size;

    //constructor
    public LinkedListDeque61B() {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(TempHold x) {
        //it's actually adding on the right of sentinel
        IntNode newIntNode = new IntNode(sentinel, x, sentinel.next);
        //let the existing mode's prev connect with new node
        sentinel.next.prev = newIntNode;
        //let sentinel's next connect with new node
        sentinel.next = newIntNode;
        //help counting the size
        size += 1;

    }

    @Override
    public void addLast(TempHold x) {
        //adding the node to the left of sentinel
        IntNode newIntNode = new IntNode(sentinel.prev, x, sentinel);
        //let old_last's next point to new node
        sentinel.prev.next = newIntNode;
        //let sentinel's prev connect with new node
        sentinel.prev = newIntNode;
        //help counting the size
        size += 1;

    }

    @Override
    public List<TempHold> toList() {
        List<TempHold> returnList = new ArrayList<>();
        IntNode currentNode = sentinel.next;

        while (currentNode != sentinel) {
            returnList.add(currentNode.item);
            currentNode = currentNode.next;

        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public TempHold removeFirst() {
        if (sentinel.next != sentinel) {
            IntNode removeNode = sentinel.next;
            TempHold removeItem = removeNode.item;
            //let sentinel pointer point to the second node
            sentinel.next = removeNode.next;
            //let second node point to sentinel
            sentinel.next.prev = sentinel;
            size -= 1;
            return removeItem;
        }
        return null;

    }

    @Override
    public TempHold removeLast() {
        if (sentinel.prev != sentinel) {
            IntNode removeNode = sentinel.prev;
            TempHold removeItem = removeNode.item;
            //let sentinel pointer point to the second last node
            sentinel.prev = removeNode.prev;
            //let second node point to sentinel
            sentinel.prev.next = sentinel;
            size -= 1;
            return removeItem;

        }
        return null;
    }

    @Override
    public TempHold get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        //start from the first valid node, sentinel.next
        IntNode currentNode = sentinel.next;
        while (index > 0) {
            currentNode = currentNode.next;
            index -= 1;
        }
        return currentNode.item;

    }

    @Override
    public TempHold getRecursive(int index) {
        //base case if index is out of bound, index = -1
        if (index < 0 || index >= size) {
            return null;
        }
        //need helper function
        return getRecursiveHelper(sentinel.next, index);

    }

    private TempHold getRecursiveHelper(IntNode node, int index) {
        //base case, if we find the item then return
        if (index == 0) {
            return node.item;
        }
        //if we did not reach to correct item, then keep moving
        return getRecursiveHelper(node.next, index - 1);

    }
}
