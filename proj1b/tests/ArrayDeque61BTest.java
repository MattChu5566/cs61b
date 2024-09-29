import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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

    @Test
    void testToList() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.toList()).containsExactly();

      arrDeque.addFirst(1);
      arrDeque.addLast(2);

      assertThat(arrDeque.toList()).containsExactly(1, 2);
    }

    @Test
    void testAddFirst() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();

      arrDeque.addFirst(1);
      assertThat(arrDeque.toList()).containsExactly(1);

      arrDeque.addFirst(2);
      assertThat(arrDeque.toList()).containsExactly(2, 1);

      for (int i = 3; i <= 9; i++) {
        arrDeque.addFirst(i);
      }
      assertThat(arrDeque.toList()).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1);

      for (int i = 0; i < 9; i++) {
        arrDeque.removeFirst();
      }
      arrDeque.addFirst(1);
      assertThat(arrDeque.toList()).containsExactly(1);
    }

    @Test
    void testAddLast() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();

      arrDeque.addLast(1);
      assertThat(arrDeque.toList()).containsExactly(1);

      arrDeque.addLast(2);
      assertThat(arrDeque.toList()).containsExactly(1, 2);

      for (int i = 3; i <= 9; i++) {
        arrDeque.addLast(i);
      }
      assertThat(arrDeque.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);

      for (int i = 0; i < 9; i++) {
        arrDeque.removeFirst();
      }
      arrDeque.addLast(1);
      assertThat(arrDeque.toList()).containsExactly(1);
    }

    @Test
    void testGet() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.get(0)).isNull();

      arrDeque.addFirst(1);
      
      assertThat(arrDeque.get(0)).isEqualTo(1);
      assertThat(arrDeque.get(-1)).isNull();
      assertThat(arrDeque.get(2)).isNull();
    }

    @Test
    void testIsEmpty() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.isEmpty()).isTrue();

      arrDeque.addFirst(1);
      assertThat(arrDeque.isEmpty()).isFalse();

      arrDeque.removeFirst();
      assertThat(arrDeque.isEmpty()).isTrue();
    }

    @Test
    void testSize() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.size()).isEqualTo(0);

      arrDeque.addFirst(1);
      assertThat(arrDeque.size()).isEqualTo(1);

      arrDeque.removeFirst();
      assertThat(arrDeque.size()).isEqualTo(0);

      arrDeque.removeFirst();
      assertThat(arrDeque.size()).isEqualTo(0);
    }

    @Test
    void testRemoveFirst() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.removeFirst()).isNull();

      arrDeque.addFirst(1);
      assertThat(arrDeque.removeFirst()).isEqualTo(1);

      arrDeque.addFirst(1);
      arrDeque.addFirst(2);
      assertThat(arrDeque.removeFirst()).isEqualTo(2);
      assertThat(arrDeque.toList()).containsExactly(1);

      arrDeque.removeFirst();
      for (int i = 0; i < 9; i++) {
        arrDeque.addLast(i);
      }

      for (int i = 0; i < 6; i++) {
        arrDeque.removeFirst();
      }
      assertThat(arrDeque.toList()).containsExactly(6, 7, 8);
    }

    @Test
    void testRemoveLast() {
      Deque61B<Integer> arrDeque = new ArrayDeque61B<>();
      assertThat(arrDeque.removeLast()).isNull();

      arrDeque.addFirst(1);
      assertThat(arrDeque.removeLast()).isEqualTo(1);

      arrDeque.addFirst(1);
      arrDeque.addFirst(2);
      assertThat(arrDeque.removeLast()).isEqualTo(1);
      assertThat(arrDeque.toList()).containsExactly(2);

      arrDeque.removeLast();
      for (int i = 0; i < 9; i++) {
        arrDeque.addLast(i);
      }

      for (int i = 0; i < 6; i++) {
        arrDeque.removeLast();
      }
      assertThat(arrDeque.toList()).containsExactly(0, 1, 2);
    }
}
