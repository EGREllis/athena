package net.scythe.domain.board.mat.xml;

import net.scythe.domain.board.mat.Building;
import net.scythe.domain.board.mat.Mat;
import net.scythe.domain.board.mat.MatFactory;
import net.scythe.domain.resource.Resource;
import net.scythe.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAXParserMatFactory extends DefaultHandler implements MatFactory {
    private Map<String, Mat> mats;
    private Map<String, ActionHolder> actions;
    private ActionHolder actionHolder;

    @Override
    public Map<String, Mat> newMats() {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("xml/board/standard_mat.xml")) {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);
            SAXParser parser = spf.newSAXParser();
            parser.parse(inputStream, this);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            Util.throwUncheckedException(e);
        }
        return mats;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        dispatchStartProcessing(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        dispatchEndProcessing(uri, localName, qName);
    }

    private void dispatchStartProcessing(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName) {
            case "actions":
                parseActions();
                break;
            case "action":
                parseAction(attributes);
                break;
            case "option":
                parseOption(attributes);
                break;
            default:
                Util.unreachableBranch(localName);
                break;
        }
    }

    private void dispatchEndProcessing(String uri, String localName, String qName) throws SAXException {
        switch(localName) {
            case "action":
                completeAction();
                break;
            default:
                Util.unreachableBranch(localName);
                break;
        }
    }

    private void completeAction() {
        actions.put(actionHolder.id, actionHolder);
        actionHolder = null;
    }

    private void parseActions() {
        actions = new HashMap<>();
    }

    private void parseAction(Attributes attributes) throws SAXException {
        String id = null;
        String position = null;
        String building = null;
        String resource = null;

        for (int i = 0; i < attributes.getLength(); i++) {
            String attr = attributes.getQName(i).toLowerCase();
            switch (attr) {
                case "id":
                    id = attributes.getValue(i).toLowerCase();
                    break;
                case "position":
                    position = attributes.getValue(i).toLowerCase();
                    break;
                case "building":
                    building = attributes.getValue(i).toLowerCase();
                    break;
                case "resource":
                    resource = attributes.getValue(i).toLowerCase();
                    break;
                default:
                    Util.unreachableBranch(attr);
                    break;
            }
        }

        if (id == null || position == null) {
            throw new SAXException(String.format("id = %1$s, position = %2$s", id, position));
        } else if (id != null && "top".equals(position.toLowerCase()) && (building == null || resource != null)) {
            throw new SAXException(String.format("Top actions must include a building %1$s and no resource %2$s", building, resource));
        } else if (id != null && !"top".equals(position.toLowerCase()) && (resource == null || building != null)) {
            throw new SAXException(String.format("Bottom actions must include a resource %1$s and no building %2$s", resource, building));
        }

        this.actionHolder = new ActionHolder(id,
                "top".equals(position.toLowerCase()),
                Building.parseBuilding(building),
                Resource.parseResource(resource));
    }

    private void parseOption(Attributes attributes) throws SAXException {
        String name = null;
        Integer min = null;
        Integer max = null;
        Integer initial = null;
        Integer cost = null;
        Resource resource = null;

        for (int i  = 0; i < attributes.getLength(); i++) {
            String key = attributes.getQName(i).toLowerCase();
            switch (key) {
                case "name":
                    name = attributes.getValue(i);
                    break;
                case "min":
                    min = Integer.parseInt(attributes.getValue(i));
                    break;
                case "max":
                    max = Integer.parseInt(attributes.getValue(i));
                    break;
                case "initial":
                    initial = Integer.parseInt(attributes.getValue(i));
                    break;
                case "cost":
                    cost = Integer.parseInt(attributes.getValue(i));
                    break;
                case "resource":
                    resource = Resource.parseResource(attributes.getValue(i));
                    break;
                default:
                    Util.unreachableBranch(key);
                    break;
            }
        }

        if (name == null || min == null || max == null || initial == null) {
            throw new SAXException(String.format("Name: %1$s Min: %2$s Max: %3$s Initial: %4$s", name, min, max, initial));
        } else if ( (cost == null && resource != null) || (cost != null && resource == null) ) {
            throw new SAXException(String.format("Either neither or both cost and resource must be specified: cost: %1$s resource: %2$s", cost, resource));
        }

        OptionHolder optionHolder;
        if (cost == null && resource == null) {
            optionHolder = new OptionHolder(name, min, max, initial);
        } else {
            optionHolder = new OptionHolder(name, min, max, initial, cost, resource);
        }

        actionHolder.addOptionHolder(optionHolder);
    }

    private static class ActionHolder {
        private final String id;
        private final boolean isTop;
        private final Building building;
        private final Resource resource;
        private final List<OptionHolder> options = new ArrayList<>();

        public ActionHolder(String id, boolean isTop, Building building, Resource resource) {
            this.id = id;
            this.isTop = isTop;
            this.building = building;
            this.resource = resource;
        }

        public String getId() {
            return id;
        }

        public boolean isTop() {
            return isTop;
        }

        public Building getBuilding() {
            return building;
        }

        public Resource getResource() {
             return resource;
        }

        public List<OptionHolder> getOptions() {
            return options;
        }

        public void addOptionHolder(OptionHolder optionHolder) {
            options.add(optionHolder);
        }
    }

    private static class OptionHolder {
        private String name;
        private int min;
        private int max;
        private int initial;
        private Integer cost;
        private Resource resource;

        public OptionHolder(String name, int min, int max, int initial) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.initial = initial;
        }

        public OptionHolder(String name, int min, int max, int initial, Integer cost, Resource resource) {
            this.name = name;
            this.min = min;
            this.max = max;
            this.initial = initial;
            this.cost = cost;
            this.resource = resource;
        }

        public String getName() {
            return name;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public int getInitial() {
            return initial;
        }

        public Integer getCost() {
            return cost;
        }

        public Resource getResource() {
            return resource;
        }
    }
}
