
package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */

public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Map<String, Integer> wordsToCount = new LinkedHashMap<>();
        // LinkedHashMap для ускорения итерирования, Map для хранения и быстрого доступа к количеству повторений у слова

        Queue<Map.Entry<String, Integer>> mostFreqWords = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        Queue<Map.Entry<String, Integer>> leastFreqWords = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        // Для быстрого отбрасывания лишних элементов

        new WordParser(WAR_AND_PEACE_FILE_PATH) // O(n) где n кол-во слов
                .forEachWord(word -> wordsToCount.merge(word, 1, Integer::sum));
        // O(n), где n кол-во слов

        for (Map.Entry<String, Integer> wordToCount: wordsToCount.entrySet()) { //O(n + s) для перебора hashMap (n - кол-во слов в hashmap, s - capacity у hashmap)
            mostFreqWords.add(wordToCount); // O(log10), т.к. максимум 10 слов в очереди -> ~3
            if (mostFreqWords.size() > 10)  // O(1)
                mostFreqWords.poll(); // O(log10), т.к. максимум 10 слов в очереди -> ~3
            leastFreqWords.add(wordToCount); // O(log10), т.к. максимум 10 слов в очереди -> ~3
            if (leastFreqWords.size() > 10) //O(1)
                leastFreqWords.poll(); // O(log10), т.к. максимум 10 слов в очереди -> ~3
        }
        // итого -> O((n+s) * 4) = O(4n + 4s) или O(n)

        System.out.println("TOP 10 наиболее используемых слов:");
        getOrderedQueue(mostFreqWords)
                .forEach((entry) -> System.out.printf("%s - %d раз(а)\n", entry.getKey(), entry.getValue()));
        //(O(10log10) + 10) ~43 т.к. 10log10 у функции getOrderedQueue + 10 на итерацию или O(1)
        System.out.println("LAST 10 наименее используемых:");
        getOrderedQueue(leastFreqWords)
                .forEach((entry) -> System.out.printf("%s - %d раз(а)\n", entry.getKey(), entry.getValue()));
        //(O(10log10) + 10) ~43 т.к. 10log10 у функции getOrderedQueue + 10 на итерацию или O(1)

        //Итоговая сложность O(n + 4n + 4s  + 43 + 43) -> O(5n + 4S + 86) или O(n)
    }

    private static List<Map.Entry<String, Integer>> getOrderedQueue(Queue<Map.Entry<String, Integer>> queue) {
        List<Map.Entry<String, Integer>> result = new LinkedList<>();
        while (!queue.isEmpty()) {
            result.addFirst(queue.poll());
        }
        return result;
        // 10log10 т.к. isEmpty и addFirst - O(1), у poll - O(logn), где n - кол-во элементов (max 10), итераций так же максимум 10
    }
}
