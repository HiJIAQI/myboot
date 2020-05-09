package com.file.upload.controller;

import com.file.upload.UserDTO;
import com.file.upload.constant.FileConstant;
import com.file.upload.utils.WordReadUtil;
import com.file.upload.utils.filedownload.FileDownLoadUtil;
import com.file.upload.utils.fileupload.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 功能描述：
 *
 * @author JIAQI
 * @date 2020/4/15 - 16:36
 */
@Controller
@Slf4j
public class FileUploadController {

    //获取系统类型  区分是window 还是Linux环境
    private final String SONPATH = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
    private final String PATH = System.getProperty("os.name").toLowerCase().startsWith("win") ? "E:/MyImg" + SONPATH : "/data/imageserver/wechat" + SONPATH;

    /**
     * 走参数校验注解
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/save/valid")
    @ResponseBody
    public String save(@Validated @RequestBody UserDTO userDTO) {
        return "SUCCESS";
    }

    /**
     * 进入文件上传页
     */
    @GetMapping("/upload")
    public String uploadView(Model model) {
        StringBuilder builder = WordReadUtil.countLength();
        model.addAttribute("builder", builder);
        return "/upload";
    }

    /**
     * 单文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadView(MultipartFile file) throws Exception {
        String saveUpload = FileUploadUtil.uploadFile(PATH, "ONE", FileConstant.BOOK_IMG_PATTERN, file);
        return saveUpload;
    }

    /**
     * 多文件上传
     */
    @PostMapping("/batchUpload")
    @ResponseBody
    public String uploadView(MultipartFile[] files) throws Exception {
        return "saveUpload";
    }

    //文件下载相关代码

    /**
     * 文件下载
     */
    @GetMapping("/downloadFile")
    @ResponseBody
    public String downloadFile(String fileName) throws Exception {
        log.info("单个文件下载接口入参:[filename={}]", fileName);
        if (fileName.isEmpty()) {
            return "文件不能为空";
        }
        try {
            FileDownLoadUtil.downOneFile("E://MyImg//20200415//", fileName);
        } catch (Exception e) {
            log.error("单个文件下载接口异常:{fileName={},ex={}}", fileName, e);
        }
        return "下载失败";
    }

    /**
     * 批量打包下载文件
     *
     * @param fileName 文件名，多个用英文逗号分隔
     */
    @GetMapping("/down-together")
    public void downTogether(@RequestParam(value = "filename", required = false) String fileName) {
        log.info("批量打包文件下载接口入参:[filename={}]", fileName);
        if (fileName.isEmpty()) return;
        List<String> fileNameList = Arrays.asList(fileName.split(","));
        if (CollectionUtils.isEmpty(fileNameList)) return;
        try {
            FileDownLoadUtil.downTogetherAndZip("E://MyImg//20200415//", fileNameList);
        } catch (Exception e) {
            log.error("批量打包文件下载接口异常:{fileName={},ex={}}", fileName, e);
        }
    }

}
