import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {


     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    //test case from 1a

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

    @Test
    public void TestItertor() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        lld1.addLast(10);      // [10]
        lld1.addFirst(0);      // [0, 10]
        lld1.addFirst(-10);    // [-10, 0, 10]

        Iterator<Integer> iterList = lld1.iterator();

        assertWithMessage("True")
                .that(lld1.get(0))
                .isEqualTo(iterList.next());
        assertWithMessage("True")
                .that(lld1.get(1))
                .isEqualTo(iterList.next());
        assertWithMessage("True")
                .that(lld1.get(2))
                .isEqualTo(iterList.next());
        assertWithMessage("True")
                .that(lld1.get(2))
                .isNotEqualTo(iterList.next());

    }

    @Test
    public void testEquals() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();
        Deque61B<Integer> lld3 = new ArrayDeque61B<>();

        lld1.addLast(10);      // [10]
        lld1.addFirst(0);      // [0, 10]
        lld1.addFirst(-10);    // [-10, 0, 10]

        lld2.addLast(10);      // [10]
        lld2.addFirst(0);      // [0, 10]
        lld2.addFirst(-10);    // [-10, 0, 10]

        lld3.addLast(10);      // [10]
        lld3.addFirst(0);      // [0, 10]
        lld3.addFirst(-100);    // [-10, 0, 10]

        boolean firstCase = lld1.equals(lld2);
        boolean secondCase = lld1.equals(lld3);

        assertWithMessage("True")
                .that(firstCase)
                .isEqualTo(true);
        assertWithMessage("True")
                .that(secondCase)
                .isEqualTo(false);
    }

    @Test
    public void testToString() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();

        lld1.addLast(10);      // [10]
        lld1.addFirst(0);      // [0, 10]
        lld1.addFirst(-10);    // [-10, 0, 10]

        assertWithMessage("with item")
                .that(lld1.toString())
                .isEqualTo("[-10, 0, 10]");
        assertWithMessage("no item")
                .that(lld2.toString())
                .isEqualTo("[]");

    }

    @Test
    public void testResize() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
        Deque61B<Integer> lld2 = new ArrayDeque61B<>();


        for (int i = 1; i <= 16; i++) {
            lld1.addLast(i);
            lld2.addFirst(i);
        }

        // Remove elements to reduce size to 4 (one-quarter of 16)
        for (int i = 0; i < 12; i++) {
            lld1.removeLast();
            lld2.removeFirst();
        }

        assertWithMessage("lld1 size after removals")
                .that(lld1.size())
                .isEqualTo(4);
        assertWithMessage("lld2 size after removals")
                .that(lld2.size())
                .isEqualTo(4);



    }

    @Test
    public void resizeUpAndDown() {
        Deque61B<Integer> lld3 = new ArrayDeque61B<>();

        for (int i = 0; i < 16; i++) {
            lld3.addLast(i);
        }
        assertThat(lld3.size()).isEqualTo(16);

        for(int i = 0; i < 14; i++) {
            lld3.removeFirst();
        }

        assertThat(lld3.size()).isEqualTo(2);
        assertThat(lld3.get(0)).isEqualTo(14);
        assertThat(lld3.get(1)).isEqualTo(15);
    }

    @Test
    public void removeLastTriggerResize() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

        for (int i =0; i < 16; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.size()).isEqualTo(16);
        lld1.removeLast();
        assertThat(lld1.size()).isEqualTo(15);
        assertThat(lld1.get(14)).isEqualTo(14);

    }

}
