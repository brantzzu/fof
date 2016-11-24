package controllers;

import java.io.InputStream;
import java.util.ArrayList;  
import java.util.Date;  
import java.util.List;  
import java.util.Map; 


import play.Logger;
import utils.Constant;
import utils.MessageUtil;
import models.weixin.resp.NewsMessage;
import models.weixin.resp.TextMessage; 
import models.weixin.resp.Article;
  
public class CoreServiceImpl {  
  
    //public static Logger log = Logger.getLogger(CoreServiceImpl.class);  
      
    public static String processRequest(InputStream inputStream) {  
        String respMessage = null;  
        try {  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(inputStream);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName"); 
            Logger.info("CoreServiceImpl:the openid from weixin menus is %s ", fromUserName);
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");
            String respContent = "";
            // 文本消息
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(Constant.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            
            // 创建图文消息  
            NewsMessage newsMessage = new NewsMessage();  
            newsMessage.setToUserName(fromUserName);  
            newsMessage.setFromUserName(toUserName);  
            newsMessage.setCreateTime(new Date().getTime());  
            newsMessage.setMsgType(Constant.RESP_MESSAGE_TYPE_NEWS);  
            newsMessage.setFuncFlag(0);  

            //List<Article> articleList = new ArrayList<Article>();
            List articleList = new ArrayList();
            // 文本消息  
            if (msgType.equals(Constant.REQ_MESSAGE_TYPE_TEXT)) {  
                // 接收用户发送的文本消息内容  
                String content = requestMap.get("Content");  
  
                if ("?".equals(content) || "？".equals(content) || "help".equals(content) || "帮助".equals(content)) {  
                    textMessage.setContent("平台提供自行开发的FOF产品及其他FOF产品的导购服务。FOF通过将资金分散到不同公募和私募基金，从而实现降低用户投资私募基金门槛、合理分摊投资风险等。回复数字1，可查看FOF组合推荐!");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                
                }
//                else if ("1".equals(content)) {  
//                    textMessage.setContent("FOF产品组合方案即将推出，敬请期待！");  
//                    // 将文本消息对象转换成xml字符串  
//                    respMessage = MessageUtil.textMessageToXml(textMessage);  
//                }
                // 单图文消息  
//                else if ("31".equals(content)) {  
//                    Article article = new Article();  
//                    article.setTitle("团队介绍");  
//                    article.setDescription("上海市金融信息技术研究重点实验室");  
//                    article.setPicUrl("http://baike.baidu.com/pic/%E4%B8%8A%E6%B5%B7%E8%B4%A2%E7%BB%8F%E5%A4%A7%E5%AD%A6/546589/0/6159252dd42a28346c09fa8059b5c9ea14cebf8a?fr=lemma&ct=single#aid=0&pic=6159252dd42a28346c09fa8059b5c9ea14cebf8a");  
//                    article.setUrl("http://fitl.sufe.edu.cn/?cat=6");  
//                    articleList.add(article);  
//  
//                    // 设置图文消息个数  
//                    newsMessage.setArticleCount(articleList.size());  
//                    // 设置图文消息包含的图文集合  
//  
//                    newsMessage.setArticles(articleList);  
//                    // 将图文消息对象转换成xml字符串  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                }  
                 
                else {  
                    textMessage.setContent("输入问号?,或是help,或是帮助,可以查看本微信号的使用方法。");  
                    // 将文本消息对象转换成xml字符串  
                    respMessage = MessageUtil.textMessageToXml(textMessage);  
                }  
            }//事件处理开始  
            else if (msgType.equals(Constant.REQ_MESSAGE_TYPE_EVENT)) {  
            // 事件类型  
            String eventType = requestMap.get("Event");  

            if (eventType.equals(Constant.EVENT_TYPE_SUBSCRIBE)) {  
                // 关注  
            	respMessage = "感谢您关注!我们为个人和机构用户提供资产配置、套期保值、个性化量化组合投资策略等服务的专业化管理和服务平台。产品定位:平台主要面向普通及专业化的投资者,具有扁平化及普惠金融的特点;平台也与券商、保险等金融机构以及专业策略提供者合作进行产品的合作研发;同时平台的学习培训面向中高端用户和高校等。\n";  
                StringBuffer contentMsg = new StringBuffer();  
//                contentMsg.append("您还可以回复下列内容，体验相应服务").append("\n\n");  
//                contentMsg.append("?  获取平台帮助信息").append("\n");  
//                contentMsg.append("11 基金净值查询").append("\n");  
//                contentMsg.append("12 FOF组合净值查询").append("\n");
//                contentMsg.append("21 FOF组合激进型").append("\n");
//                contentMsg.append("22 FOF组合稳健型").append("\n");
//                contentMsg.append("23 FOF组合保守型").append("\n");
//                contentMsg.append("31 团队介绍").append("\n");
                respMessage = respMessage+contentMsg.toString();  
                  
            } else if (eventType.equals(Constant.EVENT_TYPE_UNSUBSCRIBE)) {  
                // 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息  

            }  else if (eventType.equals(Constant.EVENT_TYPE_CLICK)) {  

                // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                String eventKey = requestMap.get("EventKey");  

                // 自定义菜单点击事件
//                if (eventKey.equals("11")) {  
//                	textMessage.setContent("激进型组合");
//                	respMessage = MessageUtil.textMessageToXml(textMessage); 
//                } else if (eventKey.equals("12")) {  
//                	textMessage.setContent("稳健型组合");
//                	respMessage = MessageUtil.textMessageToXml(textMessage);
//                } else if (eventKey.equals("13")) {  
//                	textMessage.setContent("保守型组合");
//                	respMessage = MessageUtil.textMessageToXml(textMessage); 
//                } else if (eventKey.equals("21")) {  
//                	textMessage.setContent("组合A菜单被点击！"); 
//                	respMessage = MessageUtil.textMessageToXml(textMessage); 
//                } else if (eventKey.equals("22")) {  
//                	textMessage.setContent("组合B菜单被点击！"); 
//                	respMessage = MessageUtil.textMessageToXml(textMessage); 
//                } else if (eventKey.equals("23")) {  
//                	textMessage.setContent("组合C菜单被点击！"); 
//                	respMessage = MessageUtil.textMessageToXml(textMessage); 
//                } 
//                else {  
//                	Article article = new Article();  
//                    article.setTitle("团队介绍");  
//                    article.setDescription("上海市金融信息技术研究重点实验室");  
//                    article.setPicUrl("http://baike.baidu.com/pic/%E4%B8%8A%E6%B5%B7%E8%B4%A2%E7%BB%8F%E5%A4%A7%E5%AD%A6/546589/0/6159252dd42a28346c09fa8059b5c9ea14cebf8a?fr=lemma&ct=single#aid=0&pic=6159252dd42a28346c09fa8059b5c9ea14cebf8a");  
//                    article.setUrl("http://fitl.sufe.edu.cn/?cat=6");  
//                    articleList.add(article);
//                   
//  
//                    // 设置图文消息个数  
//                    newsMessage.setArticleCount(articleList.size());  
//                    // 设置图文消息包含的图文集合  
//  
//                    newsMessage.setArticles(articleList);  
//                    // 将图文消息对象转换成xml字符串  
//                    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
//                	//textMessage.setContent("团队介绍菜单被点击！"); 
//                	//respMessage = MessageUtil.textMessageToXml(textMessage); 
//                }
            }
        }    
              
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return respMessage;  
    }  
  
  
}  
