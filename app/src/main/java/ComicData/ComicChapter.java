package ComicData;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;

import static ComicData.comicStaticValue.COMIC_PICS;
import static ComicData.comicStaticValue.COMIC_PIC_DOM;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画章节
 * 创建人：SmileSB101
 * 创建时间：2017/3/14 0014 16:40
 * 修改人：Administrator
 * 修改时间：2017/3/14 0014 16:40
 * 修改备注：
 */

public final class ComicChapter implements Serializable{

	private static final String TAG = "ComicChapter";

	private String chapterLink;
	private ArrayList<String> picList;
	private Activity activity;
	private String chapterName;

	Handler handler;

	/**
	 * 构造
	 * @param chapterLink 章节链接
	 * @param picList 章节图片列表
	 */
	public ComicChapter(String chapterLink,String chapterName,ArrayList<String> picList){
		this.chapterLink = chapterLink;
		this.picList = picList;
		this.chapterName = chapterName;
	}


	public String getChapterLink(){
		return chapterLink;
	}

	public ArrayList<String> getPicList(){
		return picList;
	}

	public String getChapterName(){
		return chapterName;
	}

}
