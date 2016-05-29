package cn.ucai.superwechat.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import cn.ucai.superwechat.bean.Contact;
import cn.ucai.superwechat.bean.Group;
import cn.ucai.superwechat.bean.Location;
import cn.ucai.superwechat.bean.Member;
import cn.ucai.superwechat.bean.Message;
import cn.ucai.superwechat.bean.User;
import cn.ucai.superwechat.biz.ISuperWeChatBiz;
import cn.ucai.superwechat.biz.SuperWeChatBiz;
import cn.ucai.superwechat.utils.Utils;


/**
 * Servlet implementation class Server
 * @author chen
 *
 */
@WebServlet("/Server")
public class Server extends HttpServlet {
	private static final long serialVersionUID = -3135817168732302431L;
	ISuperWeChatBiz biz;
	
	public Server(){
		super();
		biz = new SuperWeChatBiz();
	}

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter(I.KEY_REQUEST);
		System.out.println("doGet requestType="+requestType);
		response.setContentType("text/html;charset=utf-8");
		if(requestType == null){
			return;
		}
		switch(requestType){
		case I.REQUEST_SERVERSTATUS:
			getServerStatus(request, response);
			break;
		case I.REQUEST_UNREGISTER:
			unRegister(request,response);
			break;
		case I.REQUEST_LOGIN:
			login(response, request);
			break;
		case I.REQUEST_DOWNLOAD_AVATAR:
			downloadAvatar(request, response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_AVATAR:
			downloadGroupAvatar(request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACT_LIST:
			downloadContactList(request, response);
			break;
		case I.REQUEST_DOWNLOAD_CONTACT_ALL_LIST:
			downloadContactAllList(request, response);
			break;
		case I.REQUEST_ADD_CONTACT:
			addContact(request, response);
			break;
		case I.REQUEST_DELETE_CONTACT:
			deleteContact(request, response);
			break;
		case I.REQUEST_FIND_USER:
			findUserByUserName(request, response);
			break;
		case I.REQUEST_FIND_USERS:
			findUsersByUserName(request, response);
			break;
		case I.REQUEST_FIND_USERS_BY_NICK:
			findUsersByNick(request, response);
			break;
		case I.REQUEST_FIND_USERS_FOR_SEARCH:
			findUsersForSearch(request, response);
			break;
		case I.REQUEST_UPLOAD_LOCATION:
			uploadLocation(request, response);
			break;
		case I.REQUEST_UPDATE_LOCATION:
			updateLocation(request, response);
			break;
		case I.REQUEST_DOWNLOAD_LOCATION:
			downloadLocation(request, response);
			break;
		case I.REQUEST_ADD_GROUP_MEMBER:
			addGroupMember(request,response);
			break;
		case I.REQUEST_ADD_GROUP_MEMBERS:
			addGroupMembers(request,response);
			break;
		case I.REQUEST_UPDATE_GROUP_NAME:
			updateGroupName(request,response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_MEMBERS:
			downloadGroupMembers(request,response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_LIMIT:
			downloadGroupMembersByLimit(request,response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID:
			downloadGroupMembersByHXID(request,response);
			break;
		case I.REQUEST_DOWNLOAD_GROUP_MEMBERS_BY_HXID_LIMIT:
			downloadGroupMembersByHXIDLimit(request,response);
			break;
		case I.REQUEST_DELETE_GROUP_MEMBER:
			deleteGroupMember(request,response);
			break;
		case I.REQUEST_DELETE_GROUP_MEMBERS:
			deleteGroupMembers(request,response);
			break;
		case I.REQUEST_DELETE_GROUP:
			deleteGroup(request,response);
			break;
		case I.REQUEST_DOWNLOAD_GROUPS:
			downloadAllGroups(request,response);
			break;
		case I.REQUEST_FIND_PUBLIC_GROUPS:
			findPublicGroup(request,response);
			break;
		case I.REQUEST_FIND_GROUP:
			findGroupByName(request,response);
			break;
		case I.REQUEST_FIND_GROUP_BY_ID:
			findGroupById(request,response);
			break;
		case I.REQUEST_FIND_GROUP_BY_HXID:
			findGroupByHXID(request,response);
			break;
		case I.REQUEST_FIND_PUBLIC_GROUP_BY_HXID:
			findPublicGroupByHXID(request,response);
			break;
		case I.REQUEST_UPDATE_USER_NICK:
			updateUserNickByName(request,response);
			break;
		case I.REQUEST_UPDATE_USER_PASSWORD:
			updateUserPassowrdByName(request,response);
			break;
		}
	}
	
	/**
	 * 根据用户账号更新用户密码
	 * @param request
	 * @param response
	 */
	private void updateUserPassowrdByName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		String newPassword = request.getParameter(I.User.PASSWORD);
		User user = biz.updateUserPasswordByUserName(userName, newPassword);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户账号更新用户昵称
	 * @param request
	 * @param response
	 */
	private void updateUserNickByName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		String newNick = request.getParameter(I.User.NICK);
		try {
			newNick = new String(newNick.getBytes(I.ISON8859_1), I.UTF_8);
			User user = biz.updateUserNickByUserName(userName, newNick);
			ObjectMapper om = new ObjectMapper();
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据群组环信id查找群组
	 * @param request
	 * @param response
	 */
	private void findGroupByHXID(HttpServletRequest request,
			HttpServletResponse response) {
		String hxid = request.getParameter(I.Group.HX_ID);
		Group group = biz.findGroupByGroupHXID(hxid);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 根据群组环信id查找群组
	 * @param request
	 * @param response
	 */
	private void findPublicGroupByHXID(HttpServletRequest request,
			HttpServletResponse response) {
		String hxid = request.getParameter(I.Group.HX_ID);
		Group group = biz.findPublicGroupByHXID(hxid);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据群组id查找群组
	 * @param request
	 * @param response
	 */
	private void findGroupById(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Group.GROUP_ID);
		Group group = biz.findGroupByGroupId(Integer.parseInt(groupId));
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据群组名称模糊查找群组集合
	 * @param request
	 * @param response
	 */
	private void findGroupByName(HttpServletRequest request,
			HttpServletResponse response) {
		String groupName = request.getParameter(I.Group.NAME);
		try {
			groupName = new String(groupName.getBytes(I.ISON8859_1), I.UTF_8);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Group[] groups = biz.findGroupByGroupName(groupName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), groups);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载公开群组，并分页显示
	 * @param request
	 * @param response
	 */
	private void findPublicGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		Group[] groups = biz.findPublicGroup(userName, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), groups);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载用户所有群组
	 * @param request
	 * @param response
	 */
	private void downloadAllGroups(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		Group[] groups = biz.findAllGroup(userName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), groups);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 删除群组
	 * @param request
	 * @param response
	 */
	private void deleteGroup(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Group.GROUP_ID);
		Message message = biz.deleteGroup(Integer.parseInt(groupId));
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), message);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 删除多个群组成员
	 * @param request
	 * @param response
	 */
	private void deleteGroupMembers(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Member.GROUP_ID);
		String userName = request.getParameter(I.Member.USER_NAME);
		boolean isSuccess = biz.deleteGroupMembers(Integer.parseInt(groupId), userName);
		ObjectMapper om = new ObjectMapper();
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_GROUP_DELETE_MEMBER_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_DELETE_MEMBER_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 删除群组成员
	 * @param request
	 * @param response
	 */
	private void deleteGroupMember(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Member.GROUP_ID);
		String userName = request.getParameter(I.Member.USER_NAME);
		boolean isSuccess = biz.deleteGroupMember(Integer.parseInt(groupId), userName);
		ObjectMapper om = new ObjectMapper();
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_GROUP_DELETE_MEMBER_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_DELETE_MEMBER_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载群组成员，并分页显示
	 * @param request
	 * @param response
	 */
	private void downloadGroupMembersByHXIDLimit(HttpServletRequest request,
			HttpServletResponse response) {
		String hxid = request.getParameter(I.Member.GROUP_HX_ID);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		Member[] members = biz.downloadGroupMembersByHXID(hxid, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), members);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载所有群组成员
	 * @param request
	 * @param response
	 */
	private void downloadGroupMembersByHXID(HttpServletRequest request,
			HttpServletResponse response) {
		String hxid = request.getParameter(I.Member.GROUP_HX_ID);
		Member[] members = biz.downloadGroupMembersByHXID(hxid);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), members);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * 下载群组成员，并分页显示
	 * @param request
	 * @param response
	 */
	private void downloadGroupMembersByLimit(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Member.GROUP_ID);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		Member[] members = biz.downloadGroupMembers(Integer.parseInt(groupId), pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), members);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载所有群组成员
	 * @param request
	 * @param response
	 */
	private void downloadGroupMembers(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = request.getParameter(I.Member.GROUP_ID);
		Member[] members = biz.downloadGroupMembers(Integer.parseInt(groupId));
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), members);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 更新群组名称
	 * @param request
	 * @param response
	 */
	private void updateGroupName(HttpServletRequest request,
			HttpServletResponse response) {
		String newGroupName = request.getParameter(I.Group.NAME);
		String groupId = request.getParameter(I.Group.GROUP_ID);
		Group group = biz.updateGroupName(Integer.parseInt(groupId), newGroupName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), group);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 添加多个群组成员
	 * @param request
	 * @param response
	 */
	private void addGroupMembers(HttpServletRequest request,
			HttpServletResponse response) {
		String userIds = request.getParameter(I.Member.USER_ID);
		String userNames = request.getParameter(I.Member.USER_NAME);
		String hxid = request.getParameter(I.Member.GROUP_HX_ID);
		boolean isSuccess = biz.addGroupMembers(userIds,userNames,hxid);
		ObjectMapper om = new ObjectMapper();
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_GROUP_ADD_MEMBER_SCUUESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_ADD_MEMBER_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 添加群组成员
	 * @param request
	 * @param response
	 */
	private void addGroupMember(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter(I.Member.USER_ID);
		String userName = request.getParameter(I.Member.USER_NAME);
		String hxid = request.getParameter(I.Member.GROUP_HX_ID);
		Group group = biz.addGroupMember(Integer.parseInt(userId),userName,hxid,I.PERMISSION_NORMAL);
		ObjectMapper om = new ObjectMapper();
		try {
			if(group!=null){
				group.setResult(true);
				group.setMsg(I.MSG_GROUP_ADD_MEMBER_SCUUESS);
				om.writeValue(response.getOutputStream(), group);
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_ADD_MEMBER_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 下载附近的用户集合
	 * @param request
	 * @param response
	 */
	private void downloadLocation(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Location.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] users = biz.findUsers4Location(userName, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), users);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户地理位置
	 * @param request
	 * @param response
	 */
	private void updateLocation(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter(I.Location.USER_ID);
		String userName = request.getParameter(I.Location.USER_NAME);
		String latitude = request.getParameter(I.Location.LATITUDE);
		String longitude = request.getParameter(I.Location.LONGITUDE);
		String isSearched = request.getParameter(I.Location.IS_SEARCHED);
		Location location = new Location(Integer.parseInt(userId), userName, 
				Double.parseDouble(latitude), Double.parseDouble(longitude), 
				Boolean.parseBoolean(isSearched),System.currentTimeMillis()+"");
		boolean isSuccess = biz.uploadUserLocation(location);
		ObjectMapper om = new ObjectMapper();
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_LOCATION_UPDATE_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_LOCATION_UPDATE_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传用户地理位置
	 * @param request
	 * @param response
	 */
	private void uploadLocation(HttpServletRequest request,
			HttpServletResponse response) {
		String userId = request.getParameter(I.Location.USER_ID);
		String userName = request.getParameter(I.Location.USER_NAME);
		String latitude = request.getParameter(I.Location.LATITUDE);
		String longitude = request.getParameter(I.Location.LONGITUDE);
		Location location = new Location(Integer.parseInt(userId), userName, 
				Double.parseDouble(latitude), Double.parseDouble(longitude), 
				Utils.int2boolean(I.LOCATION_IS_SEARCH_ALLOW),System.currentTimeMillis()+"");
		boolean isSuccess = biz.uploadUserLocation(location);
		ObjectMapper om = new ObjectMapper();
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_LOCATION_UPLOAD_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_LOCATION_UPLOAD_FAIL));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据用户账号查找用户
	 * @param request
	 * @param response
	 */
	private void findUserByUserName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		User user = biz.findUserByUserName(userName);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据账号查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersByUserName(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersByUserName(userName,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 根据昵称查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersByNick(HttpServletRequest request,
			HttpServletResponse response) {
		String nick = request.getParameter(I.User.NICK);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersByNick(nick,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据昵称或者用户名查找用户集合
	 * @param request
	 * @param response
	 */
	private void findUsersForSearch(HttpServletRequest request,
			HttpServletResponse response) {
		String nick = request.getParameter(I.User.NICK);
		String username = request.getParameter(I.User.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		User[] user = biz.findUsersForSearch(nick,username,pageId,pageSize);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), user);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除好友
	 * @param request
	 * @param response
	 */
	private void deleteContact(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		String name = request.getParameter(I.Contact.CU_NAME);
		boolean isSuccess = biz.deleteContact(userName, name);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), isSuccess);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 添加好友
	 * @param request
	 * @param response
	 */
	private void addContact(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		String name = request.getParameter(I.Contact.CU_NAME);
		Contact contact = biz.addContact(userName, name);
		ObjectMapper om = new ObjectMapper();
		try {
			om.writeValue(response.getOutputStream(), contact);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载好友集合
	 * @param request
	 * @param response
	 */
	private void downloadContactAllList(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		Contact[] contacts = biz.findContactsByUserName(userName);
		ObjectMapper om = new ObjectMapper();
		 try {
			 om.writeValue(response.getOutputStream(), contacts);
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}
	
	/**
	 * 下载好友集合
	 * @param request
	 * @param response
	 */
	private void downloadContactList(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = request.getParameter(I.Contact.USER_NAME);
		int pageId = Integer.parseInt(request.getParameter(I.PAGE_ID));
		int pageSize = Integer.parseInt(request.getParameter(I.PAGE_SIZE));
		Contact[] contacts = biz.findContactsByUserName(userName, pageId, pageSize);
		ObjectMapper om = new ObjectMapper();
		 try {
			 om.writeValue(response.getOutputStream(), contacts);
		 }catch(IOException e){
			 e.printStackTrace();
		 }
	}

	/**
	 * 下载群组头像
	 * @param request
	 * @param response
	 */
	private void downloadGroupAvatar(HttpServletRequest request,
			HttpServletResponse response) {
		File file = null;
		String avatar = request.getParameter(I.AVATAR_TYPE);
		file = new File(I.AVATAR_PATH + I.AVATAR_TYPE_GROUP_PATH 
				+ I.BACKSLASH + avatar + I.AVATAR_SUFFIX_JPG);
		System.out.println("file.path="+file.getPath());
		downloadAvatar(file, response);
	}

	/**
	 * 下载用户头像
	 * @param request
	 * @param response
	 */
	private void downloadAvatar(HttpServletRequest request, HttpServletResponse response) {
		File file = null;
		String avatar = request.getParameter(I.AVATAR_TYPE);
		file = new File(I.AVATAR_PATH + I.AVATAR_TYPE_USER_PATH 
				+ I.BACKSLASH + avatar + I.AVATAR_SUFFIX_JPG);
		System.out.println("file.path="+file.getPath());
		downloadAvatar(file, response);
	}
	
	/**
	 * 下载头像图片
	 * @param file
	 * @param response
	 */
	private void downloadAvatar(File file, HttpServletResponse response){
		if (!file.exists()) {
			System.out.println("头像不存在");
			return;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			int len;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			System.out.println("头像下载完毕");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeStream(in);
		}
	}

	/**
	 * 登陆
	 * @param response
	 * @param request
	 */
	private void login(HttpServletResponse response, HttpServletRequest request) {
		ObjectMapper om = new ObjectMapper();
		String userName = request.getParameter(I.User.USER_NAME);
		String password = request.getParameter(I.User.PASSWORD);
		User user = biz.login(userName, password);
		try {
			om.writeValue(response.getOutputStream(), user);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 取消注册，删除账号和图片
	 * @param request
	 * @param response
	 */
	private void unRegister(HttpServletRequest request,
			HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		String userName = request.getParameter(I.User.USER_NAME);
		boolean isSuccess = biz.unRegister(userName);
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UNREGISTER_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UNREGISTER_FAIL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得服务端连接状态，并返回给客户端
	 * @param request
	 * @param response
	 */
	private void getServerStatus(HttpServletRequest request,
			HttpServletResponse response){
		ObjectMapper om = new ObjectMapper();
		Message msg = new Message(true,I.MSG_CONNECTION_SUCCESS);
		try {
			om.writeValue(response.getOutputStream(), msg);
		}catch(JsonGenerationException e){
			e.printStackTrace();
		}catch(JsonMappingException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,IOException{
		String requestType = request.getParameter(I.KEY_REQUEST);
		System.out.println("doGet requestType="+requestType);
		switch(requestType){
			case I.REQUEST_REGISTER:
				register(request, response);
				break;
			case I.REQUEST_UPLOAD_AVATAR:
				uploadAvatarByNameOrHXID(request,response);
				break;
			case I.REQUEST_CREATE_GROUP:
				createGroup(request,response);
				break;
		}
	}

	/**
	 * 创建群组
	 * @param request
	 * @param response
	 */
	private void createGroup(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		String hxid = request.getParameter(I.Group.HX_ID);
		try {
			if(biz.findGroupByGroupHXID(hxid)!=null){
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_HXID_EXISTS));
			} else {
				isSuccess = uploadAvatar(hxid, I.AVATAR_TYPE_GROUP, request);
				if(isSuccess){
					String name = request.getParameter(I.Group.NAME);
					name = new String(name.getBytes(I.ISON8859_1), I.UTF_8);
					String disc = request.getParameter(I.Group.DESCRIPTION);
					disc = new String(disc.getBytes(I.ISON8859_1), I.UTF_8);
					String owner = request.getParameter(I.Group.OWNER);
					String isPublic = request.getParameter(I.Group.IS_PUBLIC);
					String allowInvites = request.getParameter(I.Group.ALLOW_INVITES);
					Group group = new Group(hxid, name, disc, owner, System.currentTimeMillis()+"",
							I.GROUP_MAX_USERS_DEFAULT, I.GROUP_AFFILIATIONS_COUNT_DEFAULT, 
							Boolean.parseBoolean(isPublic), Boolean.parseBoolean(allowInvites));
					int id = biz.createGroup(group);
					System.out.println("create group,id="+id);
					if(id>0){
						isSuccess = biz.updateAvatar(id, hxid, I.AVATAR_TYPE_GROUP);
						if(isSuccess){
							String userId = request.getParameter(I.User.USER_ID);
							Group g = biz.addGroupMember(Integer.parseInt(userId),owner,hxid,I.PERMISSION_OWNER);
							if(g!=null){
								g.setResult(true);
								g.setMsg(I.MSG_GROUP_CREATE_SCUUESS);
								om.writeValue(response.getOutputStream(), g);
							} else {
								om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_ADD_MEMBER_FAIL));
							}
						} else {
							om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
						}
					}else{
						om.writeValue(response.getOutputStream(), new Message(false, I.MSG_GROUP_CREATE_FAIL));
					}
				} else {
					om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 上传头像
	 * @param request
	 * @param response
	 */
	private void uploadAvatarByNameOrHXID(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		String type = request.getParameter(I.AVATAR_TYPE);
		if(type.equals(I.AVATAR_TYPE_USER_PATH)) {
			String userName = request.getParameter(I.User.USER_NAME);
			isSuccess = uploadAvatar(userName, I.AVATAR_TYPE_USER, request);
		} else {
			String hxid = request.getParameter(I.Group.HX_ID);
			isSuccess = uploadAvatar(hxid, I.AVATAR_TYPE_GROUP, request);
		}
		try {
			if(isSuccess){
				om.writeValue(response.getOutputStream(), new Message(true, I.MSG_UPLOAD_AVATAR_SUCCESS));
			} else {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) {
		ObjectMapper om = new ObjectMapper();
		boolean isSuccess = false;
		// 步骤1-从request中获取userName、nick、password
		String userName = request.getParameter(I.User.USER_NAME);
		// 步骤2-验证用户名是否已经存在
		try {
			if (biz.findUserByUserName(userName) != null) {
				om.writeValue(response.getOutputStream(), new Message(false, I.MSG_REGISTER_USERNAME_EXISTS));
			} else {
				// 步骤3-上传头像图片
				isSuccess = uploadAvatar(userName, I.AVATAR_TYPE_USER, request);
				if(isSuccess) {
					String nick = request.getParameter(I.User.NICK);
					// 解决乱码问题
					nick = new String(nick.getBytes(I.ISON8859_1), I.UTF_8);
					String password = request.getParameter(I.User.PASSWORD);
					// 步骤4-将三个数据封装在一个UserBean对象中
					User user = new User(I.ID_DEFAULT,userName, password, nick,I.UN_READ_MSG_COUNT_DEFAULT);
					// 步骤5-调用业务逻辑层的方法进行注册
					int id = biz.register(user);
					System.out.println("register user,id="+id);
					// 步骤6-调用业务逻辑层的方法上传头像数据
					if(id>0){
						isSuccess = biz.updateAvatar(id, userName, I.AVATAR_TYPE_USER);
						// 步骤7-将isSuccess发送给客户端
						if(isSuccess){
							om.writeValue(response.getOutputStream(), new Message(true, I.MSG_REGISTER_SUCCESS));
						} else {
							om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
						}
					}else{
						om.writeValue(response.getOutputStream(), new Message(false, I.MSG_REGISTER_FAIL));
					}
				} else {
					om.writeValue(response.getOutputStream(), new Message(false, I.MSG_UPLOAD_AVATAR_FAIL));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 
	/**
	 * 上传头像
	 * @param name
	 * @param type
	 * @param request
	 * @return
	 */
	private boolean uploadAvatar(String name, int type, HttpServletRequest request) {
		String path;
		switch (type) {
		case I.AVATAR_TYPE_USER:
			path = I.AVATAR_PATH + I.AVATAR_TYPE_USER_PATH + I.BACKSLASH;
			break;
		case I.AVATAR_TYPE_GROUP:
		default:
			path = I.AVATAR_PATH + I.AVATAR_TYPE_GROUP_PATH + I.BACKSLASH;
			break;
		}
		String fileName = name + I.AVATAR_SUFFIX_JPG;
		System.out.println("头像上传路径:" + path + fileName);
		File file = new File(path,fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024 * 8];
			int len;
			while ((len = request.getInputStream().read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally{
			closeStream(fos);
		}
		return true;
	}
	
	/**
	 * 关闭文件输出流
	 * @param fos
	 */
	private void closeStream(FileOutputStream fos) {
		try {
			if(fos!=null){
				fos.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭文件输入流
	 * @param fis
	 */
	private void closeStream(FileInputStream fis) {
		try {
			if(fis!=null){
				fis.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
