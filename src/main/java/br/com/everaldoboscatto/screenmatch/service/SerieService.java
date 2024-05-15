package br.com.everaldoboscatto.screenmatch.service;

import br.com.everaldoboscatto.screenmatch.dto.EpisodioDTO;
import br.com.everaldoboscatto.screenmatch.dto.SerieDTO;
import br.com.everaldoboscatto.screenmatch.model.Categoria;
import br.com.everaldoboscatto.screenmatch.model.Episodio;
import br.com.everaldoboscatto.screenmatch.model.Serie;
import br.com.everaldoboscatto.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // O @Service indica ao Spring que é uma classe que ele vai gerenciar
public class SerieService {

    // Injetar dependência da SerieRepository
    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());

    }
    public List<SerieDTO> obterTop5Series() {

        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    // Obter uma lista de objetos do tipo série e transformar numa lista de objetos tipo sérieDTO
    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO( s.getId(),
                        s.getTitulo(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.getGenero(),
                        s.getAtores(),
                        s.getPoster(),
                        s.getSinopse()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        //return converteDados(repositorio.findTop5ByOrderByEpisodiosDataLancamentoDesc());
        // Refatorar
        return converteDados(repositorio.encontrarEpisodiosMaisRecentes());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),
                    s.getTitulo(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.getGenero(),
                    s.getAtores(),
                    s.getPoster(),
                    s.getSinopse());
        }
        return null;
    }
    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(
                            e.getNumeroTemporada(),
                            e.getNumeroEpisodio(),
                            e.getTituloEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }
    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        return repositorio.obterEpisodiosPorTemporada(id, numero)
                .stream()
                .map(e -> new EpisodioDTO(e.getNumeroTemporada(),
                        e.getNumeroEpisodio(),
                        e.getTituloEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterSeriesPorCategoria(String nomeGenero) {
        Categoria categoria = Categoria.fromStringPortugues(nomeGenero);
        return converteDados(repositorio.findByGenero(categoria));

    }

    public List<EpisodioDTO> obterTopEpisodios(Long id) {
        var serie = repositorio.findById(id).get();
        return repositorio.topEpisodiosPorSerie(serie)
                .stream()
                .map(e -> new EpisodioDTO(
                        e.getNumeroTemporada(),
                        e.getNumeroEpisodio(),
                        e.getTituloEpisodio()))
                .collect(Collectors.toList());
    }
}
