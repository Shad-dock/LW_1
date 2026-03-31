package org.example.parser;

import org.example.model.Mission;

import org.example.model.blocks.EconomicAssessmentBlock;
import org.w3c.dom.Document ;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlParser implements IMissionParser{
    private Map<Integer, String> techOwnerMap = new HashMap<>();

    @Override
    public Mission parse(File file) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);
        doc.getDocumentElement().normalize();
        techOwnerMap.clear();

        MissionBuilder missionBuilder = new MissionBuilder();

        //Mission mission = new Mission();

        missionBuilder.setMissionId(getXmlValue(doc, "missionId"));
        missionBuilder.setDate(getXmlValue(doc, "date"));
        missionBuilder.setLocation(getXmlValue(doc, "location"));
        missionBuilder.setOutcome(getXmlValue(doc, "outcome"));

        String notes = getXmlValue(doc, "notes");
        if (notes != null) missionBuilder.addNote(notes);

        String comment = getXmlValue(doc, "comment");
        if (comment != null) missionBuilder.addNote(comment);

        String damageCost = getXmlValue(doc, "damageCost");
        if(damageCost != null){
            try{
                missionBuilder.setDamageCost(Integer.parseInt(damageCost));
            } catch (NumberFormatException e) {}
        }

        NodeList curseNode = doc.getElementsByTagName("curse");
        if(curseNode.getLength() > 0){
            Element curseEl = (Element) curseNode.item(0);
            //Mission.Curse curse = new Mission.Curse();
            missionBuilder.setCurseName(getElementValue(curseEl, "name"));
            missionBuilder.setCurseThreatLevel(getElementValue(curseEl, "threatLevel"));
            //missionBuilder.setCurse(curse);
        }

        NodeList sorcererNode = doc.getElementsByTagName("sorcerer");
        for(int i = 0; i < sorcererNode.getLength(); i++){
            Element sorcererEl = (Element) sorcererNode.item(i);
            //Mission.Sorcerer sorcerer = new Mission.Sorcerer();
            String name = (getElementValue(sorcererEl, "name"));
            String rank = (getElementValue(sorcererEl, "rank"));
            //mission.addSorcerer(sorcerer);
            missionBuilder.addSorcerer(name, rank);
        }

        NodeList techniqueNodes = doc.getElementsByTagName("technique");
        for(int i = 0; i < techniqueNodes.getLength(); i++){
            Element techniqueEl = (Element) techniqueNodes.item(i);
            //Mission.Technique technique = new Mission.Technique();
            String name = (getElementValue(techniqueEl, "name"));
            String type = (getElementValue(techniqueEl, "type"));
            //technique.setOwner(getElementValue(techniqueEl, "owner"));
            String ownerName = getElementValue(techniqueEl, "owner");
            techOwnerMap.put(i, ownerName);

            int intDamage = 0;
            String damage = getElementValue(techniqueEl, "damage");
            if(damage != null){
                try{
                    intDamage = (Integer.parseInt(damage));
                } catch (NumberFormatException e) {}
            }
            missionBuilder.addTechnique(name, type, ownerName, intDamage);

        }
//        link(mission);
//        findAndSetNotes(doc, mission);
        parseAdditionalBlocks(doc.getDocumentElement(), missionBuilder);

        return missionBuilder.build();
    }

//    private void link(Mission mission){
//        for(int i = 0; i<mission.getTechniques().size(); i++){
//            Mission.Technique technique = mission.getTechniques().get(i);
//            String ownerName = techOwnerMap.get(i);
//            for (Mission.Sorcerer sorcerer : mission.getSorcerers()){
//                if(ownerName.equals(sorcerer.getName())){
//                    technique.setOwner(sorcerer);
//                    break;
//                }
//            }
//        }
//    }

//    private void findAndSetNotes(Document doc, Mission mission) {
//        NodeList techniquesList = doc.getElementsByTagName("techniques");
//
//        if (techniquesList.getLength() > 0) {
//            Element techniquesElement = (Element) techniquesList.item(0);
//            Node nextSibling = techniquesElement.getNextSibling();
//
//            while (nextSibling != null && nextSibling.getNodeType() != Node.ELEMENT_NODE) {
//                nextSibling = nextSibling.getNextSibling();
//            }
//
//            if (nextSibling != null) {
//                mission.setNotes(nextSibling.getTextContent());
//            }
//        }
//    }
    private void parseAdditionalBlocks(Element root, MissionBuilder builder) {
        // EconomicAssessment
        NodeList econNodes = root.getElementsByTagName("economicAssessment");
        if (econNodes.getLength() > 0) {
            Element econElem = (Element) econNodes.item(0);
            EconomicAssessmentBlock block = new EconomicAssessmentBlock();
            block.setTotalDamageCost(getIntValue(econElem, "totalDamageCost"));
            block.setRecoveryDays(getIntValue(econElem, "recoveryEstimateDays"));
            builder.addAdditionalBlock(block);
        }
    }

    private int getIntValue(Element element, String tag) {
        String val = getElementValue(element, tag);
        if (val != null) {
            try {
                return Integer.parseInt(val);
            } catch (NumberFormatException e) {}
        }
        return 0;
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
