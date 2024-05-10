package br.com.everaldoboscatto.screenmatch.controller;
import br.com.everaldoboscatto.screenmatch.dto.SerieDTO;
import br.com.everaldoboscatto.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
// Indicar que todas as requisiçoes vão partir de series, elmina a necessidade de repetição em todos os métodos
@RequestMapping("/series")

public class SerieController {

    // Injetar a classe SerieService
    @Autowired
    private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }
    @GetMapping("/lancamentos")
    public List<SerieDTO>obterLancamentos() {
        return servico.obterLancamntos();
    }
}
