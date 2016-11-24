package models.fund;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;


/** 用户信息表 **/
@Entity
@Table(name="t_user_profile")
public class UserProfile extends Model {
	
	/** 用户姓名 **/
	@Column(name="name")
	public String name; 
	
	/** 出生日期 **/
	@Column(name="birthday")
	public Date birthday; 
	
	/** 手机号码 **/
	@Column(name="phone")
	public String phone; 
	
	/** 用户类型 **/
	@ManyToOne
	@JoinColumn(name="user_type_id")
	public  UserType userType; 
	
	/** 头像URL　**/
	@Column(name="headimgurl")
	public String headImage; 
	
	/** 用户信息所对应的微信用户 **/
	public WeixinAuth relatedWXAuth() { 
		return WeixinAuth.find("user=?", this).first(); 
	}
}
