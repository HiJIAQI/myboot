package com.file.upload.utils.fileupload;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * 自定义的MultipartFile的实现类
 * 主要用于Multipart与base64文件之间的转换
 *
 * @author JIAQI
 * @date 2018/12/25 - 22:51
 */
public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    /**
     * base64转MultipartFile
     *
     * @param file base64文件字符串
     * @return MultipartFile
     */
    public static MultipartFile base64ToMultipart(String file) {
        try {
            String[] baseStrs = file.split(",");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MultipartFile转BASE64字符串
     *
     * @param file base64文件字符串
     * @return Base64字符串
     */
    public static String MultipartToBase64(MultipartFile file) {
        BASE64Encoder bEncoder = new BASE64Encoder();
        String[] suffix = file.getOriginalFilename().split("\\.");
        String prefix = "data:image/jpg;base64,".replace(getFilePrefix(file), suffix[suffix.length - 1]);
        String base64EncoderImg = null;
        try {
            base64EncoderImg = prefix + bEncoder.encode(file.getBytes()).replaceAll("[\\s*\t\n\r]", "");
        } catch (IOException e) {
            throw new RuntimeException("MultipartFile转BASE64字符串失败,请重试");
        }
        return base64EncoderImg;
    }

    //获取文件后缀
    private static String getFilePrefix(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }
}

