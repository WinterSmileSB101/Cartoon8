package UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import ComicData.ComicRankList;
import UI.Fragments.comicDetailsFragment;
import winter.zxb.smilesb101.cartoon8.R;
import winter.zxb.smilesb101.cartoon8.comicDetailsActivity;

/**
 * 项目名称：Cartoon8
 * 类描述：推荐recyclerView的adapter
 * 创建人：SmileSB101
 * 创建时间：2017/3/6 0006 21:35
 * 修改人：Administrator
 * 修改时间：2017/3/6 0006 21:35
 * 修改备注：
 */

public class Tuijian_recyclerViewAdapter extends RecyclerView.Adapter{
	private Fragment fragment;

	private ComicRankList comicRankList;
	private Context context;
	private static final int HEADVIEW_VIEWTYPE = 0;
	private static final String TAG = "Tuijian_RV_Adapter";

	public Tuijian_recyclerViewAdapter(ComicRankList comicRankList,Context context,Fragment fragment){
		this.comicRankList = comicRankList;
		this.context = context;
		this.fragment = fragment;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		View view = null;
		context = parent.getContext();
		view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_recyclerview_item,parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
		final MyViewHolder holder1 = (MyViewHolder)holder;
		//Log.i(TAG,"onBindViewHolder: "+comicRankList.getRank_comic_pic().get(position));
		//Log.i(TAG,"onBindViewHolder: POS: "+position);
		Glide.with(context)
		.load(comicRankList.getRank_comic_pic().get(position))
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(holder1.item_image);
		//Log.i(TAG,"onBindViewHolder: 名称："+comicRankList.getRank_comic_name().get(position));
		//Log.i(TAG,"onBindViewHolder: 更新到："+comicRankList.getRank_comic_lasestName().get(position));
		holder1.item_name.setText(comicRankList.getRank_comic_name().get(position));
		holder1.item_nowHua.setText(comicRankList.getRank_comic_lasestName().get(position));
		holder1.rootView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Log.i(TAG,"onClick: 详情去向按下");
				int pos = holder1.getAdapterPosition();//获取当前按下位置
				String htmlLink = comicRankList.getRank_comic_link().get(pos);//获取漫画网址
				//跳转，考虑缓存数据
				comicDetailsFragment fragment1 = comicDetailsFragment.newInstance(htmlLink);
				Intent intent = new Intent(context,comicDetailsActivity.class);
				intent.putExtra(comicDetailsActivity.BGLINK_STRING,comicRankList.getRank_comic_pic().get(pos));
				intent.putExtra(comicDetailsActivity.HTMLLINK_STRING,comicRankList.getRank_comic_link().get(pos));
				intent.putExtra(comicDetailsActivity.NAME_STRING,comicRankList.getRank_comic_name().get(pos));
				intent.putExtra(comicDetailsActivity.LASTEST_STRING,comicRankList.getRank_comic_lasestName().get(pos));
				fragment.getActivity().startActivity(intent,ActivityOptionsCompat.makeSceneTransitionAnimation(fragment.getActivity(),holder1.item_image,"comicImage").toBundle());
			}
		});
	}

	@Override
	public int getItemCount(){
		return comicRankList.getRank_comic_pic().size();
	}

	@Override
	public int getItemViewType(int position){
		return super.getItemViewType(position);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder{

		private View rootView;
		private ImageView item_image;
		private TextView item_name;
		private TextView item_nowHua;

		public MyViewHolder(View itemView){
			super(itemView);
			rootView = itemView;
			item_image = (ImageView)rootView.findViewById(R.id.rank_recycle_imageView);
			item_name = (TextView)rootView.findViewById(R.id.item_name);
			item_nowHua = (TextView)rootView.findViewById(R.id.item_nowHua);
		}
	}
}
