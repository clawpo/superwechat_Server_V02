package cn.ucai.superwechat.bean;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
public class User extends Location implements java.io.Serializable {
	private static final long serialVersionUID = 6848921231724157394L;

	// Fields

	/**
	 * 
	 */
	private Integer MUserId;
	private String MUserName;
	private String MUserPassword;
	private String MUserNick;
	private Integer MUserUnreadMsgCount;

	// Constructors

	/** default constructor */
	public User() {
	}
	
	public User(boolean result, int msg) {
		this.setResult(result);
		this.setMsg(msg);
	}

	/** minimal constructor */
	public User(Integer MUserId, String MUserName, String MUserPassword, String MUserNick) {
		this.MUserId = MUserId;
		this.MUserName = MUserName;
		this.MUserPassword = MUserPassword;
		this.MUserNick = MUserNick;
	}

	/** full constructor */
	public User(Integer MUserId, String MUserName, String MUserPassword, String MUserNick,
			Integer MUserUnreadMsgCount) {
		this(MUserId, MUserName, MUserPassword, MUserNick);
		this.MUserUnreadMsgCount = MUserUnreadMsgCount;
	}

	// Property accessors
	public Integer getMUserId() {
		return this.MUserId;
	}

	public void setMUserId(Integer MUserId) {
		this.MUserId = MUserId;
	}

	public String getMUserName() {
		return this.MUserName;
	}

	public void setMUserName(String MUserName) {
		this.MUserName = MUserName;
	}

	public String getMUserPassword() {
		return this.MUserPassword;
	}

	public void setMUserPassword(String MUserPassword) {
		this.MUserPassword = MUserPassword;
	}

	public String getMUserNick() {
		return this.MUserNick;
	}

	public void setMUserNick(String MUserNick) {
		this.MUserNick = MUserNick;
	}

	public Integer getMUserUnreadMsgCount() {
		return this.MUserUnreadMsgCount;
	}

	public void setMUserUnreadMsgCount(Integer MUserUnreadMsgCount) {
		this.MUserUnreadMsgCount = MUserUnreadMsgCount;
	}

	@Override
	public String toString() {
		return "User [MUserId=" + MUserId + ", MUserName=" + MUserName
				+ ", MUserPassword=" + MUserPassword + ", MUserNick="
				+ MUserNick + ", MUserUnreadMsgCount=" + MUserUnreadMsgCount
				+ "]";
	}
	

}