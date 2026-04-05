package utils;
<<<<<<< HEAD
import io.restassured.response.Response;
import java.util.function.Supplier;

public class WaitUtils {
    public static Response waitForNonEmptyResponse(
            Supplier<Response> supplier,
            long maxWaitMillis,
            long pollMillis) {

        long waitUntil = System.currentTimeMillis() + maxWaitMillis;
        Response response;

        do {
            response = supplier.get();
            String body = response.getBody().asString();

            if (body != null && !body.equals("[]") && !body.isBlank()) {
                return response;
            }

            try {
                Thread.sleep(pollMillis);
=======
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
>>>>>>> ab219e7d007fb592df4446232b45517321056ed8
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
<<<<<<< HEAD

        } while (System.currentTimeMillis() < waitUntil);

        return response;
=======
        }
        return result;
>>>>>>> ab219e7d007fb592df4446232b45517321056ed8
    }
}
