package controllers.fund;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import models.fund.BenchMark;
import models.fund.FundBasicInfo;
import models.fund.FundBasicAbbr;
import models.fund.FundNav;
import models.fund.IndexPrice;
import models.fund.FundEvaluation1Year;
import models.fund.FundEvaluation2Year;
import models.fund.FundEvaluation3Year;
import models.fund.FundHolding;
import models.fund.FundManager;
import models.fund.ManagerEvaluation1Year;
import models.fund.ManagerTenure;
import play.Logger;
import play.Play;
import play.db.jpa.GenericModel.JPAQuery;
import play.db.jpa.JPA;
import play.libs.F.T2;
import play.libs.F.T3;
import play.modules.router.Get;
import play.modules.router.Gets;
import play.mvc.Controller;
import play.templates.JavaExtensions;
import services.FundServices;
import utils.DateUtil;



public class Funds extends Controller{

	public static String s_pageSize = Play.configuration.getProperty("crud.pageSize", "20");
	public static int pageSize = Integer.valueOf(s_pageSize);  
	
	@Get("/fundsCount")
	public static void countFunds() { 
		renderText(FundBasicInfo.count()); 
	}
	
	/**
	 * Json格式的基金信息，用于基金列表		
	 */
	@Get("/fundsJson")
	public static void fundsJson() {
		List<FundBasicAbbr> jsonInfos = FundBasicAbbr.find(" from FundBasicAbbr t where t.investmentType1 <> '货币市场型基金' ").fetch();
		renderJSON(jsonInfos);
	}

	@Get("/fundsJson/{fundType}")
	public static void fundTypeJson(String fundType){
		List<FundBasicAbbr> infos = FundBasicAbbr.find("from FundBasicAbbr t where t.investmentType1=? ",fundType).fetch();
		renderJSON(infos);
		
	}
	
	/**
	 * 
	 * 获取一段时间范围内的基金净值
	 */
	@Get("/fundNavJson/{fund_code}/{startDate}/{endDate}")
		public static void fundReturnJson(String startDate, String endDate,String fund_code){
			List <FundNav> fundNav = FundNav.find("fundCode=? and navDate>=? and navDate<=?  order by navDate  asc", fund_code,startDate,endDate).fetch();
		/*    
		List <FundNav> fundNav = JPA.em().createQuery("select f.navDate,f.accumulatedNav from FundNav f  join IndexPrice i " 
		    		                             +" on f.navDate = date_format(t.navDare,'%Y-%m-%d') where i.indexType ='hs300' "
		  		                             + "and f.fundCode=? and f.navDate>=? and f.navDate<=?  order by f.navDate  asc",fund_code,startDate,endDate).fetch();
		*/  	
			renderJSON(fundNav);
		
	}
	
	@Get("/benchmark/{startDate}/{endDate}")
		public static void benchmarkReturnJson(String startDate, String endDate){
			List <BenchMark> benchMark = BenchMark.find("navDate>=? and navDate<=? order by navDate  asc", startDate,endDate).fetch();
			renderJSON(benchMark);
		
		}

	/** 
	 * 1-1 基金经理列表 
	 *  基金列表页面
	 */
	@Gets({@Get("/manager/list/{<[0-9]+>page}"),
		@Get("/manager/list")})
	public static void managerList(int page){ 
		List<FundManager> infos = FundBasicInfo.find("from FundManager t")
				.from((page)*pageSize).fetch(pageSize);
    	long count = FundManager.count(); 
    	
    	int pages = JavaExtensions.page(count, pageSize); 
		render(infos,count,pages,page); 
	}
	
	@Get("/manager/info/{<[0-9]+>id}")
	public static void managerInfo(Long id){ 
		FundManager t =  FundManager.findById(id);
		ManagerEvaluation1Year e1 = t.evaluation1Year();
		
		render(t,e1); 
	}
	
	/** 
	 * 1-1 基金列表 
	 *  基金列表页面
	 */
	@Gets({@Get("/fund/list/{<[0-9]+>page}"),
		@Get("/fund/list")})
	public static void fundList(int page){ 
		List<String> types = FundServices.fundInvestmentTypes();	
    	String  jsonParameter = "/fundsJson";
		render(types,jsonParameter); 
	}
	

	
	/** 
	 * 1-1 基金列表按照类型获取
	 * @param fundType
	 * @param page
	 */
	@Gets({@Get("/fund/data/{fundType}"),
		   @Get("/fund/data/{fundType}/{<[0-9]+>page}")})
	public static void fundData(String fundType,int page){
		List<String> types = FundServices.fundInvestmentTypes();
		long count = FundBasicInfo.count("from FundBasicInfo t where t.investmentType1=? ",fundType); 
		List<FundBasicInfo> infos = FundBasicInfo.find("from FundBasicInfo t where t.investmentType1=? ",fundType)
				.from((page)*pageSize).fetch(pageSize);
    	
    	int pages = JavaExtensions.page(count, pageSize); 
    	String jsonParameter = "/fundsJson/" + fundType ;
    	render(types,fundType,infos,count,pages,page ,jsonParameter); 
	}
	
	/** 
	 * 2-0 基金详情页面框架，显示净值走势
	 * 通过该框架，可以展示
	 * <ol>
	 * <li>基金净值信息 
	 * 	   <ul>
	 * 			<li> 近30天的净值数据:{@link FundBasicInfo#latestOneMonthNav()}
	 * 			<li> 近3个月的净值数据： {@link FundBasicInfo#latestQuaterNav()}
	 * 			<li> ...  
	 *     </ul>
	 * <li>产品详情  {@link models.fund.FundBasicInfo}  在该tab中展示基金的若干属性
	 * <li>持仓情况 {@link FundBasicInfo#latestFundHolding()}}  
	 * <li>基金经理  {@link FundBasicInfo#managerTenures()}
	 * <li>基金评价 {@link FundBasicInfo#evaluation1Year()} {@linkplain FundBasicInfo#evaluation2Year()} {@linkplain FundBasicInfo#evaluation3Year()}
	 * </ol>
	 * @param fundCode 基金代码
	 */
	@Get("/fund/info/{<[0-9]+>fundCode}")
	public static void fundInfo(String fundCode){ 
		FundBasicAbbr fund = FundBasicAbbr.find("from FundBasicAbbr t where t.fund_code=? ",fundCode).first();
		BenchMark benchmark = BenchMark.find("from BenchMark").first();
		//获取基金最新净值日期
		String fundLatestNavDate = FundNav.find("select t.navDate from FundNav t where t.fundCode =? order by navDate desc",fundCode).first();
		BenchMark benchMark = new BenchMark();
		//获取基金起始日期的净值
		T2<String,String> latestMonthRange = DateUtil.latestOneMonth(); 
		String latestMonthJsonParameter = "/fundNavJson/"+ fundCode +"/"+ latestMonthRange._1 + "/" + fundLatestNavDate;
		BigDecimal startMonthFundNav = FundNav.find("select t.accumulatedNav from FundNav t where t.fundCode=? and t.navDate >=? order by t.navDate asc",fundCode,latestMonthRange._1).first();		
		String hs300LatestMothReturn = benchMark.benchMarkReturn(latestMonthRange._1,fundLatestNavDate);
		//System.out.println(latestMonthJsonParameter);
		//近半年基金净值
		T2<String,String> latestHalfYearRange = DateUtil.latestHalfYear();
		String latestHalfYearJsonParameter = "/fundNavJson/"+ fundCode +"/"+ latestHalfYearRange._1 + "/" + fundLatestNavDate;
		BigDecimal startHalfYearFundNav = FundNav.find("select t.accumulatedNav from FundNav t where t.fundCode=? and t.navDate >=? order by t.navDate asc",fundCode,latestHalfYearRange._1).first();
		String hs300LatestHalfYearReturn = benchMark.benchMarkReturn(latestHalfYearRange._1,fundLatestNavDate);
		//近一年基金净值
		T2<String,String> latestYearRange = DateUtil.latestYear();
		String latestYearJsonParameter = "/fundNavJson/"+ fundCode +"/"+ latestYearRange._1 + "/" + fundLatestNavDate;
		BigDecimal startYearFundNav = FundNav.find("select t.accumulatedNav from FundNav t where t.fundCode=? and t.navDate >=? order by t.navDate asc",fundCode,latestYearRange._1).first();
		String hs300LatestYearReturn = benchMark.benchMarkReturn(latestYearRange._1,fundLatestNavDate);
		//近3个月基金净值
		T2<String,String> latestQuarterRange = DateUtil.latestQuarter();
		String latestQuarterJsonParameter = "/fundNavJson/"+ fundCode +"/"+ latestQuarterRange._1 + "/" + fundLatestNavDate;
		BigDecimal startQuarterFundNav = FundNav.find("select t.accumulatedNav from FundNav t where t.fundCode=? and t.navDate >=? order by t.navDate asc",fundCode,latestQuarterRange._1).first();
		String hs300LatestQuarterReturn = benchMark.benchMarkReturn(latestQuarterRange._1,fundLatestNavDate);
	
		//System.out.println(latestMonthJsonParameter); 
		render(fund,benchmark,latestMonthJsonParameter,startMonthFundNav,hs300LatestMothReturn,
			   latestHalfYearJsonParameter,startHalfYearFundNav,hs300LatestHalfYearReturn,
			   latestYearJsonParameter,startYearFundNav,hs300LatestYearReturn,
			   latestQuarterJsonParameter,startQuarterFundNav,hs300LatestQuarterReturn);
		
	}
	
	@Get("/fund/test/{<[0-9]+>fundCode}")
	public static void fundTest(String fundCode){
	
		render();
	}
	
	
	@Get("/fund/info/detail/{<[0-9]+>fundCode}")
	public static void fundDetail(String fundCode) { 
		//FundBasicInfo fund = FundServices.getFundByCode(fundCode);
		FundBasicAbbr fund = FundBasicAbbr.find("from FundBasicAbbr t where t.fund_code=? ",fundCode).first();
		render(fund);  
	}
	
	/**
	 * 
	 * @param fundCode
	 */
	@Get("/fund/info/manager/{<[0-9]+>fundCode}")
	public static void managerDetail(String fundCode) { 
		//FundBasicInfo fund = FundServices.getFundByCode(fundCode);
		FundBasicAbbr fund = FundBasicAbbr.find("from FundBasicAbbr t where t.fund_code=? ",fundCode).first();
		List<FundManager> managers = FundServices.getManagerByCode(fundCode);
		render(managers,fund);
	}
	
	/** 
	 * 基金评价
	 * @param fundCode
	 */
	@Get("/fund/info/evaluation/{<[0-9]+>fundCode}")
	public static void evaluation(String fundCode) { 
		//FundBasicInfo fund = FundServices.getFundByCode(fundCode);
		FundBasicAbbr fund = FundBasicAbbr.find("from FundBasicAbbr t where t.fund_code=? ",fundCode).first();
		FundEvaluation1Year e1 = fund.evaluation1Year(); 
		render(fund,e1);  
	}
	
	/** 
	 * 基金评价
	 * @param fundCode
	 */
	@Get("/fund/info/holding/{<[0-9]+>fundCode}")
	public static void holding(String fundCode) { 
		FundBasicAbbr fund = FundBasicAbbr.find("from FundBasicAbbr t where t.fund_code=? ",fundCode).first();
		T2<FundHolding,List<FundHolding>> t = fund.latestFundHolding();
		//System.out.println(t);
		if (t._1 != null){
			List<FundHolding> holdings = t._2;
			String reportYear = t._1.reportYear +"年   第" + t._1.reportQuarter +"季度"; 
			render(fund,holdings,reportYear);  		
		}else {
			String reportYear = "";
			render(fund, reportYear);
		}
		
	}
	
}
