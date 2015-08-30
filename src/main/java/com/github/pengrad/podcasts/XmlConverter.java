package com.github.pengrad.podcasts;

import com.github.pengrad.json.JSONObject;
import com.github.pengrad.json.XML;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.w3c.dom.Document;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * stas
 * 8/31/15
 */
public class XmlConverter {

    public static JsonObject toJson(Document document) {
        String xmlString = toString(document);
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        return new Gson().fromJson(jsonObject.toString(), JsonObject.class);
    }

    public static String toJsonString(Document document) {
        String xmlString = toString(document);
        JSONObject jsonObject = XML.toJSONObject(xmlString);
        return jsonObject.toString();
    }

    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

}