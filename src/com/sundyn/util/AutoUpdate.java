package com.sundyn.util;

import java.util.*;
import com.sundyn.service.*;

public class AutoUpdate extends TimerTask
{
    private FormlistService formlistService;
    
    public FormlistService getFormlistService() {
        return this.formlistService;
    }
    
    public void setFormlistService(final FormlistService formlistService) {
        this.formlistService = formlistService;
    }
    
    @Override
    public void run() {
        this.formlistService.reset();
    }
}
