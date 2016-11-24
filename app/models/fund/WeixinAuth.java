package models.fund;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

/** 微信登录信息 **/
@Entity
@Table(name="t_auth_weixin")
public class WeixinAuth extends Model{
	/** 用户信息 **/
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserProfile user; 
	
	/** openid **/
	@Column(name="openid")
	public String openId; 
	
	/** 上次登录时间 **/
	@Column(name="last_login")
	@Temporal(TemporalType.TIMESTAMP)
	public Date lastLogin; 
	
	public static boolean isExists(String openid) { 
		return WeixinAuth.count("openId=?", openid)>0;
	}
	
	/** 
	 * 当前openid 对应的用户
	 * @param openid
	 * @return
	 */
	public static UserProfile loginUser(String openid) { 
		WeixinAuth auth  = WeixinAuth.find("openId=?", openid).first();
		return auth==null?null:auth.user; 
	}
	
	public WeixinAuth(Builder builder) { 
		this.user = builder.user; 
		this.openId = builder.openId; 
		this.lastLogin = builder.lastLogin; 
	}
	
	public static class Builder { 
		public UserProfile user; 
		public String openId; 
		public Date lastLogin; 
		
		public Builder user(UserProfile aUser) { 
			user = aUser; 
			return this; 
		}
		
		public Builder openId(String sopenId) { 
			openId = sopenId; 
			return this; 
		}
		
		public Builder lastLogin(Date date) { 
			lastLogin = date;
			return this; 
		}
		
		public WeixinAuth build() { 
			return new WeixinAuth(this); 
		}
	}
}
