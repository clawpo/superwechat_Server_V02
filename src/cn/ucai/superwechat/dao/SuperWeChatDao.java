package cn.ucai.superwechat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.ucai.superwechat.bean.Avatar;
import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Location;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.User;
import cn.ucai.superwechat.servlet.I;
import cn.ucai.superwechat.utils.JdbcUtils;
import cn.ucai.superwechat.utils.Utils;


/**
 * 数据访问层
 * @author chen
 *
 */
public class SuperWeChatDao implements ISuperWeChatDao {
	
	private final String SQL_QUERY_AVATAR = ","+ I.Avatar.TABLE_NAME;
	private final String SQL_QUERY_LOCATION = ","+ I.Location.TABLE_NAME;
	private final String SQL_QUERY_USER = ","+ I.User.TABLE_NAME;
	private final String SQL_QUERY_GROUP = ","+ I.Group.TABLE_NAME;
	private final String SQL_COMPARE_USER_NAME_AVATAR = " and " + I.User.USER_NAME + "=" + I.Avatar.USER_NAME + " ";
	private final String SQL_COMPARE_USER_ID_AVATAR = " and " + I.User.USER_ID + "=" + I.Avatar.USER_ID + " ";
//	private final String SQL_COMPARE_USER_NAME_LOCATION = " and " + I.User.USER_NAME + "=" + I.Location.USER_NAME + " ";
	private final String SQL_COMPARE_USER_ID_LOCATION = " and " + I.User.USER_ID + "=" + I.Location.USER_ID + " ";
//	private final String SQL_COMPARE_USER_NAME_CONTACT = " and " + I.User.USER_NAME + "=" + I.Contact.CU_NAME + " ";
	private final String SQL_COMPARE_USER_ID_CONTACT = " and " + I.User.USER_ID + "=" + I.Contact.CU_ID + " ";
	private final String SQL_COMPARE_GROUP_ID_MEMBER = " and " + I.Member.GROUP_ID + "=" + I.Group.GROUP_ID + " ";
	private final String SQL_COMPARE_AVATAR_USER = " and " + I.Avatar.AVATAR_TYPE + "=0 ";
	private final String SQL_COMPARE_AVATAR_GROUP = " and " + I.Avatar.AVATAR_TYPE + "=1 ";
	private final String SQL_COMPARE_GROUP_ID_AVATAR = " and " + I.Group.GROUP_ID + "=" + I.Avatar.USER_ID + " ";
//	private final String SQL_COMPARE_GROUP_HXID_AVATAR = " and " + I.Group.HX_ID + "=" + I.Avatar.USER_NAME + " ";
	private final String SQL_COMPARE_USER_ID_MEMBER = " and " + I.User.USER_ID + "=" + I.Member.USER_ID + " ";

	@Override
	public User findUserByUserName(String userName) {
		System.out.println("SuperQQDao.findUserByUserName,userName="+userName);
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR
				+ " where " + I.User.USER_NAME + "=?" 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				System.out.println("user="+userName.toString());
				return user;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	
	@Override
	public User findUserById(int id) {
		System.out.println("SuperQQDao.findUserById,id="+id);
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR
				+ " where " + I.User.USER_ID + "=?" 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			set = statement.executeQuery();
			if(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				System.out.println("id="+id);
				return user;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	/**
	 * 从set中读取user表的一条记录
	 * @param set
	 * @return
	 * @throws SQLException
	 */
	private void readUser(ResultSet set,User user) throws SQLException {
		user.setMUserId(set.getInt(I.User.USER_ID));
		user.setMUserName(set.getString(I.User.USER_NAME));
		user.setMUserPassword(set.getString(I.User.PASSWORD));
		user.setMUserNick(set.getString(I.User.NICK));
		user.setMUserUnreadMsgCount(set.getInt(I.User.UN_READ_MSG_COUNT));
	}
	
	private void readAvatar(ResultSet set, Avatar avatar) throws SQLException {
		avatar.setMAvatarId(set.getInt(I.Avatar.AVATAR_ID));
		avatar.setMAvatarUserId(set.getInt(I.Avatar.USER_ID));
		avatar.setMAvatarUserName(set.getString(I.Avatar.USER_NAME));
		avatar.setMAvatarPath(set.getString(I.Avatar.AVATAR_PATH));
		avatar.setMAvatarType(set.getInt(I.Avatar.AVATAR_TYPE));
	}
	
	private void readLocation(ResultSet set, Location location) throws SQLException {
		location.setMLocationId(set.getInt(I.Location.LOCATION_ID));
		location.setMLocationUserId(set.getInt(I.Location.USER_ID));
		location.setMLocationUserName(set.getString(I.Location.USER_NAME));
		location.setMLocationLatitude(set.getDouble(I.Location.LATITUDE));
		location.setMLocationLongitude(set.getDouble(I.Location.LONGITUDE));
		location.setMLocationIsSearched(Utils.int2boolean(set.getInt(I.Location.IS_SEARCHED)));
		location.setMLocationLastUpdateTime(set.getString(I.Location.UPDATE_TIME));
	}
	
	private void readGroup(ResultSet set, Group group) throws SQLException {
		group.setMGroupId(set.getInt(I.Group.GROUP_ID));
		group.setMGroupHxid(set.getString(I.Group.HX_ID));
		group.setMGroupName(set.getString(I.Group.NAME));
		group.setMGroupDescription(set.getString(I.Group.DESCRIPTION));
		group.setMGroupOwner(set.getString(I.Group.OWNER));
		group.setMGroupLastModifiedTime(set.getString(I.Group.MODIFIED_TIME));
		group.setMGroupMaxUsers(set.getInt(I.Group.MAX_USERS));
		group.setMGroupAffiliationsCount(set.getInt(I.Group.AFFILIATIONS_COUNT));
		group.setMGroupIsPublic(Utils.int2boolean(set.getInt(I.Group.IS_PUBLIC)));
		group.setMGroupAllowInvites(Utils.int2boolean(set.getInt(I.Group.ALLOW_INVITES)));
	}
	
	private void readMember(ResultSet set, Member member) throws SQLException {
		member.setMMemberId(set.getInt(I.Member.MEMBER_ID));
		member.setMMemberUserId(set.getInt(I.Member.USER_ID));
		member.setMMemberUserName(set.getString(I.Member.USER_NAME));
		member.setMMemberGroupId(set.getInt(I.Member.GROUP_ID));
		member.setMMemberGroupHxid(set.getString(I.Member.GROUP_HX_ID));
		member.setMMemberPermission(set.getInt(I.Member.PERMISSION));
	}

	@Override
	public User[] findUsersByUserName(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR
				+ " where " + I.User.USER_NAME + " like ?" 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ " limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+userName+"%");
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			User[] users = new User[0];
			while(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				users = Utils.add(users, user);
			}
			return users;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User[] findUsersByNick(String nick, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR
				+ " where " + I.User.NICK + " like ?" 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ " limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+nick+"%");
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			User[] users = new User[0];
			while(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				users = Utils.add(users, user);
			}
			return users;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public User[] findUsersForSearch(String nick,String username, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR
				+ " where " + I.User.NICK + " like ?" 
				+ " or " + I.User.USER_NAME + " like ?"
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ " limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, "%"+nick+"%");
			statement.setString(2, "%"+username+"%");
			statement.setInt(3, pageId);
			statement.setInt(4, pageSize);
			set = statement.executeQuery();
			User[] users = new User[0];
			while(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				users = Utils.add(users, user);
			}
			return users;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User[] findUsers4Location(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " +I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR + SQL_QUERY_LOCATION
				+ " where " + I.User.USER_NAME + "<> ? " 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_USER_ID_LOCATION
				+ SQL_COMPARE_AVATAR_USER
				+ "and " + I.User.USER_ID + " in (select "
				+ I.Location.USER_ID +" from " + I.Location.TABLE_NAME
				+ " where " + I.Location.IS_SEARCHED + "=1"
				+ "order by " + I.Location.UPDATE_TIME + " desc"
				+ ") limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set = statement.executeQuery();
			User[] users = new User[0];
			while(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				readLocation(set, user);
				users = Utils.add(users, user);
			}
			return users;
		}catch(SQLException e ){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int addUser(User user) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + I.User.TABLE_NAME
				+ "(" + I.User.USER_NAME
				+ "," + I.User.PASSWORD 
				+ "," + I.User.NICK
				+ "," + I.User.UN_READ_MSG_COUNT 
				+ ")values(?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getMUserName());
			statement.setString(2, user.getMUserPassword());
			statement.setString(3, user.getMUserNick());
			statement.setInt(4, user.getMUserUnreadMsgCount());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			if(set!=null && set.next()){
				int id = set.getInt(1);
				user.setMUserId(id);
				return id;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return 0;
	}

	@Override
	public boolean deleteUser(String userName) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "delete from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			int count = statement.executeUpdate();
			return count>0;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateUserNick(User user, String newNick) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "update " + I.User.TABLE_NAME
				+ " set "+ I.User.NICK + "=?"
				+ " where "+I.User.USER_NAME+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, newNick);
			statement.setString(2, user.getMUserName());
			int count = statement.executeUpdate();
			return count == 1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateUserPassword(User user, String newPassword) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "update " + I.User.TABLE_NAME
				+ " set "+ I.User.PASSWORD + "=?"
				+ " where "+I.User.USER_NAME+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, newPassword);
			statement.setString(2, user.getMUserName());
			int count = statement.executeUpdate();
			return count == 1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean updateUserLocation(Location location) {
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "update " + I.Location.TABLE_NAME
				+ " set "+ I.Location.LATITUDE + "=?,"
				+ I.Location.LONGITUDE+"=?,"
				+ I.Location.IS_SEARCHED+"=?,"
				+ I.Location.UPDATE_TIME+"=?"
				+ " where "+I.Location.USER_NAME+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setDouble(1, location.getMLocationLatitude());
			statement.setDouble(2, location.getMLocationLongitude());
			statement.setInt(3, Utils.boolean2int(location.getMLocationIsSearched()));
			statement.setString(4, location.getMLocationLastUpdateTime());
			statement.setString(5, location.getMLocationUserName());
			int count = statement.executeUpdate();
			return count == 1;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public User getUserLocation(String userName) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " +I.User.TABLE_NAME 
				+ SQL_QUERY_AVATAR + SQL_QUERY_LOCATION
				+ " where " + I.User.USER_NAME + "= ? " 
				+ SQL_COMPARE_USER_NAME_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ SQL_COMPARE_USER_ID_LOCATION;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if(set.next()){
				User user = new User();
				readUser(set, user);
				readAvatar(set, user);
				readLocation(set, user);
				System.out.println("user"+user);
				return user;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public int uploadUserLocation(Location location) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + I.Location.TABLE_NAME
				+ "(" + I.Location.USER_ID
				+ "," + I.Location.USER_NAME + "," + I.Location.LATITUDE
				+ "," + I.Location.LONGITUDE + "," + I.Location.IS_SEARCHED
				+ "," + I.Location.UPDATE_TIME + ")values(?,?,?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, location.getMLocationUserId());
			statement.setString(2, location.getMLocationUserName());
			statement.setDouble(3, location.getMLocationLatitude());
			statement.setDouble(4, location.getMLocationLongitude());
			statement.setInt(5, Utils.boolean2int(location.getMLocationIsSearched()));
			statement.setString(6, location.getMLocationLastUpdateTime());
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			if(set!=null && set.next()){
				int id = set.getInt(1);
				location.setMLocationId(id);
				return id;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return 0;
	}
	
	@Override
	public boolean isExistsContact(String userName, String name) {
		PreparedStatement statement = null;
		ResultSet set = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select " + I.Contact.CONTACT_ID
				+ " from " + I.Contact.TABLE_NAME
				+ " where " + I.Contact.USER_NAME + "=?"
				+ " and " + I.Contact.CU_NAME + "=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setString(2, name);
			set = statement.executeQuery();
			if(set.next()){
				int id = set.getInt(I.Contact.CONTACT_ID);
				return id>0;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return false;
	}

	/**
	 * 根据账号查询id
	 * @param userName
	 * @return
	 */
	private int findIdByUserName(String userName){
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select " + I.User.USER_ID
				+ " from " + I.User.TABLE_NAME
				+ " where " + I.User.USER_NAME + "=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, userName);
			set = statement.executeQuery();
			if(set.next()){
				int id = set.getInt(I.User.USER_ID);
				return id;
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return 0;
	}

	@Override
	public Contact findContactById(int myuid, int cuid) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where " + I.Contact.USER_ID + "=? and "
				+ I.Contact.CU_ID + "=?" 
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			set = statement.executeQuery();
			if(set.next()){
				Contact contact = new Contact();
				readContact(set, contact);
				readUser(set, contact);
				readAvatar(set, contact);
				return contact;
			}
		}catch (SQLException e){
			e.printStackTrace();
		} finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	/**
	 * 从set中获取contact表中的一条记录
	 * @param set
	 * @return
	 * @throws SQLException 
	 */
	private void readContact(ResultSet set,Contact contact) throws SQLException {
		contact.setMContactId(set.getInt(I.Contact.CONTACT_ID));
		contact.setMContactUserId(set.getInt(I.Contact.USER_ID));
		contact.setMContactUserName(set.getString(I.Contact.USER_NAME));
		contact.setMContactCid(set.getInt(I.Contact.CU_ID));
		contact.setMContactCname(set.getString(I.Contact.CU_NAME));
	}

	@Override
	public Contact findContactById(int id) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where "+I.Contact.CONTACT_ID+"=?"
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, id);
			set=statement.executeQuery();
			if(set.next()){
				Contact c = new Contact();
				readContact(set, c);
				readUser(set, c);
				readAvatar(set, c);
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	@Override
	public Contact[] findContactsByUserName(String userName) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where "+I.Contact.USER_NAME+"=?"
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			Contact[] contacts=new Contact[0];
			while(set.next()){
				Contact c = new Contact();
				readContact(set, c);
				readUser(set, c);
				readAvatar(set, c);
				contacts=Utils.add(contacts, c);
			}
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where "+I.Contact.USER_ID+"=?"
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			set=statement.executeQuery();
			Contact[] contacts=new Contact[0];
			while(set.next()){
				Contact c = new Contact();
				readContact(set, c);
				readUser(set, c);
				readAvatar(set, c);
				contacts=Utils.add(contacts, c);
			}
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		
		return null;
	}

	@Override
	public Contact[] findContactsByUserName(String userName, int pageId, int pageSize) {
		ResultSet set = null;
		PreparedStatement statement = null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where "+I.Contact.USER_NAME+"=?"
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ " limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			Contact[] contacts=new Contact[0];
			while(set.next()){
				Contact c = new Contact();
				readContact(set, c);
				readUser(set, c);
				readAvatar(set, c);
				contacts=Utils.add(contacts, c);
			}
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from " + I.Contact.TABLE_NAME
				+ SQL_QUERY_USER + SQL_QUERY_AVATAR
				+ " where "+I.Contact.USER_ID+"=?"
				+ SQL_COMPARE_USER_ID_CONTACT
				+ SQL_COMPARE_USER_ID_AVATAR
				+ SQL_COMPARE_AVATAR_USER
				+ " limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			Contact[] contacts=new Contact[0];
			while(set.next()){
				Contact c = new Contact();
				readContact(set, c);
				readUser(set, c);
				readAvatar(set, c);
				contacts=Utils.add(contacts, c);
			}
			return contacts;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		
		return null;
	}
	@Override
	public Contact addContact(String userName, String name) {
		boolean existsContact = isExistsContact(userName, name);
		Contact contact = new Contact();
		if(existsContact){
			System.out.println("已经是联系人");
			contact.setResult(false);
			contact.setMsg(I.MSG_CONTACT_FIRENDED);
			return contact;
		}
		int myuid=findIdByUserName(userName);
		int cuid=findIdByUserName(name);
		contact.setResult(false);
		contact.setMsg(I.MSG_CONTACT_FAIL);
		ResultSet set = null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Contact.TABLE_NAME
			+"("+I.Contact.USER_ID
			+","+I.Contact.USER_NAME
			+","+I.Contact.CU_ID
			+","+I.Contact.CU_NAME
			+")values(?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, myuid);
			statement.setString(2, userName);
			statement.setInt(3, cuid);
			statement.setString(4, name);
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			if(set!=null && set.next()){
				int id = set.getInt(1);
				if(id>0){
					contact = findContactById(id);
					contact.setResult(true);
				}
			}			
			return contact;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return null;
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Contact.TABLE_NAME
			+" where "+I.Contact.USER_ID+"=? and "
			+I.Contact.CU_ID+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, myuid);
			statement.setInt(2, cuid);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean deleteContact(String userName, String name) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Contact.TABLE_NAME
			+" where "+I.Contact.USER_NAME+"=? and "
			+I.Contact.CU_NAME+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			statement.setString(2, name);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public int createGroup(Group group) {
		ResultSet set=null;
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Group.TABLE_NAME+"("
			+I.Group.HX_ID+","
			+I.Group.NAME+","
			+I.Group.DESCRIPTION+","
			+I.Group.OWNER+","
			+I.Group.MODIFIED_TIME+","
			+I.Group.MAX_USERS+","
			+I.Group.AFFILIATIONS_COUNT+","
			+I.Group.IS_PUBLIC+","
			+I.Group.ALLOW_INVITES
			+")values(?,?,?,?,?,?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, group.getMGroupHxid());
			statement.setString(2, group.getMGroupName());
			statement.setString(3, group.getMGroupDescription());
			statement.setString(4, group.getMGroupOwner());
			statement.setString(5, group.getMGroupLastModifiedTime());
			statement.setInt(6, group.getMGroupMaxUsers());
			statement.setInt(7, group.getMGroupAffiliationsCount());
			statement.setInt(8, Utils.boolean2int(group.getMGroupIsPublic()));
			statement.setInt(9, Utils.boolean2int(group.getMGroupAllowInvites()));
			statement.executeUpdate();
			set = statement.getGeneratedKeys();
			if(set!=null && set.next()){
				int id = set.getInt(1);
				System.out.println("dao.createGroup,id="+id);
				return id; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return 0;
	}

	@Override
	public boolean updateGroupName(Group group, String newGroupName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.NAME+"=?,"
			+ I.Group.MODIFIED_TIME+"=?"
			+" where "+I.Group.GROUP_ID+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, newGroupName);
			statement.setString(2, group.getMGroupLastModifiedTime());
			statement.setInt(3, group.getMGroupId());
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
			
		return false;
	}

	@Override
	public boolean updateGroupAffiliationsCount(Group group, int affiliationsCount) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="update "+I.Group.TABLE_NAME
			+" set "+I.Group.AFFILIATIONS_COUNT+"=?,"
			+ I.Group.MODIFIED_TIME+"=?"
			+" where "+I.Group.GROUP_ID+"=?";
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, affiliationsCount);
			statement.setString(2, group.getMGroupLastModifiedTime());
			statement.setInt(3, group.getMGroupId());
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
			
		return false;
	}

	@Override
	public boolean deleteGroup(int groupId) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Group.TABLE_NAME
			+" where "+I.Group.GROUP_ID+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, groupId);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public Group[] findAllGroup(String userName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Member.TABLE_NAME
			+ SQL_QUERY_GROUP + SQL_QUERY_AVATAR
			+ " where "+I.Member.USER_NAME+"=?"
			+ SQL_COMPARE_GROUP_ID_MEMBER
			+ SQL_COMPARE_AVATAR_GROUP
			+ SQL_COMPARE_GROUP_ID_AVATAR;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, userName);
			set=statement.executeQuery();
			Group[] groups=new Group[0];
			while(set.next()){
				Group g = new Group();
				readGroup(set, g);
				readAvatar(set, g);
				groups=Utils.add(groups, g);
			}
			return groups;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Group[] findGroupByGroupName(String groupName) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+ SQL_QUERY_AVATAR
			+" where "+I.Group.NAME+" like ?"
			+ SQL_COMPARE_GROUP_ID_AVATAR
			+ SQL_COMPARE_AVATAR_GROUP;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, "%"+groupName+"%");
			set=statement.executeQuery();
			Group[] groups = new Group[0];
			while(set.next()){
				Group group = new Group();
				readGroup(set,group);
				readAvatar(set, group);
				groups = Utils.add(groups, group);
			}
			return groups;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Group findGroupByGroupHXID(String hxid) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+ SQL_QUERY_AVATAR
			+" where "+I.Group.HX_ID+"=?"
			+ SQL_COMPARE_GROUP_ID_AVATAR
			+ SQL_COMPARE_AVATAR_GROUP;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, hxid);
			set=statement.executeQuery();
			if(set.next()){
				Group group = new Group();
				readGroup(set,group);
				readAvatar(set, group);
				return group;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}
	
	@Override
	public Group findGroupByGroupId(int groupId) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+ SQL_QUERY_AVATAR
			+ " where "+I.Group.GROUP_ID+"=?"
			+ SQL_COMPARE_GROUP_ID_AVATAR
			+ SQL_COMPARE_AVATAR_GROUP;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, groupId);
			set=statement.executeQuery();
			if(set.next()){
				Group group = new Group();
				readGroup(set, group);
				readAvatar(set, group);
				System.out.println("dao.findGroupByGroupId="+group);
				return group;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}


	@Override
	public boolean addGroupMember(Member member) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Member.TABLE_NAME+"("
				+I.Member.USER_ID+","
				+I.Member.USER_NAME+","
				+I.Member.GROUP_ID+","
				+I.Member.GROUP_HX_ID+","
				+I.Member.PERMISSION
				+")values(?,?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, member.getMMemberUserId());
			statement.setString(2, member.getMMemberUserName());
			statement.setInt(3, member.getMMemberGroupId());
			statement.setString(4, member.getMMemberGroupHxid());
			statement.setInt(5, member.getMMemberPermission());
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean addGroupMembers(Member[] members) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="insert into "+I.Member.TABLE_NAME+"("
				+I.Member.USER_ID+","
				+I.Member.USER_NAME+","
				+I.Member.GROUP_ID+","
				+I.Member.GROUP_HX_ID+","
				+I.Member.PERMISSION
				+")values(?,?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			connection.setAutoCommit(false);
			statement=connection.prepareStatement(sql);
			for(Member member: members) {
				statement.setInt(1, member.getMMemberUserId());
				statement.setString(2, member.getMMemberUserName());
				statement.setInt(3, member.getMMemberGroupId());
				statement.setString(4, member.getMMemberGroupHxid());
				statement.setInt(5, member.getMMemberPermission());
				statement.addBatch();
			}
			int[] count = statement.executeBatch();
//			connection.commit();
		    connection.setAutoCommit(true);
		    System.out.println("addGroupMembers count="+count.toString());
			return count!=null && count.length==members.length;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}


	@Override
	public boolean deleteGroupMember(int groupId, String memberName) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Member.TABLE_NAME
			+ " where "+I.Member.USER_NAME+"=?"
			+ " and "+ I.Member.GROUP_ID +" =?";
//		String sql_updateString = "update "+ I.Group.TABLE_NAME
//			+ " set " + I.Group.AFFILIATIONS_COUNT + "="
//			+ I.Group.AFFILIATIONS_COUNT +"-1"
//			+ " where " + I.Group.GROUP_ID +"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, memberName);
			statement.setInt(2, groupId);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public Group[] findPublicGroup(String userName, int pageId, int pageSize) {
		Group[] groups=new Group[0];
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Group.TABLE_NAME
			+ SQL_QUERY_AVATAR
			+ " where "+I.Group.IS_PUBLIC+"=?"
			+ SQL_COMPARE_GROUP_ID_AVATAR
			+ SQL_COMPARE_AVATAR_GROUP
			+ " and " + I.Group.GROUP_ID + " not in ("
			+ "select " + I.Member.GROUP_ID + " from "
			+ I.Member.TABLE_NAME + " where "
			+ I.Member.USER_NAME + "=?"
			+ ") limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, I.GROUP_PUBLIC);
			statement.setString(2, userName);
			statement.setInt(3, pageId);
			statement.setInt(4, pageSize);
			set=statement.executeQuery();
			while(set.next()){
				Group group = new Group();
				readGroup(set, group);
				readAvatar(set, group);
				groups=Utils.add(groups, group);
			}
			return groups;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Member[] findGroupMembersByHXID(String hxid) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Member.TABLE_NAME
			+ SQL_QUERY_USER + SQL_QUERY_AVATAR
			+" where "+I.Member.GROUP_HX_ID+"=?"
			+ SQL_COMPARE_USER_ID_MEMBER
			+ SQL_COMPARE_USER_ID_AVATAR
			+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, hxid);
			set=statement.executeQuery();
			Member[] members = new Member[0];
			while(set.next()){
				Member m = new Member();
				readMember(set, m);
				readUser(set, m);
				readAvatar(set, m);
				members = Utils.add(members, m);
			}
			return members;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Member[] findGroupMembersByHXID(String hxid, int pageId, int pageSize) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Member.TABLE_NAME
			+ SQL_QUERY_USER + SQL_QUERY_AVATAR
			+" where "+I.Member.GROUP_HX_ID+"=?"
			+ SQL_COMPARE_USER_ID_MEMBER
			+ SQL_COMPARE_USER_ID_AVATAR
			+ SQL_COMPARE_AVATAR_USER
			+ "limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setString(1, hxid);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			Member[] members = new Member[0];
			while(set.next()){
				Member m = new Member();
				readMember(set, m);
				readUser(set, m);
				readAvatar(set, m);
				members = Utils.add(members, m);
			}
			return members;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Member[] findGroupMembersByGroupId(int groupId) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Member.TABLE_NAME
			+ SQL_QUERY_USER + SQL_QUERY_AVATAR
			+" where "+I.Member.GROUP_ID+"=?"
			+ SQL_COMPARE_USER_ID_MEMBER
			+ SQL_COMPARE_USER_ID_AVATAR
			+ SQL_COMPARE_AVATAR_USER;
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, groupId);
			set=statement.executeQuery();
			Member[] members = new Member[0];
			while(set.next()){
				Member m = new Member();
				readMember(set, m);
				readUser(set, m);
				readAvatar(set, m);
				members = Utils.add(members, m);
			}
			return members;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public Member[] findGroupMembersByGroupId(int groupId, int pageId, int pageSize) {
		PreparedStatement statement=null;
		ResultSet set=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="select * from "+I.Member.TABLE_NAME
			+ SQL_QUERY_USER + SQL_QUERY_AVATAR
			+" where "+I.Member.GROUP_ID+"=?"
			+ SQL_COMPARE_USER_ID_MEMBER
			+ SQL_COMPARE_USER_ID_AVATAR
			+ SQL_COMPARE_AVATAR_USER
			+ "limit ?,?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, groupId);
			statement.setInt(2, pageId);
			statement.setInt(3, pageSize);
			set=statement.executeQuery();
			Member[] members = new Member[0];
			while(set.next()){
				Member m = new Member();
				readMember(set, m);
				readUser(set, m);
				readAvatar(set, m);
				members = Utils.add(members, m);
			}
			return members;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(set, statement, connection);
		}
		return null;
	}

	@Override
	public boolean updateAvatar(int id, String name, String path, int type) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql = "insert into " + I.Avatar.TABLE_NAME
				+ "(" + I.Avatar.USER_ID
				+ "," + I.Avatar.USER_NAME 
				+ "," + I.Avatar.AVATAR_PATH
				+ "," + I.Avatar.AVATAR_TYPE + ")values(?,?,?,?)";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.setString(2, name);
			statement.setString(3, path);
			statement.setInt(4, type);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
			
		return false;
	}

	@Override
	public boolean deleteGroupMembers(Member[] members) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Member.TABLE_NAME
				+ " where " + I.Member.GROUP_ID
				+ "=? and " + I.Member.USER_ID
				+ "=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			connection.setAutoCommit(false);
			statement=connection.prepareStatement(sql);
			for(Member member: members) {
				statement.setInt(1, member.getMMemberGroupId());
				statement.setInt(2, member.getMMemberUserId());
				statement.addBatch();
			}
			int[] count = statement.executeBatch();
//			connection.commit();
		    connection.setAutoCommit(true);
		    System.out.println("addGroupMembers count="+count.toString());
			return count!=null && count.length==members.length;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}

	@Override
	public boolean deleteGroupAllMemberByGroupId(int groupId) {
		PreparedStatement statement=null;
		Connection connection = JdbcUtils.getConnection();
		String sql="delete from "+I.Member.TABLE_NAME
			+" where "+I.Member.GROUP_ID+"=?";
		System.out.println("connection="+connection+",sql="+sql);
		try {
			statement=connection.prepareStatement(sql);
			statement.setInt(1, groupId);
			int count = statement.executeUpdate();
			return count>0;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtils.closeAll(null, statement, connection);
		}
		return false;
	}
	

}
