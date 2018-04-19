package idwall.desafio.bot.telegram;

/**
 * Classe que inicia a execução do BOT do Telegram
 * 
 * @author Lindemberg Andrade
 * */

public class MainTelegram {
	
	public static void main(String[] args) {
		// Cria e inicia o BOT do Telegram
		BotTelegram bot = new BotTelegram();
		bot.start();
	}

}
