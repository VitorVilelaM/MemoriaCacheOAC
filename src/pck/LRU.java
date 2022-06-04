package pck;

import java.util.ArrayList;

public class LRU extends Cache {
	public LRU(int tamMemoria, int tamCache, int pal, int l) {
		super(tamMemoria, tamCache, pal, l);
	}

	public static void Substituir() {
		int tag = (int) (Math.log(tamanhoMemoria / palavra) / Math.log(2)) - 1;

		ValoresAssociativo[] posicoes = new ValoresAssociativo[nLinhas];
		String valorTag;
		int i = 0, count = 0, missCache = 0, endereco = 0, valor = 0, menor = 0, op = 0;
		float total = 0, hitCache = 0;

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
				for (int j = 0; j < posicoes.length; j++) {
					if (posicoes[j].getTag().equals(valorTag)) {
						op = 1;
						hitCache++;
						break;
					} else {
						op = 0;
					}
				}
				if (op == 0) {
					for (int j = 0; j < posicoes.length; j++) {
						if (posicoes[j].getAcesso() <= menor) {
							if (posicoes[j].getTag().equals(valorTag)) {
								hitCache++;
								posicoes[j].setAcesso(posicoes[j].getAcesso() + 1);
							} else {
								menor = posicoes[j].getAcesso();
								missCache++;
								posicoes[j].setTag(valorTag);
							}
							break;
						}

					}
				}

			}
			count++;
			i++;
		}

		total = (hitCache * 100) / count;
		System.out.println("Acessos: " + count);
		System.out.println("MissCache: " + missCache);
		System.out.println("HitCache: " + hitCache);
		System.out.printf("Precisao: %.2f", total);
		System.out.println("%");
	}
}
