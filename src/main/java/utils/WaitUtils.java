package utils;
import io.restassured.response.Response;
import java.util.function.Supplier;

public class WaitUtils {
//    public static Response waitForNonEmptyResponse(
//            Supplier<Response> supplier,
//            long maxWaitMillis,
//            long pollMillis) {
//
//        long waitUntil = System.currentTimeMillis() + maxWaitMillis;
//        Response response;

//        do {
//            response = supplier.get();
//            String body = response.getBody().asString();
//            if (body != null && !body.equals("[]") && !body.isBlank()) {
//                return response;
//            }
//
//            try {
//                Thread.sleep(pollMillis);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                throw new RuntimeException(e);
//            }
//
//        } while (System.currentTimeMillis() < waitUntil);
//        return response;
//    }
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
