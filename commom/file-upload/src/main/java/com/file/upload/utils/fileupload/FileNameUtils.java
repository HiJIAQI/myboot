package com.file.upload.utils.fileupload;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/15 - 17:30
 */
public class FileNameUtils {

    /**
     * 获取文件后缀
     *
     * @param fileName 原始的文件名
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     *
     * @param prefix         自定义文件前缀
     * @param fileOriginName 源文件名
     */
    public static String getNewFileName(String prefix, String fileOriginName) {
        //判断自定义文件前缀是否为空
        if (StringUtils.isEmpty(prefix)) {
            return buildFileBody() + FileNameUtils.getSuffix(fileOriginName);
        }
        return prefix + buildFileBody() + FileNameUtils.getSuffix(fileOriginName);
    }

    /**
     * 日期(年月日时分秒)+获取UUID的前8位数字进行
     */
    private static String buildFileBody() {
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return date + UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}
