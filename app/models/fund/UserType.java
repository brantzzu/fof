package models.fund;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import play.db.jpa.Model;

/** 用户类型 **/
@Entity
@Table(name="t_user_type")
public class UserType extends Model {

	/** 代码 **/
	@Column(name = "type_code")
	public String code; 
	
	/** 名称 **/
	@Column(name="type_name")
	public String name; 
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="t_fund_portfolio_opt_user_rlt",
	joinColumns={@JoinColumn(name="USER_TYPE_CODE")},
	inverseJoinColumns={@JoinColumn(name="FUND_PORTFOLIO_OPT_ID")})
	public List<FundPortfolio> portfolios = new ArrayList<FundPortfolio>(); 
}
