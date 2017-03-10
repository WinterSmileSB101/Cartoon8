package UI.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;

import UI.Fragments.comicCaseFragment;
import UI.Fragments.comicStackFragment;
import UI.Fragments.mainDiscoreyFragment;
import UI.Fragments.mainMineFragment;
import UI.Fragments.viewPagerFragmentRank;

/**
 * 项目名称：Cartoon8
 * 类描述：主界面的ViewPager的Adapter
 * 创建人：SmileSB101
 * 创建时间：2017/2/28 0028 20:51
 * 修改人：Administrator
 * 修改时间：2017/2/28 0028 20:51
 * 修改备注：
 */

public class contentMainSectionAdapter extends FragmentPagerAdapter{
	private ArrayList<String> pageTitles;
	private comicCaseFragment comicCaseFragment;
	private comicStackFragment comicStackFragment;
	private mainDiscoreyFragment discoreyFragment;
	private mainMineFragment mineFragment;
	public contentMainSectionAdapter(FragmentManager fm){
		super(fm);
		pageTitles = new ArrayList<>();
		pageTitles.add("漫 架");
		pageTitles.add("漫 城");
		pageTitles.add("发 现");
		pageTitles.add("我 的");
		comicCaseFragment = new comicCaseFragment();
		comicStackFragment = comicStackFragment.newInstence(fm);
		discoreyFragment = new mainDiscoreyFragment();
		mineFragment = new mainMineFragment();
	}

	@Override
	public Fragment getItem(int position){
		switch(position)
		{
			case 0:
				return comicCaseFragment;
			case 1:
				return comicStackFragment;
			case 2:
				return discoreyFragment;
			case 3:
				return mineFragment;
			default:
				return comicStackFragment;
		}
	}

	@Override
	public int getCount(){
		return pageTitles.size();
	}

	@Override
	public CharSequence getPageTitle(int position){
		return pageTitles.get(position);
	}
}
