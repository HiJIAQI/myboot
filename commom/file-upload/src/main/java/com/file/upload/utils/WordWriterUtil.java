package com.file.upload.utils;

import com.file.upload.enums.ResultEnum;
import com.file.upload.handler.FileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 功能描述：word文档内容修改工具类
 *
 * @author JIAQI
 * @date 2020/5/9 - 15:38
 */
public class WordWriterUtil {

    public static void writerWord(String requireStr, String pathName) {
        File file = new File(pathName);
        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(file);
            String[] values = requireStr.split("\r\n");
            for (String value : values) {
                // 创建段落
                XWPFParagraph firstParagraph = document.createParagraph();
                XWPFRun run = firstParagraph.createRun();
                // 换行
                //run.addBreak();
                // 缩进
                run.addTab();
                run.setText(value);
            }
            document.write(out);
            out.close();
        } catch (Exception e) {
            throw new FileException(ResultEnum.FILE_WRITER_ERROR);
        }
    }

    public static void main(String[] args) throws Exception {
        String requireStr = "测试需求是主要是整理测试焦点（包括一些界面、输入域、业务流程)";
        requireStr += "\r\n可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "\r\n可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        requireStr += "可以说，测试需求是告诉你要测什么，而测试用例是告诉你怎么测。";
        writerWord(requireStr, "E:\\img\\book\\1\\BOOK202005071454195ef2a8b4.doc");
        System.out.println("create_table document written success.");
    }

}
