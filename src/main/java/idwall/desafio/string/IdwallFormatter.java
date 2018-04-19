package idwall.desafio.string;

import idwall.desafio.exception.LimitException;

/**
 * Created by Rodrigo Catão Araujo on 06/02/2018.
 * Edited by Lindemberg Andrade
 */
public class IdwallFormatter extends StringFormatter {

	/** Identificador de quebra de linha */
	private final String IDENTIFICADOR_QUEBRA_LINHA = "\n";
	
	/** Identificador do espaço em branco */
	private final String ESPACO_EM_BRANCO = " ";
	
	/** Posição inicial do texto */
	private final Integer POSICAO_INICIAL_TEXTO = 0;
	
	/** Texto já formatado */
	private StringBuffer textoFormatado;
	
	/** Indica se o texto deve ou não ser justificado */
	private Boolean justify;
	
    /**
     * Should format as described in the challenge
     *
     * @param text
     * @return
     */
    @Override
    public String format(String text) {
        return format(text,this.limit,false);
    }

    /**
     * Should format as described in the challenge
     *
     * @param text
     * @param limit
     * @param justify
     * @return
     */
	@Override
	public String format(String text, Integer limit, Boolean justify) {
		this.limit = limit;
		this.justify = justify;
		
		String[] linhasDoTexto = text.split(IDENTIFICADOR_QUEBRA_LINHA);
		textoFormatado = new StringBuffer();
		
		for(String linha : linhasDoTexto) {
			try {
				processarLinha(linha);
			} catch (LimitException e) {
				e.printStackTrace();
			}
		}
		
		return textoFormatado.toString();
	}

	/**
	 * Processa uma determinada linha do texto e já adiciona ao resultado final
	 * 
	 * @param linha
	 * @throws LimitException 
	 * */
	private void processarLinha(String linha) throws LimitException {
		String[] palavras = linha.split(ESPACO_EM_BRANCO);
		
		StringBuffer linhaFormatada = new StringBuffer();
		for(String palavra : palavras) {
			Integer qntCharLinha = linhaFormatada.toString().length(),
				qntCharPalavraAtual = palavra.length();
			
			if(qntCharPalavraAtual > limit)
				throw new LimitException(qntCharPalavraAtual, limit);
			
			if(!atingiuLimiteChar(qntCharLinha,qntCharPalavraAtual)) {
				adicionaPalavraNaLinhaFormatada(linhaFormatada,palavra);
			}else {
				finalizaFormatacaoLinha(linhaFormatada,palavra);
				linhaFormatada = new StringBuffer();
				adicionaPalavraNaLinhaFormatada(linhaFormatada,palavra);
			}
		}

		adicionarLinhaAoTextoFormatado(removerUltimoEspacoEmBranco(linhaFormatada.toString()));
		linhaFormatada = new StringBuffer();
		
	}

	/**
	 * Método responsável por finaliazar a formatação de uma linha, justificando-a caso necessário
	 * e removendo os últimos espaços em branco, caso ele tenha sido adicionado anteriormente.
	 * 
	 * @param linhaFormatada
	 * @param palavra
	 * */
	private void finalizaFormatacaoLinha(StringBuffer linhaFormatada, String palavra) {
		linhaFormatada = new StringBuffer(removerUltimoEspacoEmBranco(linhaFormatada.toString()));
		if(isJustificarTexto(linhaFormatada.toString()))
			linhaFormatada = new StringBuffer(justificarLinha(linhaFormatada.toString()));
		
		adicionarLinhaAoTextoFormatado(linhaFormatada.toString());
	}

	/**
	 * Método que adiciona uma quebra de linha ao texto formatado, caso o conteúdo da linha seja vazio
	 * ou, caso contrário, já adiciona a linha formatada no texto formatado.
	 * 
	 * @param linha
	 * */
	private void adicionarLinhaAoTextoFormatado(String linha) {
		if(linha.equals("")) {
			textoFormatado.append("\n");
		}else {
			textoFormatado.append(linha).append("\n");
		}
		
	}

	/**
	 * Verifica se o texto deve ser formatado
	 * 
	 * @param texto
	 * 
	 * @return
	 * */
	private boolean isJustificarTexto(String texto) {
		return justify && texto.length() < limit;
	}

	/**
	 * Remove o último espaço em branco de uma linha
	 * 
	 * @param conteudo
	 * 
	 * @return
	 * */
	private String removerUltimoEspacoEmBranco(String conteudo) {
		Integer posicaoFinal = conteudo.length()-1;
		return conteudo.substring(POSICAO_INICIAL_TEXTO, posicaoFinal);
	}

	/**
	 * Adiciona palavra à uma linha formatada
	 * 
	 * @param linhaFormatada
	 * @param palavra
	 * */
	private void adicionaPalavraNaLinhaFormatada(StringBuffer linhaFormatada, String palavra) {
		linhaFormatada.append(palavra).append(ESPACO_EM_BRANCO);
	}

	/**
	 * Verifica se a quantidade de caracteres definida elo usuário já foi atingida na linha
	 * 
	 * @param qntCharLinha
	 * @param qntCharPalavra
	 * 
	 * @return
	 * */
	private boolean atingiuLimiteChar(Integer qntCharLinha, Integer qntCharPalavra) {
		return qntCharLinha + qntCharPalavra > limit;
	}

	/**
	 * Justifica uma linha
	 * 
	 * @param linha
	 * @return
	 * */
	private String justificarLinha(String linha) {
		int qnrCharFaltante = limit - linha.length();
		StringBuilder linhaJustificada = new StringBuilder();
		
		Boolean naoJustificado = true;
		if(qnrCharFaltante > 0) {
			while(naoJustificado) {
				for(int i=0; i<linha.length(); i++) {
					char aux = linha.charAt(i);
					if(aux == ' ' && qnrCharFaltante > 0) {
						linhaJustificada.append("  ");
						qnrCharFaltante--;
					}else
						linhaJustificada.append(aux);
				}
				
				if(qnrCharFaltante == 0)
					naoJustificado = false;
				else {
					linha = linhaJustificada.toString();
					linhaJustificada = new StringBuilder();
				}
			}
		}
		return linhaJustificada.toString();
	}
    
    
}
