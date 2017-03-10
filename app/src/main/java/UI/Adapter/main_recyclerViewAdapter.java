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

import ComicData.Comic;
import winter.zxb.smilesb101.cartoon8.R;

/**
 * 项目名称：Cartoon8
 * 类描述：主界面的recyclerView
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 13:20
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 13:20
 * 修改备注：
 */

public class main_recyclerViewAdapter extends RecyclerView.Adapter{
	private ArrayList<Comic> comicArrayList;
	private Context context;
	public main_recyclerViewAdapter(ArrayList<Comic> arrayList){
		comicArrayList = arrayList;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
		context = parent.getContext();
		View view = LayoutInflater.from(context)
				.inflate(R.layout.bookcase_recycler_item,parent,false);
		return new viewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
		final viewHolder holder1 = (viewHolder)holder;
		Comic comic = comicArrayList.get(position);
		Glide.with(context)
				.load(comic.getComic_image())
				.into(holder1.item_image);
		holder1.item_name.setText(comic.getName());
	}

	@Override
	public int getItemCount(){
		return comicArrayList.size();
	}

	static class viewHolder extends RecyclerView.ViewHolder{
		private ImageView item_image;
		private TextView item_name;
		private View item_View;
		public viewHolder(View itemView){
			super(itemView);
			item_View = itemView;
			item_image = (ImageView)itemView.findViewById(R.id.item_image);
			item_name = (TextView)itemView.findViewById(R.id.item_name);
		}
	}
}
