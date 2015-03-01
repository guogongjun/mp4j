package com.yeapoo.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML工具类
 * 
 * @author Simon
 *
 */
public class XmlUtil {

    private XmlUtil() {}

    private static DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

    /**
     * 将XML类型的InputStream转化为XML Document
     * 
     * @param xmlInputStream InputStream
     * @return
     */
    public static Document getDocument(InputStream xmlInputStream) {
        Document document = null;
        try {
            DocumentBuilder builder = docFactory.newDocumentBuilder(); 
            document = builder.parse(xmlInputStream);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 将XML类型的String转化为XML Document
     * 
     * @param xmlString
     * @return
     */
    public static Document getDocument(String xmlString) {
        Document document = null;
        try {
            DocumentBuilder builder = docFactory.newDocumentBuilder(); 
            document = builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            document.getDocumentElement().normalize();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 获取node的文本内容
     * 
     * @param document Document
     * @param nodeName String
     * @return
     */
    public static String getNodeContent(Document document, String nodeName) {
        NodeList nl = document.getElementsByTagName(nodeName);
        if (0 == nl.getLength()) {
            return "";
        } else {
            return nl.item(0).getTextContent();
        }
    }
}
