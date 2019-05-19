package br.newtonpaiva.dados.estrutura.model;

import java.util.Stack;

public class Jogador {
	private String nome;
	private int pontos;
	private Stack<Integer> pilha;

	public Jogador(String nome) {
		this.nome = nome;
		pilha = new Stack<Integer>();
	}

	public Jogador() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Stack<Integer> getPilha() {
		return pilha;
	}

}
