package pck;

import java.util.ArrayList;

public class Direto extends Cache{	
	
	
	public Direto(int tamMemoria, int tamCache, int pal, int l) {
		super(tamMemoria, tamCache, pal, l);
	}

	public static void Substituir() {
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
}
