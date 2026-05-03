import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }
    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");

        MaxArrayDeque61B<String> mad2 = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad2.addLast("a");
        mad2.addLast("b");
        mad2.addLast("c");
        mad2.addLast("d");
        assertThat(mad2.max()).isEqualTo("a");

        MaxArrayDeque61B<String> mad3 = new MaxArrayDeque61B<>(new StringLengthComparator());
        assertThat(mad3.max()).isEqualTo(null);

        Comparator<Integer> mad4 = Comparator.naturalOrder();
        MaxArrayDeque61B<Integer> mad5 = new MaxArrayDeque61B<>(mad4);
        Comparator<Integer> mad6 = Comparator.reverseOrder();

        mad5.addFirst(4);
        mad5.addFirst(3);
        mad5.addFirst(2);
        mad5.addFirst(1);

        int maxItem = mad5.max(mad6);
        assertThat(maxItem).isEqualTo(1);

    }

    @Test
    public void isEmptyTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();
        Deque61B<Integer> lld3 = new ArrayDeque61B<>();

        lld2.addFirst(-2);   // [-2]
        lld2.addFirst(0);    // [-2, 0]
        lld2.addFirst(2);    // [-2, 0, 2]
        lld2.addFirst(4);    // [-2, 0, 2, 4]
        lld2.addFirst(6);    // [-2, 0, 2, 4, 6]

        lld3.addFirst(null); //

        assertWithMessage("isEmpty should be true []")
                .that(lld1.isEmpty())
                .isEqualTo(true);

        assertWithMessage("isEmpty should be false [-2, 0, 2, 4, 6]")
                .that(lld2.isEmpty())
                .isEqualTo(false);

        assertWithMessage("isEmpty should be false [null]")
                .that(lld3.isEmpty())
                .isEqualTo(false);
    }

    @Test
    public void sizeTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();
        Deque61B<Integer> lld3 = new ArrayDeque61B<>();
        Deque61B<Integer> lld4 = new ArrayDeque61B<>();

        lld2.addLast(-2);  // [-2]
        lld2.addLast(0);   // [-2, 0]
        lld2.addLast(2);   // [-2, 0, 2]
        lld2.addLast(4);   // [-2, 0, 2, 4]
        lld2.addLast(6);   // [-2, 0, 2, 4, 6]

        lld3.addLast(-2);  // [-2]
        lld3.addLast(0);   // [-2, 0]
        lld3.addLast(2);   // [-2, 0, 2]
        lld3.removeFirst();   // [0, 2]
        lld3.removeLast();    // [0]

        lld4.removeLast();    // []

        assertWithMessage("size should be 0 []")
                .that(lld1.size())
                .isEqualTo(0);

        assertWithMessage("size should be 5 [-2, 0, 2, 4, 6]")
                .that(lld2.size())
                .isEqualTo(5);

        assertWithMessage("size should be 1 [0]")
                .that(lld3.size())
                .isEqualTo(1);

        assertWithMessage("size should be 0 []")
                .that(lld4.size())
                .isEqualTo(0);
    }

    @Test
    public void getTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.get(0))
                .isEqualTo(-2);

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.get(-523523))
                .isEqualTo(null);

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.get(824828623))
                .isEqualTo(null);
    }


    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        Deque61B<String> lld3 = new ArrayDeque61B<>();
        Deque61B<Integer> lld4 = new ArrayDeque61B<>();


        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        lld3.addLast("only item"); // ["only item"]

        lld4.addLast(-2); // [-2]
        lld4.addLast(0);  // [-2, 0]
        lld4.removeFirst();  // [0]

        assertWithMessage("value should -2 [0, 2, 4, 6]")
                .that(lld1.removeFirst())
                .isEqualTo(-2);

        assertWithMessage("value should null []")
                .that(lld2.removeFirst())
                .isEqualTo(null);

        assertWithMessage("value should 'only item' ['only item']")
                .that(lld3.removeFirst())
                .isEqualTo("only item");
        assertWithMessage("one item left")
                .that(lld4.get(0))
                .isEqualTo(0);
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();
        Deque61B<String> lld3 = new ArrayDeque61B<>();

        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        lld3.addLast("only item"); // ["only item"]

        assertWithMessage("value should 6 [-2, 0, 2, 4]")
                .that(lld1.removeLast())
                .isEqualTo(6);

        assertWithMessage("value should null []")
                .that(lld2.removeLast())
                .isEqualTo(null);

        assertWithMessage("value should 'only item' ['only item']")
                .that(lld3.removeLast())
                .isEqualTo("only item");
    }

    @Test
    public void addFirstTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addFirst(-10);  //[-10]
        lld1.addFirst(-5);   //[-5, -10]
        lld1.addFirst(0);    //[0, -5, -10]
        lld1.addFirst(5);    //[5, 0, -5, -10]
        lld1.addFirst(10);   //[10, 5, 0, -5, -10]

        assertWithMessage("the list should be [] ")
                .that(lld1.toList())
                .containsExactly(10, 5, 0, -5, -10)
                .inOrder();

        assertWithMessage("the list should be [] ")
                .that(lld2.toList())
                .containsExactly()
                .inOrder();
    }

    @Test
    public void addLastTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<String> lld2 = new ArrayDeque61B<>();

        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        assertWithMessage("the list should be [] ")
                .that(lld1.toList())
                .containsExactly(-2, 0, 2, 4, 6)
                .inOrder();

        assertWithMessage("the list should be [] ")
                .that(lld2.toList())
                .containsExactly()
                .inOrder();
    }

    @Test
    public void addAfterRemoveAllTest() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        lld1.addLast(-2);      // [-2]
        lld1.removeFirst();
        lld1.addLast(999999);  // [999999]

        lld2.addFirst(2);      // [2]
        lld2.removeLast();
        lld2.addFirst(888888); // [888888]

        assertWithMessage("the list should be [] ")
                .that(lld1.toList())
                .containsExactly(999999)
                .inOrder();

        assertWithMessage("the list should be [] ")
                .that(lld2.toList())
                .containsExactly(888888)
                .inOrder();

    }
    
}
