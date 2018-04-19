package idwall.desafio.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import idwall.desafio.string.IdwallFormatter;

/**
 * Classe de teste para o processamento de texto
 * 
 * @author Lindemberg Andrade
 * */
class IdwallFormatterTest {
		
	@Test
	void testFormatString() {
		IdwallFormatter formatador = new IdwallFormatter();
		
		String texto = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n" +
	            "\n" +
	            "And God said, \"Let there be light,\" and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light \"day,\" and the darkness he called \"night.\" And there was evening, and there was morning - the first day.";
		
		String saida = "In the beginning God created the heavens\n" + 
				"and the earth. Now the earth was\n" + 
				"formless and empty, darkness was over\n" + 
				"the surface of the deep, and the Spirit\n" + 
				"of God was hovering over the waters.\n" + 
				"\n" + 
				"And God said, \"Let there be light,\" and\n" + 
				"there was light. God saw that the light\n" + 
				"was good, and he separated the light\n" + 
				"from the darkness. God called the light\n" + 
				"\"day,\" and the darkness he called\n" + 
				"\"night.\" And there was evening, and\n" + 
				"there was morning - the first day.\n" + 
				"";

		String textoSaida = formatador.format(texto);
		assertEquals(saida, textoSaida);
	}

	@Test
	void testFormatStringIntegerBoolean() {
		IdwallFormatter formatador = new IdwallFormatter();
		
		String texto = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, darkness was over the surface of the deep, and the Spirit of God was hovering over the waters.\n" +
	            "\n" +
	            "And God said, \"Let there be light,\" and there was light. God saw that the light was good, and he separated the light from the darkness. God called the light \"day,\" and the darkness he called \"night.\" And there was evening, and there was morning - the first day.";
		
		String saida = "In the beginning God created the heavens\n" + 
				"and    the  earth.  Now  the  earth  was\n" + 
				"formless  and  empty,  darkness was over\n" + 
				"the  surface of the deep, and the Spirit\n" + 
				"of God was hovering over the waters.\n" + 
				"\n" + 
				"And  God said, \"Let there be light,\" and\n" + 
				"there  was light. God saw that the light\n" + 
				"was  good,  and  he  separated the light\n" + 
				"from  the darkness. God called the light\n" + 
				"\"day,\"    and  the  darkness  he  called\n" + 
				"\"night.\"  And  there  was  evening,  and\n" + 
				"there was morning - the first day.\n" + 
				"";
		
		assertEquals(saida, formatador.format(texto, 40, true));
	}

}
