package utils;
import com.github.javafaker.Faker;
import static constants.Constants.*;

public class DataGeneratorHelper {
    public static Faker faker = new Faker();
    public static  String getRandomCurrency() {
        return faker.options().option(GEL, USD, EUR, GBP);
    }
    public static  int getRandomAmount() {
        return faker.number().numberBetween(100, 5000);
    }
}
