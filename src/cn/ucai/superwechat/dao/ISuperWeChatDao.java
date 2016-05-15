package cn.ucai.superwechat.dao;

import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Location;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.User;


/**
 * 数据访问层接口文件
 * @author chen
 *
 */
public interface ISuperWeChatDao {
	/**
	 * 根据用户账号查找用户
	 * @param userName
	 * @return
	 */
	User findUserByUserName(String userName);

	/**
	 * 根据用户账号查找用户集合，模糊查询
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByUserName(String userName,int pageId,int pageSize);

	/**
	 * 根据用户昵称查找用户集合，模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByNick(String nick,int pageId,int pageSize);

	/**
	 * 根据昵称或者用户名查找用户集合，模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersForSearch(String nick,int pageId,int pageSize);

	/**
	 * 查找附近的人，根据上线时间排序
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsers4Location(String userName,int pageId,int pageSize);

	/**
	 * 添加用户,并返回用户id
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * 删除用户
	 * @param userName
	 * @return
	 */
	boolean deleteUser(String userName);

	/**
	 * 更新用户昵称
	 * @param user
	 * @return
	 */
	boolean updateUserNick(User user, String newNick);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	boolean updateUserPassword(User user, String newPassword);
	
	/**
	 * 更新用户地理位置
	 * @param user
	 * @return
	 */
	boolean updateUserLocation(Location location);
	

	/**
	 * 获取用户地理位置
	 * @param user
	 * @return
	 */
	User getUserLocation(String userName);
	

	/**
	 * 上传用户地理位置
	 * @param user
	 * @return
	 */
	int uploadUserLocation(Location location);

	/**
	 * 根据用户账号查询双方是否为好友
	 * @param userName
	 * @param name
	 * @return
	 */
	boolean isExistsContact(String userName,String name);

	/**
	 * 根据uid和ciu查询联系人信息
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	Contact findContactById(int myuid,int cuid);

	/**
	 * 根据id查找好友数据
	 * @param id
	 * @return
	 */
	Contact findContactById(int id);

	/**
	 * 根据用户账号查找好友集合
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactsByUserName(String userName);

	/**
	 * 根据用户id查找好友集合
	 * @param myuid
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactListByMyuid(int myuid);
	
	/**
	 * 根据用户账号查找好友集合
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactsByUserName(String userName,int pageId,int pageSize);

	/**
	 * 根据用户id查找好友集合
	 * @param myuid
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Contact[] findContactListByMyuid(int myuid,int pageId,int pageSize);

	/**
	 * 添加好友
	 * @param userName
	 * @param name
	 * @return
	 */
	Contact addContact(String userName,String name);

	/**
	 * 删除好友
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(int myuid,int cuid);

	/**
	 * 删除好友
	 * @param myuid
	 * @param cuid
	 * @return
	 */
	boolean deleteContact(String userName,String name);

	/**
	 * 创建群组
	 * @param group
	 * @return
	 */
	Group createGroup(Group group);
	
	/**
	 * 更新群名称
	 * @param group
	 * @param newGroupName
	 * @return
	 */
	boolean updateGroupName(Group group,String newGroupName);

	/**
	 * 更新群人数
	 * @param group
	 * @param AffiliationsCount
	 * @return
	 */
	boolean updateGroupAffiliationsCount(Group group,int affiliationsCount);

	/**
	 * 删除群组
	 * @param groupId
	 * @return
	 */
	boolean deleteGroup(int groupId);

	/**
	 * 根据用户账号查找所在群组集合
	 * @param userName
	 * @return
	 */
	Group[] findAllGroup(String userName);

	/**
	 * 查找公开群组，不包含已经加入的
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Group[] findPublicGroup(String userName,int pageId,int pageSize);

	/**
	 * 根据群组名称查找群组列表
	 * @param groupName
	 * @return
	 */
	Group[] findGroupByGroupName(String groupName);

	/**
	 * 根据群组环信id查找群组列表
	 * @param groupName
	 * @return
	 */
	Group findGroupByGroupHXID(String hxid);

	/**
	 * 根据群组id查找群组
	 * @param groupId
	 * @return
	 */
	Group findGroupByGroupId(int groupId);

	/**
	 * 添加群组成员
	 * @param member
	 * @return
	 */
	boolean addGroupMember(Member member);

	/**
	 * 添加多个群组成员
	 * @param member
	 * @return
	 */
	boolean addGroupMembers(Member[] members);

	/**
	 * 删除群组成员
	 * @param groupId
	 * @param memberName
	 * @return
	 */
	boolean deleteGroupMember(int groupId, String memberName);

	/**
	 * 根据群组id查询所有群组成员
	 * @param groupId
	 * @return
	 */
	Member[] findGroupMembersByGroupId(int groupId);
	/**
	 * 根据群组id查询所有群组成员,并分页显示
	 * @param groupId
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Member[] findGroupMembersByGroupId(int groupId, int pageId, int pageSize);

	/**
	 * 更新头像
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateAvatar(int id, String name, String path, int type);

	/**
	 * 根据用户id查找用户
	 * @param id
	 * @return
	 */
	public User findUserById(int id);
	
	/**
	 * 删除指定的群组成员
	 * @param users
	 * @return
	 */
	boolean deleteGroupMembers(Member[] members);
	
	/**
	 * 根据群组Id删除所有群成员
	 * @param groupId
	 * @return
	 */
	boolean deleteGroupAllMemberByGroupId(int groupId);

}
