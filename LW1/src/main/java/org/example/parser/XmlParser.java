package org.example.parser;

import org.example.model.Mission;

import org.w3c.dom.Document ;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlParser implements IMissionParser{
    @Override
    public Mission parse(File file) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();

        Mission mission = new Mission();

        mission.setMissionId(getXmlValue(doc, "missionId"));
        mission.setDate(getXmlValue(doc, "date"));
        mission.setLocation(getXmlValue(doc, "location"));
        mission.setOutcome(getXmlValue(doc, "outcome"));

        String damageCost = getXmlValue(doc, "damageCost");
        if(damageCost != null){
            try{
                mission.setDamageCost(Integer.parseInt(damageCost));
            } catch (NumberFormatException e) {}
        }

        NodeList curseNode = doc.getElementsByTagName("curse");
        if(curseNode.getLength() > 0){
            Element curseEl = (Element) curseNode.item(0);
            Mission.Curse curse = new Mission.Curse();
            curse.setName(getElementValue(curseEl, "name"));
            curse.setThreatLevel(getElementValue(curseEl, "threatLevel"));
            mission.setCurse(curse);
        }

        NodeList sorcererNode = doc.getElementsByTagName("sorcerer");
        for(int i = 0; i < sorcererNode.getLength(); i++){
            Element sorcererEl = (Element) sorcererNode.item(i);
            Mission.Sorcerer sorcerer = new Mission.Sorcerer();
            sorcerer.setName(getElementValue(sorcererEl, "name"));
            sorcerer.setRank(getElementValue(sorcererEl, "rank"));
            mission.addSorcerer(sorcerer);
        }

        NodeList techniqueNodes = doc.getElementsByTagName("technique");
        for(int i = 0; i < techniqueNodes.getLength(); i++){
            Element techniqueEl = (Element) techniqueNodes.item(i);
            Mission.Technique technique = new Mission.Technique();
            technique.setName(getElementValue(techniqueEl, "name"));
            technique.setType(getElementValue(techniqueEl, "type"));
            technique.setOwner(getElementValue(techniqueEl, "owner"));

            String damage = getElementValue(techniqueEl, "damage");
            if(damage != null){
                try{
                    technique.setDamage(Integer.parseInt(damage));
                } catch (NumberFormatException e) {}
            }
            mission.addTechnique(technique);

        }
        findAndSetNotes(doc, mission);
        return mission;
    }

    private void findAndSetNotes(Document doc, Mission mission) {
        NodeList techniquesList = doc.getElementsByTagName("techniques");

        if (techniquesList.getLength() > 0) {
            Element techniquesElement = (Element) techniquesList.item(0);
            Node nextSibling = techniquesElement.getNextSibling();

            while (nextSibling != null && nextSibling.getNodeType() != Node.ELEMENT_NODE) {
                nextSibling = nextSibling.getNextSibling();
            }

            if (nextSibling != null) {
                mission.setNotes(nextSibling.getTextContent());
            }
        }
    }

    @Override
    public boolean support(File file){
        return file.getName().toLowerCase().endsWith(".xml");
    }

    private String getXmlValue(Document doc, String tag){
        NodeList nodes = doc.getElementsByTagName(tag);
        if(nodes.getLength() > 0){
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private String getElementValue(Element element, String tag){
        NodeList nodes = element.getElementsByTagName(tag);
        if(nodes.getLength() > 0){
            return nodes.item(0).getTextContent();
        }
        return null;
    }
}
