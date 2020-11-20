package net.scythe.domain.board.map.xml;

import net.scythe.domain.board.*;
import net.scythe.domain.board.map.*;
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

public class SAXParserScytheMapFactory extends DefaultHandler implements ScytheMapFactory {
    private Map<String, Space> spaces;
    private Map<String, Map<Direction, Space>> links;
    private Map<String, Map<Direction, Space>> rivers;

    @Override
    public void startElement (String uri, String localName,
                              String qName, Attributes attributes)
            throws SAXException {
        dispatchProcessing(uri, localName, qName, attributes);
    }

    @Override
    public ScytheMap newBoard() {
        try (InputStream input = ClassLoader.getSystemResourceAsStream("xml/board/standard_board.xml")) {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser parser = spf.newSAXParser();
            parser.parse(input, this);
        } catch (SAXException | ParserConfigurationException | IOException se) {
            Util.throwUncheckedException(se);
        }
        return new ScytheMap(spaces);
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
        rivers = new HashMap<>();
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
        Map<Direction, Space> river = new HashMap<>();
        Space space = new Space(id, terrain, neighbours, river, encounter, tunnel, faction);
        spaces.put(id, space);
        links.put(id, neighbours);
        rivers.put(id, river);
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
        if (neighbour.containsKey(direction)) {
            throw new SAXException(String.format("Attempt to set neighbour on Space(%1$s) in direction(%2$s) with Space(%3$s) but found Space(%4$s)", originId, direction, arrivalId, neighbour.get(direction).getId()));
        } else {
            neighbour.put(direction, spaces.get(arrivalId));
        }
    }

    private void parseRivers() {
    }

    private void parseRiver(Attributes attributes) throws SAXException {
        String aTile = null;
        String bTile = null;

        for (int i = 0; i < attributes.getLength(); i++) {
            String key = attributes.getQName(i).toLowerCase();
            switch (key) {
                case "a":
                    aTile = attributes.getValue(i).toLowerCase();
                    break;
                case "b":
                    bTile = attributes.getValue(i).toLowerCase();
                    break;
                default:
                    Util.unreachableBranch(key);
                    break;
            }
        }
        if (aTile == null || bTile == null) {
            throw new SAXException(String.format("At least one of a or b was not specified in a river. a(%1$s) b(%2$s)", aTile, bTile));
        } else if (!spaces.containsKey(aTile)) {
            throw new SAXException(String.format("River tile 'a'(%1$s) was not specified in <spaces>. b(%2$s)", aTile, bTile));
        } else if (!spaces.containsKey(bTile)) {
            throw new SAXException(String.format("River tile 'b'(%1$s) was not specified in <spaces>. a(%2$s)", bTile, aTile));
        }
        Space spaceA = spaces.get(aTile);
        Space spaceB = spaces.get(bTile);
        Map<Direction,Space> riversA = rivers.get(aTile);
        Map<Direction,Space> riversB = rivers.get(bTile);
        Direction aDir = spaceA.getDirectionToNeighbour(spaceB);
        Direction bDir = spaceB.getDirectionToNeighbour(spaceA);
        if (aDir == null) {
            throw new SAXException(String.format("Could not identify a direction to travel from %1$s to %2$s (%3$s)", aTile, bTile, spaceA));
        } else if (bDir == null) {
            throw new SAXException(String.format("Could not identify a direction to travel from %1$s to %2$s (%3$s)", bTile, aTile, spaceB));
        }
        riversA.put(aDir, spaceB);
        riversB.put(bDir, spaceA);
    }
}
