package idwall.desafio.crawler;

import java.util.List;

/**
 * Classe que processa as categorias do Subreddit a serem buscadas
 * 
 * @author Lindemberg Andrade
 * */

public class SubredditCrowler {
	
	/** Crowler */
	SubredditCrowlerLeg crowler;
	
	/** Lista de categorias */
	private String[] categorias ;
	
	/** URL base do Subreddit */
	private static final String URL_SUBREDDIT = "https://www.reddit.com/r/";

	/**
	 * Método que processa a lista de subreddits informada pelo usuário
	 * 
	 * @param subreddits
	 * */
	public void process(String subreddits) {
		if(subreddits != null) {
			categorias = subreddits.split(";");
			crowler = new SubredditCrowlerLeg();
			
			for(String categoria : categorias) {
				crowler.crawl(URL_SUBREDDIT + categoria.trim());
			}
			
		}
	}

	/**
	 * Método que imprime os dados buscados pelo Crowler
	 * */
	public void printData() {
		for(Subreddit subreddit : crowler.getSubreddits()) {
			System.out.println("Subreddit: " + subreddit.getTitulo());
			for(SubredditThread thread : subreddit.getThreads())
				System.out.print(thread.toString());
		}
	}
	
	public List<Subreddit> getSubreddits() {
		return crowler.getSubreddits();
	}

}
