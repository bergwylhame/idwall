package idwall.desafio.bot.telegram;

import java.util.ArrayList;
import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import idwall.desafio.crawler.Subreddit;
import idwall.desafio.crawler.SubredditCrowler;
import idwall.desafio.crawler.SubredditThread;

/**
 * Classe responsável por criar uma conexão com o BOT do Telegram
 * e responder às requisições dos usuários
 * 
 * @author Lindemberg Andrade
 * */

public class BotTelegram {
	/** Token de acesso ao bot do telegram */
	private static final String TOKEN = "564718153:AAEzx2nRykv5Be_rIoKEVjpeq7KY01Gt4hY";
	/** Comando - /nadaprafazer */
	private static final String COMANDO_NADA_PRA_FAZER = "/nadaprafazer";

	/** Criação do objeto bot com as informações de acesso */
	private TelegramBot bot = new TelegramBot(TOKEN);

	/** objeto responsável por receber as mensagens */
	private GetUpdatesResponse updatesResponse;
	/** objeto responsável por gerenciar o envio de respostas */
	private SendResponse sendResponse;
	/** objeto responsável por gerenciar o envio de ações do chat */
	private BaseResponse baseResponse;

	/** Lista de chat que estão aguardando os subreddits para responder */
	private List<Long> listaParaEnvioSubreddit = new ArrayList<Long>();

	/**
	 * Método que inicia o bot do Telegram e fica aguardando os comnandos
	 * e gerando as respostas aos usuários
	 * */
	public void start() {
		// controle de off-set, isto é, a partir deste ID será lido as mensagens
		// pendentes na fila
		int m = 0;

		// loop infinito pode ser alterado por algum timer de intervalo curto
		while (true) {
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));
			List<Update> updates = updatesResponse.updates();

			// análise de cada ação da mensagem
			for (Update update : updates) {
				m = update.updateId() + 1;
				System.out.println("Recebendo mensagem:" + update.message().text());

				String mensagem = update.message().text();
				if (mensagem != null && mensagem.toLowerCase().contains(COMANDO_NADA_PRA_FAZER)) {
					enviarMensagem(update, "Entendi, você está entediado(a) e gostaria de ver alguns Subreddits que estão Bombando.\n"
							+ "Quais assuntos você gostaria de ver? Exemplo: cats;brazil;AskReddit");

					if (!remetenteEsperandoResposta(update))
						listaParaEnvioSubreddit.add(update.message().chat().id());

				} else {
					if (remetenteEsperandoResposta(update)) {
						processarParametrosGerarResposta(update,mensagem);
					}else{
						enviarMensagem(update, "Desculpe, mas não entendi!");
					}
				}
			}

		}
	}

	/**
	 * Método usado para processar os parãmetros do comando enviado pelo usuário, processar
	 * a informação e gerar a resposta com os dados solicitados.
	 * 
	 * @param mensagem
	 * @param update
	 * */
	private void processarParametrosGerarResposta(Update update, String mensagem) {
		enviarMensagem(update, "Buscando subreddits " + mensagem);
		String categories = mensagem;
		SubredditCrowler crowler = new SubredditCrowler();
		crowler.process(categories);

		for (Subreddit subreddit : crowler.getSubreddits()) {
			enviarMensagem(update, "Subreddit: " + subreddit.getTitulo());
			
			if(subreddit.getThreads().isEmpty())
				enviarMensagem(update, "Nenhuma Thread Bombada nesse momento!");
			
			for (SubredditThread thread : subreddit.getThreads())
				enviarMensagem(update, thread.toString());
		}
		listaParaEnvioSubreddit.remove(update.message().chat().id());		
	}

	/**
	 * Método usado para verificar se o remetente já enviou o comando e está
	 * aguardando a resposta no BOT, dependendo dos argumentos enviados por ele;
	 * 
	 * @param update
	 * @return
	 * */
	private boolean remetenteEsperandoResposta(Update update) {
		return listaParaEnvioSubreddit.contains(update.message().chat().id());
	}

	/**
	 * Método usado para enviar uma resposta ao remetente da mensagem
	 * */
	private void enviarMensagem(Update update, String mensagem) {
		enviarAction(update,ChatAction.typing.name());
		sendResponse = bot.execute(new SendMessage(update.message().chat().id(),mensagem));
		
		// verificação de mensagem enviada com sucesso
		System.out.println("Mensagem Enviada?" + sendResponse.isOk());
		
	}

	/**
	 * Método usado para enviar um comando para o chat, como "Escrevendo", por exemplo.
	 * @param update
	 * @param action
	 * */
	private void enviarAction(Update update, String action) {
		baseResponse = bot
				.execute(new SendChatAction(update.message().chat().id(), action));
		
		// verificação de ação de chat foi enviada com sucesso
		System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());
		
	}
	
	
}
