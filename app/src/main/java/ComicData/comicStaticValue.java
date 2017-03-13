package ComicData;

import java.util.ArrayList;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画静态类
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 18:33
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 18:33
 * 修改备注：
 */

public final class comicStaticValue{

	/**
	 * 类别的dom
	 */
	public final static String CLASS_NAME = "ul li a[title~=]";
	public final static String CLASS_LINK = "ul li a[title~=]";
	/**
	 * 排行榜的起始名称dom
	 */
	public final static ArrayList<String> RankList_patternNames = initRankList_patternName();
	public final static String RankList_TailString_COMIC_PIC = "div.main-list-wrap ul li a img";
	public final static String RankList_TailString_COMIC_LINK = "div.main-list-wrap ul li a";
	public final static String RankList_TailString_COMIC_LASESTNAME = "div.main-list-wrap ul li a p";
	public final static String RankList_TailString_COMIC_NAME = "div.main-list-wrap ul li a h3";
	public final static String RankList_TailString_RANKNAME = "h2";

	/**
	 * 漫画的起始DOM名称列表
	 */
	public final static ArrayList<String> ComicDetails_patternNames = initComicDetails_patternName();
	/**
	 * 漫画详情的pattern语句（DOM）
	 * 位置0：最新话
	 * 位置1：最后更新时间
	 * 位置2：作者
	 * 位置3：类别
	 */
	public final static String Comic_Details_COMIC_L_U_A_C = "div.book-detail dl dd";
	/**
	 * 漫画详情的pattern语句（DOM）
	 * 获取漫画状态（连载或者完结）
	 */
	public final static String Comic_Details_COMIC_STATUS = "div.book-detail div.thumb i";
	/**
	 * 漫画详情的pattern语句（DOM）
	 * 获取漫画介绍
	 */
	public final static String Comic_Details_COMIC_INTRODUCE = "div.book-detail div#bookIntro p";
	/**
	 * 漫画详情的pattern语句（DOM）
	 * 获取漫画的章节列表
	 */
	public final static String Comic_Details_COMIC_CHAPTERS = "div#chapterList ul li a";

	/**
	 * 漫画详情的更新周期语句
	 */
	public final static String Comic_Details_COMIC_UPDATEROUND = "div.book-detail div#bookIntro div";


	static ArrayList<String> initComicDetails_patternName()
	{
		ArrayList<String> strings = new ArrayList<>();
		strings.add(Comic_Details_COMIC_L_U_A_C);
		strings.add(Comic_Details_COMIC_STATUS);
		strings.add(Comic_Details_COMIC_INTRODUCE);
		strings.add(Comic_Details_COMIC_CHAPTERS);
		strings.add(Comic_Details_COMIC_UPDATEROUND);
		return strings;
	}

	static ArrayList<String> initRankList_patternName()
	{
		ArrayList<String> strings = new ArrayList<>();
		strings.add("div#main-lianzai div ");
		strings.add("div#main-wanjie div ");
		strings.add("div#main-caise div ");
		strings.add("div#main-shangjia div ");
		return strings;
	}

	/**
	 *漫画的图片列表（一张）
	 */
	public final static String ComicContent_COMICPIC = "div.manga-box img";

	/**
	 * 所有评论（用来获取数量）
	 */
	public final static String PREVIEW_ALL = "div.list-container-wap div img";
	/**
	 * 评论的用户的名称和头像
	 */
	public final static String PREVIEW_ITEM_IMAGE_NAME = "div.list-container-wap div img";
	/**
	 * 评论内容
	 */
	public final static String PREVIEW_ITEM_CONTENT = "div.list-container-wap div div div p";
	/**
	 * 评论时间
	 */
	public final static String PREVIEW_ITEM_DATE = "div.list-container-wap div div div span.list-cmt-time-wap";
	/**
	 * 评论的DOM集合
	 * 0是所有评论（数量）
	 * 1是名称和头像（取值src头像 title名称）
	 * 2是评论内容
	 * 3是评论时间
	 */
	public final static ArrayList<String> PREVIEW_DOM =initPREVIEW_DOM();

	static ArrayList<String> initPREVIEW_DOM()
	{
		ArrayList<String> strings = new ArrayList<>();
		strings.add(PREVIEW_ALL);
		strings.add(PREVIEW_ITEM_IMAGE_NAME);
		strings.add(PREVIEW_ITEM_CONTENT);
		strings.add(PREVIEW_ITEM_DATE);
		return strings;
	}

	/**
	 * 获取漫画的图片列表（DOM）
	 */
	public static final String COMIC_PICS = "script[type='text/javascript']";
	/**
	 * 获取漫画的话数（这里包括了漫画名称）
	 */
	public static final String COMIC_HUA = "div.main-bar h1";

	/**
	 * 获取漫画的名称
	 */
	public static final String COMIC_NAME = "div.main-bar h1 a";

	/**
	 * 漫画浏览页面的DOM语句集合
	 * 0为漫画图片列表
	 * 1为漫画的话数
	 * 2为漫画的名称
	 */
	public static final ArrayList<String> COMIC_PIC_DOM = initCOMIC_PIC_DOM();

	static ArrayList<String> initCOMIC_PIC_DOM()
	{
		ArrayList<String> list = new ArrayList<>();
		list.add(COMIC_PICS);
		list.add(COMIC_HUA);
		list.add(COMIC_NAME);
		return list;
	}
}
