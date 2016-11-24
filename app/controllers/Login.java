package controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import models.fund.UserProfile;
import models.fund.UserType;
import models.fund.WeixinAuth;
import models.weixin.service.OAuthService;
import play.Logger;
import play.modules.router.Get;
import play.modules.router.Post;
import play.mvc.Before;
import play.mvc.Controller;
import services.AuthService;
import utils.AccessTokenOAuth;

public class Login extends Controller {

    
	@Get("/token")
	public static void accessToken(String code,String state){ 
		if(StringUtils.isNotEmpty(code)){ 
			AccessTokenOAuth accessToken = OAuthService.getOAuthAccessToken(code);
			String openid = accessToken.getOpenid();
			if (!WeixinAuth.isExists(openid)) {
				redirect("Login.bindForm", openid);
			}else { 
				session.put("username", openid);
				redirect(flash.get("url")); 
			}
		}
	}
	

}
