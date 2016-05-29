package cn.ucai.superwechat.biz;

import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Location;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.Message;
import cn.ucai.superwechat.bean.User;


/**
 * Servlet调用的业务逻辑层接口
 * @author chen
 *
 */
public interface ISuperWeChatBiz {
	/**
	 * 注册用户
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int register(User user) throws Exception;
	/**
	 * 取消注册，删除用户及图片
	 * @param userName
	 * @return
	 */
	boolean unRegister(String userName);
	/**
	 * 登陆
	 * @param userName
	 * @param password
	 * @return
	 */
	User login(String userName,String password);
	
	/**
	 *根据用户账号查找用户
	 * @param userName
	 * @return
	 */
	User findUserByUserName(String userName);
	
	/**
	 * 根据用户账号查找用户,模糊查询
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByUserName(String userName,int pageId,int pageSize);
	/**
	 * 根据用户昵称查找用户,模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersByNick(String nick,int pageId,int pageSize);
	/**
	 * 根据昵称或者用户名查找用户集合,模糊查询
	 * @param nick
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsersForSearch(String nick,String username,int pageId,int pageSize);
	/**
	 * 下载附近的人
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	User[] findUsers4Location(String userName,int pageId,int pageSize);
	/**
	 * 上传用户的地理位置
	 * @param location
	 * @return
	 */
	boolean uploadUserLocation(Location location);
	/**
	 * 更新用户的地理位置
	 * @param location
	 * @return
	 */
	boolean updateUserLocation(Location location);
	/**
	 * 根据用户账号更新用户昵称
	 * @param user
	 * @return
	 */
	User updateUserNickByUserName(String userName, String newNick);
	/**
	 * 根据用户账号更新用户密码
	 * @param userName
	 * @param newPassword
	 * @return
	 */
	User updateUserPasswordByUserName(String userName, String newPassword);
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
	int createGroup(Group group);
	/**
	 * 修改群组名称
	 * @param group
	 * @param newGroupName
	 * @return
	 */
	Group updateGroupName(int groupId,String newGroupName);
	/**
	 * 删除群组
	 * @param groupName
	 * @return
	 */
	Message deleteGroup(int groupId);
	/**
	 * 下载当前用户所属的所有群
	 * @param userName
	 * @return
	 */
	Group[] findAllGroup(String userName);
	/**
	 * 下载所有公开群
	 * @param userName
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Group[] findPublicGroup(String userName,int pageId,int pageSize);
	/**
	 * 根据群组名称查找群组，模糊查询
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
	 * 根据群组环信id查找公开群组列表
	 * @param groupName
	 * @return
	 */
	Group findPublicGroupByHXID(String hxid);
	/**
	 * 根据群组id查找群组
	 * @param groupId
	 * @return
	 */
	Group findGroupByGroupId(int groupId);
	/**
	 * 添加多个群组成员
	 * @param groupName
	 * @param memberName
	 * @return
	 */
	boolean addGroupMembers(String userIds,String userNames,String hxid);
	/**
	 * 添加群组成员
	 * @param group
	 * @return
	 */
	Group addGroupMember(String userName,String hxid,int permission);
	/**
	 * 添加群组成员
	 * @param group
	 * @return
	 */
	Group addGroupMember(int userId,String userName,String hxid,int permission);
	/**
	 * 根据群组id下载所有群组成员
	 * @param groupId
	 * @return
	 */
	Member[] downloadGroupMembersByHXID(String hxid);
	/**
	 * 根据群组id下载所有群组成员,分页显示
	 * @param groupId
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Member[] downloadGroupMembersByHXID(String hxid,int pageId,int pageSize);
	/**
	 * 根据群组id下载所有群组成员
	 * @param groupId
	 * @return
	 */
	Member[] downloadGroupMembers(int groupId);
	/**
	 * 根据群组id下载所有群组成员,分页显示
	 * @param groupId
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	Member[] downloadGroupMembers(int groupId,int pageId,int pageSize);
	/**
	 * 删除群组成员
	 * @param groupName
	 * @param memberName
	 * @return
	 */
	boolean deleteGroupMember(int groupId,String memberName);
	/**
	 * 删除多个群组成员
	 * @param groupId
	 * @param memberNames
	 * @return
	 */
	boolean deleteGroupMembers(int groupId,String memberNames);
	/**
	 * 上传头像数据
	 * @param name
	 * @param avatar
	 * @return
	 */
	boolean updateAvatar(int id, String name,int type);
	

}
