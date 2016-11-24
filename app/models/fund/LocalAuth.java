package models.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.jpa.Model;
import play.libs.Crypto;
import play.libs.Crypto.HashType;

/** 本地登录用户信息 **/
@Entity
@Table(name="T_AUTH_LOCAL")
public class LocalAuth extends Model {

	/** 用户信息 **/
	@ManyToOne
	@JoinColumn(name="user_id")
	public UserProfile user; 
	
	/** 本地登录用户名 **/
	@Column(name="login_name")
	public String loginName; 
	
	/** 本地登录密码  SHA-1加密后的密码**/
	@Column(name="password")
	public String password; 
	
	/*** 
	 * 本地登录是否成功
	 * @param loginName
	 * @param password
	 * @return 是否成功验证登录信息
	 */
	public boolean localLogin(String loginName,String password){ 
		long count = LocalAuth.count("from LocalAuth t where t.loginName=? and t.password = ? ", loginName,
				Crypto.passwordHash(password,HashType.SHA1));
		return count>0; 
	}
}
