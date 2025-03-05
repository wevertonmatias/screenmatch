package br.com.alura.screematch.principal;

import br.com.alura.screematch.model.DadosEpisodio;
import br.com.alura.screematch.model.DadosSerie;
import br.com.alura.screematch.model.DadosTemporada;
import br.com.alura.screematch.service.ConsumoApi;
import br.com.alura.screematch.service.ConverteDados;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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

        temporadas.forEach(t -> t.episodios().forEach(System.out::println));

    }
}
