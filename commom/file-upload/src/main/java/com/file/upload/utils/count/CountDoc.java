package com.file.upload.utils.count;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class CountDoc {

    public static void countLength() throws IOException {
        File file = new File("E:\\MyImg\\20200415\\ONE202004151744260fa79cf8.doc");
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);

            // 读取段落
            List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

//            // 总页数
//            int pages = xdoc.getProperties().getExtendedProperties()
//                    .getUnderlyingProperties().getPages();
//            // 忽略空格的总字符数
//            int wordCount = xdoc.getProperties().getExtendedProperties()
//                    .getUnderlyingProperties().getCharacters();
//            // 另外还有getCharactersWithSpaces()方法获取带空格的总字数。
//            System.out.println("Total pages=" + pages +"页; "+ " Total wordCount=" + wordCount);
//

            int count = 0;
            int i = 1;
            for (XWPFParagraph xwpfParagraph : paragraphs) {
                int linLength = 0;
                String lineStr = "";
                // 获取段落内所有XWPFRun
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    linLength += xwpfRun.toString().trim().length();
                    lineStr += xwpfRun.toString();
                    count += xwpfRun.toString().trim().length();
                }
                System.out.println("第" + i + "行内容：'" + lineStr + "'      长度：" + linLength);
                i++;
            }
            System.out.println("文章总行数：" + paragraphs.size() + " 行");
            System.out.println("文章总字数：" + count);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        CountDoc.countLength();
    }

}
