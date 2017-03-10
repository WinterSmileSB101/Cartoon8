package UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ComicData.Comic;
import UI.Adapter.main_recyclerViewAdapter;
import winter.zxb.smilesb101.cartoon8.R;

/**
 * 项目名称：Cartoon8
 * 类描述：书架Fragment
 * 创建人：SmileSB101
 * 创建时间：2017/3/1 0001 09:57
 * 修改人：Administrator
 * 修改时间：2017/3/1 0001 09:57
 * 修改备注：
 */

public class comicCaseFragment extends Fragment{

	private View rootView;
	private Context context;
	private RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
		context = container.getContext();
		rootView = inflater.inflate(R.layout.fragment_comiccase_layout,container,false);
		InitView();
		return rootView;
	}

	void InitView()
	{
		if(rootView!=null)
		{
			recyclerView = (RecyclerView)rootView.findViewById(R.id.comiccase_recyclerView);
		}
		InitValue();
	}

	void InitValue()
	{
		//从数据库或者网络获取数据
		ArrayList<Comic> comicArrayList = new ArrayList<>();
		for(int i = 0;i<8;i++) {
			Comic comic = new Comic();
			comic.setName("哈哈");
			comic.setComic_image("");
			comicArrayList.add(comic);
		}
		main_recyclerViewAdapter adapter = new main_recyclerViewAdapter(comicArrayList);
		StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(manager);
		recyclerView.setAdapter(adapter);
	}
}
