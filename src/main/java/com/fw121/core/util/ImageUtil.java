package com.fw121.core.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 图片上传工具类
 * Created by mvt-zhaosandong-mac on 2018/7/22.
 */
public class ImageUtil {

    /**
     * 保存文件，直接以multipartFile形式
     * @param multipartFile
     * @param path 文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveImg(MultipartFile multipartFile, String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String root_fileName = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + root_fileName.substring(root_fileName.lastIndexOf("."));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }

}
