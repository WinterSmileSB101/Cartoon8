package winter.zxb.smilesb101.cartoon8;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ComicData.Comic;
import UI.Adapter.comicDetailsRecyclerAdapter;
import UI.LayoutManager.CustomStraggerLayoutManager;

public final class index_download_Acritity extends AppCompatActivity implements View.OnClickListener{

	private static final String TAG = "download_Acritity";
	public static final String ADAPTER_KEY = "ADAPTER";
	public static final String COMIC_KEY = "COMIC";
	public static final String ISDOWNLOAD_KEY = "ISDOWNLOAD";

	/**
	 * 漫画对象
	 */
	private Comic comic;
	private Toolbar toolbar;
	private ActionBar actionBar;
	private RecyclerView recyclerView;
	private comicDetailsRecyclerAdapter adapter;

	private TextView comic_status,comic_updateTime;

	private ArrayList<String> downLoad_Link;
	private boolean isDownLoad = false;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_download_layout);
		toolbar = (Toolbar)findViewById(R.id.download_toolbar);
		setSupportActionBar(toolbar);
		if(getSupportActionBar()!=null)
		{
			actionBar = getSupportActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.mipmap.left_arrow_72px_1205452_easyicon);
			actionBar.setTitle(comic.getName());
		}
		recyclerView  = (RecyclerView)findViewById(R.id.download_recyclerView);
		comic_status = (TextView)findViewById(R.id.download_Status);
		comic_updateTime = (TextView)findViewById(R.id.download_UpdateTime);
	}

	void InitValue()
	{
		if(getIntent()!=null)
		{
			Intent intent = getIntent();
			adapter = (comicDetailsRecyclerAdapter)intent.getSerializableExtra(ADAPTER_KEY);
			comic = (Comic)intent.getSerializableExtra(COMIC_KEY);
			isDownLoad = intent.getBooleanExtra(ISDOWNLOAD_KEY,false);

			if(adapter==null && comic != null)
			{
				adapter = new comicDetailsRecyclerAdapter(comic,isDownLoad,this);
			}
			else if(adapter == null && comic == null)
			{
				Toast.makeText(this,"没有任何有效数据呢=.=",Toast.LENGTH_SHORT).show();
				return;
			}
			recyclerView.setLayoutManager(new CustomStraggerLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
			recyclerView.setNestedScrollingEnabled(false);
			recyclerView.setAdapter(adapter);

			comic_status.setText(comic.getComic_Status());
			comic_updateTime.setText(comic.getLastUpdateTime());
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
				//启动下载服务
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
	public void onClick(View v){
		switch(v.getId())
		{
			case R.id.download_allChose:
				//全选
				downLoad_Link = adapter.choseAllitem();//全选,更新全选集合
				break;
			case R.id.download_downloadBtn:
				if(downLoad_Link == null)
				{
					//为空,显示提示
					Toast.makeText(this,"还没有选取任何章节噢！",Toast.LENGTH_SHORT).show();
					return;
				}
				//启动下载服务
				break;
			default:
				break;
		}
	}
}
