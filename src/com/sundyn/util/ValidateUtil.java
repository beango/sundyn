package com.sundyn.util;

import com.opensymphony.xwork2.util.LocalizedTextUtil;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;
import java.util.Set;

public class ValidateUtil {
	private static Validator validator; // 它是线程安全的
	 
    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
        validator = factory.getValidator();
    }
    public static <T> void validate(T t) throws Exception{
        validate(t, null);
    }

    public static <T> void validate(T t, Locale locale) throws Exception {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t); 
        if(constraintViolations.size() > 0) { 
            String validateError = ""; 
            for(ConstraintViolation<T> constraintViolation: constraintViolations) {
                String msg = constraintViolation.getMessage();
                System.out.println(msg);
                if (null != locale && msg.startsWith("{") && msg.endsWith("}")){
                    HttpServletRequest req = ServletActionContext.getRequest();
                    System.out.println(msg.substring(1, msg.length()-1) + ", " + locale + req.getLocale());
                    msg = LocalizedTextUtil.findDefaultText(msg.substring(1, msg.length()-1), locale);
                }

                validateError += msg + "\r\n";
            } 
            throw new ValidException(validateError);
        } 
    } 
}
