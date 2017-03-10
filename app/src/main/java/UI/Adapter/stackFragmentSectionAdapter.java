package UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import UI.Fragments.viewPagerFragmentCartoon;
import UI.Fragments.viewPagerFragmentClassify;
import UI.Fragments.viewPagerFragmentRank;
import UI.Fragments.viewPagerFragmentTuijian;

/**
 * 项目名称：Cartoon8
 * 类描述：
 * 创建人：SmileSB101
 * 创建时间：2017/3/2 0002 16:30
 * 修改人：Administrator
 * 修改时间：2017/3/2 0002 16:30
 * 修改备注：
 */

public class stackFragmentSectionAdapter extends FragmentPagerAdapter{

	private ArrayList<String> titles;

	private viewPagerFragmentTuijian fragmentTuijian;
	private viewPagerFragmentRank fragmentRank;
	private viewPagerFragmentClassify fragmentClassify;
	private viewPagerFragmentCartoon fragmentCartoon;

	public stackFragmentSectionAdapter(FragmentManager fm){
		super(fm);
		titles = new ArrayList<>();
		titles.add("推 荐");
		titles.add("榜 单");
		titles.add("分 类");
		titles.add("动 画");

		fragmentTuijian = new viewPagerFragmentTuijian();
		fragmentRank = new viewPagerFragmentRank();
		fragmentClassify = new viewPagerFragmentClassify();
		fragmentCartoon = new viewPagerFragmentCartoon();
	}

	@Override
	public Fragment getItem(int position){
		switch(position)
		{
			case 0:
				return fragmentTuijian;
			case 1:
				return fragmentRank;
			case 2:
				return fragmentClassify;
			case 3:
				return fragmentCartoon;
			default:
				break;
		}
		return null;
	}

	@Override
	public int getCount(){
		return titles.size();
	}

	@Override
	public CharSequence getPageTitle(int position){
		return titles.get(position);
	}
}
