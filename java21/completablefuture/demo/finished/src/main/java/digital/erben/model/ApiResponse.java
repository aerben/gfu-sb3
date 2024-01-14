package digital.erben.model;

import java.util.List;

public record ApiResponse(
    int page,
    String next,
    int entries,
    List<MovieResult> results
) {}
