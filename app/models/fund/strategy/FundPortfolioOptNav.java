package models.fund.strategy;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.libs.F.T2;
import services.FundPortfolioService;

/** 基金组合净值 **/
@Entity
@Table(name="t_portfolio_strategy_nav")
public class FundPortfolioOptNav extends Model {
	
	/** 净值日期 **/
	@Column(name="nav_date")
	public String navDate; 
	
	/** 组合代码 **/
	@Column(name="PORTFOLIO_STRATEGY_CODE")
	public String portfolioCode;  
	
	/** 净值  **/
	@Column(name="nav")
	public BigDecimal nav; 
	
	/** 较前一交易日增长率 **/
	@Column(name="DAYILY_GROWTH_RATE")
	public BigDecimal dailyGrowthRate; 

	/** 基准净值 **/
	@Column(name="BENCHMARK_NAV")
	public BigDecimal benchmarkNav; 
	
	public static List<FundPortfolioOptNav> lastMonthNavs(String code){ 
		T2<String,String> ldate = FundPortfolioService.getLastWeekAndMonthForNavs(code); 
		return FundPortfolioOptNav.find("portfolioCode=? and navDate>=?", code,ldate._2).fetch(); 
	}
	public static List<FundPortfolioOptNav> allNavs(String code){ 
		return FundPortfolioOptNav.find("portfolioCode=? order by navDate asc", code).fetch(); 
	}
}
