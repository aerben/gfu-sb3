

List<String> names = Arrays.asList("Paul", "Peter", "Markus");
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compareTo(String a, String b){
        return a.compareTo(b);
    }
});


Arrays.asList("Paul", "Peter", "Markus").sort((x, y) -> x.compareTo(y));


() -> System.out.println("Hi")

s -> System.out.println(s)

(x, y) -> {
    System.out.println(x);
    System.out.println(y);
};

(Integer x, Integer y) -> x.compareTo(y)



List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

List<Integer> evenNumbers = new ArrayList<>();

for (Integer number : numbers) {
    if (number % 2 == 0) {
        evenNumbers.add(number);
    }
}


Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).filter( number -> number % 2 == 0).toList();
