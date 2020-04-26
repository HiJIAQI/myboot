package com.file.upload.constant;

/**
 * 功能描述：文件上传常量
 *
 * @author JIAQI
 * @date 2020/4/21 - 11:01
 */
public class FileConstant {

    //书籍功能模块文件上传路径
    public static final String UPLOAD_PATH = "E:/img/";

    public static final String BOOK_FAIL_UPLOAD_PATH = "book/";

    //书籍功能模块文件读取路径
    public static final String BOOK_FAIL_READ_PATH = "/book/";

    //书籍功能模块文件命名前缀
    public static final String BOOK_IMG_PREFIX = "BOOK";
    public static final String BOOK_FAIL_PREFIX = "BOOK";

    // 文件格式校验规则
    public static final String BOOK_IMG_PATTERN[] = {".jpg", ".png", ".jpeg"};
    public static final String BOOK_WORD_PATTERN[] = {".doc", ".docx"};

}
