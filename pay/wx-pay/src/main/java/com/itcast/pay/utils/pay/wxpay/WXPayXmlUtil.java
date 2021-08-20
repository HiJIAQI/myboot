package com.itcast.pay.utils.pay.wxpay;

import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public final class WXPayXmlUtil {

    /**
     * XML解析
     *
     * @param inStream 微信发的流信息
     * @param encoding 编码格式
     * @return
     */
    public static String inputStream2String(InputStream inStream, String encoding) {
        String result = null;
        ByteArrayOutputStream outStream = null;
        try {
            if (inStream != null) {
                outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[1024];
                int count = 0;
                while ((count = inStream.read(tempBytes)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                tempBytes = null;
                outStream.flush();
                result = new String(outStream.toByteArray(), encoding);
                outStream.close();
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
}
