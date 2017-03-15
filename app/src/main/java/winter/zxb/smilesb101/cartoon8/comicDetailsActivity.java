package winter.zxb.smilesb101.cartoon8;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ComicData.Comic;
import ComicData.ComicChapter;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import UI.Adapter.comicDetailsRecyclerAdapter;
import UI.Adapter.previewRecyclerAdapter;
import UI.LayoutManager.CustomStraggerLayoutManager;
import User.Prview;
import User.User;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static ComicData.comicStaticValue.ComicDetails_patternNames;
import static ComicData.comicStaticValue.PREVIEW_DOM;

public final class comicDetailsActivity extends AppCompatActivity implements View.OnClickListener{

	public static final String HTMLLINK_STRING = "HTMLLINK";
	public static final String BGLINK_STRING = "BGLINK";
	public static final String NAME_STRING = "NAME";
	public static final String LASTEST_STRING = "LASTEST";
	private static final String TAG = "detailsActivity";

	private String htmlLink;
	private String BG_link;
	private String name;
	private String lastestHua;

	private Activity context;

	private ImageView comic_Image;
	private ImageView toolBar_BG;
	private TextView comic_name;
	private RecyclerView recyclerView;
	private RecyclerView preview_RecyclerView;
	private ArrayList<Prview> prviews;
	private previewRecyclerAdapter previewAdapter;
	private comicDetailsRecyclerAdapter AllListadapter;
	private comicDetailsRecyclerAdapter PartListadapter;
	private comicDetailsRecyclerAdapter DownLoadListadapter;
	private TextView comic_status;
	private TextView comic_UpdateTime;
	private TextView comic_athuor;
	private TextView comic_classify;
	private TextView comic_introduce;
	private TextView comic_UpdateRound;
	private Button shoucangBtn;
	private Button startReadBtn;
	private ActionBar actionBar;
	private AppBarLayout appBarLayout;
	private TextView collsPasetext;
	private ImageView collPaseimage;
	private NestedScrollView bottom_content;
	private RelativeLayout showRecycler;

	private boolean isFirstLayout;

	private Comic comic;

	private ArrayList<String> chapterLink,chapterHua;
	private ArrayList<ComicChapter> chapterList;


	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what) {
				case NetWorkUtils.MSG_HTML:
					String html = msg.obj.toString();
					if(!html.equals(NetWorkUtils.HTML_EMPTY)) {
						InitLayoutValue(html);
					}
					else
					{
						Log.i(TAG,"handleMessage: 网络错误！！");
					}
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comic_details_layout);
		context = this;
		isFirstLayout = true;
		InitView();
		InitValue();
	}

	void InitView()
	{
		comic_Image = (ImageView)findViewById(R.id.comic_details_comicImage);
		toolBar_BG = (ImageView)findViewById(R.id.comic_details_toolbarBG);
		comic_name = (TextView)findViewById(R.id.comic_details_comicName);
		recyclerView = (RecyclerView)findViewById(R.id.comic_details_recyclerView);
		comic_status = (TextView)findViewById(R.id.comic_details_Status);
		comic_UpdateTime = (TextView)findViewById(R.id.comic_details_comicUpdateTime);
		comic_athuor = (TextView)findViewById(R.id.comic_details_comicAuthor);
		comic_classify = (TextView)findViewById(R.id.comic_details_comicClassfy);
		shoucangBtn = (Button)findViewById(R.id.comic_details_comicShouCangBtn);
		startReadBtn = (Button)findViewById(R.id.comic_details_comicStartReadBtn);
		comic_introduce = (TextView)findViewById(R.id.comic_details_comicIntroduce);
		appBarLayout = (AppBarLayout)findViewById(R.id.comic_details_appBarLayout);
		comic_UpdateRound = (TextView)findViewById(R.id.comic_details_updateRound);
		collsPasetext = (TextView)findViewById(R.id.right);
		collPaseimage = (ImageView)findViewById(R.id.left);
		bottom_content = (NestedScrollView)findViewById(R.id.bottom_content);
		showRecycler = (RelativeLayout)findViewById(R.id.comic_details_showAllRecycler);
		preview_RecyclerView = (RecyclerView)findViewById(R.id.preview_RecyclerView);
	}
	void InitValue()
	{
		shoucangBtn.setOnClickListener(this);
		startReadBtn.setOnClickListener(this);
		showRecycler.setOnClickListener(this);
		Toolbar toolbar = (Toolbar)findViewById(R.id.comic_details_toolbar);
		setSupportActionBar(toolbar);
		if(getSupportActionBar()!=null)
		{
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.mipmap.left_arrow_72px_1205452_easyicon);
		}
		if(getIntent()!=null) {
			Log.i(TAG,"InitValue: 详情活动创建复制");
			BG_link = getIntent().getStringExtra(BGLINK_STRING);
			htmlLink = getIntent().getStringExtra(HTMLLINK_STRING);
			NetWorkUtils.getHtmlPage(this,htmlLink,handler);//获取网页数据
			name = getIntent().getStringExtra(NAME_STRING);
			lastestHua = getIntent().getStringExtra(LASTEST_STRING);
			comic_name.setText(name);
			actionBar.setTitle(name);
			Glide.with(this)
					.load(BG_link)
					.into(comic_Image);
			Glide.with(this)
					.load(BG_link)
					.bitmapTransform(new BlurTransformation(this, 100, 2))
					.into(toolBar_BG);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cmoic_details_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId())
		{
			case R.id.details_menu_down:
				//下载按钮按下
				//切换状态（布局样式）
				InitLayoutDownLoad();
				break;
			case android.R.id.home:////主键id 必须这样写
				onBackPressed();//按返回图标直接回退上个界面
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed(){
		if(isFirstLayout) {
			super.onBackPressed();
		}
		else
		{
			InitLayoutDetails();
		}
	}

	/**
	 * 根据获取到的网页来设置页面
	 * @param html 网页
	 */
	void InitLayoutValue(String html)
	{
		new AsyncTask<String,Void,Void>(){
			@Override
			protected Void doInBackground(String... params){
				comic = new Comic();
				//Log.i(TAG,"doInBackground: "+params[0]);
				ArrayList<String> strings = HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(0),HtmlAnalysisUtils.WHAT_innerHTML);
				comic.setLastestChapter(strings.get(0));
				comic.setLastUpdateTime(strings.get(1));
				comic.setComic_athour(strings.get(2));
				comic.setComic_class(strings.get(3));

				comic.setComic_Status(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(1),HtmlAnalysisUtils.WHAT_innerHTML).get(0));
				String s = HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(2),HtmlAnalysisUtils.WHAT_innerHTML).get(2);
				if(!"".equals(s))
				{
					s = "\n"+s;
				}
				comic.setComic_introduce(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(2),HtmlAnalysisUtils.WHAT_innerHTML).get(1)+s);
				//获取漫画的每话的名称以及链接
				chapterLink = HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(3),"href");
				chapterHua = HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(3),"title");
				chapterList = new ArrayList<ComicChapter>();
				for(int i = 0,length = chapterLink.size();i<length;i++) {
					chapterList.add(new ComicChapter(chapterLink.get(i),chapterHua.get(i),new ArrayList<String>()));
				}
				comic.setChapters(chapterList);
				comic.setUpdateRound(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(4),HtmlAnalysisUtils.WHAT_innerHTML).get(0));

				prviews = new ArrayList<Prview>();
				//获取评论的数量
				int length = HtmlAnalysisUtils.getPatcherNumber(params[0],PREVIEW_DOM.get(0));
				Prview prview = null;
				User user = null;
				for(int i = 0;i<length;i++)
				{
					prview = new Prview();
					user = new User();
					user.setImage(HtmlAnalysisUtils.getComicDetails(params[0],PREVIEW_DOM.get(1),"src").get(i));
					user.setName(HtmlAnalysisUtils.getComicDetails(params[0],PREVIEW_DOM.get(1),"title").get(i));
					prview.setUser(user);
					prview.setContent(HtmlAnalysisUtils.getComicDetails(params[0],PREVIEW_DOM.get(2),HtmlAnalysisUtils.WHAT_innerHTML).get(i));
					prview.setDate(HtmlAnalysisUtils.getComicDetails(params[0],PREVIEW_DOM.get(3),HtmlAnalysisUtils.WHAT_innerHTML).get(i));
					prviews.add(prview);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid){
				super.onPostExecute(aVoid);
				//Log.i(TAG,"onPostExecute: 完成后台操作，更新UI");
				comic_status.setText(comic.getComic_Status()+"(话)");
				comic_athuor.setText(comic.getComic_athour());
				comic_classify.setText(comic.getComic_class());
				comic_UpdateTime.setText("更新于："+comic.getLastUpdateTime());
				//Log.i(TAG,"onPostExecute: 介绍"+comic.getComic_introduce());
				comic_introduce.setText(comic.getComic_introduce());
				if(!"".equals(comic.getUpdateRound())) {
					comic_UpdateRound.setText("[更新周期：" + comic.getUpdateRound() + " ]");
				}
				else
				{
					comic_UpdateRound.setText("[更新周期：暂无 ]");
				}
				ArrayList<String> titles = chapterHua;
				ArrayList<String> links = chapterLink;
				AllListadapter = new comicDetailsRecyclerAdapter(comic,false,context);

				//设置部分显示的comic
				Comic comic1 = new Comic();
				comic1.setLastestChapter(comic.getLastestChapter());
				comic1.setLastUpdateTime(comic.getLastUpdateTime());
				comic1.setComic_athour(comic.getComic_athour());
				comic1.setComic_class(comic.getComic_class());
				comic1.setComic_Status(comic.getComic_Status());
				comic1.setComic_introduce(comic.getComic_introduce());
				comic1.setUpdateRound(comic.getUpdateRound());
				ArrayList<ComicChapter> chapters = new ArrayList<ComicChapter>();
				for(int i = 0,lenth = 12;i<lenth;i++)
				{
					chapters.add(new ComicChapter(links.get(i),titles.get(i),new ArrayList<String>()));
				}
				comic1.setChapters(chapters);
				PartListadapter = new comicDetailsRecyclerAdapter(comic1,false,context);
				CustomStraggerLayoutManager layoutManager = new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
				recyclerView.setLayoutManager(layoutManager);
				recyclerView.setNestedScrollingEnabled(false);
				recyclerView.setAdapter(PartListadapter);
				bottom_content.setVisibility(View.VISIBLE);

				//设置评论列表
				previewAdapter = new previewRecyclerAdapter(prviews);
				preview_RecyclerView.setNestedScrollingEnabled(false);
				preview_RecyclerView.setLayoutManager(new LinearLayoutManager(context));

				preview_RecyclerView.setAdapter(previewAdapter);
			}
		}.execute(html);
	}

	@Override
	public void onClick(View v){
		switch(v.getId())
		{
			case R.id.comic_details_comicStartReadBtn:
				//开始阅读按钮按下,隐藏上面所有布局（连载之前的所有布局）
				break;
			case R.id.comic_details_comicShouCangBtn:
				//收藏按钮按下

				break;
			case R.id.comic_details_showAllRecycler:
				//展开收起按钮
				recyclerView.setLayoutManager(new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
				if(getString(R.string.seeAll).equals(collsPasetext.getText().toString().trim()))
				{
					//点击展开
					collPaseimage.setImageResource(R.mipmap.up_arrow_135);
					collsPasetext.setText(getString(R.string.shouqi));
					recyclerView.setAdapter(AllListadapter);
				}
				else
				{
					collPaseimage.setImageResource(R.mipmap.down_arrow_13);
					collsPasetext.setText(getString(R.string.seeAll));
					recyclerView.setAdapter(PartListadapter);
				}
				break;
			case R.id.download_allChose:
				//全选按钮按下
				break;
			case R.id.download_downloadBtn:
				//下载按钮按下
				break;
			default:
				break;
		}
	}

	/**
	 * 实例化详情界面
	 */
	void InitLayoutDetails()
	{
		setContentView(R.layout.comic_details_layout);
		isFirstLayout = true;
		InitView();
		shoucangBtn.setOnClickListener(this);
		startReadBtn.setOnClickListener(this);
		showRecycler.setOnClickListener(this);
		bottom_content.setVisibility(View.VISIBLE);
		Toolbar toolbar = (Toolbar)findViewById(R.id.comic_details_toolbar);
		setSupportActionBar(toolbar);
		if(getSupportActionBar()!=null)
		{
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.mipmap.left_arrow_72px_1205452_easyicon);
		}
		if(getIntent()!=null) {
			Log.i(TAG,"InitValue: 详情活动创建复制");
			BG_link = getIntent().getStringExtra(BGLINK_STRING);
			htmlLink = getIntent().getStringExtra(HTMLLINK_STRING);
			name = getIntent().getStringExtra(NAME_STRING);
			lastestHua = getIntent().getStringExtra(LASTEST_STRING);
			comic_name.setText(name);
			actionBar.setTitle(name);
			Glide.with(this)
					.load(BG_link)
					.into(comic_Image);
			Glide.with(this)
					.load(BG_link)
					.bitmapTransform(new BlurTransformation(this, 100, 2))
					.into(toolBar_BG);
		}
		comic_status.setText(comic.getComic_Status()+"(话)");
		comic_athuor.setText(comic.getComic_athour());
		comic_classify.setText(comic.getComic_class());
		comic_UpdateTime.setText("更新于："+comic.getLastUpdateTime());
		Log.i(TAG,"onPostExecute: 介绍"+comic.getComic_introduce());
		comic_introduce.setText(comic.getComic_introduce());
		if(!"".equals(comic.getUpdateRound())) {
			comic_UpdateRound.setText("[更新周期：" + comic.getUpdateRound() + " ]");
		}
		else
		{
			comic_UpdateRound.setText("[更新周期：暂无 ]");
		}
		CustomStraggerLayoutManager layoutManager = new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setNestedScrollingEnabled(false);
		recyclerView.setAdapter(PartListadapter);
		//设置评论列表
		preview_RecyclerView.setNestedScrollingEnabled(false);
		preview_RecyclerView.setLayoutManager(new LinearLayoutManager(context));
		preview_RecyclerView.setAdapter(previewAdapter);
}

	/**
	 * 实例化下载页面的布局
	 */
	void InitLayoutDownLoad()
	{
		isFirstLayout = false;
		setContentView(R.layout.fragment_download_layout);
		Toolbar toolbar = (Toolbar)findViewById(R.id.download_toolbar);
		setSupportActionBar(toolbar);
		actionBar = getSupportActionBar();
		if(actionBar!=null)
		{
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.mipmap.left_arrow_72px_1205452_easyicon);
			actionBar.setTitle(comic.getName());
		}

		comic_status = (TextView)findViewById(R.id.download_Status);
		comic_status.setText(comic.getComic_Status()+"(话)");
		comic_UpdateTime = (TextView)findViewById(R.id.download_UpdateTime);
		comic_UpdateTime.setText(comic.getLastUpdateTime());
		recyclerView = (RecyclerView)findViewById(R.id.download_recyclerView);
		CustomStraggerLayoutManager layoutManager = new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		if(DownLoadListadapter==null)
		{
			DownLoadListadapter = new comicDetailsRecyclerAdapter(comic,true,context);
		}
		recyclerView.setAdapter(DownLoadListadapter);

		findViewById(R.id.download_allChose).setOnClickListener(this);
		findViewById(R.id.download_downloadBtn).setOnClickListener(this);
	}
}
