package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 基金行业配置信息 **/
@Entity
@Table(name="t_fund_industry_allocation")
public class FundIndustryAllocation extends Model {
	
	/** 基金 **/
	@ManyToOne
	@JoinColumn(name="fund_code")
	public FundBasicInfo fund; 
	
	/** 行业类别 **/
	@Column(name="INDUSTRY_CATEGORY")
	public String industryCategory; 
	
	/** 占净值比例**/
	@Column(name="PROPORTION_OF_NAV")
	public String proportionNav; 
	
	/** 市值_万元**/
	@Column(name="MCAP_MILLION")
	public String marketCapital; 
	
}
