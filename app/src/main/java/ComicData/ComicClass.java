package ComicData;

import java.util.ArrayList;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画分类
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 15:21
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 15:21
 * 修改备注：
 */

public class ComicClass{
	/**
	 * 分类名称
	 */
	private ArrayList<String> class_name;
	/**
	 * 分类链接
	 */
	private ArrayList<String> class_link;

	public ComicClass(){
	}

	public ComicClass(ArrayList<String> class_name,ArrayList<String> class_link){
		this.class_name = class_name;
		this.class_link = class_link;
	}

	public ArrayList<String> getClass_name(){
		return class_name;
	}

	public void setClass_name(ArrayList<String> class_name){
		this.class_name = class_name;
	}

	public ArrayList<String> getClass_link(){
		return class_link;
	}

	public void setClass_link(ArrayList<String> class_link){
		this.class_link = class_link;
	}
}
