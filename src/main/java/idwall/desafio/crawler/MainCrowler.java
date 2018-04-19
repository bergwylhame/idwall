package idwall.desafio.crawler;

/**
 * Classe Principal para execução do Crowler do Reddit
 * 
 * Created by Lindemberg Andrade on 16/04/2018.
 * */
public class MainCrowler {
	private static String categories = "askreddit;cats;worldnews";
	
	public static void main(String[] args) {
		switch (args.length) {
        	case 1:
	            categories = args[0];
	            break;
		}
		
		// Inicia o Croler e processa os dados, imprimindo o resultado no console
		SubredditCrowler crowler = new SubredditCrowler();
		crowler.process(categories);
		crowler.printData();
	}
}
