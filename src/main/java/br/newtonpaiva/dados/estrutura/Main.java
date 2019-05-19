package br.newtonpaiva.dados.estrutura;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import br.newtonpaiva.dados.estrutura.model.Jogador;

public class Main {
	private static Random random;
	private static InputStreamReader stream;
	private static BufferedReader reader;

	private static final int LIMITE_PONTOS = 21;
	private static final int RANGE_MAXIMO = 13;
	private static final int PONTOS_PENALIZACAO = 5;

	private static final int OBTER_CARTA = 1;
	private static final int FINALIZAR = 2;

	private static final String NOME_PRIMEIRO_JOGADOR = "1";
	private static final String NOME_SEGUNDO_JOGADOR = "2";

	private static final String OPCAO_SIM = "S";
	private static final String OPCAO_NAO = "N";

	private static final String STRING_JOGADOR_GANHADOR = "Jogador %s ganhador!%n";

	static {
		random = new Random();
		stream = new InputStreamReader(System.in);
		reader = new BufferedReader(stream);
	}

	public static void main(String[] args) throws IOException {
		Jogador j1 = new Jogador(NOME_PRIMEIRO_JOGADOR);
		Jogador j2 = new Jogador(NOME_SEGUNDO_JOGADOR);

		jogar(j1);
		if (j1.getPontos() > LIMITE_PONTOS) {
			System.out.printf(STRING_JOGADOR_GANHADOR, j2.getNome());
		} else {
			jogar(j2);
			if (j2.getPontos() > LIMITE_PONTOS) {
				System.out.printf(STRING_JOGADOR_GANHADOR, j1.getNome());
				exibirResultado(j1);
			} else {
				Jogador ganhador = j1.getPontos() > j2.getPontos() ? j1 : j2;
				exibirResultado(ganhador);
			}
		}
	}

	private static void jogar(Jogador jogador) throws IOException {
		int opcao = 0;
		while (opcao != FINALIZAR) {
			opcao = getOpcaoObterCartaOuFinalizar(jogador);
			switch (opcao) {
			case OBTER_CARTA:
				int numero = getRandom();
				jogador.getPilha().push(numero);
				jogador.setPontos(jogador.getPontos() + numero);
				if (jogador.getPontos() > LIMITE_PONTOS) {
					solicitarDescartarOuFinalizar(jogador);
					opcao = FINALIZAR;
				}
				break;
			case FINALIZAR:
				break;
			default:
				System.out.println("Opção inválida!\n");
				break;
			}
		}
	}

	private static int getRandom() {
		return random.nextInt(RANGE_MAXIMO) + 1;
	}

	private static int getOpcaoObterCartaOuFinalizar(Jogador jogador) throws IOException {
		StringBuilder builder = new StringBuilder()
				.append("Jogador %s:%n")
				.append("%d - Obter carta%n")
				.append("%d - Finalizar%n");
		System.out.printf(builder.toString(), jogador.getNome(), OBTER_CARTA, FINALIZAR);
		return Integer.parseInt(reader.readLine());
	}

	private static void solicitarDescartarOuFinalizar(Jogador jogador) throws IOException {
		String resposta = "";
		while (!resposta.equalsIgnoreCase(OPCAO_SIM) && !resposta.equalsIgnoreCase(OPCAO_NAO)) {
			resposta = getOpcaoDescartarOuFinalizar();
			if (resposta.equalsIgnoreCase(OPCAO_SIM)) {
				int ultima = jogador.getPilha().pop();
				jogador.setPontos(jogador.getPontos() - (ultima + PONTOS_PENALIZACAO));
			}
		}
	}

	private static String getOpcaoDescartarOuFinalizar() throws IOException {
		StringBuilder builder = new StringBuilder()
				.append("Mais de %s pontos!%n")
				.append("Deseja descartar a última carta comprada (%s/%s)?%n")
				.append("(penalização de %s pontos)%n");
		System.out.printf(builder.toString(), LIMITE_PONTOS, OPCAO_SIM, OPCAO_NAO, PONTOS_PENALIZACAO);
		return reader.readLine();
	}

	private static void exibirResultado(Jogador jogador) {
		System.out.printf(STRING_JOGADOR_GANHADOR, jogador.getNome());
		System.out.println("Cartas:");
		while (!jogador.getPilha().isEmpty()) {
			System.out.println(jogador.getPilha().pop());
		}
	}

}
