package controllers;

import models.weixin.service.OAuthService;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;

public class WeixinAuth extends Controller {
    static final String C = "__continuation";
    static final String A = "__callback";
    static final String F = "__future";
    
	@Before
	static void checkAccess() throws Throwable {
		Logger.info(" request url is %s", request.url); 
		flash.put("url", request.url);

		if(!session.contains("username")) {
        	
        	String url = OAuthService.getOauthUrl("http://quants.sufe.edu.cn/token", "utf-8", "snsapi_base");
        	
        	Logger.info("the redirect url is %s", url);
        	redirect(url);
        }
    }
}
