package com.mrlu.sven.common;

import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by stefan on 16-1-13.
 */
public class HandleModelResult {
    private static Logger logger = Logger.getLogger(HandleModelResult.class);

    public static ModelResult returnOkModelResult(Object... entitys){
        logger.info("[HandleModelResult result:] " + JSON.toJSONString(entitys));
        ModelResult result = new ModelResult(ModelResult.CODE_200);
        result.setMessage("操作成功");
        for(Object entity: entitys){
            if(entity instanceof List){
                result.setList((List) entity);
            }else if(entity instanceof ResultPage){
                result.setResultPage((ResultPage) entity);
            }else{
                result.setEntity(entity);
            }
        }

        return result;
    }

    public static ModelResult returnExceptionModelResult(Throwable e){
        logger.info("[HandleModelResult exception:] " + JSON.toJSONString(e));
        if(e instanceof SvenException){
            return HandleModelResult.return400ModelResult((SvenException) e);
        }

        return HandleModelResult.return500ModelResult();
    }

    public static ModelResult return400ModelResult(SvenException e){
        ModelResult result = new ModelResult(e.getErrCode());
        result.setMessage(e.getErrDesc());
        return result;
    }

    public static ModelResult return500ModelResult(){
        ModelResult result = new ModelResult(ModelResult.CODE_500);
        result.setMessage("服务器异常");
        return result;
    }

    public static ModelResult return401ModelResult(){
        ModelResult result = new ModelResult(HttpStatus.UNAUTHORIZED.value());
        result.setMessage("没有认证");
        return result;
    }
}
