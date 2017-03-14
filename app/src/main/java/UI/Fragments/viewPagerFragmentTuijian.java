package UI.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.youth.banner.Banner;

import java.util.ArrayList;

import ComicData.ComicClass;
import ComicData.ComicRankList;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import UI.Adapter.Tuijian_recyclerViewAdapter;
import UI.LayoutManager.CustomStraggerLayoutManager;
import winter.zxb.smilesb101.cartoon8.R;

import static ComicData.comicStaticValue.CLASS_LINK;
import static ComicData.comicStaticValue.CLASS_NAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_LASESTNAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_LINK;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_NAME;
import static ComicData.comicStaticValue.RankList_TailString_COMIC_PIC;
import static ComicData.comicStaticValue.RankList_TailString_RANKNAME;
import static ComicData.comicStaticValue.RankList_patternNames;

/**
 * 项目名称：Cartoon8
 * 类描述：推荐漫画碎片
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 16:32
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 16:32
 * 修改备注：
 */

public final class viewPagerFragmentTuijian extends Fragment{

	private View rootView;
	private Context context;
	private Fragment fragment;

	private Banner banner;
	private LinearLayout recyclerViewContent;
	private ArrayList<RecyclerView> recyclerViews;
	private ArrayList<Tuijian_recyclerViewAdapter> arrayList;


	private static final String TAG = "FragmentTuijian";

	private ComicClass comicClass = new ComicClass();
	private ArrayList<ComicRankList> comicRankLists;

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			switch(msg.what) {
				case NetWorkUtils.MSG_HTML:
					String html = msg.obj.toString();
					//Log.i(TAG,"handleMessage: 正确返回"+html);
					/*setPic(HtmlAnalysisUtils.getImageOfHtml(html,"img[src~=(?i)\\.(jpe?g)]"));
					comicClass.setClass_name(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","title"));
					comicClass.setClass_link(HtmlAnalysisUtils.getPageClass(html,"ul li a[title~=]","href"));*/

					if(!html.equals(NetWorkUtils.HTML_EMPTY)) {
						InitLayoutValue(html);
					}
					else
					{
						Log.i(TAG,"handleMessage: TUIJIAN 为空！！");
					}
					break;
			}
		}
	};

	void InitLayoutValue(String html)
	{
		Log.i(TAG,"InitLayoutValue: "+"更新前");
		new AsyncTask<String,Void,Void>(){
			@Override
			protected Void doInBackground(String... params){
				//获取类别
				/*comicClass.setClass_name(HtmlAnalysisUtils.getPageClass(params[0],CLASS_NAME,"title"));
				comicClass.setClass_link(HtmlAnalysisUtils.getPageClass(params[0],CLASS_LINK,"href"));*/
				comicRankLists = new ArrayList<>();
				ComicRankList comicRankList = null;
				for(String s:RankList_patternNames) {
					comicRankList = new ComicRankList();
					comicRankList.setRank_comic_link(HtmlAnalysisUtils.getRankList(params[0],s + RankList_TailString_COMIC_LINK,"href"));
					comicRankList.setRank_comic_pic(HtmlAnalysisUtils.getRankList(params[0],s +RankList_TailString_COMIC_PIC,"data-src"));
					comicRankList.setRank_comic_lasestName(HtmlAnalysisUtils.getRankList(params[0],s+RankList_TailString_COMIC_LASESTNAME,HtmlAnalysisUtils.WHAT_innerHTML));
					comicRankList.setRank_comic_name(HtmlAnalysisUtils.getRankList(params[0],s+RankList_TailString_COMIC_NAME,HtmlAnalysisUtils.WHAT_innerHTML));
					comicRankList.setRank_Name(HtmlAnalysisUtils.getRankList(params[0],s+RankList_TailString_RANKNAME,HtmlAnalysisUtils.WHAT_innerHTML).get(0));
					comicRankLists.add(comicRankList);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid){
				//更新UI
				Log.i(TAG,"onPostExecute: "+comicClass.getClass_name());
				//动态生成Recycler
				recyclerViews = new ArrayList<RecyclerView>();
				arrayList = new ArrayList<Tuijian_recyclerViewAdapter>();
				View view = null;
				for(ComicRankList rankList : comicRankLists)
				{
					view = LayoutInflater.from(context)
							.inflate(R.layout.rank_recyclerview_layout,recyclerViewContent,false);
					DaynamicInitRecyclerView(view,rankList);//动态更新
					recyclerViewContent.addView(view);
				}
			}
		}.execute(html);

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		context = container.getContext();
		rootView = inflater.inflate(R.layout.fragment_tuijian_layout,container,false);
		fragment = this;
		InitView();
		return rootView;
	}

	void InitView()
	{
		if(rootView!=null)
		{
			Log.i(TAG,"InitView: 获取控件");
			banner = (Banner)rootView.findViewById(R.id.tuijian_fragment_banner);
			recyclerViewContent = (LinearLayout)rootView.findViewById(R.id.rank_recyclerContent);
			InitValue();
		}
	}

	void InitValue()
	{
		NetWorkUtils.getHtmlPage(this.getActivity(),"http://m.dangniao.com/",handler);
	}

	void DaynamicInitRecyclerView(View view,ComicRankList comicRank)
	{
		RecyclerView r = (RecyclerView)view.findViewById(R.id.rank_recyclerView);
		Tuijian_recyclerViewAdapter a = new Tuijian_recyclerViewAdapter(comicRank,context,fragment);
		CustomStraggerLayoutManager layoutmanager = new CustomStraggerLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
		r.setLayoutManager(layoutmanager);
		r.setAdapter(a);
		recyclerViews.add(r);
		arrayList.add(a);
		((TextView)view.findViewById(R.id.rank_name)).setText(comicRank.getRank_Name());
		view.findViewById(R.id.rank_more).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Log.i(TAG,"onClick: 点击更多");
			}
		});
	}
}
