package digital.erben;

import digital.erben.model.MovieResult;
import digital.erben.movies.MovieRating;
import digital.erben.movies.MovieRatingsDataset;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class FunWithCompletableFuture {

    private static final MovieRatingsDataset DATASET = MovieRatingsDataset.getInstance();
    private static final MovieDetailsApi API = MovieDetailsApi.instance();

    public static void main(String[] args) {
        // Hier werden zwei Einträge aus dem Dataset geladen, der Name entnommen,
        // und schließlich die MoviesApi aufgerufen.
        // Die Api gibt für jeden Titel mehrere Treffer zurück
        // Da der Java HttpClient ein CompletableFuture zurückgibt, werden wir damit weiterarbeiten.
        List<CompletableFuture<List<MovieResult>>> ft = DATASET
            .load()
            .limit(2)
            .map(MovieRating::name)
            .map(API::loadDetailsForMovie)
            .toList();

        // Wir wollen zuerst waren, bis alle Ergebnisse für alle API-Aufrufe da sind
        CompletableFuture
            .allOf(ft.toArray(CompletableFuture[]::new))
            // Leider gibt es keine elegante Möglichkeit, das Ergebnis aller CompletableFutures direkt zu nutzen.
            // man muss sich behelfen, in dem man die ursprüngliche Liste der Futures oben nimmt
            // und mit join das Ergebnis extrahiert.
            // Wir müssen danach aus dem Stream<List<Result>> ein Stream<Result> machen mittels flatMap
            .thenApply(ignored ->
                ft
                    .stream()
                    .map(CompletableFuture::join)
                    .flatMap(Collection::stream)
                    .toList()
            )
            // und endlich können wir die Ergebnisse rendern
            .thenAccept(AsciiTableRenderer::render)
            .join();
    }
}
