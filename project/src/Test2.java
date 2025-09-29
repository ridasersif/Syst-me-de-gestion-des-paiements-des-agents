import java.util.*;
import java.util.stream.Stream;

public class Test2 {
    record Car(String type, String make, String model, Integer engineCapacity) {}

    public static void main(String[] args) {
        List<Car> cars = List.of(
                new Car("SUV", "Toyota", "RAV4", 2500),
                new Car("Sedan", "Honda", "Accord", 2000),
                new Car("Hatchback", "Volkswagen", "Golf", 1600),
                new Car("Coupe", "BMW", "M4", 3000),
                new Car("Pickup", "Ford", "F-150", 3500),
                new Car("Sedan", "Mercedes-Benz", "C-Class", 2200),
                new Car("SUV", "Hyundai", "Tucson", 2000),
                new Car("Electric", "Tesla", "Model 3", 0),
                new Car("Sports", "Ferrari", "488 GTB", 3900),
                new Car("Crossover", "Nissan", "Qashqai", 1800)
        );

        List<Car> sedanCars = cars.stream().filter(car -> car.type.equals("Sedan")).toList();
        List<String> carMakeList = cars.stream().map(car -> car.make).toList();
        List<String> carMakeModelList = cars.stream().flatMap(car ->Stream.of(car.make,car.model)).toList();

        carMakeModelList.forEach(System.out::println);
    }
}
