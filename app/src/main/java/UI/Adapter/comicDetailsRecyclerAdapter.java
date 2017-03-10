package UI.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ComicData.comicStaticValue;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import winter.zxb.smilesb101.cartoon8.R;

import static ComicData.comicStaticValue.ComicContent_COMICPIC;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画详情页面的recyclerView
 * 创建人：SmileSB101
 * 创建时间：2017/3/8 0008 22:43
 * 修改人：Administrator
 * 修改时间：2017/3/8 0008 22:43
 * 修改备注：
 */

public class comicDetailsRecyclerAdapter extends RecyclerView.Adapter{
	private static final String TAG = "DetailsRecyclerAdapter";

	private static Context context;
	private ArrayList<String> comic_chapters;
	private ArrayList<String> comic_chapterLinks;
	private static String comic_ChapterImage;
	private View rootView;
	private static MyViewHolder holder;

	public comicDetailsRecyclerAdapter(ArrayList<String> comic_chapters,ArrayList<String> comic_chapterLinks){
		this.comic_chapters = comic_chapters;
		this.comic_chapterLinks = comic_chapterLinks;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		this.context = parent.getContext();
		rootView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.comicdetails_recycler_item,parent,false);
		return new MyViewHolder(rootView);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
		this.holder = (MyViewHolder)holder;
		this.holder.chapterName.setText(comic_chapters.get(position));
		NetWorkUtils.getHtmlPage(context,comic_chapterLinks.get(position),handler);
	}

	@Override
	public int getItemCount(){
		return comic_chapters.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
		private View rootView;
		private TextView chapterName;
		private ImageView chapterImage;
		public MyViewHolder(View itemView){
			super(itemView);
			rootView = itemView;
			chapterImage = (ImageView)rootView.findViewById(R.id.item_image);
			chapterName = (TextView)rootView.findViewById(R.id.item_name);
		}
	}

	static Handler handler = new Handler(){
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
	static void InitLayoutValue(String html)
	{
		new AsyncTask<String,Void,Void>()
		{
			@Override
			protected Void doInBackground(String... params){
				comic_ChapterImage = HtmlAnalysisUtils.getComicDetails(params[0],comicStaticValue.ComicContent_COMICPIC,"src").get(0);
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid){
				super.onPostExecute(aVoid);
			}
		}.execute(html);
	}
}
