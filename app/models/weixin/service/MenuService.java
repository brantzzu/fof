package models.weixin.service;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import models.weixin.menu.Button;
import models.weixin.menu.Menu;
import utils.WeixinUtil;

/**
 * 菜单创建
 */
public class MenuService {

	public static Logger log = Logger.getLogger(MenuService.class);

	/**
	 * 菜单创建（POST） 限100（次/天）
	 */
	public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 菜单查询
	 */
	public static String MENU_GET = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param jsonMenu
	 *            json格式
	 * @return 状态 0 表示成功、其他表示失败
	 */
	public static Integer createMenu(String jsonMenu) {
		int result = 0;
		String token = WeixinUtil.getToken();
		if (token != null) {
			// 拼装创建菜单的url
			String url = MENU_CREATE.replace("ACCESS_TOKEN", token);
			// 调用接口创建菜单
			JSONObject jsonObject = WeixinUtil.httpsRequest(url, "POST", jsonMenu);

			if (null != jsonObject) {
				if (0 != jsonObject.getInt("errcode")) {
					result = jsonObject.getInt("errcode");
					log.error("创建菜单失败 errcode:" + jsonObject.getInt("errcode")
							+ "，errmsg:" + jsonObject.getString("errmsg"));
				}
			}
		}
		return result;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @return 0表示成功，其他值表示失败
	 */
	public static Integer createMenu(Menu menu) {
		return createMenu(JSONObject.fromObject(menu).toString());
	}


	/**
	 * 查询菜单
	 * 
	 * @return 菜单结构json字符串
	 */
	public static JSONObject getMenuJson() {
		JSONObject result = null;
		String token = WeixinUtil.getToken();
		if (token != null) {
			String url = MENU_GET.replace("ACCESS_TOKEN", token);
			result = WeixinUtil.httpsRequest(url, "GET", null);
		}
		return result;
	}

	/**
	 * 查询菜单
	 * @return Menu 菜单对象
	 */
	public static Menu getMenu() {
		JSONObject json = getMenuJson().getJSONObject("menu");
		System.out.println(json);
		Menu menu = (Menu) JSONObject.toBean(json, Menu.class);
		return menu;
	}

	public static void main(String[] args) {
//		getMenu();
		Button sb_conservative = new Button("保守型组合", "view", "13", "http://quants.sufe.edu.cn/portfolio/conservative", null);
		Button sb_sound = new Button("稳健型组合", "view", "12", "http://quants.sufe.edu.cn/portfolio/sound", null);
    	Button sb_radical = new Button("激进型组合", "view", "11", "http://quants.sufe.edu.cn/portfolio/radical", null);
		Button btn1 = new Button("基金组合", "click", null, null, new Button[] {sb_radical,sb_sound,sb_conservative});

		Button sb_collection = new Button("我的收藏", "view", "21", "http://quants.sufe.edu.cn/profile/mycollection", null);
    	Button sb_capital = new Button("我的资产", "view", "22", "http://quants.sufe.edu.cn/profile/mycapital", null);
    	Button sb_profile = new Button("个人信息", "view", "23", "http://quants.sufe.edu.cn/profile", null);
		Button btn2 = new Button("我的组合", "click", null, null, new Button[] {sb_collection,sb_capital,sb_profile});

		Button sb_zhaopin = new Button("招聘信息", "view", "31", "http://mp.weixin.qq.com/s?__biz=MzA4NTAxNjY4MQ==&mid=406671870&idx=1&sn=f1665885b99213d314cdc9bd4dc2c33c#rd",
				null);
		Button sb_jianjie = new Button("实验室简介", "view", "32", "http://mp.weixin.qq.com/s?__biz=MzA4NTAxNjY4MQ==&mid=400579749&idx=1&sn=0a03d22a4421497e29c945395a43ec96&scene=18#rd",
				null);
		Button btn3 = new Button("关于我们", "click", null, null, new Button[] {
				sb_zhaopin, sb_jianjie });

		Menu menu = new Menu(new Button[] { btn1, btn2, btn3 });

		
		createMenu(menu);
	}
}
