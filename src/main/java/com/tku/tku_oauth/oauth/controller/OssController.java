package com.tku.tku_oauth.oauth.controller;

import com.tku.tku_oauth.oauth.service.serviceImp.OssFileManagerService;
import com.tku.tku_oauth.oauth.utils.FileUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/1 10:33
 */
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssFileManagerService fileUploadService;

    /*
     * 方法简介.上传
     *
     * @author HSG
     * @date 创建时间 2020/6/9
     * @since V1.0
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile)
            throws Exception {
        return this.fileUploadService.upload(uploadFile);
    }


    /*
     * 方法简介.删除
     *
     * @author HSG
     * @date 创建时间 2020/6/9
     * @since V1.0
    */
    @RequestMapping("/deleteFile")
    @ResponseBody
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);

    }

    /*
     * 方法简介.下载
     *
     * @author HSG
     * @date 创建时间 2020/6/9
     * @since V1.0
    */
    @RequestMapping("/downloadFile")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "UTF-8"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}