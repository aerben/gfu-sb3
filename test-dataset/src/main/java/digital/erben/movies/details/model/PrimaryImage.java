package digital.erben.movies.details.model;

public record PrimaryImage(
    String id,
    int width,
    int height,
    String url,
    Markdown caption,
    String __typename
) {}
