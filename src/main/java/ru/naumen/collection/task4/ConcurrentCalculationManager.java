package ru.naumen.collection.task4;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Класс управления расчётами
 */
    public class ConcurrentCalculationManager<T> {

            private final BlockingQueue<Future<T>> tasks = new LinkedBlockingQueue<>();
            private final ExecutorService executorService = Executors.newFixedThreadPool(4);

        /**
         * Добавить задачу на параллельное вычисление
         */
        public void addTask(Supplier<T> task) {
            Future<T> future = executorService.submit(task::get);
            tasks.add(future);
        }

        /**
         * Получить результат вычисления.
         * Возвращает результаты в том порядке, в котором добавлялись задачи.
         */
        public T getResult() {
            try {
                return tasks.take().get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }