package net.scythe;

import net.scythe.domain.board.map.ScytheMap;
import net.scythe.domain.board.map.ScytheMapFactory;
import net.scythe.domain.board.map.xml.SAXParserScytheMapFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ScytheMapFactory boardFactory = new SAXParserScytheMapFactory();
        ScytheMap board = boardFactory.newBoard();
    }
}
