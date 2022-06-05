package pck;

import java.util.ArrayList;

public class Cache {
	protected static int tamanhoCache;
	protected static int tamanhoMemoria;
	protected static int palavra;
	protected static int nLinhas;
	protected static int logLinha;

	public Cache(int tamMemoria, int tamCache, int pal, int l) {
		this.tamanhoMemoria = tamMemoria;
		this.tamanhoCache = tamCache;
		this.palavra = pal;
		this.nLinhas = l;
		this.logLinha = (int) (Math.log(nLinhas) / Math.log(2));
	}

	public void Direto() {
		Direto.Substituir();
	}

	public void Associativo(int op) {
		int tag = (int) (Math.log(tamanhoMemoria / palavra) / Math.log(2)) - 1;

		ValoresAssociativo[] posicoes = new ValoresAssociativo[nLinhas];
		String valorTag;
		int i = 0, count = 0, missCache = 0, endereco = 0, valor = 0, hitCache = 0, id = 0;

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);

			endereco = tag + 2;

			int bin[] = intToBinary(acesso, endereco);

			String stringBin = intToBinaryString(acesso, endereco);

			valorTag = stringBin.substring(0, tag);
			ValoresAssociativo end = new ValoresAssociativo(valorTag, valor);
			if (i < nLinhas) {
				posicoes[i] = end;
			} else {
				if (op == 1) {
					hitCache = LRU.Substituir(posicoes, valorTag, hitCache);
				} else if (op == 2) {
					hitCache = FIFO.Substituir(posicoes, valorTag, hitCache, id);
					id++;
				} else if (op == 3) {
					hitCache = LFU.Substituir(posicoes, valorTag, end, hitCache);
				} else if (op == 4) {
					hitCache = Aleatorio.Substituir(posicoes, valorTag, hitCache, nLinhas);
				}
			}
			count++;
			i++;
		}
		missCache = (int) (count - hitCache);
		mostrarInformacoes(count, hitCache, missCache);
	}

	public void AssociativoConjunto(int op, int tam) {
		int endereco = 0;
		int i = 0, j = 0, nBloco, count = 0, missCache, hitCache = 0, blocos, tag, logBlocos, valor = 0, id = 0;

		blocos = nLinhas / tam;
		tag = (int) ((int) (Math.log(tamanhoMemoria / palavra) / Math.log(2)) + 1 - (Math.log(blocos) / Math.log(2))
				- 2);
		logBlocos = (int) (Math.log(blocos) / Math.log(2));
		ValoresAssociativo[][] posicoes = new ValoresAssociativo[blocos][tam];
		String valorTag;
		String valorBloco;

		ArrayList<String> teste = FileManager.stringReader("./dados/teste_1.txt");
		for (String linha : teste) {
			int acesso = Integer.parseInt(linha);

			endereco = tag + logBlocos + 2;
			int bin[] = intToBinary(acesso, endereco);

			String stringBin = intToBinaryString(acesso, endereco);

			valorTag = stringBin.substring(0, tag);
			valorBloco = stringBin.substring(tag, logBlocos + tag);
			nBloco = Integer.parseInt(valorBloco, 2);

			ValoresAssociativo end = new ValoresAssociativo(valorTag, valor);
			for (j = 0; j < tam; j++) {
				if (posicoes[nBloco][j] == null) {
					posicoes[nBloco][j] = end;
				}else {
					i++;
				}
			}
			if (i != 0) {
				if (op == 1) {
					hitCache = LRU.Substituir(posicoes[nBloco], valorTag, hitCache);
				} else if (op == 2) {
					hitCache = FIFO.Substituir(posicoes[nBloco], valorTag, hitCache, id);
					id++;
				} else if (op == 3) {
					hitCache = LFU.Substituir(posicoes[nBloco], valorTag, end, hitCache);
				} else if (op == 4) {
					hitCache = Aleatorio.Substituir(posicoes[nBloco], valorTag, hitCache, tam);
				}
			}

			count++;
		}

		missCache = (int) (count - hitCache);
		mostrarInformacoes(count, hitCache, missCache);
	}

	public void mostrarInformacoes(int count, float hitCache, int missCache) {
		float total;

		total = (hitCache * 100) / count;
		System.out.println("Acessos: " + count);
		System.out.println("MissCache: " + missCache);
		System.out.println("HitCache: " + hitCache);
		System.out.println("Precisao: " + total);
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
