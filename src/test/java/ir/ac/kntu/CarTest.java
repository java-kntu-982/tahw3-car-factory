package ir.ac.kntu;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author S.Shayan Daneshvar
 */
@SuppressWarnings("JavaReflectionMemberAccess")
@TestMethodOrder(OrderAnnotation.class)
public class CarTest {
    private static Class<Car> carClass;
    private static final List<Constructor<?>> constructors = new ArrayList<>();

    @BeforeAll
    public static void prepare() {
        carClass = Car.class;
        constructors.addAll(Arrays.asList(carClass.getDeclaredConstructors()));
    }

    @Test
    public void defaultConstructorTest() {
        Assertions.assertEquals(1, constructors
                .stream()
                .filter(x -> x.getParameterCount() == 0)
                .count(), "No Default Constructor Found!");
        System.err.println("Default Constructor Found");
    }

    @Test
    public void oneArgConstructorTest() {
        constructors.stream()
                .filter(x -> x.getParameterCount() == 1)
                .map(z -> z.getParameters()[0].getType().equals(String.class))
                .findFirst()
                .ifPresentOrElse(Assertions::assertTrue, Assertions::fail);
        System.err.println("One Args Constructor Found!");
    }

    @Test
    public void twoArgsConstructor() {
        constructors.stream()
                .filter(x -> x.getParameterCount() == 2)
                .map(z -> z.getParameters()[0].getType().equals(String.class)
                        && z.getParameters()[1].getType().equals(String.class))
                .findFirst()
                .ifPresentOrElse(Assertions::assertTrue, Assertions::fail);
        System.err.println("Two Args Constructor Found!");
    }

    @Test
    @Order(Integer.MAX_VALUE)
    public void allConstructorsFunctionalityTest() {
        final String hunter = "Hunter";
        final String blue = "Blue";
        final String white = "White";
        final String fride = "Fride";
        try {
            Car car = Car.class.getConstructor().newInstance();
            Assertions.assertEquals(fride, car.getName(), "Wrong name" +
                    " - default Constructor");
            Assertions.assertEquals(white, car.getColor(), "Wrong " +
                    "Color - default Constructor");
            Car car2 = Car.class.getConstructor(String.class)
                    .newInstance(hunter);
            Assertions.assertEquals(hunter, car2.getName(), "Wrong " +
                    "name - 1 arg Constructor");
            Assertions.assertEquals(white, car2.getColor(), "Wrong " +
                    "Color - 1 arg Constructor");
            Car car1 = Car.class.getConstructor(String.class, String.class)
                    .newInstance(hunter, blue);
            Assertions.assertEquals(hunter, car1.getName(), "Wrong " +
                    "name - 2 args Constructor");
            Assertions.assertEquals(blue, car1.getColor(), "Wrong " +
                    "Color - 2 Args Constructor");
        } catch (Exception ex) {
            ex.printStackTrace();
            Assertions.fail("Wrong Constructors");
        }
    }

    @Test
    public void testPackage() {
        Assertions.assertEquals("ir.ac.kntu",
                carClass.getPackage().getName());
    }
}
