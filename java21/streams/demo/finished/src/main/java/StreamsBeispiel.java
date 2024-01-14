import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Person(String vorname, int alter, Geschlecht geschlecht, List<String> nicknames) {}

enum Geschlecht {
    MAENNLICH,
    WEIBLICH,
    DIVERS,
}

@SuppressWarnings("ConstantValue")
public class StreamsBeispiel {

    static List<Person> PERSONEN = Arrays.asList(
        new Person("Max", 25, Geschlecht.MAENNLICH, Collections.emptyList()),
        new Person("Anna", 30, Geschlecht.WEIBLICH, Collections.emptyList()),
        new Person("Sara", 20, Geschlecht.WEIBLICH, Collections.emptyList()),
        new Person("Maria", 20, Geschlecht.WEIBLICH, Collections.emptyList()),
        new Person("Tom", 35, Geschlecht.DIVERS, Collections.emptyList()),
        new Person("Ben", 40, Geschlecht.MAENNLICH, Collections.emptyList())
    );

    public static void main(String[] args) {
        filterBeispiel();

        sortBeispiel();

        mapBeispiel();

        reduceBeispiel();

        groupByBeispiel();

        distinctBeispiel();

        anyAllMatch();

        statefulStateless();

        parallel();

        System.out.println(Optional.of("val").isPresent());
        // true
        System.out.println(Optional.empty().isPresent());
        // false
        Optional.of("val").ifPresent(System.out::println);
        // val
        System.out.println(Optional.ofNullable(null).isPresent());
        // false

    }

    private static void parallel() {
        IntStream
            .range(0, Integer.MAX_VALUE)
            .parallel()
            .forEach(l -> {
                try {
                    System.out.print(l + ",");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    private static void statefulStateless() {
        var sortedDistinctVornamen = PERSONEN
            .stream()
            .map(Person::vorname) // stateless
            .distinct() // stateful
            .sorted() // stateful
            .toList();
    }

    private static void anyAllMatch() {
        Optional<Person> any = PERSONEN.stream().filter(p -> p.alter() == 20).findAny();
        System.out.println("findAny: " + any);

        Optional<Person> anyNotFound = PERSONEN
            .stream()
            .filter(p -> p.alter() == 0)
            .findAny();
        System.out.println("findAnyNotFound: " + anyNotFound);

        System.out.println(
            "any older than 20: " + PERSONEN.stream().anyMatch(p -> p.alter() > 20)
        );

        System.out.println(
            "all older than 50: " + PERSONEN.stream().allMatch(p -> p.alter() > 50)
        );
    }

    private static void distinctBeispiel() {
        // Beispiel Distinct
        List<Integer> distinctAlter = PERSONEN
            .stream()
            .map(Person::alter)
            .distinct()
            .toList();
        System.out.println("Distinct Alter: " + distinctAlter);
    }

    private static void groupByBeispiel() {
        // Beispiel Gruppieren
        Map<Geschlecht, List<Person>> gruppiertNachGeschlecht = PERSONEN
            .stream()
            .collect(Collectors.groupingBy(Person::geschlecht));

        System.out.println("gruppiertNachGeschlecht: " + gruppiertNachGeschlecht);
    }

    private static void reduceBeispiel() {
        // Beispiel Reduzieren
        Optional<Double> durchschnittsalter = PERSONEN
            .stream()
            .map(p -> (double) p.alter())
            .reduce(Double::sum)
            .map(sum -> sum / PERSONEN.size());

        System.out.println("durchschnittsalter: " + durchschnittsalter.orElse(0.0));
    }

    private static void mapBeispiel() {
        // Beispiel Mappen
        List<String> namenListe = PERSONEN.stream().map(Person::vorname).toList();

        System.out.println("namenListe: " + namenListe);
    }

    private static void sortBeispiel() {
        // Beispiel Sortieren
        List<Person> nachAlterSortiert = PERSONEN
            .stream()
            .sorted(Comparator.comparingInt(Person::alter)) // gibt es auch reversed
            .toList();

        System.out.println("nachAlterSortiert: " + nachAlterSortiert);
    }

    private static void filterBeispiel() {
        // Beispiel Filtern
        List<Person> ueber30 = PERSONEN.stream().filter(p -> p.alter() > 30).toList();

        System.out.println("ueber30: " + ueber30);
    }
}
