package models.fund;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;;

/** 
 * 用户收藏的基金组合
 *
 */
@Entity
@Table(name="t_user_collections")
public class UserCollection extends Model {
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserProfile user; 
	
	@ManyToOne
	@JoinColumn(name="portfolio_id")
	public FundPortfolio portfolio; 
	
	@Column(name="collection_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date collectionTime; 
}
