package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 基金经理任职信息 **/
@Entity
@Table(name="t_fund_manager_rlt")
public class ManagerTenure extends Model {
	
	/** 基金 **/
	@Column(name="FUND_CODE")
	public String fundCode; 
	
	/** 基金经理 **/
	@ManyToOne
	@JoinColumn(name="FUND_MANAGER_ID")
	public FundManager manager; 
	
	/** 任职开始日期**/
	@Column(name="TENURE_START_DATE")
	public String tenureStart; 
	
	/** 任职结束日期**/
	@Column(name="TENURE_END_DATE")
	public String tenureEnd; 

	/** 任职天数**/
	@Column(name="TENURE_DAYS")
	public String tenureDays; 
	
	/** 任职回报**/
	@Column(name="TENURE_RETURN")
	public String tenureReturn; 
	
	/** 基金类型 **/
	@Column(name="fund_type")
	public String fundType; 
	
	/** 基金规模(亿元)**/
	@Column(name="FUND_SCALE_BILLION")
	public String fundScaleBillion; 
}
