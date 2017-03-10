package ComicData;

import java.util.ArrayList;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画排行榜
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 15:10
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 15:10
 * 修改备注：
 */

public class ComicRankList{
	/**
	 * 排行名称
	 */
	private String rank_Name;
	/**
	 * 排行代码
	 */
	private String rank_Code;

	/**
	 * 排行榜的漫画链接表
	 */
	private ArrayList<String> rank_comic_link;
	/**
	 * 排行榜的漫画名称表
	 */
	private ArrayList<String> rank_comic_name;
	/**
	 * 排行榜的漫画图片表
	 */
	private ArrayList<String> rank_comic_pic;
	/**
	 * 排行榜的漫画最新一话的名称
	 */
	private ArrayList<String> rank_comic_lasestName;

	public ComicRankList(){
	}

	public ComicRankList(String rank_Name,String rank_Code,ArrayList<String> rank_comic_link,ArrayList<String> rank_comic_name,ArrayList<String> rank_comic_pic,ArrayList<String> rank_comic_lasestName){
		this.rank_Name = rank_Name;
		this.rank_Code = rank_Code;
		this.rank_comic_link = rank_comic_link;
		this.rank_comic_name = rank_comic_name;
		this.rank_comic_pic = rank_comic_pic;
		this.rank_comic_lasestName = rank_comic_lasestName;
	}

	public String getRank_Name(){
		return rank_Name;
	}

	public void setRank_Name(String rank_Name){
		this.rank_Name = rank_Name;
	}

	public String getRank_Code(){
		return rank_Code;
	}

	public void setRank_Code(String rank_Code){
		this.rank_Code = rank_Code;
	}

	public ArrayList<String> getRank_comic_link(){
		return rank_comic_link;
	}

	public void setRank_comic_link(ArrayList<String> rank_comic_link){
		this.rank_comic_link = rank_comic_link;
	}

	public ArrayList<String> getRank_comic_name(){
		return rank_comic_name;
	}

	public void setRank_comic_name(ArrayList<String> rank_comic_name){
		this.rank_comic_name = rank_comic_name;
	}

	public ArrayList<String> getRank_comic_pic(){
		return rank_comic_pic;
	}

	public void setRank_comic_pic(ArrayList<String> rank_comic_pic){
		this.rank_comic_pic = rank_comic_pic;
	}

	public ArrayList<String> getRank_comic_lasestName(){
		return rank_comic_lasestName;
	}

	public void setRank_comic_lasestName(ArrayList<String> rank_comic_lasestName){
		this.rank_comic_lasestName = rank_comic_lasestName;
	}
}
