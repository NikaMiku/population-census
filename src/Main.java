import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> lastNames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    lastNames.get(new Random().nextInt(lastNames.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        // Фильтр несовершеннолетних
        long count = persons.stream()
                .filter(value -> value.age < 18)
                .count();
        System.out.println(count);
        // Фильтр призывников по фамилии
        List<String> conscript = persons.stream()
                .filter(value -> value.sex == Sex.MAN && value.age >= 18 && value.age <= 27)
                .map(Person::getLastName)
                .collect(Collectors.toList());
        System.out.println(conscript);
        //Сортировка работоспособных
        List<String> worked = persons.stream()
//                .filter(value -> value.education == Education.HIGHER && (value.sex == Sex.MAN && value.age >= 18 && value.age <= 65) || (value.sex == Sex.WOMAN && value.age >= 18 && value.age <= 60))
                .filter(person -> person.getEducation().equals(Education.HIGHER))
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getSex().equals(Sex.MAN) ? person.getAge() <= 65 : person.getAge() <= 60)
                .map(Person::getLastName)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        System.out.println(worked);
    }

    public enum Sex {
        MAN,
        WOMAN
    }

    public enum Education {
        ELEMENTARY,
        SECONDARY,
        FURTHER,
        HIGHER
    }

    static class Person {
        private String name;
        private String lastName;
        private Integer age;
        private Sex sex;
        private Education education;

        public Person(String name, String lastName, Integer age, Sex sex, Education education) {
            this.name = name;
            this.lastName = lastName;
            this.age = age;
            this.sex = sex;
            this.education = education;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public Integer getAge() {
            return age;
        }

        public Sex getSex() {
            return sex;
        }
        public Education getEducation() {
            return education;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", family='" + lastName + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", education=" + education +
                    '}';
        }
    }
}
