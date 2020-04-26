package com.file.upload.utils.count;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能描述：
 * https://zhuanlan.zhihu.com/p/101184286
 * @author JIAQI
 * @date 2020/4/16 - 15:22
 */
public class WordReader {
    WordExtractor wordExtractor;

    public static void main(String[] args) {
        System.out.println("该word文档(docx格式)总页数如下：");
        new WordReader().getPageCount("E:\\MyImg\\20200415\\ONE202004151744260fa79cf8.doc");

        System.out.println("\n获取整个word文本内容:");
        System.out.println(new WordReader().getTextFromWord("E:\\MyImg\\20200415\\ONE202004151744260fa79cf8.doc"));

        System.out.println("按段获取文本内容:");
        System.out.println(new WordReader().getTextByParagraph("E:\\MyImg\\20200415\\ONE202004151744260fa79cf8.doc"));
    }

    // 统计word文件总页数(仅docx格式的有效！) doc格式也有相应的方法，但是由于doc本身的问题，导致获取的页数总是错误的！
    public void getPageCount(String filePath) {
        XWPFDocument docx;
        try {
            docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));
            int pages = docx.getProperties().getExtendedProperties()
                    .getUnderlyingProperties().getPages();// 总页数
            int wordCount = docx.getProperties().getExtendedProperties()
                    .getUnderlyingProperties().getCharacters();// 忽略空格的总字符数
            // 另外还有getCharactersWithSpaces()方法获取带空格的总字数。
            System.out.println("Total pages=" + pages +"页; "+ " Total wordCount=" + wordCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取word文档中所有文本的方法(仅对doc文件有效)
    public String getTextFromWord(String filePath) {
        String res = null;
        File file = new File(filePath);
        try {
            FileInputStream fis = new FileInputStream(file);
            wordExtractor = new WordExtractor(fis);
            // 获取所有文本
            res = wordExtractor.getText();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    // 按段获取文本(仅对doc文件有效)
    public String getTextByParagraph(String filePath) {
        String res = null;
        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);
            wordExtractor = new WordExtractor(fis);
            // 获取段文本
            String[] strArray = wordExtractor.getParagraphText();
            for (int i = 0; i < strArray.length; i++) {
                System.out.println("第 " + (i+1)+" 段\n"+strArray[i]);
            }

            // 这个构造函数从InputStream中加载Word文档
            HWPFDocument doc = new HWPFDocument(
                    (InputStream) new FileInputStream(filePath));
            // 这个类为HWPF对象模型,对文档范围段操作
            Range range = doc.getRange();
            int num = range.numParagraphs();
            System.out.println("该文档共" + num + "段");//空行也算一段
            System.out.println("获取第"+num+"段内容如下：\n"+range.getParagraph(num-1).text());
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
