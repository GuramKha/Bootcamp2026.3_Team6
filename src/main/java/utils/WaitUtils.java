package utils;
import java.util.function.Supplier;

public class WaitUtils {
        public static <T> T waitFor(Supplier<T> supplier, java.util.function.Predicate<T> condition, int maxAttempts, long waitMillis) {
        T result = null;
        for (int i = 0; i < maxAttempts; i++) {
            result = supplier.get();
            if (condition.test(result)) {
                return result;
            }
            try {
                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
