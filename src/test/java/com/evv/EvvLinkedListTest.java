package com.evv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EvvLinkedListTest {

    private EvvLinkedList<String> stringExample;

    private static final int initStringExampleSize = 7;

    @BeforeEach
    void prepareData() {
        stringExample = new EvvLinkedList<>();
        stringExample.add("Java");
        stringExample.add("Kotlin");
        stringExample.add("Groovy");
        stringExample.add("Maven");
        stringExample.add("Gradle");
        stringExample.add("JUnit");
        stringExample.add("Spring");
    }

    @Test
    void add_NormalFlow() {
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);

        // when
        stringExample.add("Hibernate");

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize + 1);
        assertThat(stringExample.get(initStringExampleSize)).isEqualTo("Hibernate");
    }

    @Test
    void add_addNull_ShouldAddNormally() {
        // when
        stringExample.add(null);

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize + 1);
        assertThat(stringExample.get(initStringExampleSize)).isNull();
    }

    @Test
    void remove_NormalFlow() {
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);

        // when
        boolean result1 = stringExample.remove("Maven");
        boolean result2 = stringExample.remove("Maven"); // второе удаление должно быть false

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize - 1);
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void remove_CheckFirstElement_ShouldPassNormally() {
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);

        // when
        boolean result1 = stringExample.remove("Java");
        boolean result2 = stringExample.remove("Java"); // второе удаление должно быть false

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize - 1);
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void remove_CheckLastElement_ShouldPassNormally() {
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);

        // when
        boolean result1 = stringExample.remove("Spring");
        boolean result2 = stringExample.remove("Spring"); // второе удаление должно быть false

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize - 1);
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void remove_NullElement_ShouldReturnFalse() {
        // when
        boolean result = stringExample.remove(null);

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);
        assertThat(result).isFalse();
    }

    @Test
    void remove_FromEmptyList_ShouldReturnFalse() {
        // given
        stringExample = new EvvLinkedList<>();

        // when
        boolean result = stringExample.remove("Maven");

        // then
        assertThat(stringExample.size()).isEqualTo(0);
        assertThat(result).isFalse();
    }

    @Test
    void get_NormalFlow() {
        // when
        String javaResult = stringExample.get(0);
        String groovyResult = stringExample.get(2);
        String gradleResult = stringExample.get(4);
        String springResult = stringExample.get(initStringExampleSize - 1);

        // then
        assertThat(javaResult).isEqualTo("Java");
        assertThat(groovyResult).isEqualTo("Groovy");
        assertThat(gradleResult).isEqualTo("Gradle");
        assertThat(springResult).isEqualTo("Spring");
    }

    @Test
    void get_NegativeIndex_ShouldThrowIndexOutOfBoundsException() {
        // when
        assertThatThrownBy(() -> stringExample.get(-1))
        // then
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index -1 out of bounds for length 7");
    }

    @Test
    void get_TooBigIndex_ShouldThrowIndexOutOfBoundsException() {
        // when
        assertThatThrownBy(() -> stringExample.get(15))
        // then
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index 15 out of bounds for length 7");
    }

    @Test
    void set() {
        // when
        String groovyResult = stringExample.set(2, "LiquiBase");
        String jUnitResult = stringExample.set(5, "Mockito");

        //then
        assertThat(groovyResult).isEqualTo("Groovy");
        assertThat(stringExample.get(2)).isEqualTo("LiquiBase");
        assertThat(jUnitResult).isEqualTo("JUnit");
        assertThat(stringExample.get(5)).isEqualTo("Mockito");
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);
    }

    @Test
    void set_NullValue_ShouldSetNormally() {
        // when
        String jUnitResult = stringExample.set(5, null);

        // then
        assertThat(jUnitResult).isEqualTo("JUnit");
        assertThat(stringExample.get(5)).isEqualTo(null);
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);
    }

    @Test
    void subList_NormalFlow() {
        // when
        EvvLinkedList<String> result = stringExample.subList(2, 5);

        // then
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo("Groovy");
        assertThat(result.get(1)).isEqualTo("Maven");
        assertThat(result.get(2)).isEqualTo("Gradle");
    }

    @Test
    void subList_FromHead_NormalFlow() {
        // when
        EvvLinkedList<String> result = stringExample.subList(0, 2);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("Java");
        assertThat(result.get(1)).isEqualTo("Kotlin");
    }

    @Test
    void subList_FromTail_NormalFlow() {
        // when
        EvvLinkedList<String> result = stringExample.subList(initStringExampleSize - 2, initStringExampleSize);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo("JUnit");
        assertThat(result.get(1)).isEqualTo("Spring");
    }

    @Test
    void subList_lowerIndexIsNegative_ShouldThrowIndexOutOfBoundsException() {
        // when
        assertThatThrownBy(() -> stringExample.subList(-5, 5))
        // then
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Range [-5, 5) out of bounds for length 7");
    }

    @Test
    void subList_higherIndexIsTooBig_ShouldThrowIndexOutOfBoundsException() {
        // when
        assertThatThrownBy(() -> stringExample.subList(0, 8))
        // then
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Range [0, 8) out of bounds for length 7");
    }

    @Test
    void subList_lowerIndexBiggerThenHigherIndex_ShouldThrowIndexOutOfBoundsException() {
        // when
        assertThatThrownBy(() -> stringExample.subList(6, 4))
        // then
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Range [6, 4) out of bounds for length 7");
    }

    @Test
    void size() {
        // when
        int result = stringExample.size();

        // then
        assertThat(result).isEqualTo(initStringExampleSize);
    }

    @Test
    void size_TestEmptyListSize_ShouldReturnZero() {
        // given
        stringExample = new EvvLinkedList<>();

        // when
        int result = stringExample.size();

        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void equals_IdenticallyElements_ShouldReturnTrue() {
        // given
        EvvLinkedList<String> stringExample2 = new EvvLinkedList<>();
        stringExample2.add("Java");
        stringExample2.add("Kotlin");
        stringExample2.add("Groovy");
        stringExample2.add("Maven");
        stringExample2.add("Gradle");
        stringExample2.add("JUnit");
        stringExample2.add("Spring");

        // when
        boolean result = stringExample.equals(stringExample2);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void equals_NotIdenticallyElements_ShouldReturnFalse() {
        // given
        EvvLinkedList<String> stringExample2 = new EvvLinkedList<>();
        stringExample2.add("Java");
        stringExample2.add("Kotlin");
        stringExample2.add("Groovy");
        stringExample2.add("Maven");
        stringExample2.add("Gradle");
        stringExample2.add("JUnit");
        stringExample2.add("Spring2");

        // when
        boolean result = stringExample.equals(stringExample2);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void hashCode_IdenticallyElements_MustBeTheSame() {
        // given
        EvvLinkedList<String> stringExample2 = new EvvLinkedList<>();
        stringExample2.add("Java");
        stringExample2.add("Kotlin");
        stringExample2.add("Groovy");
        stringExample2.add("Maven");
        stringExample2.add("Gradle");
        stringExample2.add("JUnit");
        stringExample2.add("Spring");

        // when
        int result1 = stringExample.hashCode();
        int result2 = stringExample2.hashCode();

        // then
        assertThat(result1).isEqualTo(result2);
    }



    @Test
    void hashCode_NotIdenticallyElements_MustBeDifferent() {
        // given
        EvvLinkedList<String> stringExample2 = new EvvLinkedList<>();
        stringExample2.add("Java");
        stringExample2.add("Kotlin");
        stringExample2.add("Groovy");
        stringExample2.add("Maven");
        stringExample2.add("Gradle");
        stringExample2.add("JUnit");
        stringExample2.add("Spring2");

        // when
        int result1 = stringExample.hashCode();
        int result2 = stringExample2.hashCode();

        // then
        assertThat(result1).isNotEqualTo(result2);
    }
}