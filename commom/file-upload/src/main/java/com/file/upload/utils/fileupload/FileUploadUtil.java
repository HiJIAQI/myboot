package com.file.upload.utils.fileupload;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义的MultipartFile的实现类
 *
 * @author JIAQI
 * @date 2018/12/25 - 22:51
 */
public class FileUploadUtil {

    /**
     * 图片上传(带压缩处理)
     *
     * @param path          需要上传的路径
     * @param filePrefix    进行自定义的前缀
     * @param multipartFile 图片
     * @return 图片名称
     */
    public static String uploadImg(String path, String filePrefix, MultipartFile multipartFile) {
        return upload(path, filePrefix, multipartFile, true);
    }

    /**
     * 文件上传
     *
     * @param path          需要上传的路径
     * @param filePrefix    进行自定义的前缀
     * @param multipartFile 文件
     * @return 文件名称
     */
    public static String uploadFile(String path, String filePrefix, MultipartFile multipartFile) {
        return upload(path, filePrefix, multipartFile, false);
    }

    /**
     * 将上传的图片保存到服务器
     *
     * @param path          上传服务器路径
     * @param filePrefix    文件名称前缀
     * @param multipartFile 所上传的图片
     * @param isCondense    是否进行压缩
     * @return 保存到数据库的文件名
     */
    private static String upload(String path, String filePrefix, MultipartFile multipartFile, boolean isCondense) {

        // 获取文件名
        String originalFilename = multipartFile.getOriginalFilename();

        // 获取文件后缀名
        String ext = FileNameUtils.getSuffix(originalFilename);

        // 判断文件格式
        List<String> extList = Arrays.asList(".jpg", ".png", ".jpeg");
        if (!extList.contains(ext)) {
            throw new RuntimeException("文件格式有误");
        }

        // 获取新的图片名
        String newFilename = FileNameUtils.getNewFileName(filePrefix, originalFilename);

        // 将文件写入服务器
        String fileLocalPath = path + "/" + newFilename;
        File localFile = new File(fileLocalPath);

        //判断文件父目录是否存在 若不存在就去创建
        if (!localFile.exists()) {
            File file = new File(localFile.getParent());
            file.getParentFile().mkdir();
        }
        //判断文件子目录是否存在 若不存在就去创建
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdir();
        }

        //保存成功返回图片名字
        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            throw new RuntimeException("图片上传失败");
        }

        // 对文件进行压缩处理
        if (isCondense) {
            condenseImg(fileLocalPath, fileLocalPath);
        }

        return newFilename;
    }

    /**
     * 功能描述: 文件删除
     *
     * @param fileName 文件名称
     * @param path     路径
     */
    public static boolean deleteMsg(String path, String fileName) {
        boolean flag = false;
        List<String> msgs = new ArrayList<>();
        File file = new File(path + "/" + fileName);
        if (file.exists() && file.isFile()) {
            flag = file.delete();
        }
        return flag;
    }

    /**
     * 功能描述:图片压缩
     * 相当于对保存的文件进行压缩
     *
     * @param oldImgPath 原图文件的路径
     * @param newImgPath 压缩后文件的路径
     */

    private static void condenseImg(String oldImgPath, String newImgPath) {
        try {
            Thumbnails.of(oldImgPath)
                    // 图片缩放80%, 不能和size()一起使用
                    .scale(0.7f)
                    // 图片质量压缩70%
                    .outputQuality(0.6f)
                    .toFile(newImgPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

}

