package UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import winter.zxb.smilesb101.cartoon8.R;

/**
 * 项目名称：Cartoon8
 * 类描述：漫画详情页面
 * 创建人：SmileSB101
 * 创建时间：2017/3/8 0008 13:07
 * 修改人：Administrator
 * 修改时间：2017/3/8 0008 13:07
 * 修改备注：
 */

public class comicDetailsFragment extends Fragment{
	public static final String LINK_KEY = "Link";
	private static final String TAG  = "DetailsFragmentt";

	private static Fragment fragment;
	private Context context;
	private String link;
	private View rootView;

	public static comicDetailsFragment newInstance(String link)
	{
		comicDetailsFragment fragment = new comicDetailsFragment();
		Bundle bundle = new Bundle();
		bundle.putString(LINK_KEY,link);
		fragment.setArguments(bundle);
		return fragment;
	}


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
		Log.i(TAG,"onCreateView: 创建View");
		rootView = inflater.inflate(R.layout.comic_details_layout,container,false);
		Bundle bundle = getArguments();
		if(bundle!=null)
		{
			link = bundle.getString(LINK_KEY);
		}
		else
		{
			link = "";
		}
		InitView();
		return rootView;
	}

	void InitView()
	{
		if(rootView!=null)
		{
			//进行View的相关操作
			InitValue();
		}
	}

	void InitValue()
	{}
}
