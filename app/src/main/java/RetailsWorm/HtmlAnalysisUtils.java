package RetailsWorm;

import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * 项目名称：Cartoon8
 * 类描述：网页分析类
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 13:07
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 13:07
 * 修改备注：
 */

public class HtmlAnalysisUtils{
	private static final String TAG = "HtmlAnalysisUtils";
	public static final String ERROR = "ERROR";

	public static final String WHAT_innerHTML = "innerHTML";


	public static String getImageOfHtml(String html,String pattern)
	{
		try{
			Document doc = Jsoup.parse(html);
			Element ele = doc.select(pattern).first();
			if(ele!=null)
			{
				Log.i(TAG,"getImageOfHtml: "+ele.attr("src"));
				return ele.attr("src");
			}
		}
		catch(Exception e)
		{
			Log.i(TAG,"getImageOfHtml: ERROR:"+e.getMessage());
			return ERROR;
		}
		return "";
	}

	public static ArrayList<String> getPageClass(String html,String pattern,String getWhat)
	{
		ArrayList<String > lists = new ArrayList<>();
		try {
			Document doc = Jsoup.parse(html);
			Elements eles = doc.select(pattern);
			for(Element e:eles)
			{
				if(!getWhat.equals("")&&!getWhat.equals(WHAT_innerHTML)) {
					Log.i(TAG,"getPageTitles: " + e.attr(getWhat));
					lists.add(e.attr(getWhat));
				}
				else if(getWhat.equals(WHAT_innerHTML))
				{
					Log.i(TAG,"getPageTitles: "+WHAT_innerHTML+"   "+e.text().trim());
				}
				else
				{
					Log.i(TAG,"getPageTitles: "+e.toString());
					lists.add(e.toString());
				}
			}
		}
		catch(Exception e)
		{
			return null;
		}
		return lists;
	}

	/**
	 * 获取排名的表的信息
	 * @param html 网页源码
	 * @param pattern 匹配字符串
	 * @param getWhat 获取什么内容
	 * @return
	 */
	public static ArrayList<String> getRankList(String html,String pattern,String getWhat)
	{
		ArrayList<String> lists = new ArrayList<>();
		try {
			Log.i(TAG,"getRankList: 开始匹配patttern: "+pattern);
			Document doc = Jsoup.parse(html);
			Elements eles = doc.select(pattern);
			for(Element e:eles)
			{
				if(!getWhat.equals("")&&!getWhat.equals(WHAT_innerHTML)) {
					Log.i(TAG,"getRankList:"+getWhat+"   "+e.attr(getWhat));
					lists.add(e.attr(getWhat));
				}
				else if(getWhat.equals(WHAT_innerHTML))
				{
					Log.i(TAG,"getRankList: "+WHAT_innerHTML+"   "+e.text().trim());
					lists.add(e.text().trim());
				}
				else
				{
					Log.i(TAG,"getRankList: "+e.toString());
					lists.add(e.toString());
				}
			}
		}
		catch(Exception e)
		{
			Log.i(TAG,"getRankList: 错误返回");
			return null;
		}
		Log.i(TAG,"getRankList: 正确返回");
		return lists;
	}

	/**
	 * 获取漫画详情
	 * @param html 网页源码
	 * @param pattern 匹配字符串
	 * @param getWhat 获取什么内容
	 * @return
	 */
	public static ArrayList<String> getComicDetails(String html,String pattern,String getWhat)
	{
		ArrayList<String> lists = new ArrayList<>();
		try {
			Log.i(TAG,"getRankList: 开始匹配patttern: "+pattern);
			Document doc = Jsoup.parse(html);
			Elements eles = doc.select(pattern);
			for(Element e:eles)
			{
				//Log.i(TAG,"getComicDetails: 正确匹配");
				if(!getWhat.equals("")&&!getWhat.equals(WHAT_innerHTML)) {
					Log.i(TAG,"getRankList:"+getWhat+"   "+e.attr(getWhat));
					lists.add(e.attr(getWhat));
				}
				else if(getWhat.equals(WHAT_innerHTML))
				{
					Log.i(TAG,"getRankList: "+WHAT_innerHTML+"   "+e.text().trim());
					lists.add(e.text().trim());
				}
				else
				{
					Log.i(TAG,"getRankList: "+e.toString());
					lists.add(e.toString());
				}
			}
		}
		catch(Exception e)
		{
			Log.i(TAG,"getRankList: 错误返回");
			return null;
		}
		Log.i(TAG,"getRankList: 正确返回");
		return lists;
	}

}
