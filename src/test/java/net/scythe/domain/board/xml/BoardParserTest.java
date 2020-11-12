package net.scythe.domain.board.xml;

import net.scythe.domain.board.Board;
import net.scythe.domain.board.BoardFactory;
import org.junit.Test;

public class BoardParserTest {
    @Test
    public void when_SAXBoardFactoryUsed_then_nonNullAndNoParseExceptionsThrown() {
        BoardFactory factory = new SAXParserBoardFactory();
        Board board = factory.newBoard();
        assert board != null;
    }
}
