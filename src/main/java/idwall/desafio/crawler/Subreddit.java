package idwall.desafio.crawler;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um Subreddit, com a listagem de threads.
 * 
 * @author Lindemberg Andrade
 */
public class Subreddit {

	/** Listagem de Threads do Subreddit */
	private List<SubredditThread> threads;

	/** Título do Subreddit */
	private String titulo;

	public Subreddit(String titulo) {
		this.titulo = titulo;
		threads = new ArrayList<SubredditThread>();
	}

	public Subreddit() {
		threads = new ArrayList<SubredditThread>();
	}

	public void addThread(SubredditThread thread) {
		threads.add(thread);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<SubredditThread> getThreads() {
		return threads;
	}

}
