package pck;

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

		Aleatorio.Substituir();

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
