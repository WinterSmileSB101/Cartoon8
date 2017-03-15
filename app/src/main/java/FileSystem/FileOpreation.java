package FileSystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 项目名称：Cartoon8
 * 类描述：文件操作类
 * 创建人：SmileSB101
 * 创建时间：2017/3/15 0015 11:09
 * 修改人：Administrator
 * 修改时间：2017/3/15 0015 11:09
 * 修改备注：
 */

public class FileOpreation{

	/**
	 * 软件文件根目录
	 */
	public static final String rootPath = Environment.getExternalStorageDirectory().getPath();

	/**
	 * 软件图片目录
	 */
	public static final String imagePath = rootPath+"image/";
	/**
	 * 软件漫画目录
	 */
	public static final String comicPath = rootPath+"comic/";
	/**
	 * 保存图片到指定路径
	 * @param bitmap 图片
	 * @param path 路径 文件名(漫画名称-漫画章节-漫画第几张图  或者日期)
	 * @param context
	 * @return
	 */
	public static boolean saveFileTo(Bitmap bitmap,String path,Context context){
		boolean flag = false;
		try {
			createPath();
			File file = new File(path);
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
			fos.flush();
			fos.close();
			Toast.makeText(context,"图片已经保存成功！路径："+path,Toast.LENGTH_SHORT).show();
			flag = true;
		} catch(FileNotFoundException e) {
			Toast.makeText(context,"=.=图片保存失败了！文件位置没找到！",Toast.LENGTH_SHORT).show();
			//e.printStackTrace();
		} catch(IOException e) {
			Toast.makeText(context,"=.=图片保存失败了！文件输入输出错误！\n"+e.getMessage(),Toast.LENGTH_SHORT).show();
			//e.printStackTrace();
		}
		return flag;
	}

	private static boolean createPath()
	{
		File file = new File(rootPath);
		if(!file.exists())
		{
			file.mkdir();
		}
		file = new File(imagePath);
		if(!file.exists())
		{
			file.mkdir();
		}
		file = new File(comicPath);
		if(!file.exists())
		{
			file.mkdir();
		}
		return true;
	}
}
