
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
        Map<String, Integer> wordsToCount = new HashMap<>();
        // Можно было использовать  LinkedHashMap для ускорения итерирования, но будет затратнее по памяти

        AbstractQueue<Map.Entry<String, Integer>> mostFreqWords = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        AbstractQueue<Map.Entry<String, Integer>> leastFreqWords = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        // Для быстрого отбрасывания лишних элементов

        new WordParser(WAR_AND_PEACE_FILE_PATH) // O(n) где n кол-во слов
                .forEachWord(word -> {
                    if (wordsToCount.containsKey(word))
                        wordsToCount.put(word, wordsToCount.get(word) + 1);
                    else
                        wordsToCount.put(word, 1);
                });
        // O(n), где n кол-во слов

        for (Map.Entry<String, Integer> wordToCount: wordsToCount.entrySet()) {
            mostFreqWords.add(wordToCount);
            if (mostFreqWords.size() > 10)
                mostFreqWords.poll();
            leastFreqWords.add(wordToCount);
            if (leastFreqWords.size() > 10)
                leastFreqWords.poll();
        }
        //O(n + s) для перебора hashMap, size -> O(1), add(poll) - O(log(10) ~ 3) т.к. максимум 10 слов
        // итого -> O(n + s + 3 * 4 + 1 * 2) -> O(n + s + 14)

        System.out.println("TOP 10 наиболее используемых слов:");
        mostFreqWords.stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .forEach((entry) -> System.out.printf("%s - %d раз(а)\n", entry.getKey(), entry.getValue()));
        //(O(10log10) + 10) ~43 т.к. в очереди максимум 10 слов, сложность сортировки nlogn + итерация по элементам

        System.out.println("LAST 10 наименее используемых:");
        leastFreqWords.stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .forEach((entry) -> System.out.printf("%s - %d раз(а)\n", entry.getKey(), entry.getValue()));
        //(O(10log10) + 10) ~43 т.к. в очереди максимум 10 слов, сложность сортировки nlogn + итерация по элементам

        //Итоговая сложность O(n + n + s + 14 + 43 + 43) -> O(2n + S + 100) или O(n + s)
    }
}
