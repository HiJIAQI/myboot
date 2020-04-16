package com.file.upload.controller;

import com.file.upload.utils.fileupload.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/15 - 16:36
 */
@Controller
public class FileUploadController {

    //获取系统类型  区分是window 还是Linux环境
    private final String SONPATH = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
    private final String PATH = System.getProperty("os.name").toLowerCase().startsWith("win") ? "E:/MyImg" + SONPATH : "/data/imageserver/wechat" + SONPATH;

    /**
     * 进入文件上传页
     */
    @GetMapping("/upload")
    public String uploadView() {
        return "/upload";
    }

    /**
     * 单文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadView(MultipartFile file) throws Exception {
        String saveUpload = FileUploadUtil.uploadImg(PATH, "ONE", file);
        return saveUpload;
    }

    /**
     * 多文件上传
     */
    @PostMapping("/batchUpload")
    @ResponseBody
    public String uploadView(MultipartFile[] files) throws Exception {
        //String saveUpload = FileUploadUtil.uploadImg(PATH, files, "ONE");
        return "saveUpload";
    }

}
