package com.sundyn.util;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.action.EmployeeAction;
import com.sundyn.interceptor.SignatureInterceptor;
import org.apache.log4j.Logger;

public class ErrorProcess extends ActionSupport {
    private Exception exception;
    private static final Logger logger;
    static {
        logger = Logger.getLogger((Class) ErrorProcess.class.getClass());
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String execute()
    {
        logger.error("ErrorProcess", exception);
        ActionContext.getContext().getValueStack().push(this.exception.getMessage() + SignatureInterceptor.FormatStackTrace(this.exception));//放到值栈顶
        return this.SUCCESS;
    }
}
