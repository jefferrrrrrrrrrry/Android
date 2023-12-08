package com.example.job;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.job.Job.JobItem;
import com.example.job.Job.JobsAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JobSearchService extends Service {
    private WebView webView;
    private JSONArray jsonArray;
    private boolean hasPerformedSearch = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 在这里执行后台任务
        performBackgroundTask(intent.getStringExtra("search_key"));
        return START_NOT_STICKY;
    }

    private void performBackgroundTask(String searchKey) {
        // 执行后台任务，例如抓取网页数据
        webView = new WebView(this);
        webView.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容
                if (!hasPerformedSearch) {
                    String key_word = searchKey;
                    System.out.println(key_word);
                    hasPerformedSearch = true;
                    performSearch(key_word);
                }else {
                    extractAndProcessData();
                    Intent broadcastIntent = new Intent("com.example.job.JOB_SEARCH_COMPLETE");
                    sendBroadcast(broadcastIntent);
                    // 停止 Service
                    stopSelf();
                }
            }
        });
        // 加载网页
        webView.loadUrl("https://www.zhipin.com/beijing/");
    }

    private void performSearch(String searchKey) {
        // Inject JavaScript to fill in the search box
        String fillSearchBoxScript = "document.querySelector('.ipt-search').value = \'"+searchKey+"\';";
        webView.evaluateJavascript(fillSearchBoxScript, null);

        // Inject JavaScript to click the search button
        String clickSearchButtonScript = "document.querySelector('.btn.btn-search').click();";
        webView.evaluateJavascript(clickSearchButtonScript, null);
    }

    private void extractAndProcessData() {

        webView.evaluateJavascript("(function() { \n"+
                "const jobElements = document.querySelectorAll('.item');\n"+
                "const jobsArray = [];\n" +
                "\n" +
                "// 遍历所有元素\n" +
                "jobElements.forEach((jobElement, index) => {\n" +
                "    const title = jobElement.querySelector('.title-text').textContent.trim();\n" +
                "    const salary = jobElement.querySelector('.salary').textContent.trim();\n" +
                "    const company = jobElement.querySelector('.company').textContent.trim();\n" +
                "    const hrname = jobElement.querySelector('.user-wrap').querySelector('.name').textContent.trim();\n" +
                "    const href = jobElement.querySelector('a').getAttribute('href');\n"+
                "    // Extracting labels\n" +
                "    const jobInfo = {\n" +
                "        title,\n" +
                "        salary,\n" +
                "        company,\n" +
                "        hrname,\n" +
                "        href,\n" +
                "    };\n" +
                "    jobsArray.push(jobInfo);\n" +
                "});" +
                "return JSON.stringify(jobsArray);})();", value -> {
            try {
                value=value.substring(1,value.length()-1).replaceAll("\\\\","");
                JSONArray jsonArray=new JSONArray(value);
                JobsAll.getAll().clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // 获取 JSON 对象中的属性值
                    String title = jsonObject.getString("title");
                    String salary = jsonObject.getString("salary");
                    String company = jsonObject.getString("company");
                    String hrname = jsonObject.getString("hrname");
                    String href = jsonObject.getString("href");
                    JobsAll.getAll().add(new JobItem(title,company,hrname,salary,"https://www.zhipin.com/"+href));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }
}