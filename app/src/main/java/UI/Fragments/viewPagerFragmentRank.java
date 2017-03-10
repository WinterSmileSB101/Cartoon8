package UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youth.banner.Banner;

import java.util.ArrayList;

import ComicData.ComicClass;
import ComicData.ComicRankList;
import RetailsWorm.HtmlAnalysisUtils;
import RetailsWorm.NetWorkUtils;
import winter.zxb.smilesb101.cartoon8.R;

import static ComicData.comicStaticValue.CLASS_LINK;
import static ComicData.comicStaticValue.CLASS_NAME;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画榜单的Fragment
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 21:01
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 21:01
 * 修改备注：
 */

public class viewPagerFragmentRank extends Fragment{

	private View rootView;
	private Context context;

	private Banner banner;
	private RecyclerView recyclerView;





	public static viewPagerFragmentRank newInstance()
	{
		viewPagerFragmentRank fragmentRank = new viewPagerFragmentRank();
		return fragmentRank;
	}




	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
		rootView = inflater.inflate(R.layout.fragment_rank_layout,container,false);
		context = container.getContext();
		InitValue();
		return rootView;
	}

	void InitView()
	{
		if(rootView!=null)
		{
			banner = (Banner)rootView.findViewById(R.id.rank_banner);
			recyclerView = (RecyclerView)rootView.findViewById(R.id.rank_recyclerView);
		}
	}
	void InitValue()
	{

	}
}
