package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 基金经理信息 **/
@Entity
@Table(name="t_fund_manager_info")
public class FundManager extends Model {

	/** 基金经理姓名 **/
	@Column(name="FUND_MANAGER_NAME")
	public String managerName; 
	
	/** 基金经理简介**/
	@Column(name="FUND_MANAGER_INTRO")
	public String managerIntro; 
	
	/** 任职开始日期 **/
	@Column(name="TENURE_START_DATE")
	public String tenureStartDate; 
	
	/** 任职公司 **/
	@Column(name="TENURE_COMPANY")
	public String tenureCompany; 
	
	/** 
	 * 基金经理评价1年
	 * @return
	 */
	public ManagerEvaluation1Year evaluation1Year(){ 
		return ManagerEvaluation1Year.find("fundManager=?", this.id+"").first(); 
	}
}
