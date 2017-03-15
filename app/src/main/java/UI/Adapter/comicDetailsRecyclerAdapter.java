package UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import ComicData.Comic;
import ComicData.ComicChapter;
import ComicData.comicStaticValue;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import winter.zxb.smilesb101.cartoon8.R;
import winter.zxb.smilesb101.cartoon8.WatchComicActivity;

import static ComicData.comicStaticValue.ComicDetails_patternNames;


/**
 * 项目名称：Cartoon8
 * 类描述：漫画详情页面的recyclerView
 * 创建人：SmileSB101
 * 创建时间：2017/3/8 0008 22:43
 * 修改人：Administrator
 * 修改时间：2017/3/8 0008 22:43
 * 修改备注：
 */

public final class comicDetailsRecyclerAdapter extends RecyclerView.Adapter implements Serializable{
	private static final String TAG = "DetailsRecyclerAdapter";

	private ArrayList<ComicChapter> chapters;
	private Comic comic;
	private String comic_ChapterImage;
	private View rootView;
	private static MyViewHolder lastHolder = null;
	private Activity activity;

	/**
	 * 下载的holder集合
	 */
	private ArrayList<MyViewHolder> holders;
	/**
	 * 下载的链接集合
	 */
	private ArrayList<String> downlaoad_selectPoss;

	/**
	 * 是否是下载的adapter
	 */
	private boolean isDownLoad = false;
	private Context context;

	public comicDetailsRecyclerAdapter(Comic comic,boolean isDownLoad,Activity activity){
		this.comic = comic;
		this.isDownLoad = isDownLoad;
		downlaoad_selectPoss = new ArrayList<>();
		holders = new ArrayList<>();
		chapters = comic.getChapters();
		this.activity = activity;
		//Log.i(TAG,"comicDetailsRecyclerAdapter: 获取图片列表前前");
		//Log.i(TAG,"comicDetailsRecyclerAdapter: "+chapters.size());
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		context = parent.getContext();
		rootView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.comicdetails_recycler_item,parent,false);
		return new MyViewHolder(rootView);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position){
		holders.add((MyViewHolder)holder);//添加到所有holder集合中
		final MyViewHolder NowHolder = (MyViewHolder)holder;//必须使用final否者会改变赋值（导致图片逻辑在adapter的最后一个的问题）
		String s = chapters.get(position).getChapterName();
		NowHolder.chapterName.setText(s);
		/*if(comic.getChapters().get(position).getPicList().equals(new ArrayList<String>())) {//不存在就通过网络访问
			NetWorkUtils.getHtmlPage(activity,comic.getChapters().get(position).getChapterLink(),handler);
		}*/
		NowHolder.chapterName.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				int pos = NowHolder.getAdapterPosition();
				if(!isDownLoad) {
					//按下，打开看漫画界面（漫画活动）
					if(lastHolder != null) {
						//设置成默认图片
						lastHolder.chapterName.setBackgroundResource(R.drawable.comic_btn);
					}
					NowHolder.chapterName.setBackgroundResource(R.drawable.round_rect_click);
					lastHolder = NowHolder;//赋值上次点击的holder
					Intent intent = new Intent(activity,WatchComicActivity.class);
					intent.putExtra(WatchComicActivity.COMIC_VALUE,comic);
					Log.i(TAG,"onClick: 漫画链接 "+chapters.get(pos).getChapterLink());
					intent.putExtra(WatchComicActivity.COMIC_LINK,chapters.get(pos).getChapterLink());
					activity.startActivity(intent);
				}
			   else {
					String link = chapters.get(pos).getChapterLink();
					//多选下载的adapter
					if(downlaoad_selectPoss.contains(link))
					{
						//已经存在，则取消选择
						NowHolder.chapterName.setBackgroundResource(R.drawable.comic_btn);
						downlaoad_selectPoss.remove(link);
					}
					else
					{
						//不存在添加
						NowHolder.chapterName.setBackgroundResource(R.drawable.round_rect_click);
						downlaoad_selectPoss.add(link);
					}
				}
			}
		});
	}

	@Override
	public int getItemCount(){
		return chapters.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
		private View rootView;
		private Button chapterName;
		public MyViewHolder(View itemView){
			super(itemView);
			rootView = itemView;
			chapterName = (Button)rootView.findViewById(R.id.item_name);
		}
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what) {
				case NetWorkUtils.MSG_HTML:
					String html = msg.obj.toString();
					/*setPic(HtmlAnalysisUtils.getImageOfHtml(html,"img[src~=(?i)\\.(jpe?g)]"));
					comicClass.setClass_name(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","title"));
					comicClass.setClass_link(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","href"));*/
					Log.i(TAG,"handleMessage: 详情获取图片");
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
	 * 处理网页返回后的界面
	 * @param html
	 */
	void InitLayoutValue(String html)
	{
		new AsyncTask<String,Void,Void>()
		{
			@Override
			protected Void doInBackground(String... params){
				//comic_ChapterImage = HtmlAnalysisUtils.getComicDetails(params[0],comicStaticValue.ComicContent_COMICPIC,"src").get(0);
				return null;
			}
			@Override
			protected void onPostExecute(Void aVoid){
				super.onPostExecute(aVoid);
			}
		}.execute(html);
	}

	/**
	 * 全选
	 * @return 下载的链接集合
	 */
	public ArrayList<String> choseAllitem()
	{
		if(isDownLoad)
		{
			downlaoad_selectPoss = new ArrayList<>();
			int index = 0;
			for(MyViewHolder holder : holders)
			{
				holder.chapterName.setBackgroundResource(R.drawable.round_rect_click);
				downlaoad_selectPoss.add(chapters.get(index).getChapterLink());
				index++;
			}
		}
		return downlaoad_selectPoss;
	}

	/**
	 * 获取下载链接集合
	 * @return
	 */
	public ArrayList<String> getDownlaoad_selectPoss(){
		return downlaoad_selectPoss;
	}
}
