package com.fw121.core.rest;

import com.fw121.core.domain.R;
import com.fw121.core.exception.FWException;
import com.fw121.core.service.LocaleMessageService;
import com.fw121.core.util.ImageUtil;
import com.fw121.core.util.JwtUtils;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传控制器
 */
@Api(tags ="测试")
@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private LocaleMessageService messageService;

    @Value("${file.location}")
    private String location;

    @Value("${file.path}")
    private String fileContextPath;

    @RequestMapping(value="upload",method = {RequestMethod.POST, RequestMethod.PUT})
    public R upload(@RequestParam("file") MultipartFile multipartFile)  {
        if (multipartFile.isEmpty() || multipartFile.getOriginalFilename().length() <= 0) {
            throw new FWException("图片不能为空");
        }
        String contentType = multipartFile.getContentType();
        if (!contentType.contains("")) {
            throw new FWException("图片格式错误");
        }
        String root_fileName = multipartFile.getOriginalFilename();
        logger.info("上传文件:name={},type={}", root_fileName, contentType);
        //获取路径
        String return_path = JwtUtils.getCurrentUsername();
        String filePath = location + return_path;
        logger.info("文件保存路径={}", filePath);
        String file_name = null;
        try {
            file_name = ImageUtil.saveImg(multipartFile, filePath);
            if(file_name != null || file_name.length() > 0){
                String url = fileContextPath + return_path+ File.separator+file_name;
                return R.ok(url);
            }
        } catch (IOException e) {
            throw new FWException("上传图片异常");
        }
        return R.error("上传图片出错");
    }

}
