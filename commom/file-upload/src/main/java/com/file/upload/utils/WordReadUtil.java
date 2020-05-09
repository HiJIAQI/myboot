package com.file.upload.utils;

import com.file.upload.enums.ResultEnum;
import com.file.upload.handler.FileException;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述：word文件读取工具类封装
 *
 * @author JIAQI
 * @date 2020 04/20 - 15:30
 */
public class WordReadUtil {

    /**
     * word文件读取
     *
     * @param pathName 所需读取的文件路径
     * @return 总页数、内容、段落数、字数
     */
    public static Map<String, Object> readWordContent(String pathName) {
        File file = new File(pathName);
        if (!file.exists()) {
            throw new FileException(ResultEnum.FILE_NOT_EXIST);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断word文档类型 做相应读取
        if (pathName.endsWith(".doc")) {
            map = redToDOC(file);
        } else if (pathName.endsWith("docx")) {
            map = redToDOCX(file);
        }
        return map;
    }

    /**
     * 读取Word2003以及之前的版本文档
     *
     * @param file 所需读取的文件
     * @return 总页数、内容、段落数、字数
     */
    private static Map<String, Object> redToDOC(File file) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream(file);
            //HWPFDocument doc = new HWPFDocument(fis);
            //WordExtractor ex = new WordExtractor(doc);
            // 用于读取和写入.doc扩展文件
            WordExtractor ex = new WordExtractor(fis);
            int pageCount = ex.getSummaryInformation().getPageCount();
            // 定义总字数
            int wordCount = 0;
            StringBuilder content = new StringBuilder();
            // 获取段落
            String[] docParagraph = ex.getParagraphText();
            for (String paragraph : docParagraph) {
                content.append("<p>" + paragraph + "</p>");
                wordCount += paragraph.trim().length();
            }
            // 总页数
            map.put("totalPage", pageCount);
            // 内容
            map.put("content", content);
            // 总段落数
            map.put("paragraphCount", docParagraph.length);
            // 总字数
            map.put("wordCount", wordCount);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException(ResultEnum.FILE_READ_ERROR);
        }
        return map;
    }

    /**
     * 读取word2007新型文档。
     *
     * @param file 所需读取的文件
     * @return 总页数、内容、段落数、字数
     */
    private static Map<String, Object> redToDOCX(File file) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            FileInputStream fis = new FileInputStream(file);
            // 用于读取和写入.docx扩展文件
            XWPFDocument docx = new XWPFDocument(fis);
            // 获取总页数
            int totalPage = docx.getProperties().getExtendedProperties()
                    .getUnderlyingProperties().getPages();
            // 定义总字数
            int wordCount = 0;
            StringBuilder content = new StringBuilder();
            // 获取段落
            List<XWPFParagraph> paragraphs = docx.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                wordCount += paragraph.getText().trim().length();
                content.append("<p>" + paragraph.getText() + "</p>");
            }

            /*for (XWPFParagraph xwpfParagraph : paragraphs) {
                String lineStr = "";
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    lineStr += xwpfRun.toString();
                    wordCount += xwpfRun.toString().trim().length();
                }
                content.append("<p>" + lineStr + "</p>");
            }*/

            // 总页数
            map.put("totalPage", totalPage);
            // 内容
            map.put("content", content);
            // 总段落数
            map.put("paragraphCount", paragraphs.size());
            // 总字数
            map.put("wordCount", wordCount);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileException(ResultEnum.FILE_READ_ERROR);
        }
        return map;
    }

    public static void main(String[] args) {
        //WordReadUtil.countLength();
        WordReadUtil.readWordContent("E:\\img\\book\\1\\BOOK20200507153300c828be93.docx");
    }

    public static StringBuilder countLength() {
        StringBuilder builder = new StringBuilder();
        File file = new File("E:\\img\\book\\1\\BOOK20200507152023e50ace6b.doc");
        try {
            FileInputStream fis = new FileInputStream(file);

            XWPFDocument xdoc = new XWPFDocument(fis);

            // 读取段落
            List<XWPFParagraph> paragraphs = xdoc.getParagraphs();

            // 总页数
            int pages = xdoc.getProperties().getExtendedProperties()
                    .getUnderlyingProperties().getPages();

            int wordCount = 0;
            int line = 1;
            for (XWPFParagraph xwpfParagraph : paragraphs) {
                int linLength = 0;
                String lineStr = "";
                // 获取段落内容
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    linLength += xwpfRun.toString().trim().length();
                    lineStr += xwpfRun.toString();
                    wordCount += xwpfRun.toString().trim().length();
                }
                builder.append(lineStr + "</br>");
                System.out.println("第" + line + "行内容：'" + lineStr + "'      长度：" + linLength);
                line++;
            }
            System.err.println("文章总段落数：" + paragraphs.size() + " 段");
            System.err.println("文章总字数：" + wordCount);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder;
    }
}
