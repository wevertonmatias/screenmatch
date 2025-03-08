package br.com.alura.screematch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
    @JsonAlias("Title") String nome,
    @JsonAlias("totalSeasons") String totalTemporadas,
    @JsonAlias("imdbRating") String avaliacao
) {
}
