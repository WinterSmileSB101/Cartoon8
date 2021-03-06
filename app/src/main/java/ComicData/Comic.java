package ComicData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画类
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 14:53
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 14:53
 * 修改备注：
 */

public final class Comic implements Serializable{
	/**
	 * 漫画名称
	 */
	private String Name;
	/**
	 * 漫画数量
	 */
	private int comic_num;
	/**
	漫画简介
	 */
	private String comic_introduce;
	/**
	 * 漫画评分
	 */
	private float comic_mark;
	/**
	 * 漫画人气
	 */
	private int comic_popularity;

	/**
	 * 最后更新时间
	 */
	private String lastUpdateTime;

	/**
	 * 开始更新时间
	 */
	private String startUpdateTime;
	/**
	 * 更新周期
	 */
	private String updateRound;
	/**
	 * 最新章节
	 */
	private String lastestChapter;
	/**
	 * 漫画作者
	 */
	private String comic_athour;
	/**
	 * 漫画类别
	 */
	private String comic_class;
	/**
	 * 漫画封面图片
	 */
	private String comic_image;
	/**
	 * 漫画更新状态（连载或者完结）
	 */
	private String comic_Status;
	/**
	 * 漫画的章节列表
	 */
	private ArrayList<ComicChapter> chapters;

	public Comic(){
	}


	public String getName(){
		return Name;
	}

	public void setName(String name){
		Name = name;
	}

	public int getComic_num(){
		return comic_num;
	}

	public void setComic_num(int comic_num){
		this.comic_num = comic_num;
	}

	public String getComic_introduce(){
		return comic_introduce;
	}

	public void setComic_introduce(String comic_introduce){
		this.comic_introduce = comic_introduce;
	}

	public float getComic_mark(){
		return comic_mark;
	}

	public void setComic_mark(float comic_mark){
		this.comic_mark = comic_mark;
	}

	public int getComic_popularity(){
		return comic_popularity;
	}

	public void setComic_popularity(int comic_popularity){
		this.comic_popularity = comic_popularity;
	}

	public String getLastUpdateTime(){
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getStartUpdateTime(){
		return startUpdateTime;
	}

	public void setStartUpdateTime(String startUpdateTime){
		this.startUpdateTime = startUpdateTime;
	}

	public String getComic_athour(){
		return comic_athour;
	}

	public void setComic_athour(String comic_athour){
		this.comic_athour = comic_athour;
	}

	public String getComic_class(){
		return comic_class;
	}

	public void setComic_class(String comic_class){
		this.comic_class = comic_class;
	}

	public String getComic_image(){
		return comic_image;
	}

	public void setComic_image(String comic_image){
		this.comic_image = comic_image;
	}

	public String getLastestChapter(){
		return lastestChapter;
	}

	public void setLastestChapter(String lastestChapter){
		this.lastestChapter = lastestChapter;
	}

	public String getComic_Status(){
		return comic_Status;
	}

	public void setComic_Status(String comic_Status){
		this.comic_Status = comic_Status;
	}

	public String getUpdateRound(){
		return updateRound;
	}

	public void setUpdateRound(String updateRound){
		this.updateRound = updateRound;
	}

	public ArrayList<ComicChapter> getChapters(){
		return chapters;
	}

	public void setChapters(ArrayList<ComicChapter> chapters){
		this.chapters = chapters;
	}
}
