package models.fund;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
import play.libs.F.T2;

@Entity
@Table(name="t_fund_latest_nav")
public class FundBasicAbbr extends GenericModel{
	/** 基金名称 **/
	@Column(name="fund_name")
	public String fund_name; 
	
	@Column(name="fund_code")
	public String fund_code ;
	
	/** 基金投资类型, 一级分类  **/
	@Column(name="investmentType1")
	public String investmentType1 ;
	
	/**净值日期**/
	@Column(name="nav_date")
	public String navDate;
	
	/**净值**/
	@Column(name="nav")
	public BigDecimal nav;
	
	/** 成立日期 **/
	@Column(name="ESTABLE_DATE")
	public String establishDate; 
	
	/**投资目标**/
	@Column(name="inverstment_target")
	public String inverstment_target; 
	
	/**基金公司**/
	@Column(name="fund_company")
	public String fund_company; 
	
	/**业绩比较基准**/
	@Column(name="benchmark")
	public String benchmark; 
	
	/** 成立规模 **/
	@Column(name="ESTABLE_SCALE")
	public String establishScale; 
	
	/** 累积净值 **/
	@Column(name="accumulated_nav")
	public BigDecimal accumulatedNav; 
	
	/**较前一日增长率 **/
	@Column(name="daily_growth_rate")
	public BigDecimal dailyGrowthRate; 
	
	/**最近一周收益**/
	@Column(name="latest_week_return")
	public BigDecimal latest_week_return;
	
	/**最近一月收益**/
	@Column(name="latest_month_return")
	public BigDecimal latest_month_return;
	
	/**最近3月收益**/
	@Column(name="latest_3month_return")
	public BigDecimal latest_3month_return;
	
	/**最近6月收益**/
	@Column(name="latest_6month_return")
	public BigDecimal latest_6month_return;
	
	/**最近3月收益**/
	@Column(name="latest_year_return")
	public BigDecimal latest_year_return;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    public Long id;
	
	
	public T2<FundHolding,List<FundHolding>> latestFundHolding(){ 
		FundHolding t =  FundHolding.find("fundCode=? order by reportYear desc,reportQuarter desc", this.fund_code).first();
		if(t != null)  {
			List<FundHolding> list= FundHolding.find("fundCode=? and reportYear=? and reportQuarter=? order by reportYear desc,reportQuarter desc",
					this.fund_code,t.reportYear,t.reportQuarter).fetch(); 
			return new T2(t,list); 
		}else {
			return new T2(null,null);
		}
		
	}
	
	public FundEvaluation1Year evaluation1Year(){ 
		return FundEvaluation1Year.find("fundCode=?", this.fund_code).first(); 
	}
}

