package winter.zxb.smilesb101.cartoon8;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ComicData.Comic;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import UI.Adapter.comicDetailsRecyclerAdapter;
import UI.LayoutManager.CustomStraggerLayoutManager;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static ComicData.comicStaticValue.ComicDetails_patternNames;

public class comicDetailsActivity extends AppCompatActivity{

	public static final String HTMLLINK_STRING = "HTMLLINK";
	public static final String BGLINK_STRING = "BGLINK";
	public static final String NAME_STRING = "NAME";
	public static final String LASTEST_STRING = "LASTEST";
	private static final String TAG = "detailsActivity";

	private String htmlLink;
	private String BG_link;
	private String name;
	private String lastestHua;

	private static Context context;

	private static ImageView comic_Image;
	private static ImageView toolBar_BG;
	private static TextView comic_name;
	private static RecyclerView recyclerView;
	private static comicDetailsRecyclerAdapter adapter;
	private static TextView comic_status;
	private static TextView comic_UpdateTime;
	private static TextView comic_athuor;
	private static TextView comic_classify;
	private static TextView comic_introduce;
	private static Button shoucangBtn;
	private static Button startReadBtn;

	private static Comic comic;


	static Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what) {
				case NetWorkUtils.MSG_HTML:
					String html = msg.obj.toString();
					/*setPic(HtmlAnalysisUtils.getImageOfHtml(html,"img[src~=(?i)\\.(jpe?g)]"));
					comicClass.setClass_name(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","title"));
					comicClass.setClass_link(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","href"));*/

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
	}
	void InitValue()
	{
		if(getIntent()!=null) {
			BG_link = getIntent().getStringExtra(BGLINK_STRING);
			htmlLink = getIntent().getStringExtra(HTMLLINK_STRING);
			NetWorkUtils.getHtmlPage(this,htmlLink,handler);//获取网页数据
			name = getIntent().getStringExtra(NAME_STRING);
			lastestHua = getIntent().getStringExtra(LASTEST_STRING);
			comic_name.setText(name);
			Glide.with(this)
					.load(BG_link)
					.into(comic_Image);
			Glide.with(this)
					.load(BG_link)
					.bitmapTransform(new BlurTransformation(this, 100, 2))
					.into(toolBar_BG);
		}
	}

	/**
	 * 根据获取到的网页来设置页面
	 * @param html 网页
	 */
	static void InitLayoutValue(String html)
	{
		new AsyncTask<String,Void,Void>(){
			@Override
			protected Void doInBackground(String... params){
				comic = new Comic();
				ArrayList<String> strings = HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(0),HtmlAnalysisUtils.WHAT_innerHTML);
				comic.setLastestChapter(strings.get(0));
				comic.setLastUpdateTime(strings.get(1));
				comic.setComic_athour(strings.get(2));
				comic.setComic_class(strings.get(3));

				comic.setComic_Status(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(1),HtmlAnalysisUtils.WHAT_innerHTML).get(0));
				comic.setComic_introduce(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(2),HtmlAnalysisUtils.WHAT_innerHTML).get(0));
				comic.setComic_Chapter_Images(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(3),"href"));
				comic.setComic_Chapter_titles(HtmlAnalysisUtils.getComicDetails(params[0],ComicDetails_patternNames.get(3),"title"));
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid){
				super.onPostExecute(aVoid);
				Log.i(TAG,"onPostExecute: 完成后台操作，更新UI");
				comic_status.setText(comic.getComic_Status()+"(话)");
				comic_athuor.setText(comic.getComic_athour());
				comic_classify.setText(comic.getComic_class());
				comic_UpdateTime.setText("更新于："+comic.getLastUpdateTime());
				comic_introduce.setText(comic.getComic_introduce());
				adapter = new comicDetailsRecyclerAdapter(comic.getComic_Chapter_titles(),comic.getComic_Chapter_Images());
				CustomStraggerLayoutManager layoutManager = new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);
				recyclerView.setLayoutManager(layoutManager);
				recyclerView.setNestedScrollingEnabled(false);
				recyclerView.setAdapter(adapter);
			}
		}.execute(html);
	}
}
