package com.sundyn.util;

import org.apache.poi.util.*;
import org.apache.poi.hwpf.converter.*;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.commons.io.*;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import org.apache.poi.xwpf.usermodel.*;
import java.io.*;
import org.apache.poi.xwpf.converter.xhtml.*;
import org.apache.poi.xwpf.converter.core.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;
import java.util.*;

public class WordToHtmlUtil
{
    private static final String doc = "/home/lpf/devTool/poi-3.13-beta1/test-data/document/Bug46220.doc";
    private static final String docx = "/home/lpf/abc.docx";
    
    public static void main(final String[] args) throws Exception {
        final String htmlResusePath = "/home/lpf/";
        docx("/home/lpf/abc.docx", htmlResusePath, "abc.html");
    }
    
    public static void doc(final String sampleFileName, final String path, final String htmlFileName) throws Exception {
        doc(new FileInputStream(sampleFileName), path, htmlFileName);
    }
    
    public static void doc(final InputStream in, final String path, final String htmlFileName) throws Exception {
        final HWPFDocument hwpfDocument = new HWPFDocument(in);
        final Document newDocument = XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument();
        final WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(newDocument);
        wordToHtmlConverter.setPicturesManager((PicturesManager)new PicturesManager() {
            public String savePicture(final byte[] content, final PictureType pictureType, final String suggestedName, final float widthInches, final float heightInches) {
                try {
                    final FileOutputStream os = new FileOutputStream(String.valueOf(path) + suggestedName);
                    final InputStream is = new ByteArrayInputStream(content);
                    IOUtils.copy(is, (OutputStream)os);
                    is.close();
                    os.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(suggestedName);
                return suggestedName;
            }
        });
        wordToHtmlConverter.processDocument((HWPFDocumentCore)hwpfDocument);
        final StringWriter stringWriter = new StringWriter();
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("encoding", "utf-8");
        transformer.setOutputProperty("method", "html");
        transformer.transform(new DOMSource(wordToHtmlConverter.getDocument()), new StreamResult(new FileOutputStream(String.valueOf(path) + htmlFileName)));
        final String result = stringWriter.toString();
    }
    
    public static void docx(final String sampleFileName, final String path, final String htmlFileName) throws Exception {
        docx(new FileInputStream(sampleFileName), path, htmlFileName);
    }
    
    public static void docx(final InputStream in, final String path, final String htmlFileName) throws Exception {
        final XWPFDocument xwpfDocument = new XWPFDocument(in);
        final File imageFolderFile = new File(path);
        final XHTMLOptions options = XHTMLOptions.create();
        options.indent(4);
        options.setFragment(false);
        options.setExtractor((IImageExtractor)new FileImageExtractor(imageFolderFile));
        options.URIResolver((IURIResolver)new FileURIResolver(imageFolderFile));
        options.setIgnoreStylesIfUnused(false);
        final OutputStream out = new FileOutputStream(new File(String.valueOf(path) + htmlFileName));
        XHTMLConverter.getInstance().convert(xwpfDocument, out, (XHTMLOptions)options);
        final File index = new File(String.valueOf(path) + "index.html");
        final org.jsoup.nodes.Document doc = Jsoup.parse(index, "gbk");
        final Elements es = doc.getElementsByTag("img");
        for (final Element e : es) {
            final String src = e.attr("src");
            final int i = src.lastIndexOf("/word/");
            System.out.println(i);
            final String imgpath = src.substring(i + 1);
            System.out.println(imgpath);
            e.attr("src", imgpath);
        }
        IOUtils.write(doc.toString().getBytes("utf-8"), (OutputStream)new FileOutputStream(index));
    }
}
