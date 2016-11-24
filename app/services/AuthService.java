package services;

import java.util.Date;

import javax.mail.Session;

import models.fund.UserProfile;
import models.fund.WeixinAuth;
import models.weixin.service.UserService;
import models.weixin.user.UserWeiXin;
import play.libs.F.Tuple;

public class AuthService {

	public static void bind(UserProfile user, String openId) { 
		WeixinAuth auth = new WeixinAuth.Builder().user(user).openId(openId).lastLogin(new Date()).build();
		
		UserWeiXin weixinUser  = UserService.getUserInfo(openId); 
		user.name = weixinUser.getNickname(); 
		user.headImage = weixinUser.getHeadimgurl(); 		
		user.save(); 
		auth.save(); 
		play.mvc.Scope.Session.current().put("username", openId);
	}
	
}
