package User;

/**
 * 项目名称：Cartoon8
 * 类描述：评论类
 * 创建人：SmileSB101
 * 创建时间：2017/3/11 0011 16:17
 * 修改人：Administrator
 * 修改时间：2017/3/11 0011 16:17
 * 修改备注：
 */

public class Prview{
	private User user;
	private String content;
	private String date;

	public Prview(){
		user = new User();
	}

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public String getContent(){
		return content;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getDate(){
		return date;
	}

	public void setDate(String date){
		this.date = date;
	}
}
