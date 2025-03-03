package br.com.alura.screematch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
