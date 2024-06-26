package br.com.everaldoboscatto.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// Fazer o mapeamento de Json para a classe
// Notação para ignorar dados não solicitados
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo, // Dizendo que Title é um titulo
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse) {
}
// O Jackson tem a notação  JsonAlias e JsonProperty, ver no material  de apoio.
// Próximo passo:
// Converter os dados para o DadosSerie