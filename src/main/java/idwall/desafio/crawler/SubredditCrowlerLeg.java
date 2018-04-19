package idwall.desafio.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Classe que representa toda a estrutura do Crowler
 * 
 * @author Lindemberg Andrade
 * */
public class SubredditCrowlerLeg {
	
	/** Simula o User Agent */
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	/** Subreddit atual de cada busca */
	private Subreddit subreddit = new Subreddit();
	
	/** Representa o Documento HTML da página do subreddit */
	private Document htmlDocument;
	
	/** Quantidade de upvotes que considera uma publicação bombada */
	private static final int BOMBADA = 5000;
	
	/** Lista de todos os subreddits buscados pelo crowller */
	private List<Subreddit> subreddits = new ArrayList<Subreddit>();

	/** 
	 * Método responsável por acessar a URL do subreddit e buscar as informações necessárias
	 * 
	 *  @param url
	 **/
	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;
			
			if (connection.response().statusCode() == 200){
				System.out.println("\n**Visitando Subreddit: " + url + "**");
			}
			
			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Erro**");
				return false;
			}

			processSubreddit();
			
			return true;
		} catch (IOException ioe) {
			// erro no HTTP request
			return false;
		}
	}

	/**
	 * Método que processa as informações de cada subreddit, extraindo as informações das Threads
	 * */
	private void processSubreddit() {
		subreddit = new Subreddit();		
		Element div = htmlDocument.getElementById("siteTable");
		if(div != null) {
			Elements children = div.children();
	
			for (Element node : children) {
				SubredditThread thread = new SubredditThread();
				thread.setTituloThread(processTituloThread(node));
				thread.setUpvotes(node.attr("data-score"));
				thread.setLinkComentarios(processarLinkComentarios(node,node.attr("data-permalink")));
				thread.setLinkThread(processarLinkThread(node.attr("data-url"), thread.getLinkComentarios()));
				
				if(subreddit.getTitulo() == null )
					subreddit.setTitulo(node.attr("data-subreddit"));
				adicionarThread(thread);
			}
		}else {
			int a = 0;
		}
		
		subreddits.add(subreddit);
		
	}

	/** 
	 * Método responsável por adicionar uma Thread já processada na listagem de threads do subreddit 
	 *  */
	private void adicionarThread(SubredditThread thread) {
		if(!thread.getTituloThread().isEmpty() && !thread.getUpvotes().isEmpty() 
				&& !thread.getLinkComentarios().isEmpty() && !thread.getLinkThread().isEmpty())
			if(isThreadBombada(thread))
				subreddit.addThread(thread);
		
	}

	/**  
	 * Verifica se uma determinada Thread é bombada
	 * */
	private boolean isThreadBombada(SubredditThread thread) {
		return Integer.parseInt(thread.getUpvotes()) >= BOMBADA;
	}

	/**
	 * Extrai a informação do link da thread
	 * */
	private String processarLinkThread(String link, String linkComment) {
		if(link.startsWith("/r/"))
			return linkComment;
			
		return link;
	}

	/**
	 * Extrai a informação do link dos comentários
	 * */
	private String processarLinkComentarios(Element node, String string) {
		Elements titulo = node.select("a");
		for(Element e : titulo) {
			if(e.attr("data-event-action").contains("comments")) {
				return e.attr("href");
			}
		}
		return "";
	}

	/**
	 * Extrai a informação do título da thread
	 * */
	private String processTituloThread(Element node) {
		Elements titulo = node.select("a");
		for(Element e : titulo) {
			if(e.toString().contains("title"))
				return e.text();
		}
		return "";
	}
	
	public List<Subreddit> getSubreddits() {
		return subreddits;
	}

}
