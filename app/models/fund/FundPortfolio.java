package models.fund;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.fund.strategy.FundPortfolioOptNav;
import play.db.jpa.Model;

/** 基金组合优化策略 **/
@Entity
@Table(name="t_fund_portfolio_opt")
public class FundPortfolio extends Model {
	
	/** 策略名称 **/
	@Column(name="FUND_PORTFOLIO_OPT_NAME")
	public String name; 
	
	/** 提供人代码 **/
	@Column(name= "STRATEGY_SUPPLIER_NO")
	public String supplierCode; 
	
	/** 提供人姓名 **/
	@Column(name="STRATEGY_SUPPLIER_NAME")
	public String supplierName; 
	
	/** 策略简介 **/
	@Column(name="STRATEGY_MEMO")
	public String intro; 
	
	@OneToMany(mappedBy="portfolio")
	public List<FundPortfolioResult> results= new ArrayList<FundPortfolioResult>() ; 
	
	@ManyToOne
	@JoinColumn(name="type_id")
	public FundPortfolioType type;
	
	/** 
	 * 组合基金的最新净值
	 * @return
	 */
	public FundPortfolioOptNav latestNav() { 
		return FundPortfolioOptNav.find("portfolio=? order by navDate desc",this).first();
	}
}
