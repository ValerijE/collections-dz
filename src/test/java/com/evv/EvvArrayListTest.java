package com.evv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class EvvArrayListTest {

    private EvvArrayList<String> stringExample;

    private static final int initStringExampleSize = 7;

    private static final int DEFAULT_CAPACITY = 10;

    @BeforeEach
    void prepareData() {
        stringExample = new EvvArrayList<>();
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
    void add_CheckIncreaseDefaultCapacity10() {
        // given
        // Добиваю размер списка до 10, чтобы при следующем добавление произошло перестроение массива
        stringExample.add("AssertJ");
        stringExample.add("Mockito");
        stringExample.add("Liquibase");
        assertThat(stringExample.size()).isEqualTo(DEFAULT_CAPACITY);

        // when
        stringExample.add("Hibernate");

        // then
        assertThat(stringExample.size()).isEqualTo(DEFAULT_CAPACITY + 1);
        assertThat(stringExample.get(DEFAULT_CAPACITY)).isEqualTo("Hibernate");
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
    void remove_NullElement_SchouldReturnFalse() {
        // when
        boolean result = stringExample.remove(null);

        // then
        assertThat(stringExample.size()).isEqualTo(initStringExampleSize);
        assertThat(result).isFalse();
    }

    @Test
    void remove_FromEmptyList_ShouldReturnFalse() {
        // given
        stringExample = new EvvArrayList<>();

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
        String springResult = stringExample.get(initStringExampleSize - 1);

        // then
        assertThat(javaResult).isEqualTo("Java");
        assertThat(groovyResult).isEqualTo("Groovy");
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
        String jUnitResult = stringExample.set(5, "Mockito");

        //then
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
    void subList() {
        // when
        EvvArrayList<String> result = stringExample.subList(2, 5);

        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo("Groovy");
        assertThat(result.get(1)).isEqualTo("Maven");
        assertThat(result.get(2)).isEqualTo("Gradle");
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
        stringExample = new EvvArrayList<>();

        // when
        int result = stringExample.size();

        // then
        assertThat(stringExample.size()).isEqualTo(0);
    }
}