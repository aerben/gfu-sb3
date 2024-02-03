package digital.erben.movies;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public final class MovieRatingsDataset {

    private static final MovieRatingsDataset INSTANCE = new MovieRatingsDataset();

    private MovieRatingsDataset() {}

    public static MovieRatingsDataset getInstance() {
        return INSTANCE;
    }

    public Stream<MovieRating> load() {
        try {
            String[] HEADERS = {
                "id",
                "name",
                "rating",
                "votes",
                "runtime",
                "genre",
                "description",
            };
            CSVFormat csvFormat = CSVFormat.DEFAULT
                .builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();
            InputStream resourceAsStream =
                MovieRatingsDataset.class.getClassLoader()
                    .getResourceAsStream("moviesdataset_2023.csv");
            InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(resourceAsStream)
            );
            Iterable<CSVRecord> records = csvFormat.parse(reader);
            return StreamSupport
                .stream(records.spliterator(), false)
                .map(this::parseRecord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MovieRating parseRecord(CSVRecord record) {
        float rating = safeParseFloat(record.get("rating"));
        int id = safeParseInt(record.get("id"));
        int votes = safeParseInt(record.get("votes").replace(",", ""));
        int runtime = safeParseInt(record.get("runtime").replace(" min", ""));
        return new MovieRating(
            id,
            record.get("name"),
            rating,
            votes,
            Duration.ofMinutes(runtime),
            Set
                .of(record.get("genre").replaceAll("[\\[\\]\"']", "").split(","))
                .stream()
                .map(String::strip)
                .collect(Collectors.toSet()),
            record.get("description").replaceAll("[\\[\\]\"']", "")
        );
    }

    private float safeParseFloat(String value) {
        float number;
        try {
            number = Float.parseFloat(value);
        } catch (NumberFormatException ignored) {
            number = (float) 0;
        }
        return number;
    }

    private int safeParseInt(String value) {
        int number;
        try {
            number = Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
            number = 0;
        }
        return number;
    }
}
