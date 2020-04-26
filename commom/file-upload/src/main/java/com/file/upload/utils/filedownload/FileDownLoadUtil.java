package com.file.upload.utils.filedownload;

import com.file.upload.enums.ResultEnum;
import com.file.upload.handler.FileException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/16 - 14:04
 */

public class FileDownLoadUtil {

    /**
     * 单个文件下载
     *
     * @param path     文件所在路径
     * @param fileName 单个文件名
     */
    public static void downOneFile(String path, String fileName) {

        File file = new File(path + fileName);

        //判断文件是否存在
        if (!file.exists()) {
            throw new FileException(ResultEnum.FILE_NOT_EXIST);
        }

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //设置返回文件的格式
        response.reset();
        //以文件流的方式返回
        response.setContentType("application/octet-stream");
        //采用utf-8 字符集
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        //设置文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new FileException(ResultEnum.FILE_DOWNLOAD_ERROR);
        }
    }

    /**
     * 文件批量打包下载
     *
     * @param path         文件所在路径
     * @param fileNameList 多个文件名，用英文逗号分隔开
     * @throws IOException
     */
    public static void downTogetherAndZip(String path, List<String> fileNameList) throws IOException {
        HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        resp.setContentType("application/x-msdownload");
        //暂时设定压缩下载后的文件名字为test.zip
        resp.setHeader("Content-Disposition", "attachment;filename=test.zip");
        String str = "";
        String rt = "\r\n";
        ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());
        for (String filename : fileNameList) {
            str += filename + rt;
            File file = new File(path + filename);
            zos.putNextEntry(new ZipEntry(filename));
            FileInputStream fis = new FileInputStream(file);
            byte b[] = new byte[1024];
            int n = 0;
            while ((n = fis.read(b)) != -1) {
                zos.write(b, 0, n);
            }
            zos.flush();
            fis.close();
        }
        //设置解压文件后的注释内容
        zos.setComment("download success:" + rt + str);
        zos.flush();
        zos.close();
    }

}
