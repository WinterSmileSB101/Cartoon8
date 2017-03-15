package UI.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import RetailsWorm.NetWorkUtils;
import UI.Adapter.stackFragmentSectionAdapter;
import winter.zxb.smilesb101.cartoon8.R;

/**
 * 项目名称：Cartoon8
 * 类描述：书库Fragment
 * 创建人：SmileSB101
 * 创建时间：2017/3/1 0001 09:58
 * 修改人：Administrator
 * 修改时间：2017/3/1 0001 09:58
 * 修改备注：
 */

public class comicStackFragment extends Fragment{

	public static final int CLASS_RECOMMEND = 0;//推荐
	public static final int CLASS_RANK = 1;//榜单
	public static final int CLASS_CLASSIFY = 2;//分类
	public static final int CLASS_CARTOON = 3;//动画

	private static final String KEY_BANNER = "BANNER";
	private static final String KEY_CLASS = "CLASS";

	private View rootView;
	private Context context;
	private TabLayout tablayout;
	private ViewPager viewPager;
	private stackFragmentSectionAdapter sectionAdapter;
	private FragmentManager fm;

	private static final String TAG = "StackFragment";

	static Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg){
			switch(msg.what)
			{
				case NetWorkUtils.MSG_HTML:
					String html = msg.obj.toString();
					Log.i(TAG,"handleMessage: "+html);
					break;
			}
		}
	};

	public static comicStackFragment newInstence(FragmentManager fm)
	{
		comicStackFragment fragment = new comicStackFragment();
		fragment.setFm(fm);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
		context = container.getContext();
		rootView = inflater.inflate(R.layout.fragment_comicstack_layout,container,false);
		InitView();
		return rootView;
	}

	void InitView()
	{
		if(rootView!=null)
		{
			tablayout = (TabLayout)rootView.findViewById(R.id.comicstack_tablayout);
			viewPager = (ViewPager)rootView.findViewById(R.id.comicstack_viewPager);
		}
		InitValue();
	}

	void InitValue()
	{
		//从网络或缓存读取数据
		//NetWorkUtils.getHtmlPage(context,"http://www.dangniao.com/",handler);
		sectionAdapter = new stackFragmentSectionAdapter(fm);
		viewPager.setAdapter(sectionAdapter);
		tablayout.setupWithViewPager(viewPager);
	}

	public void setFm(FragmentManager fm){
		this.fm = fm;
	}
}
