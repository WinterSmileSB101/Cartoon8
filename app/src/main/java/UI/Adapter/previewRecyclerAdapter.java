package UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import User.Prview;

import customView.RoundImageView;
import winter.zxb.smilesb101.cartoon8.R;

/**
 * 项目名称：Cartoon8
 * 类描述：评论的Adapter
 * 创建人：SmileSB101
 * 创建时间：2017/3/11 0011 16:09
 * 修改人：Administrator
 * 修改时间：2017/3/11 0011 16:09
 * 修改备注：
 */

public final class previewRecyclerAdapter extends RecyclerView.Adapter{
	private ArrayList<Prview> previews;
	private Context context;
	private MyViewHolder holder;

	public previewRecyclerAdapter(ArrayList<Prview> previews){
		this.previews = previews;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		context = parent.getContext();
		View view = LayoutInflater.from(context)
				.inflate(R.layout.preview_recycler_item,parent,false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
		this.holder = (MyViewHolder)holder;
		Prview prview = previews.get(position);
		Glide.with(context)
				.load(prview.getUser().getImage())
				.into(this.holder.preview_image);
		this.holder.preview_name.setText(prview.getUser().getName());
		this.holder.preview_date.setText(prview.getDate());
		this.holder.preview_content.setText(prview.getContent());
	}

	@Override
	public int getItemCount(){
		return previews.size();
	}

	static class MyViewHolder extends RecyclerView.ViewHolder{
		private View rootView;
		private RoundImageView preview_image;
		private TextView preview_name;
		private TextView preview_date;
		private TextView preview_content;

		public MyViewHolder(View itemView){
			super(itemView);
			rootView = itemView;
			preview_content = (TextView)rootView.findViewById(R.id.preview_content);
			preview_date = (TextView)rootView.findViewById(R.id.preview_date);
			preview_image = (RoundImageView)rootView.findViewById(R.id.preview_image);
			preview_name = (TextView)rootView.findViewById(R.id.preview_name);
		}
	}
}
