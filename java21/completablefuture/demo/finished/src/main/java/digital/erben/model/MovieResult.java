package digital.erben.model;

public record MovieResult(
    PrimaryImage primaryImage,
    TitleType titleType,
    TitleText titleText,
    TitleText originalTitleText,
    YearRange releaseYear,
    ReleaseDate releaseDate
) {}
