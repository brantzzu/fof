package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="t_fund_star")
public class FundStar extends Model {

	@Column(name="start_date")
	public String startDate; 
	
	@Column(name="end_date")
	public String endDate; 
	
	@Column(name="fund_code")
	public String fundCode; 
	
	@Column(name="fund_star")
	public int star; 
}
