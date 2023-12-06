package com.example.job;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);

        // 启用 JavaScript 执行
        webView.getSettings().setJavaScriptEnabled(true);

        // 设置 WebViewClient 以处理页面加载和在 WebView 内部打开链接
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容
                getWebViewHtmlContent(view);
            }
        });

        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
    }

    private void getWebViewHtmlContent(WebView webView) {
        // 使用 evaluateJavascript 方法获取整个 HTML 内容
        webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String htmlContent) {
                        // 处理获取到的 HTML 内容
                        if (htmlContent != null) {
                            // 在这里处理获取到的 HTML 内容
                            // 可以将其打印到日志或显示在 TextView 中
                            System.out.println("HTML Content: " + htmlContent);
                        }
                    }
                });
    }
}
