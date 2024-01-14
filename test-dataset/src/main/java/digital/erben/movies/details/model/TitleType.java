package digital.erben.movies.details.model;

import java.util.List;

public record TitleType(
    DisplayableTitleTypeProperty displayableProperty,
    String text,
    String id,
    boolean isSeries,
    boolean isEpisode,
    List<TitleTypeCategory> categories,
    boolean canHaveEpisodes,
    String __typename
) {}
