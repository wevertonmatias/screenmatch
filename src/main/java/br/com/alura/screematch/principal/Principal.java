package br.com.alura.screematch.principal;

import br.com.alura.screematch.model.DadosEpisodio;
import br.com.alura.screematch.model.DadosSerie;
import br.com.alura.screematch.model.DadosTemporada;
import br.com.alura.screematch.model.Episodio;
import br.com.alura.screematch.service.ConsumoApi;
import br.com.alura.screematch.service.ConverteDados;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=b2b2882f";

    public void exibeMenu() {
        System.out.println("Digite o nome da serie: ");
        var nomeSerie = leitura.nextLine();

        String endereco = this.ENDERECO + nomeSerie.replace(" ", "+") + this.API_KEY;
        var json = this.consumoApi.obterDados(endereco);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= Integer.parseInt(dados.totalTemporadas()); i++) {
            String enderecoSerie =
                    this.ENDERECO +
                    nomeSerie.replace(" ", "+") +
                    "&season=%s".formatted(i) +
                    this.API_KEY;

			json = this.consumoApi.obterDados(enderecoSerie);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

//        for (DadosTemporada dadosTemporada : temporadas) {
//            for (DadosEpisodio episodio : dadosTemporada.episodios()) {
//                System.out.println("Temporada = %d | Episodio = %s".formatted(dadosTemporada.numero(), episodio.titulo()));
//            }
//        }
//        temporadas.forEach(
//            t -> t.episodios().forEach(
//                e -> System.out.println("Temporada: %d | Episodio: %s".formatted(t.numero(), e.titulo()))
//                )
//        );

        //temporadas.forEach(t -> t.episodios().forEach(System.out::println));

        List<DadosEpisodio> dadosEpisodio = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());
//                .toList();
//        dadosEpisodio.add(new DadosEpisodio("teste", 5, "10", "2020-01-01"));
//        dadosEpisodio.forEach(System.out::println);

        System.out.println("Top 5 Episodios:");
        dadosEpisodio.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5);
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)
                        )
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano voce deseja ve os episodios da serie? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(System.out::println);
    }
}
