package br.com.alura.screematch.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {

    private Integer numeroTemporada;
    private String titulo;
    private Integer numero;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodio episodio) {
        this.numeroTemporada = temporada;
        this.titulo = episodio.titulo();
        this.numero = episodio.numero();
        try {
            this.avaliacao = Double.valueOf(episodio.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(episodio.dataLancamento());
        }catch (DateTimeParseException e) {
            this.dataLancamento = null;
        }
    }

    @Override
    public String toString() {
        return "Temporada: %s | Episodio: %s | Avaliação %s | Data de Lançamento %s | Titulo: %s"
                .formatted(this.numeroTemporada, this.numero, this.avaliacao, this.dataLancamento, this.titulo);
    }

    public Integer getNumeroTemporada() {
        return numeroTemporada;
    }

    public void setNumeroTemporada(Integer numeroTemporada) {
        this.numeroTemporada = numeroTemporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
}
