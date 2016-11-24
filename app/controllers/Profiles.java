package controllers;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import models.fund.FundPortfolio;
import models.fund.UserCollection;
import models.fund.UserProfile;
import models.fund.UserType;
import models.fund.WeixinAuth;
import models.fund.strategy.FundPortfolioOptNav;
import play.Logger;
import play.modules.router.Get;
import play.modules.router.Post;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;
import services.AuthService;
import services.FundPortfolioService;
import services.ProfileService;

/** 
 * 实现微信第二个菜单的内容
 * ①列表显示三种基金组合的内容；
 * ②点击每一个基金组合后，显示基金组合的详细信息
 */
@With(controllers.WeixinAuth.class)
public class Profiles extends Controller{

	@Post("/profiles/mycollection")
	public static void saveCollection(long id) { 
		String openid = session.get("username");
		UserProfile user = WeixinAuth.loginUser(openid);
		boolean flag = ProfileService.saveUserCollection(user, id);
		renderText(flag?"收藏成功":"收藏失败,稍候重试。");
	}
	
	@Get("/profile/mycollection")
	public static void collection() { 
		String openid = session.get("username");
		UserProfile user = WeixinAuth.loginUser(openid);
		List<UserCollection> collections = ProfileService.userCollections(user);
		
		render(collections); 
	}
	

	@Get("/profile/mycapital")
	public static void capital() { 
		String openid = session.get("username");
		UserProfile user = WeixinAuth.loginUser(openid);

		renderText("我的资产");
	}
	

	@Get("/profile")
	public static void profiles() { 
		String openid = session.get("username");
		UserProfile user = WeixinAuth.loginUser(openid);
		redirect("Login.userInfo",user.id); 
	}
	
	@Get("/portfolio/detail")
	public static void detail(long portfolioId){ 
		
		List<FundPortfolioOptNav> navs = FundPortfolioService.portfolioNavs(portfolioId, 30); 
		
		FundPortfolioOptNav nav = navs.get(0); 
		
		render(nav,navs); 
	}
	
	/**
	 * 绑定界面 openid 与 profile
	 */
	@Get("/bind")
	public static void bindForm(String openid) {
		if (StringUtils.isEmpty(openid)) {
			openid = "defaultTestOpenID";
		}
		Logger.info("the openid from bindform is %s ", openid);

		if (!WeixinAuth.isExists(openid)) {
			List<UserType> types = UserType.all().fetch();
			render(types, openid);
		}
	}

	@Post("/bind")
	public static void bind(UserProfile user, String openid) {
		Logger.info("输入的信息为：user.phone %s,user.type: %s,user.birthday:%s", user.phone, user.userType.name,
				user.birthday.toString());
		AuthService.bind(user, openid);
		render(user,openid); 
	}
	
	@Get("/userInfo/{<[0-9]+>id}")
	public static void userInfo(Long id) {
		UserProfile user = UserProfile.findById(id); 
		if(user==null) { 
			renderText("没有该用户"); 
		}
		
		WeixinAuth auth = user.relatedWXAuth(); 
		
		if(auth==null){ 
			renderText("无对应的微信用户信息");
		}
		
		String openid = auth.openId; 
		render(user,openid); 
	}
}
