import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private final Comparator<T> givenComparator;

    public MaxArrayDeque61B(Comparator<T> c) {
        super();
        this.givenComparator = c;
    }

    public T max() {
        return max(givenComparator);
    }


    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T returnMaxItem = this.get(0);

        for (int i = 0; i < size(); i++) {
            T currItem = get(i);
            if (c.compare(currItem, returnMaxItem) > 0) {
                returnMaxItem = currItem;

            }
        }
        return returnMaxItem;
    }
}
