package net.scythe.domain.board.map.xml;

import net.scythe.domain.board.map.ScytheMap;
import net.scythe.domain.board.map.ScytheMapFactory;
import org.junit.Test;

public class BoardParserTest {
    @Test
    public void when_SAXBoardFactoryUsed_then_nonNullAndNoParseExceptionsThrown() {
        ScytheMapFactory factory = new SAXParserScytheMapFactory();
        ScytheMap board = factory.newBoard();
        assert board != null;
    }
}
