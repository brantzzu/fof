package controllers;

import play.*;
import play.modules.router.*;
import play.mvc.*;
import utils.SignUtil;

import java.util.*;
import java.io.IOException;  
import java.io.InputStream; 
import java.io.UnsupportedEncodingException; 

import javax.annotation.Resource;  

import models.*;
import models.fund.strategy.PortfolioStrategy;

public class Application extends Controller {

	@Get("/")
    public static void index() {
		List<Object[]> portofolios = PortfolioStrategy.fundPortfolio();
		render(portofolios); 
    }
	
	
	@Get("/fof")
    public static void get(String signature,String timestamp,String nonce,String echostr) {  
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
           renderText(echostr);
        }
        //System.out.print(signature);
        //
    }
  
	@Post("/fof")
    public static void post() {  
        // 调用核心业务类接收消息、处理消息
		InputStream inputStream = request.body;
        String respMessage = CoreServiceImpl.processRequest(inputStream);  
        renderText(respMessage);  
        
    }
	

}
