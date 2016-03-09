package com.mrlu.sven.service.util;

import com.mrlu.sven.common.SvenException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by stefan on 16-3-9.
 */
@Component
public class QiniuUtil {

    private static String ACCESS_KEY = "";
    private static String SECRET_KEY = "";
    private static String BUCKETNAME = "";
    private static String QINIU_DOMAIN = "";

    public static String upload(byte[] data, String fileName) throws QiniuException, SvenException {
        String key = getFileName(fileName);
        UploadManager uploadManager = new UploadManager();
        Response response = uploadManager.put(data, key, getUpToken());
        if(response.statusCode != 200){
            throw new SvenException(response.statusCode, response.error);
        }

        return getDownloadUrl(key);
    }

    private static String getFileName(String fileName){
        String suffix = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
        return "sven-" + UUID.randomUUID().toString() + suffix;
    }

    private static String getUpToken(){
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKETNAME);
    }

    private static String getDownloadUrl(String key){
        return "http://" + QINIU_DOMAIN + "/" + key;
    }

    @Value("${access_key}")
    public void setACCESS_KEY(String ACCESS_KEY) {
        QiniuUtil.ACCESS_KEY = ACCESS_KEY;
    }

    @Value("${secret_key}")
    public void setSECRET_KEY(String SECRET_KEY) {
        QiniuUtil.SECRET_KEY = SECRET_KEY;
    }

    @Value("${bucketname}")
    public void setBUCKETNAME(String BUCKETNAME) {
        QiniuUtil.BUCKETNAME = BUCKETNAME;
    }

    @Value("${qiniu_domain}")
    public void setQINIU_DOMAIN(String QINIU_DOMAIN) {
        QiniuUtil.QINIU_DOMAIN = QINIU_DOMAIN;
    }
}
