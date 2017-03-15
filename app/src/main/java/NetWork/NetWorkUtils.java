package NetWork;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.concurrent.ExecutionException;

import FileSystem.FileOpreation;

import static com.bumptech.glide.request.target.Target.SIZE_ORIGINAL;

/**
 * 项目名称：Cartoon8
 * 类描述：网络访问类
 * 创建人：SmileSB101
 * 创建时间：2017/3/15 0015 10:28
 * 修改人：Administrator
 * 修改时间：2017/3/15 0015 10:28
 * 修改备注：
 */

public class NetWorkUtils{

	public static void dowload_Pic(final String link,final Activity activity)
	{
		new AsyncTask<Void,Integer,Bitmap>(){
			@Override
			protected Bitmap doInBackground(Void... params){
				try {
					Bitmap bitmap = Glide.with(activity)
							.load(link)
							.asBitmap()
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.into(SIZE_ORIGINAL,SIZE_ORIGINAL)
							.get();
					if(bitmap!=null)
					{
						FileOpreation.saveFileTo(bitmap,"1-1-1.png",activity);
					}
				} catch(InterruptedException e) {
					e.printStackTrace();
				} catch(ExecutionException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Bitmap bitmap){
				super.onPostExecute(bitmap);
			}
		}.execute();
	}
}
