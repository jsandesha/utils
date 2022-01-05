package com.highpeaksw.utils.xml;

import java.io.File;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.random.RandomGenerator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.RandomStringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.javafaker.Faker;
import com.highpeaksw.utils.DateUtilV2;
import com.highpeaksw.utils.exception.DataException;

public class XMLGeneratorUtil {

    private static final Faker faker = new Faker();

    private static final String[] currencies = new String[] { "INR", "USD", "EUR" };

    public static void main( String[] args ) throws ParserConfigurationException, TransformerException, DataException
    {
        //        generateXml();
        generatXmlCopy();

    }

    private static void generatXmlCopy() throws ParserConfigurationException, TransformerException, DataException
    {
        LocalDateTime start = LocalDateTime.now();

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        // root element
        Element root = document.createElement("payments");
        document.appendChild(root);

        for( int i = 0; i < 5000; i++ )
        {
            // employee element
            Element record = document.createElement("record");
            root.appendChild(record);

            // set an attribute to staff element
            Attr attr = document.createAttribute("id");
            attr.setValue(String.valueOf(UUID.randomUUID()));
            record.setAttributeNode(attr);

            Element payeeName = document.createElement("payee_name");
            payeeName.appendChild(document.createTextNode(faker.name().firstName()));
            record.appendChild(payeeName);

            // lastname element
            Element payerName = document.createElement("payer_name");
            payerName.appendChild(document.createTextNode(faker.name().firstName()));
            record.appendChild(payerName);

            // email element
            Element payeeIfsc = document.createElement("payee_ifsc");
            payeeIfsc.appendChild(document.createTextNode(RandomStringUtils.randomAlphabetic(4).toUpperCase()
                    + RandomStringUtils.randomNumeric(7).toUpperCase()));
            record.appendChild(payeeIfsc);

            // department elements
            Element payerIfsc = document.createElement("payer_ifsc");
            payerIfsc.appendChild(document.createTextNode(RandomStringUtils.randomAlphabetic(4).toUpperCase()
                    + RandomStringUtils.randomNumeric(7).toUpperCase()));
            record.appendChild(payerIfsc);

            Element payerAccNo = document.createElement("payer_acno");
            payerAccNo.appendChild(document.createTextNode(RandomStringUtils.randomNumeric(10)));
            record.appendChild(payerAccNo);

            Element payeeAccNo = document.createElement("payee_acno");
            payeeAccNo.appendChild(document.createTextNode(RandomStringUtils.randomNumeric(10)));
            record.appendChild(payeeAccNo);

            Element currency = document.createElement("currency");
            currency.appendChild(document.createTextNode(currencies[RandomGenerator.getDefault().nextInt(0, 3)]));
            record.appendChild(currency);
        }

        // create the xml file
        //transform the DOM Object to an XML File
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("/Users/sandesha/Documents/xml-test/test_copy_vx.xml"));

        LocalDateTime end = LocalDateTime.now();
        // If you use
        // StreamResult result = new StreamResult(System.out);
        // the output will be pushed to the standard output ...
        // You can use that for debugging 

        transformer.transform(domSource, streamResult);

        System.out.println("Time took = " + DateUtilV2.getTheNumberOfSecondsBetweenTwoLocalDateTimes(start, end));
    }

    private static void generateXml() throws ParserConfigurationException, TransformerException
    {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        //Root element
        Element rootElement = doc.createElement("payments");
        doc.appendChild(rootElement);

        for( int i = 0; i < 5; i++ )
        {
            Element docElement = doc.createElement("record");
            rootElement.appendChild(docElement);
            Attr attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(UUID.randomUUID()));
            docElement.setAttributeNode(attr);

            Element payerName = doc.createElement("payer_name");
            payerName.appendChild(doc.createTextNode(faker.name().firstName()));
            docElement.appendChild(payerName);

            Element payeeName = doc.createElement("payee_name");
            payerName.appendChild(doc.createTextNode(faker.name().firstName()));
            docElement.appendChild(payeeName);

            Element payerIfsc = doc.createElement("payer_ifsc");
            payerName.appendChild(doc.createTextNode(String.valueOf(UUID.randomUUID())));
            docElement.appendChild(payerIfsc);

            Element payeeIfsc = doc.createElement("payee_ifsc");
            payerName.appendChild(doc.createTextNode(String.valueOf(UUID.randomUUID())));
            docElement.appendChild(payeeIfsc);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("/Users/sandesha/Documents/xml-test/test.xml"));
        transformer.transform(source, result);

        // Output to console for testing
        StreamResult consoleResult = new StreamResult(System.out);
        transformer.transform(source, consoleResult);

    }
}
