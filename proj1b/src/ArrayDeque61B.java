import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ArrayDeque61B<T> implements Deque61B<T> {

    private T[] item;
    private int preIndex;
    private int nexIndex;
    private int size;
    private final int border = 16;

    //constructor
    public ArrayDeque61B() {
        //create a new array with 8 space
        item = (T[]) new Object[8];
        preIndex = 0;
        nexIndex = 1;
        size = 0;
    }

    //helper function for wrap method

    //make sure the index is always in valid length, and connect back with front
    private int wrapMethod(int num) {
        return Math.floorMod(num, item.length);
    }

    //need to use wrap method inside, to make sure store in correct order
    private void resize(int newSize) {
        if (newSize < 8) {
            newSize = 8;
        }
        T[] newArr = (T[]) new Object[newSize];
        //starting from the first element
        int tempIndex = wrapMethod(preIndex + 1);
        for (int i = 0; i < size; i++) {
            newArr[i] = item[tempIndex];
            //keep on track of the original array
            tempIndex = wrapMethod(tempIndex + 1);
        }
        //reset nexIndex and preIndex
        preIndex = newArr.length - 1;
        nexIndex = size;
        item = newArr;
    }

    @Override
    public void addFirst(T x) {
        if (size == item.length) {
            resize(item.length * 2);
        }
        item[preIndex] = x;
        preIndex = Math.floorMod(preIndex - 1, item.length);
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == item.length) {
            resize(item.length * 2);
        }
        item[nexIndex] = x;
        nexIndex = Math.floorMod(nexIndex + 1, item.length);
        size++;
    }

    @Override
    public List<T> toList() {
        //given
        List<T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
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
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        preIndex = wrapMethod(preIndex + 1);
        T tempHold = item[preIndex];
        item[preIndex] = null;
        size--;
        if (item.length >= border && size * 4 <= item.length) {
            resize(item.length / 2);
        }
        return tempHold;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nexIndex = wrapMethod(nexIndex - 1);
        T tempHold = item[nexIndex];
        item[nexIndex] = null;
        size--;
        if (item.length >= border && size * 4 <= item.length) {
            resize(item.length / 2);
        }
        return tempHold;
    }

    /*
    example for get
     0 1 2 3 4 5 6 7 8
    [0,0,1,0,0,0,0,0,0]
    index = 0
    preIndex = 1
    nexIndex = 3
    1 + 0 + 1 = 2

     */
    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            int tempIndex = wrapMethod(preIndex + index + 1);
            return item[tempIndex];
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;
        private int num;

        public ArrayDequeIterator() {
            index = wrapMethod(preIndex + 1);
            num = 0;
        }

        @Override
        public boolean hasNext() {
            if (num < size) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T tempItem = item[index];
            index = wrapMethod(index + 1);
            num++;
            return tempItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof Deque61B)) {
            return false;
        }
        Deque61B<?> ot = (Deque61B<?>) other;
        if (this.size() != ot.size()) {
            return false;
        }

        for (int i = 0; i < this.size(); i++) {
            if (!(this.get(i).equals(ot.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder returnSB = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            int index = wrapMethod(preIndex + 1 + i);
            returnSB.append(item[index].toString());
            if (i < size - 1) {
                returnSB.append(", ");
            }
        }
        returnSB.append("]");
        return returnSB.toString();
    }

}
