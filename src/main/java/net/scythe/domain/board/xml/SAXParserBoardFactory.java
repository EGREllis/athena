package net.scythe.domain.board.xml;

import net.scythe.domain.board.*;
import net.scythe.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SAXParserBoardFactory extends DefaultHandler implements BoardFactory {
    private Map<String, Space> spaces;
    private Map<String, Map<Direction, Space>> links;

    @Override
    public void startElement (String uri, String localName,
                              String qName, Attributes attributes)
            throws SAXException {
        dispatchProcessing(uri, localName, qName, attributes);
    }

    @Override
    public void endElement (String uri, String localName, String qName) throws SAXException {
    }

    @Override
    public Board newBoard() {
        try (InputStream input = ClassLoader.getSystemResourceAsStream("xml/board/standard_board.xml")) {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser parser = spf.newSAXParser();
            parser.parse(input, this);
        } catch (SAXException | ParserConfigurationException | IOException se) {
            Util.throwUncheckedException(se);
        }
        return new Board(spaces);
    }

    private void dispatchProcessing(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName) {
            case "board":
                parseBoard();
                break;
            case "spaces":
                parseSpaces();
                break;
            case "space":
                parseSpace(attributes);
                break;
            case "links":
                parseLinks();
                break;
            case "link":
                parseLink(attributes);
                break;
            case "rivers":
                parseRivers();
                break;
            case "river":
                parseRiver(attributes);
                break;
            default:
                throw new RuntimeException(String.format("This code should never be reached... but it was, with: %1$s", localName));
        }
    }

    private void parseBoard() {

    }

    private void parseSpaces() {
        spaces = new HashMap<>();
        links = new HashMap<>();
    }

    private void parseSpace(Attributes attributes) throws SAXException {
        String id = null;
        Terrain terrain = null;
        Map<Direction, Space> neighbours = new HashMap<>();
        boolean encounter = false;
        boolean tunnel = false;
        Faction faction = null;

        for (int i = 0; i < attributes.getLength(); i++) {
            String key = attributes.getQName(i).toLowerCase();
            switch (key) {
                case "id":
                    id = attributes.getValue(i);
                    break;
                case "terrain":
                    terrain = Terrain.parseTerrain(attributes.getValue(i));
                    break;
                case "encounter":
                    encounter = Boolean.parseBoolean(attributes.getValue(i));
                    break;
                case "tunnel":
                    tunnel = Boolean.parseBoolean(attributes.getValue(i));
                    break;
                case "faction":
                    faction = Faction.parseFaction(attributes.getValue(i));
                    break;
                default:
                    Util.unreachableBranch(key);
                    break;
            }
        }
        if (terrain == null || id == null) {
            throw new SAXException(String.format("Either terrain (%1$s) or id (%2$s) are null", terrain, id));
        } else if ((Terrain.HOME.equals(terrain) && faction == null) || (faction != null && !Terrain.HOME.equals(terrain))) {
            throw new SAXException(String.format("If the tile is a home tile, it must specify a faction, if it is not a home tile it must not specify a faction. Terrain (%1$S) Faction (%2$s)", terrain, faction));
        }
        Space space = new Space(terrain, neighbours, encounter, tunnel, faction);
        spaces.put(id, space);
        links.put(id, neighbours);
    }

    private void parseLinks() {
    }

    private void parseLink(Attributes attributes) throws SAXException {
        String originId = null;
        Direction direction = null;
        String arrivalId = null;

        for (int i = 0; i < attributes.getLength(); i++) {
            String key = attributes.getQName(i).toLowerCase();
            switch(key) {
                case "origin":
                    originId = attributes.getValue(i);
                    break;
                case "direction":
                    direction = Direction.parseDirection(attributes.getValue(i));
                    break;
                case "arrival":
                    arrivalId = attributes.getValue(i);
                    break;
                default:
                    Util.unreachableBranch(key);
                    break;
            }
        }
        if (originId == null || direction == null || arrivalId == null) {
            throw new SAXException(String.format("At least one of originId(%1$s) direction(%2$s) arrivalId(%3$s) is null!", originId, direction, arrivalId));
        } else if (!spaces.containsKey(originId) || !spaces.containsKey(arrivalId)) {
            throw new SAXException(String.format("At least one of originId(%1$s) or arrivalId(%2$s) is not known!", originId, arrivalId));
        }

        Map<Direction, Space> neighbour = links.get(originId);
        neighbour.put(direction, spaces.get(arrivalId));
    }

    private void parseRivers() {
    }

    private void parseRiver(Attributes attributes) {

    }
}
