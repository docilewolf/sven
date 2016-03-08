package com.mrlu.sven.controller;

import com.mrlu.sven.controller.aspect.NotAspect;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by stefan on 16-2-24.
 * 验证码操作
 */
@RequestMapping("/admin/captcha")
@Controller
public class CaptchaController {

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @NotAspect
    @RequestMapping("getImage")
    public void getCaptchaImage(String id,HttpServletResponse response){
        BufferedImage bufferedImage = imageCaptchaService.getImageChallengeForID(id);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0L);
            response.setContentType("image/jpeg");
            ImageIO.write(bufferedImage, "jpeg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注意：在验证失败后，会将id对应的验证码从缓存中清除掉，所以验证失败后需要重新再获取验证码
     * @param id
     * @param captcha
     * @return
     */
    @RequestMapping("validCaptcha")
    @ResponseBody
    public Object validCaptcha(String id, String captcha){
        Boolean result = imageCaptchaService.validateResponseForID(id, captcha);
        return result;
    }
}
