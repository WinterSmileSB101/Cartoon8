package winter.zxb.smilesb101.cartoon8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import RetailsWorm.NetWorkUtils;

public class testActivity extends AppCompatActivity{

	private static String TAG = "testActivity";
 	private WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		webView = (WebView)findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://m.dangniao.com/");
}

static class pageInterface{
	@JavascriptInterface
	public void showPageHtml(String html)
	{
		Log.i(TAG,"showPageHtml: 获取到网页数据："+html);
	}
}

}
