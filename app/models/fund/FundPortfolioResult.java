package models.fund;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 组合优化结果 **/
@Entity
@Table(name="t_fund_portfolio_opt_result")
public class FundPortfolioResult extends Model {

	/** 基金组合 **/
	@ManyToOne
	@JoinColumn(name="FUND_PORTFOLIO_OPT_ID")
	public FundPortfolio portfolio; 
	
	@ManyToOne
	@JoinColumn(name="FUND_ID")
	public FundBasicInfo fund; 
	
	/** 基金优化组合日期 **/
	@Column(name="FUND_PORTFOLIO_OPT_DATE")
	public String optDate;
	
	/** 占净值比例 **/
	@Column(name="PROPORTION_OF_NAV")
	public BigDecimal proportionNav; 
	
	/** 买卖方向 **/
	@Column(name="BS_FLAG")
	@Enumerated(EnumType.STRING)
	public BuySaleEnum buySaleFlag; 
}
