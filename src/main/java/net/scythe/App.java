package net.scythe;

import net.scythe.domain.board.Board;
import net.scythe.domain.board.BoardFactory;
import net.scythe.domain.board.xml.SAXParserBoardFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        BoardFactory boardFactory = new SAXParserBoardFactory();
        Board board = boardFactory.newBoard();
    }
}
