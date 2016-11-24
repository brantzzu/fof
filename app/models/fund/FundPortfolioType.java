package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="t_fund_portfolio_type")
public class FundPortfolioType extends Model {

	/** 基金类型代码 **/
	@Column(name="type_code")
	public String code; 
	
	/** 基金类型名称 **/
	@Column(name="type_name")
	public String name; 
	
	/** 基金类型状态  **/
	@Column(name="type_status")
	public String status; 
}
