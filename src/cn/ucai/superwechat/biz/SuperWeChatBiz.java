package cn.ucai.superwechat.biz;

import java.io.File;

import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Location;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.Message;
import cn.ucai.superwechat.bean.User;
import cn.ucai.superwechat.dao.ISuperWeChatDao;
import cn.ucai.superwechat.dao.SuperWeChatDao;
import cn.ucai.superwechat.servlet.I;


public class SuperWeChatBiz implements ISuperWeChatBiz {
	private ISuperWeChatDao dao;
	public SuperWeChatBiz(){
		dao = new SuperWeChatDao();
	}

	@Override
	public int register(User user) throws Exception {
		int id = dao.addUser(user);
		return id;
	}

	@Override
	public boolean unRegister(String userName) {
		boolean isDelete = dao.deleteUser(userName);
		if(isDelete){
			String path = I.AVATAR_PATH + I.AVATAR_TYPE_USER_PATH 
					+ I.BACKSLASH + userName + I.AVATAR_SUFFIX_JPG;
			File file = new File(path);
			if (file.exists()){
				file.delete();
			}
		}
		return isDelete;
	}

	@Override
	public User login(String userName, String password){
		System.out.println("SuperQQBiz.login,userName="+userName+",password="+password);
		User user = dao.findUserByUserName(userName);
		if (user == null) {
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		}
		if (!user.getMUserPassword().equals(password)) {
			user = new User(false,I.MSG_LOGIN_ERROR_PASSWORD);
		}
		user.setResult(true);
		user.setMsg(I.MSG_LOGIN_SUCCESS);
		return user;
	}

	@Override
	public User findUserByUserName(String userName) {
		if(userName==null || userName.isEmpty()) return null;
		User user = dao.findUserByUserName(userName);
		return user;
	}

	@Override
	public User[] findUsersByUserName(String userName, int pageId, int pageSize) {
		User[] users = dao.findUsersByUserName(userName, pageId, pageSize);
		return users;
	}

	@Override
	public User[] findUsersByNick(String nick, int pageId, int pageSize) {
		User[] users = dao.findUsersByNick(nick, pageId, pageSize);
		return users;
	}
	
	@Override
	public User[] findUsersForSearch(String nick,int pageId,int pageSize) {
		User[] users = dao.findUsersForSearch(nick, pageId, pageSize);
		return users;
	}

	@Override
	public User[] findUsers4Location(String userName, int pageId, int pageSize) {
		User[] users = dao.findUsers4Location(userName, pageId, pageSize);
		return users;
	}
	
	@Override
	public boolean uploadUserLocation(Location location) {
		int id = dao.uploadUserLocation(location);
		return id>0;
	}
	
	@Override
	public boolean updateUserLocation(Location location) {
		int id = dao.uploadUserLocation(location);
		return id>0;
	}

	@Override
	public User updateUserNickByUserName(String userName, String newNick) {
		User user = dao.findUserByUserName(userName);
		if(user==null){
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		} else if(user.getMUserNick().equals(newNick)) {
			user = new User(false,I.MSG_USER_SAME_NICK);
		} else {
			boolean isSuccess = dao.updateUserNick(user, newNick);
			if(isSuccess){
				user.setMUserNick(newNick);
				user.setResult(true);
				user.setMsg(I.MSG_USER_UPDATE_NICK_SUCCESS);
			} else {
				user.setResult(false);
				user.setMsg(I.MSG_USER_UPDATE_NICK_FAIL);
			}
		}
		return user;
	}

	@Override
	public User updateUserPasswordByUserName(String userName, String newPassword) {
		User user = dao.findUserByUserName(userName);
		if(user==null){
			user = new User(false,I.MSG_LOGIN_UNKNOW_USER);
		} else if(user.getMUserPassword().equals(newPassword)) {
			user = new User(false,I.MSG_USER_SAME_PASSWORD);
		} else {
			boolean isSuccess = dao.updateUserPassword(user, newPassword);
			if(isSuccess){
				user.setMUserPassword(newPassword);
				user.setResult(true);
				user.setMsg(I.MSG_USER_UPDATE_PASSWORD_SUCCESS);
			} else {
				user.setResult(false);
				user.setMsg(I.MSG_USER_UPDATE_PASSWORD_FAIL);
			}
		}
		return user;
	}

	@Override
	public Contact[] findContactsByUserName(String userName) {
		Contact[] contacts = dao.findContactsByUserName(userName);
		return contacts;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid) {
		Contact[] contacts = dao.findContactListByMyuid(myuid);
		return contacts;
	}
	
	@Override
	public Contact[] findContactsByUserName(String userName, int pageId, int pageSize) {
		Contact[] contacts = dao.findContactsByUserName(userName, pageId, pageSize);
		return contacts;
	}

	@Override
	public Contact[] findContactListByMyuid(int myuid, int pageId, int pageSize) {
		Contact[] contacts = dao.findContactListByMyuid(myuid, pageId, pageSize);
		return contacts;
	}

	@Override
	public Contact addContact(String userName, String name) {
		return dao.addContact(userName, name);
	}

	@Override
	public boolean deleteContact(int myuid, int cuid) {
		boolean isSuccess = dao.deleteContact(myuid, cuid);
		if(isSuccess){
			isSuccess = dao.deleteContact(cuid, myuid);
		}
		return isSuccess;
	}

	@Override
	public boolean deleteContact(String userName, String name) {
		boolean isSuccess = dao.deleteContact(userName, name);
		if(isSuccess){
			isSuccess = dao.deleteContact(name, userName);
		}
		return isSuccess;
	}

	@Override
	public int createGroup(Group group) {
		int id = dao.createGroup(group);
		return id;
	}

	@Override
	public Group updateGroupName(int groupId,String newGroupName) {
		Group group = dao.findGroupByGroupId(groupId);
		if(group==null){
			group = new Group(false,I.MSG_GROUP_UNKONW);
		} else if(group.getMGroupName().equals(newGroupName)) {
			group = new Group(false,I.MSG_GROUP_SAME_NAME);
		} else {
			group.setMGroupLastModifiedTime(System.currentTimeMillis()+"");
			boolean isSuccess = dao.updateGroupName(group, newGroupName);
			if(isSuccess){
				group.setMGroupName(newGroupName);
				group.setResult(true);
				group.setMsg(I.MSG_GROUP_UPDATE_NAME_SUCCESS);
			} else {
				group.setResult(false);
				group.setMsg(I.MSG_GROUP_UPDATE_NAME_FAIL);
			}
		}
		return group;
	}
	

	@Override
	public Message deleteGroup(int groupId) {
		//删除所有群组成员
		boolean isSuccess = dao.deleteGroupAllMemberByGroupId(groupId);
		if(isSuccess){
			//删除群组
			isSuccess = dao.deleteGroup(groupId);
			if(isSuccess){
				return new Message(true,I.MSG_GROUP_DELETE_SUCCESS);
			} else {
				return new Message(true,I.MSG_GROUP_DELETE_FAIL);
			}
		} else {
			return new Message(true,I.MSG_GROUP_DELETE_MEMBER_FAIL);
		}
	}

	@Override
	public Group[] findAllGroup(String userName) {
		return dao.findAllGroup(userName);
	}


	@Override
	public Group[] findGroupByGroupName(String groupName) {
		return dao.findGroupByGroupName(groupName);
	}

	@Override
	public Group findGroupByGroupHXID(String hxid) {
		if(hxid==null || hxid.isEmpty())return null;
		return dao.findGroupByGroupHXID(hxid);
	}
	
	@Override
	public Group findGroupByGroupId(int groupId) {
		if(groupId<=0) return null;
		return dao.findGroupByGroupId(groupId);
	}


	@Override
	public Group addGroupMember(int userId,String userName,String hxid, int permission) {
		Group group = dao.findGroupByGroupHXID(hxid);
		if(group!=null){
			Member member = new Member(userId, userName, 
					group.getMGroupId(), hxid, permission);
			boolean isSuccess = dao.addGroupMember(member);
			if(isSuccess){
				return group;
			}
		}
		return null;
	}
	@Override
	public boolean addGroupMembers(String userIds,String userNames,String hxid) {
		Group group = dao.findGroupByGroupHXID(hxid);
		if(group!=null){
			String[] userIdArray = userIds.split(",");
			String[] userNameArray = userNames.split(",");
			Member[] members = new Member[userIdArray.length];
			for(int i=0;i<userIdArray.length;i++){
				Member member = new Member(Integer.parseInt(userIdArray[i]), userNameArray[i], 
						group.getMGroupId(), hxid, I.PERMISSION_NORMAL);
				members[i] = member;
			}
			return dao.addGroupMembers(members);
		}
		return false;
	}
	@Override
	public Member[] downloadGroupMembersByHXID(String hxid) {
		if(hxid==null) return null;
		return dao.findGroupMembersByHXID(hxid);
	}
	
	@Override
	public Member[] downloadGroupMembersByHXID(String hxid,int pageId,int pageSize) {
		if(hxid==null) return null;
		return dao.findGroupMembersByHXID(hxid,pageId,pageSize);
	}
	@Override
	public Member[] downloadGroupMembers(int groupId) {
		if(groupId<=0) return null;
		return dao.findGroupMembersByGroupId(groupId);
	}
	
	@Override
	public Member[] downloadGroupMembers(int groupId,int pageId,int pageSize) {
		if(groupId<=0) return null;
		return dao.findGroupMembersByGroupId(groupId,pageId,pageSize);
	}

	@Override
	public boolean deleteGroupMember(int groupId,String memberName) {
		return dao.deleteGroupMember(groupId, memberName);
	}
	
	@Override
	public boolean deleteGroupMembers(int groupId,String memberNames) {
		String[] memberNameArray = memberNames.split(",");
		Member[] members = new Member[memberNameArray.length];
		for(int i=0;i<memberNameArray.length;i++){
			members[i] = new Member();
			members[i].setMMemberGroupId(groupId);
			members[i].setMMemberUserName(memberNameArray[i]);
		}
		return dao.deleteGroupMembers(members);
	}

	@Override
	public Group[] findPublicGroup(String userName, int pageId, int pageSize) {
		return dao.findPublicGroup(userName, pageId, pageSize);
	}
	
	@Override
	public boolean updateAvatar(int id, String name,int type) {
		if(id>0 && name!=null) {
			if(type==I.AVATAR_TYPE_USER){
				return dao.updateAvatar(id, name, I.AVATAR_TYPE_USER_PATH, I.AVATAR_TYPE_USER);
			}else{
				return dao.updateAvatar(id, name, I.AVATAR_TYPE_GROUP_PATH, I.AVATAR_TYPE_GROUP);
			}
		}
		return false;
	}

}
