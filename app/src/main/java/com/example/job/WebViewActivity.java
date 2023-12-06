package com.example.job;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        webView.clearCache(true);

        // 启用 JavaScript 执行
        WebSettings webSettings = webView.getSettings();
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);

        // 设置 WebViewClient 以处理页面加载和在 WebView 内部打开链接
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // 页面加载完成时执行 AsyncTask 获取动态 HTML 元素
                new LoadDynamicHtmlElementTask().execute();
            }
        });

        // 加载包含动态内容的网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
    }

    private class LoadDynamicHtmlElementTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                // 在后台线程中获取动态生成的 HTML 元素内容
                // 这里简化示例，实际应用可能需要更复杂的逻辑
                Thread.sleep(5000); // 模拟耗时操作
                return getDynamicHtmlElementContent("targetElementId");
            } catch (Exception e) {
                Log.e(TAG, "Error in LoadDynamicHtmlElementTask", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String htmlElementContent) {
            super.onPostExecute(htmlElementContent);
            if (htmlElementContent != null) {
                // 处理获取到的动态 HTML 元素内容
                Log.d(TAG, "Dynamic HTML element content: " + htmlElementContent);
            } else {
                Log.e(TAG, "Failed to retrieve dynamic HTML element content");
            }
        }
    }

    private String getDynamicHtmlElementContent(String elementId) {
        // 使用 WebView 提供的方法获取特定元素的内容
        return "Content of element with ID '" + elementId + "'";
    }
}

