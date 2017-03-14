package winter.zxb.smilesb101.cartoon8;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ComicData.Comic;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import Utils.Utils;

import static ComicData.comicStaticValue.COMIC_PICS;
import static ComicData.comicStaticValue.COMIC_PIC_DOM;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public final class WatchComicActivity extends AppCompatActivity implements View.OnClickListener{
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 1000;

	/**
	 * 动画时间
	 */
	private static final int UI_ANIMATION_DELAY = 200;
	private final Handler mHideHandler = new Handler();
	private RecyclerView mContentView;
	private View mControlsView;
	private MyContentViewAdapter adapter;
	private int adapterNowPos,allItems;
	private final Runnable mShowPart2Runnable = new Runnable(){
		@Override
		public void run(){
			// Delayed display of UI elements
			ActionBar actionBar = getSupportActionBar();
			if(actionBar != null) {
				actionBar.show();
			}
		}
	};
	/**
	 * 显示或者隐藏状态
	 */
	private boolean mVisible;
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

	/**
	 * 处理网页
	 * @param html
	 */
	void InitLayoutValue(final String html)
	{
		new AsyncTask<String,Void,Void>(){
			@Override
			protected Void doInBackground(String... params){
				Log.i(TAG,"doInBackground: 后台操作获取图片");
				comic = new Comic();
				comic_Pics = HtmlAnalysisUtils.getComicPicsString(params[0],COMIC_PICS,5);//第5个位置是图片位置
				comic.setName(HtmlAnalysisUtils.getComicDetails(params[0],COMIC_PIC_DOM.get(2),HtmlAnalysisUtils.WHAT_innerHTML).get(0));
				comic_hua = HtmlAnalysisUtils.getComicDetails(params[0],COMIC_PIC_DOM.get(1),HtmlAnalysisUtils.WHAT_innerHTML).get(0).replace(comic.getName(),"");//去掉多余的名称
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid){
				super.onPostExecute(aVoid);
				//Log.i(TAG,"onPostExecute: 获取图片完成");
				comic_name_bottom.setText(comic_hua);
				comic_name_top.setText(comic_hua);
				if(comic_Pics!=null)
				{
					Log.i(TAG,"onPostExecute: "+comic_Pics.get(1));
					//实例化adapter
					mContentView.setLayoutManager(new LinearLayoutManager(activity));
					adapter = new MyContentViewAdapter(comic_Pics);
					mContentView.setAdapter(adapter);
					mContentView.setOnScrollListener(scrollListener);
				}
			}
		}.execute(html);
	}
	private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener(){
		@Override
		public void onScrolled(RecyclerView recyclerView,int dx,int dy){
			super.onScrolled(recyclerView,dx,dy);
			LinearLayoutManager l = (LinearLayoutManager)recyclerView.getLayoutManager();
			adapterNowPos = l.findFirstVisibleItemPosition();
			allItems = l.getItemCount();
			//设置seekbar
			seekBar.setMax(allItems-1);
			Log.i(TAG,"onScrolled: "+allItems);
			seekBar.setProgress(adapterNowPos);
			setpicText();//设置文字
		}
	};
	private static final String TAG = "WatchComicActivity";
	/**
	 * 漫画的VALUE
	 */
	public static String COMIC_VALUE = "COMIC";
	public static String COMIC_LINK = "CMOIC_LINK";
	public static String COMIC_HUA = "HUA";
	public static String COMIC_NAME = "NAME";
	private Comic comic;
	private String comic_link;
	private String comic_hua;
	private ArrayList<String> comic_Pics;
	private Activity activity;
	private LinearLayout toplayout;
	private Button showCtrlbtn,backBtn,feedbackBtn,downLoadPicBtn,sharePicBtn;
	private LinearLayout bottomlayout;
	private TextView nextToogle,toogleBtn,preToogle;
	private LinearLayout menu_content;
	private TextView comic_name_bottom,comic_pic_state,time,battery;
	private SeekBar seekBar;
	private LinearLayout small_status;
	private RelativeLayout bottom_seekBar_help;

	private TextView seekBar_help_now,seekBar_help_num;

	private Button backbtn;
	private TextView comic_name_top,pic_state_top;

	private LinearLayout indexBtn,download_comic_Btn,light_btn,phone_switch,options_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_watch_comic);

		//注册电量监听广播
		registerReceiver(batterReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		mVisible = false;//一开始是不可见状态
		mContentView = (RecyclerView)findViewById(R.id.fullscreen_content);
		toplayout = (LinearLayout)findViewById(R.id.top_layout);
		bottomlayout = (LinearLayout)findViewById(R.id.bottom_layout);
		toogleBtn = (TextView)findViewById(R.id.toogle);
		nextToogle = (TextView)findViewById(R.id.nextHUAText);
		preToogle = (TextView)findViewById(R.id.preHUAText);
		menu_content = (LinearLayout)findViewById(R.id.menu_content);
		showCtrlbtn = (Button)findViewById(R.id.show_menuBtn);
		backBtn = (Button)findViewById(R.id.back_btn);
		feedbackBtn = (Button)findViewById(R.id.feed_backBtn);
		downLoadPicBtn = (Button)findViewById(R.id.downloadpic_Btn);
		sharePicBtn = (Button)findViewById(R.id.sharePicBtn);
		bottom_seekBar_help = (RelativeLayout)findViewById(R.id.bottom_seekBar_help);
		seekBar_help_now = (TextView)findViewById(R.id.seekBar_help_now);
		seekBar_help_num = (TextView)findViewById(R.id.seekBar_help_num);

		menu_content.setOnClickListener(this);
		showCtrlbtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		feedbackBtn.setOnClickListener(this);
		downLoadPicBtn.setOnClickListener(this);
		sharePicBtn.setOnClickListener(this);

		comic_name_bottom = (TextView)findViewById(R.id.comic_name_bottom);
		comic_pic_state = (TextView)findViewById(R.id.comic_pic_state);
		time = (TextView)findViewById(R.id.time);
		battery = (TextView)findViewById(R.id.battery);
		time.setText(Utils.getTimeHour());
		small_status = (LinearLayout)findViewById(R.id.small_status);
		seekBar = (SeekBar)findViewById(R.id.bottom_seekBar);
		seekBar.setOnSeekBarChangeListener(seekChangerListener);
		//Log.i(TAG,"onCreate: "+Utils.getPhoneBatteryInfo(this,BatteryManager.BATTERY_PROPERTY_CURRENT_NOW,Utils.DATATYPE_INT));

		toogleBtn.setOnClickListener(this);
		nextToogle.setOnClickListener(this);
		preToogle.setOnClickListener(this);
		toogleBtn.setOnTouchListener(ctrl_touchlistenner);
		nextToogle.setOnTouchListener(ctrl_touchlistenner);
		preToogle.setOnTouchListener(ctrl_touchlistenner);

		comic_name_top = (TextView)findViewById(R.id.comic_name);
		pic_state_top = (TextView)findViewById(R.id.comic_pic_state_top);

		//底边菜单
		indexBtn = (LinearLayout)findViewById(R.id.indexBtn);
		download_comic_Btn = (LinearLayout)findViewById(R.id.download_comic_Btn);
		light_btn = (LinearLayout)findViewById(R.id.light_btn);
		phone_switch = (LinearLayout)findViewById(R.id.phone_switch);
		options_btn  = (LinearLayout)findViewById(R.id.options_btn);

		indexBtn.setOnClickListener(this);
		download_comic_Btn.setOnClickListener(this);
		light_btn.setOnClickListener(this);
		phone_switch.setOnClickListener(this);
		indexBtn.setOnClickListener(this);
		options_btn.setOnClickListener(this);

		activity = this;
		if(getIntent()!=null)
		{
			Intent intent = getIntent();
			comic = (Comic)intent.getSerializableExtra(COMIC_VALUE);
			comic_link = intent.getStringExtra(COMIC_LINK);
			new Runnable(){
				@Override
				public void run(){
					NetWorkUtils.getHtmlPage(activity,comic_link,handler);//获取网页数据
				}
			}.run();
		}
	}


	private float Sy = 0,Ey = 0;//放在外面是为了更新sy的值，让滑动更加顺滑
	/**
	 * 控制板的触摸事件监听，用来处理滑动冲突（控制面板与recyclerView的滑动冲突）
	 */
	View.OnTouchListener ctrl_touchlistenner = new View.OnTouchListener(){
		@Override
		public boolean onTouch(View v,MotionEvent event){
			switch(event.getAction())
			{
				case MotionEvent.ACTION_DOWN:
					Sy = event.getY();
					//Log.i(TAG,"onTouch: 按下事件 "+Sy);
				break;
				case MotionEvent.ACTION_MOVE:
					Ey = event.getY();
					float res = (Ey-Sy);
					//Log.i(TAG,"onTouch: 分发事件给recyclerView 移动距离为 "+res);
					//让RecyclerView开始滑动
					mContentView.scrollBy(mContentView.getScrollX(),mContentView.getScrollY()-(int)res);
					Sy = event.getY();//更新开始位置
					break;
			}
			return false;
		}
	};

	/**
	 * seekBar值改变的监听
	 */
	SeekBar.OnSeekBarChangeListener seekChangerListener = new SeekBar.OnSeekBarChangeListener(){
		@Override
		public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
			//更新辅助面板
			if(bottom_seekBar_help.getVisibility()==View.VISIBLE) {
				seekBar_help_now.setText(progress + 1+"");
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar){
            //显示辅助面板
			bottom_seekBar_help.setVisibility(View.VISIBLE);
			seekBar_help_num.setText(seekBar.getMax()+1+"");//设置总数
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar){
			//关闭辅助面板
			bottom_seekBar_help.setVisibility(View.GONE);
			int progress = seekBar.getProgress();
			Log.i(TAG,"onProgressChanged: 选择了，第"+progress);
			//设置图片状态（1/9）;
			adapterNowPos = progress;
			setpicText();
			mContentView.scrollToPosition(adapterNowPos);//不能平稳滑动，否者联动出错
		}
	};

	/**
	 * 设置图片文字（第几张/总共几张）
	 */
	void setpicText()
	{
		String s = adapterNowPos+1+ "/" + allItems;
		comic_pic_state.setText(s);//设置图片的数量
		pic_state_top.setText(s);
	}
	/**
	 * 展示上下栏
	 */
	void showPanel()
	{
		//Log.i(TAG,"hidePanel: 显示");
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
		TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				-1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		translateAnimation.setDuration(UI_ANIMATION_DELAY);
		translateAnimation1.setDuration(UI_ANIMATION_DELAY);
		toplayout.setAnimation(translateAnimation1);
		bottomlayout.setAnimation(translateAnimation);
		toplayout.setVisibility(View.VISIBLE);//这里通过改变可见性来播放动画
		bottomlayout.setVisibility(View.VISIBLE);//这里通过改变可见性来播放动画
		//底部状态栏消失
		small_status.setVisibility(View.GONE);
		//隐藏上下操作板，注意只能是隐藏，不能GONE，否者会导致中间控制板占据所有屏幕的问题
		nextToogle.setVisibility(View.INVISIBLE);
		preToogle.setVisibility(View.INVISIBLE);
	}

	/**
	 * 隐藏上下栏
	 */
	void hidePanel()
	{
		//Log.i(TAG,"hidePanel: 隐藏");
		TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0.0f,
				Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
		TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		translateAnimation.setDuration(UI_ANIMATION_DELAY);
		translateAnimation1.setDuration(UI_ANIMATION_DELAY);
		toplayout.setAnimation(translateAnimation);
		bottomlayout.setAnimation(translateAnimation1);
		toplayout.setVisibility(View.INVISIBLE);//这里通过改变可见性来播放动画
		bottomlayout.setVisibility(View.INVISIBLE);//这里通过改变可见性来播放动画
		//底部状态栏显示
		small_status.setVisibility(View.VISIBLE);
		//显示上下操作板
		nextToogle.setVisibility(View.VISIBLE);
		preToogle.setVisibility(View.VISIBLE);
	}

	/**
	 * 触发器用来控制显示与隐藏
	 */
	void toogle()
	{
		if(mVisible)
		{
			hidePanel();
			mVisible = false;
		}
		else
		{
			showPanel();
			mVisible = true;
		}
	}

	@Override
	public void onClick(View v){
		switch(v.getId())
		{
			case R.id.back_btn://返回按钮
				onBackPressed();//调用返回
				break;
			case R.id.show_menuBtn://显示操作平台按钮
				//Log.i(TAG,"onClick: 操作平台按钮");
				if(View.GONE == menu_content.getVisibility()) {
					menu_content.setVisibility(View.VISIBLE);
					hidePanel();
					mVisible = false;
					//底部状态栏消失
					small_status.setVisibility(View.GONE);
				}
				break;
			case R.id.feed_backBtn://反馈按钮
				break;
			case R.id.downloadpic_Btn://下载此张图片按钮
				break;
			case R.id.sharePicBtn://分享漫画链接或者此张图片
				break;
			case R.id.toogle:
				//Log.i(TAG,"onClick: 隐藏按钮按下");
				toogle();
				break;
			case R.id.nextHUAText://下一张图片
				adapterNowPos++;
				scroolRV_To(adapterNowPos);
				break;
			case R.id.preHUAText://上一张图片
				adapterNowPos--;
				scroolRV_To(adapterNowPos);
				break;
			case R.id.menu_content://操作面板容器
				menu_content.setVisibility(View.GONE);
				//底部状态栏显示
				small_status.setVisibility(View.GONE);
				break;
			case R.id.indexBtn://目录按钮
				//目录界面
				break;
			case R.id.download_comic_Btn://下载漫画按钮
				break;
			case R.id.light_btn://亮度按钮
				break;
			case R.id.phone_switch://切换手机的横竖屏
				break;
			case R.id.options_btn://选项按钮
				break;
		}
	}

	/**
	 * 滚动到位置
	 * @param pos
	 */
	void scroolRV_To(int pos)
	{
		if(adapterNowPos>=allItems)
		{
			adapterNowPos = allItems;
			Toast.makeText(this,"已经到顶啦！",Toast.LENGTH_SHORT);
		}
		else if(adapterNowPos <= 0)
		{
			adapterNowPos = 0;
			Toast.makeText(this,"已经到底啦！",Toast.LENGTH_SHORT);
		}
		mContentView.smoothScrollToPosition(adapterNowPos);
	}

	/**
	 * 图片显示view的adapter
	 */
	class MyContentViewAdapter extends RecyclerView.Adapter
	{

		private ArrayList<String> pics;
		private Context context;

		public MyContentViewAdapter(ArrayList<String> pics){
			this.pics = pics;
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
			context = parent.getContext();
			View view = LayoutInflater.from(context)
					.inflate(R.layout.comicpics_item,parent,false);
			return new MyViewHolder(view);
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
			MyViewHolder myViewHolder = (MyViewHolder)holder;
			myViewHolder.textView.setText(position+1+"");//设置占位
			Glide.with(context)
					.load(pics.get(position))
					.into(myViewHolder.imageView);
		}

		@Override
		public int getItemCount(){
			return pics.size();
		}

		class MyViewHolder extends RecyclerView.ViewHolder{
			private View rootView;
			private ImageView imageView;
			private TextView textView;
			public MyViewHolder(View itemView){
				super(itemView);
				rootView = itemView;
				imageView = (ImageView)rootView.findViewById(R.id.imageView);
				textView = (TextView)rootView.findViewById(R.id.textView);
			}
		}
	}
	public BroadcastReceiver batterReceiver  = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context,Intent intent){
			int level = intent.getIntExtra("level",0);
			//Log.i(TAG,"onReceive: "+level);
			battery.setText(level+"%");
		}
	};
}
