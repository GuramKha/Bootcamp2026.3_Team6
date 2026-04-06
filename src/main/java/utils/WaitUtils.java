package utils;
import api_dir.steps.MoneyTransferSteps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.function.Supplier;


public class WaitUtils {
    public static final Logger logger = LogManager.getLogger(MoneyTransferSteps.class);
    public static <T> T waitFor(Supplier<T> supplier, java.util.function.Predicate<T> condition, int maxAttempts, long waitMillis) {
        T result = null;
        for (int i = 0; i < maxAttempts; i++) {
            logger.info("Attempt: {}", i + 1);
            result = supplier.get();
            if (condition.test(result)) {
                logger.info("Success on attempt: {}", i + 1);
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
