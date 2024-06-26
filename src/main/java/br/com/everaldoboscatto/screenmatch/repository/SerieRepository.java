package br.com.everaldoboscatto.screenmatch.repository;

import br.com.everaldoboscatto.screenmatch.model.Categoria;
import br.com.everaldoboscatto.screenmatch.model.Episodio;
import br.com.everaldoboscatto.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    // Bucar uma série através de um trecho do seu título
    // findBy -> significa encontrar por...
    // Optinal -> indica que pode ou não encontrar uma série com o nome indicado
    // Containing -> se existir, se encontrar
    // IgnoreCase -> indica que deve ignorar maiúsculas e minúsculas
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    // Buscar séries pelo nome do ator e pela avaliação informado
    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    // Buscar as 5 séries mais bem avaliadas
    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    // Buscar séries por categoria/gênero
    List<Serie> findByGenero(Categoria categoria);

    // Buscar séries pelo número total de temporadas - Derived Query
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int totalTemporadas, double avaliacao);

    // Buscar séries pelo número total de temporadas - Query JPQL
    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAValiacao(int totalTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.tituloEpisodio ILIKE %:trechoDoNomeEpisodio%")
    List<Episodio> episodiosPorTrechoDoNome(String trechoDoNomeEpisodio);


    // Selecionar a serie, juntar com episodios e ordenar a avaliacao do episodio em ordem decrescente,
    // limitando aos 5 primeiros episodios
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);

    // List<Serie> findTop5ByOrderByEpisodiosDataLancamentoDesc();
    // Derived Query não atende os requisitos, portanto, Refatorar para JPQL
    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();

    // Selecionar a série e os episódios,
    // pegar os episódios da série do respectivo id e a temporada do respectivo número
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.numeroTemporada = :numero")
    List<Episodio> obterEpisodiosPorTemporada(Long id, Long numero);
}

