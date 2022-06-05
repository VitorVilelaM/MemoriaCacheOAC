package pck;

import java.util.List;

public class Main {

	public static void main(String[] args) {

		int configs[] = new int[4];
		int i = 0, tamPrincipal, palavra, linha, tamCache, op = 0;
		

		String[] configText = null;
		List<String> config = FileManager.stringReader("./dados/config.txt");

		for (i = 0; i < config.size(); i++) {

			configText = config.get(i).split(" ");
			
			if (configText[3].equals("KB;")) {
				configs[i] = Integer.parseInt(configText[2]) * 1024 * 8;
			} else if (configText[3].equals("B;")) {
				configs[i] = Integer.parseInt(configText[2]) * 8;
			} else if (configText[3].equals("pal;")) {
				configs[i] = Integer.parseInt(configText[2]);
			}
		}
		
		tamPrincipal = configs[0];
		palavra = configs[1];
		tamCache = configs[2];
		linha = configs[2]/(configs[1]*configs[3]);
		
		Cache cache = new Cache(tamPrincipal, tamCache, palavra, linha);
		
		op = 4;
		
		//cache.Direto();
		cache.Associativo(op);
	}
}
