package pck;

import java.util.ArrayList;
import java.util.Random;

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
		int lCache, count = 0, missCache = 0, hitCache = 0;
		double total;

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);

			endereco = tag + logLinha + 2;

			int bin[] = intToBinary(acesso, endereco);

			String stringBin = intToBinaryString(acesso, endereco);

			valorTag = stringBin.substring(0, tag);
			valorLinha = stringBin.substring(tag, logLinha + tag);

			lCache = Integer.parseInt(valorLinha, 2);
			
			if (posicoes[lCache] == null) {
				posicoes[lCache] = valorTag;

			} else if (posicoes[lCache].equals(valorTag)) {
				hitCache++;
			} else {
				posicoes[lCache] = valorTag;
				missCache++;
			}
			count++;
		}
		total = (hitCache * 100) / count;
		System.out.println("Acessos: " + count);
		System.out.println("MissCache: " + missCache);
		System.out.println("Precisao: " + total + "%");
	}

	public void Associativo(int op) {
		int tag = (int) (Math.log(tamanhoMemoria / palavra) / Math.log(2)) - 1;

		String[] posicoes = new String[nLinhas];
		String valorTag;
		int endereco = 0;
		int i, count = 0, missCache = 0, hitCache = 0, numeroAleatorio, fifo = 0;
		double total;
		Random random = new Random();

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);

			endereco = tag + logLinha + 2;

			int bin[] = intToBinary(acesso, endereco);

			String stringBin = intToBinaryString(acesso, endereco);

			valorTag = stringBin.substring(0, tag);

			for(i = 0; i < nLinhas; i++) {
				if(posicoes[i] == null){
					posicoes[i] = valorTag;
					break;
				}else {
					if(op == 1) {
						LRU();
					}else if(op == 2){
						total = FIFO(posicoes, valorTag, fifo, missCache, hitCache);
						fifo++;
						
					}else if(op == 3){
						LFU();
					}else{
						numeroAleatorio = random.nextInt(nLinhas - 1);
						if(posicoes[numeroAleatorio].equals(valorTag)){
							hitCache++;
						}else {
							posicoes[numeroAleatorio] = valorTag;
							missCache++;
						}
					}
				}
				count++;
			}
		}
		hitCache = count - missCache;
		
		total = (hitCache * 100) / count;
		System.out.println("Acessos: " + count);
		System.out.println("MissCache: " + missCache);
		System.out.println("Precisao: " + total + "%");
	}

	
	public void LRU() {
		int id;
	}
	
	
	public int FIFO(String []posicoes, String valor, int id, int missCache, int hitCache) {
		if(posicoes[id].equals(valor)){
			hitCache++;
		}else {
			posicoes[id] = valor;
			missCache++;
		}
		
		return missCache;
	}
	
	public void LFU() {
		
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
