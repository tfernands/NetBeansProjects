/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author TERMINAL
 */
public class ReaderWriter {

    public static void loadConfigs() throws ParserConfigurationException, SAXException, IOException, TransformerException{
        File fXmlFile = new File("config.xml");
        
        if (fXmlFile.exists()) System.out.println("config file found");
        else{
            System.out.println("config file not found loading default configuration");
            return;
        }
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        Element rootElement = doc.getDocumentElement();
        Config.base_name = rootElement.getAttribute("base_name");
        Config.base_url = rootElement.getAttribute("base_url");
        Config.base_user = rootElement.getAttribute("base_user");
        Config.base_key = rootElement.getAttribute("base_key");
        Config.tableFields.clear();
        NodeList tables = rootElement.getElementsByTagName("table");
        for (int i = 0; i < tables.getLength(); i++){
            Element table = (Element)tables.item(i);
            ArrayList<String>fieldsList = new ArrayList<>();
            NodeList fields = table.getElementsByTagName("field");
            for (int j = 0; j < fields.getLength(); j++){
                fieldsList.add(fields.item(j).getAttributes().getNamedItem("name").getTextContent());
            }
            Config.tableFields.put(table.getAttributes().getNamedItem("name").getTextContent(), fieldsList);
        }
    }
    
    public static void saveConfigs() throws ParserConfigurationException, TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("exporterConfig");
        doc.appendChild(rootElement);
        
        rootElement.setAttribute("base_name", Config.base_name);
        rootElement.setAttribute("base_url", Config.base_url);
        rootElement.setAttribute("base_user", Config.base_user);
        rootElement.setAttribute("base_key", Config.base_key);
        
        for (String table_name: Config.tableFields.keySet()){
            Element table = doc.createElement("table");
            table.setAttribute("name", table_name);
            for (String field_name: Config.tableFields.get(table_name)){
                Element field = doc.createElement("field");
                field.setAttribute("name", field_name);
                table.appendChild(field);
            }
            rootElement.appendChild(table);
        }
        saveXMLDoc(doc, "config.xml");
    }
    
    public static void export() throws TransformerConfigurationException, TransformerException, SQLException, ParserConfigurationException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(Config.base_name);
        doc.appendChild(rootElement);
        
        BDConnection.connect();
        for (String table_name: Config.tableFields.keySet()){
            Element table = doc.createElement("table");
            table.setAttribute("name", table_name);
            for (String field_name: Config.tableFields.get(table_name)){
                Element field = doc.createElement("field");
                field.setAttribute("name", field_name);
                String sql = "SELECT "+field_name+" FROM "+table_name;
                ResultSet rs = BDConnection.con.createStatement().executeQuery(sql);
                while (rs.next()){
                    Element value = doc.createElement("value");
                    value.setTextContent(rs.getString(1));
                    field.appendChild(value);
                }
                table.appendChild(field);
            }
            rootElement.appendChild(table);
        }
        BDConnection.close();
        
        saveXMLDoc(doc, Config.exportDirectory);
    }
    
    private static void saveXMLDoc(Document doc, String directory) throws TransformerConfigurationException, TransformerException{
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(directory));
        transformer.transform(source, result);
    }
}
