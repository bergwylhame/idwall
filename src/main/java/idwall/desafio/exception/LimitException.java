package idwall.desafio.exception;

/**
 * Classe que é reponsável por Exceções no processamento de Texto
 * 
 *  @author Lindemberg Andrade
 * */
public class LimitException extends Exception{

	private static final long serialVersionUID = 1L;

	private int tamanhoPalavra;
	private int limite;
	
	public LimitException(int tamanhoPalavra,int limite) {
		this.tamanhoPalavra = tamanhoPalavra;
		this.limite = limite;
	}
	
	@Override
	public String toString() {
		return "O texto contém uma palavra com " + tamanhoPalavra + " caracteres, e ultrapassa o limite máximo de " + limite + 
				" caracteres por linha.";
	}
}
