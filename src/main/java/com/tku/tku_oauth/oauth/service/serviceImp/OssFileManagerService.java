package com.tku.tku_oauth.oauth.service.serviceImp;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.model.*;
import com.tku.tku_oauth.oauth.config.OssConfiguration;
import com.tku.tku_oauth.oauth.service.IResourceInfoService;
import com.tku.tku_oauth.oauth.utils.FileUploadResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 描述：
 *
 * @author HSG
 * @version 1.0
 * @date 2020/6/8 9:32
 */
@Service
public class OssFileManagerService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png",".doc",".docx"};
    @Autowired
    private OSS ossClient;

    @Autowired
    private OssConfiguration aliyunConfig;

    @Autowired
    private IResourceInfoService iResourceInfoService;
    /**
     * @author HSG
     * @desc 文件上传
     * 文档链接 https://help.aliyun.com/document_detail/84781.html?spm=a2c4g.11186623.6.749.11987a7dRYVSzn
     */
    public FileUploadResult upload(MultipartFile uploadFile) {
        JSONObject jsonObject = new JSONObject();
        String resourceType="",resourceCategory = "", resourceContent="";

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(),
                    type)) {
                isLegal = true;
                break;
            }
        }
        //封装Result对象，并且将文件的byte数组放置到result对象中
        FileUploadResult fileUploadResult = new FileUploadResult();
        if (!isLegal) {
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        //文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);// 这个也是下载、删除要用的objectName
        // 上传到阿里云
        try {
            PutObjectResult objectResult = ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));
            String ss = objectResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        jsonObject.put("resourceName",fileName);
        jsonObject.put("resourceUrl",this.aliyunConfig.getUrlPrefix() + "/" + filePath);
        jsonObject.put("resourceType",resourceType);
        jsonObject.put("resourceCategory",resourceCategory);
        jsonObject.put("resourceContent",resourceContent);
        iResourceInfoService.addResourceInfo(jsonObject);
        fileUploadResult.setStatus("done");
        fileUploadResult.setResponse("success");
        //this.aliyunConfig.getUrlPrefix() + filePath; //文件路径需要保存到数据库
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + "/" + filePath);
        fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
        return fileUploadResult;
    }
    /**
     * @author HSG
     * @desc 生成路径以及文件名 例如：//images/2019/04/28/15564277465972939.jpg
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return aliyunConfig.getFilehost() + "/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }
    /**
     * @author HSG
     * @desc 查看文件列表
     * 文档链接 https://help.aliyun.com/document_detail/84841.html?spm=a2c4g.11186623.2.13.3ad5b5ddqxWWRu#concept-84841-zh
     */
    public List<OSSObjectSummary> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }
    /**
     * @author HSG
     * @desc 删除文件
     * 文档链接 https://help.aliyun.com/document_detail/84842.html?spm=a2c4g.11186623.6.770.4f9474b4UYlCtr
     */
    public FileUploadResult delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        FileUploadResult fileUploadResult = new FileUploadResult();
        fileUploadResult.setName(objectName);
        fileUploadResult.setStatus("removed");
        fileUploadResult.setResponse("success");
        return fileUploadResult;
    }

    /**
     * @author HSG
     * @desc 下载文件
     * 文档链接 https://help.aliyun.com/document_detail/84823.html?spm=a2c4g.11186623.2.7.37836e84ZIuZaC#concept-84823-zh
     */
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), objectName);
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }
}