package com.viverselftest.util;

import com.viverselftest.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Locale;

/**
 * @author muhaoxu
 * @date 2016年12月20日 下午5:36:57
 * @version:1.0 类说明
 */
@Component
public class ErrorMsgUtils {
    @Autowired
    protected MessageSource messageSource;

    public String errorMsg(String system_language, String key) {

        Locale locale = new Locale("zh", "CN");
        if (system_language.contains("_")) {
            locale = new Locale(system_language.split("_")[0], system_language.split("_")[1]);
        }

        String value = messageSource.getMessage(key, null, locale);

        return value;
    }

    /**
     * 处理验证结果
     * 直接抛出错误
     *
     * @param error 验证结果
     * @param language 系统语言
     */
    public void handleValidatoinError(BindingResult error, String language) {
        if (error.hasErrors()) {
            String errorCode = error.getFieldError().getDefaultMessage();
            throw new ErrorException(errorCode, this.errorMsg(language, errorCode));
        }
    }
}
