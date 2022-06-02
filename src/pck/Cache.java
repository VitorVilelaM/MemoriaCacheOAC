package pck;

import java.util.ArrayList;

public class Cache {
	private int tamanhoCache;
	private int tamanhoMemoria;
	private int palavra;
	private int nLinhas;
	private int logLinha;

	public Cache(int tamMemoria, int tamCache, int pal, int l) {
		this.tamanhoMemoria = tamMemoria;
		this.tamanhoCache = tamCache;
		this.palavra = pal;
		this.nLinhas = l;
		this.logLinha = (int) (Math.log(nLinhas) / Math.log(2));
	}

	public void Direto() {

		int tag = (int) (Math.log(tamanhoMemoria / palavra) / Math.log(2)) + 1 - logLinha - 2;
		
		String[] posicoes = new String[nLinhas];
		String valorTag;
		String valorLinha;
		int endereco = 0;
		int lCache, count = 0, acerto = 0;
		double total;

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);
			
			endereco = tag+logLinha+2;
			
			int bin[] = intToBinary(acesso, endereco);
			
			String stringBin = intToBinaryString(acesso, endereco);

			valorTag = stringBin.substring(0, tag);
			valorLinha = stringBin.substring(tag, logLinha+tag);
			
			System.out.println(valorLinha);
			
			lCache = Integer.parseInt(valorLinha, 2);
			
			if (posicoes[lCache] == null) {
				posicoes[lCache] = valorTag;
	
			} else if (posicoes[lCache].equals(valorTag)) {
				acerto++;
			} else {
				posicoes[lCache] = valorTag;
			}
			count++;
		}
		total = (acerto*100)/count;
		System.out.println("Acessos: " + count);
		System.out.println("Precisao: " + total + "%");
		System.out.println("HitCache: " + acerto);
	}

	public void Associativo() {
		int tag = tamanhoMemoria - 2;
		String[] posicoes = new String[nLinhas];
		String valorTag;
		int bloco, lCache, count = 0, acerto = 0;
		double total;

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);
			int bin[] = intToBinary(acesso, 20);
			String stringBin = intToBinaryString(acesso, 20);

			valorTag = stringBin.substring(0, tag - 1);
			bloco = Integer.parseInt(valorTag, 2);

			lCache = bloco / nLinhas;

			if (posicoes[lCache] == null) {
				posicoes[lCache] = valorTag;
			} else {
			}
			count++;
		}
		total = (acerto / count) * 100;
		System.out.println("Precisão: " + total + "%");
	}

	public void MostrarInformacoes() {
		System.out.println("Memoria principal: " + tamanhoMemoria);
		System.out.println("Memoria cache: " + tamanhoCache);
		System.out.println("Palavra: " + palavra);
		System.out.println("Linhas: " + nLinhas);
		System.out.println("Log Linhas: " + logLinha);
	}

	public static int[] intToBinary(int value, int size) {
		if (value > Math.pow(2, size) - 1) {
			return null;
		}
		int bin[] = new int[size];
		int i = 0;
		while (value > 0 && i < size) {
			int num = value % 2;
			value = value / 2;
			bin[i] = num;
			i++;
		}
		for (int j = 0; j <= size / 2; j++) {
			int temp = bin[j];
			bin[j] = bin[size - j - 1];
			bin[size - j - 1] = temp;
		}
		return bin;
	}

	public static String intToBinaryString(int value, int size) {
		if (value > Math.pow(2, size) - 1) {
			return null;
		}
		char bin[] = new char[size];
		for (int i = 0; i < size; i++) {
			bin[i] = '0';
		}
		int i = 0;
		while (value > 0 && i < size) {
			int num = value % 2;
			value = value / 2;
			bin[i] = (num + "").charAt(0);
			i++;
		}
		for (int j = 0; j <= size / 2; j++) {
			char temp = bin[j];
			bin[j] = bin[size - j - 1];
			bin[size - j - 1] = temp;
		}
		String nova = new String(bin);
		return nova;
	}
}
