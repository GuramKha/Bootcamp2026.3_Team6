package utils;

import net.datafaker.Faker;
import static constants.Constants.*;
import static constants.Constants.GBP;

public class FakerSingleton {
    private static final ThreadLocal<FakerSingleton> instance =
            ThreadLocal.withInitial(FakerSingleton::new);

    private Faker faker;

    private FakerSingleton() {
        faker = new Faker();
    }

    public static FakerSingleton getInstance() {
        return instance.get();
    }

    public String getRandomNumber(int min, int max) {
        return String.valueOf(faker.number().numberBetween(min, max + 1));
    }

    public  String getRandomCurrency() {
        return faker.options().option(GEL, USD, EUR, GBP);
    }

    public  int getRandomAmount() {
        return faker.number().numberBetween(100, 5000);
    }
}