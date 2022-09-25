package dev.icsw.resumes;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainTestStreams {
    public static void main(String[] args) {
//        System.out.println(minValue(5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
//        System.out.println(minValue(1, 1, 2, 2, 3, 3, 4, 4, 5, 5));
//        System.out.println(minValue(2, 1, 2, 1, 5, 3, 4, 4, 5, 3));
//        System.out.println(minValue(3, 1, 3, 1, 2, 5, 4, 5, 2, 4));
//        System.out.println(minValue(9, 8));
//        System.out.println(minValue(8, 9));

        System.out.println(minValue2(5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
        System.out.println(minValue2(1, 1, 2, 2, 3, 3, 4, 4, 5, 5));
        System.out.println(minValue2(2, 1, 2, 1, 5, 3, 4, 4, 5, 3));
        System.out.println(minValue2(3, 1, 3, 1, 2, 5, 4, 5, 2, 4));
        System.out.println(minValue2(9, 8));
        System.out.println(minValue2(8, 9));

        int[] oddSumInts = {1, 2, 3, 4, 5, 6};
        int[] evenSumInts = {1, 2, 3, 4, 5, 7};

        List<Integer> oddSumIntegers = IntStream.of(oddSumInts).boxed().collect(Collectors.toList());
        List<Integer> evenSumIntegers = IntStream.of(evenSumInts).boxed().collect(Collectors.toList());
        System.out.println();
        System.out.println(Arrays.toString(oddSumInts) + " - сумма: " + Arrays.stream(oddSumInts).sum() + ", нечетная, удаляем все нечетные: " + oddOrEven(oddSumIntegers));
        System.out.println(Arrays.toString(evenSumInts) + " - сумма: " + Arrays.stream(evenSumInts).sum() + ", четная, удаляем все четные: " + oddOrEven(evenSumIntegers));
    }

    /*
        Реализовать метод через стрим int minValue(int[] values).
        Метод принимает массив цифр от 1 до 9,
        надо выбрать уникальные и вернуть минимально возможное число,
        составленное из этих уникальных цифр.
        Не использовать преобразование в строку и обратно.
        Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89.
     */
    public static int minValue(int... values) {
        AtomicInteger positionCount = new AtomicInteger(0);
        return IntStream.of(values)
                .distinct()
                .boxed()
                .sorted(Comparator.reverseOrder())
                .reduce(0, ((sum, item) -> {
                    int positionInIntNumber = positionCount.getAndIncrement();
                    return sum + item * tenPoweredTo(positionInIntNumber);
                }));
    }

    public static int minValue2(int... values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, ((sum, item) -> (sum * 10 + item)));
    }

    public static int tenPoweredTo(int factor) {
        int result = 1;
        for (int i = 0; i < factor; i++) {
            result = result * 10;
        }
        return result;
    }

    /*
        Реализовать метод List<Integer> oddOrEven(List<Integer> integers)
        если сумма всех чисел нечетная - удалить все нечетные,
        если четная - удалить все четные.
        Сложность алгоритма должна быть O(N).
        Optional - решение в один стрим.
     */
    public static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, Integer::sum);
        return integers.stream()
                .filter((item) -> sum % 2 != 0 ? (item % 2 == 0) : (item % 2 != 0))
                .collect(Collectors.toList());
    }
}


