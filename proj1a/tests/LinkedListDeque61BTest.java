import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import deque.*;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();

        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

        /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
        to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
        but not ["front", "middle", "back"].
        */
    }

    @Test
    /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
    *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
    public void addLastTestBasic() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front"); // after this call we expect: ["front"]
        lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
        lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
        }

        @Test
        /** This test performs interspersed addFirst and addLast calls. */
        public void addFirstAndAddLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        /* I've decided to add in comments the state after each call for the convenience of the
        person reading this test. Some programmers might consider this excessively verbose. */
        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

        assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
    }

    // Below, you'll write your own tests for LinkedListDeque61B.

    //Arrange-Act-Assert pattern:
    @Test
    public void isEmptyTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld3 = new LinkedListDeque61B<>();

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
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld3 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld4 = new LinkedListDeque61B<>();

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
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

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
    public void getRecursiveTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.getRecursive(0))
                .isEqualTo(-2);

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.getRecursive(-523523))
                .isEqualTo(null);

        assertWithMessage("value should be -2, index = 0 [-2, 0, 2, 4, 6]")
                .that(lld1.getRecursive(824828623))
                .isEqualTo(null);
    }

    @Test
    public void removeFirstTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();
        Deque61B<String> lld3 = new LinkedListDeque61B<>();

        lld1.addLast(-2); // [-2]
        lld1.addLast(0);  // [-2, 0]
        lld1.addLast(2);  // [-2, 0, 2]
        lld1.addLast(4);  // [-2, 0, 2, 4]
        lld1.addLast(6);  // [-2, 0, 2, 4, 6]

        lld3.addLast("only item"); // ["only item"]

        assertWithMessage("value should -2 [0, 2, 4, 6]")
                .that(lld1.removeFirst())
                .isEqualTo(-2);

        assertWithMessage("value should null []")
                .that(lld2.removeFirst())
                .isEqualTo(null);

        assertWithMessage("value should 'only item' ['only item']")
                .that(lld3.removeFirst())
                .isEqualTo("only item");
    }

    @Test
    public void removeLastTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();
        Deque61B<String> lld3 = new LinkedListDeque61B<>();

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
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

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
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

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
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();

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