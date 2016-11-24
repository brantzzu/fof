package services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import models.fund.FundPortfolioType;
import models.fund.strategy.FundPortfolioOptNav;

import org.joda.time.LocalDate;

import play.db.jpa.JPA;
import play.libs.F.T2;

public class FundPortfolioService {
	
	/** 
	 * 最新交易日期，用于之后获取该日期的基金组合净值
	 * @return 最新交易日期
	 */
	public static String latestDate(){ 
		String latest = FundPortfolioOptNav.find("select max(navDate) from FundPortfolioOptNav").first();
		return latest; 
	}
	
	/** 
	 * 最新日期的基金组合净值列表
	 * @param latest
	 * @param type
	 * @return 最新日期的基金组合净值列表
	 */
	public static List<FundPortfolioOptNav> navs(String latest,FundPortfolioType type) { 
		return FundPortfolioOptNav.find("from FundPortfolioOptNav where navDate=? and portfolio.type=?", 
				latest,type).fetch(); 
	}
	
	/** 
	 * 获取指定天数的基金组合净值
	 * @param portfolioId
	 * @param days
	 * @return 净值列表，其中第一个为最新的净值
	 */
	public static List<FundPortfolioOptNav> portfolioNavs(long portfolioId,int days){ 
		
		return FundPortfolioOptNav.find("from FundPortfolioOptNav where portfolio.id=? order by navDate desc", 
				portfolioId).fetch(days); 
	}
	
	public static T2<String,String> getLastWeekAndMonthForNavs(String code){ 
		Query query=  JPA.em().createNativeQuery("select d from v_portfolio_latestdate where PORTFOLIO_STRATEGY_CODE = ? limit 1");
		query.setParameter(1, code); 
		String date = query.getSingleResult().toString();
		String year = date.substring(0,4); 
		String month = date.substring(4,6); 
		String day = date.substring(6); 
		
		LocalDate ldate = new LocalDate(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day)); 
		LocalDate lastWeek = ldate.minusWeeks(1);
		LocalDate lastMonth = ldate.minusMonths(1); 
		
		String lastweek = lastWeek.toString("yyyyMMdd");
		String lastmonth = lastMonth.toString("yyyyMMdd"); 
		return new T2(lastweek,lastmonth); 
	}
	
	
	/** 
	 * 下方为新增加的方法
	 */
	
	public static Double[] strategyPortfolioGrowth(String code) { 
		
		T2<String,String> ldate = getLastWeekAndMonthForNavs(code); 
		String lastweek = ldate._1; 
		String lastmonth = ldate._2; 
		
		Query lastWeekQuery = JPA.em().createNativeQuery("select nav_date,nav from t_portfolio_strategy_nav v "
				+ "where v.PORTFOLIO_STRATEGY_CODE=? and v.nav_date>=? order by v.nav_date desc");
		lastWeekQuery.setParameter(1, code); 
		lastWeekQuery.setParameter(2, lastweek); 
		List<Object[]> lastWeekList = lastWeekQuery.getResultList();
		BigDecimal lastweekBegin = (BigDecimal)(lastWeekList.get(0))[1]; 
		BigDecimal lastweekSecond = (BigDecimal)(lastWeekList.get(1))[1]; 
		BigDecimal lastweekEnd = (BigDecimal)(lastWeekList.get(lastWeekList.size()-1))[1]; 
		
		
		Query lastMonthQuery = JPA.em().createNativeQuery("select nav_date,nav from t_portfolio_strategy_nav v "
				+ "where v.PORTFOLIO_STRATEGY_CODE=? and v.nav_date>=? order by v.nav_date desc");
		lastMonthQuery.setParameter(1, code); 
		lastMonthQuery.setParameter(2, lastmonth); 
		List<Object[]> lastMonthList = lastMonthQuery.getResultList();
		BigDecimal lastmonthBegin = (BigDecimal)(lastMonthList.get(0))[1]; 
		BigDecimal lastmonthEnd = (BigDecimal)(lastMonthList.get(lastMonthList.size()-1))[1]; 
		
		
		Query allQuery = JPA.em().createNativeQuery("select nav_date,nav from t_portfolio_strategy_nav v "
				+ "where v.PORTFOLIO_STRATEGY_CODE=? order by v.nav_date asc limit 1");
		allQuery.setParameter(1, code); 
		List<Object[]> allList = allQuery.getResultList();
		BigDecimal allBegin = (BigDecimal)(allList.get(0))[1]; 
		
		
		
		BigDecimal lastDayGrowth = (lastweekBegin.subtract(lastweekSecond)).divide(lastweekSecond,4); 
		BigDecimal lastWeekGrowth = (lastweekBegin.subtract(lastweekEnd)).divide(lastweekEnd,4); 
		BigDecimal lastMonthGrowth = (lastmonthBegin.subtract(lastmonthEnd)).divide(lastmonthEnd,4); 
		BigDecimal allGrowth = (lastweekBegin.subtract(allBegin)).divide(allBegin,4); 
		
		Double[] growth = new Double[4]; 
		growth[0] = lastDayGrowth.doubleValue(); 
		growth[1] = lastWeekGrowth.doubleValue(); 
		growth[2] = lastMonthGrowth.doubleValue(); 
		growth[3] = allGrowth.doubleValue();
		
		return growth; 
	}
}
