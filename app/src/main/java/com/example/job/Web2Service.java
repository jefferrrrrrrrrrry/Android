package com.example.job;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.ui.search.SearchFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Web2Service extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        WebView webView = new WebView(this);
        System.out.println(webView.getSettings().getUserAgentString());

        // 启用 JavaScript 执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        String search_key = intent.getStringExtra("search_key");

        webView.setWebViewClient(new WebViewClient() {
            private boolean finished = false;
            @Override
            public void onPageFinished(WebView view, String url) {
                // 在页面加载完成后获取整个 HTML 内容
                if (!finished) {
                    getWeb2(webView);
                    System.out.println("in 2");
                    finished = true;
                }
                //System.out.println("im in");
            }
        });


        webView.loadUrl("https://www.chinahr.com/job?value=" + search_key);

        return START_NOT_STICKY;
    }

    private void getWeb2(WebView webView) {
        webView.evaluateJavascript("(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    private void gothrough(Elements e, String[] input) {
                        int i = 0;
                        for (Element ele : e) {
                            input[i] = ele.text();
                            i++;
                        }
                    }
                    @Override
                    public void onReceiveValue(String htmlContent) {
                        // 处理获取到的 HTML 内容
                        String theString = "<html>\n" + htmlContent.substring(htmlContent.indexOf("\\u003Cbody>"));
                        theString = theString.replace("\\n", "\n");
                        theString = theString.replace("\\u003C", "<");
                        theString = theString.replace("\\\"", "\"");
                        theString = theString.substring(0, theString.length()-1);
                        Document document = Jsoup.parse(theString);
                        Elements elements = document.getElementsByClass("detail-card");
                        int size = elements.size();
                        String[] titles = new String[size];
                        String[] salaries = new String[size];
                        String[] comps = new String[size];
                        String[] hreF = new String[size];
                        Elements tmp = elements.select(".detail-card_left-title-name");
                        gothrough(tmp, titles);
                        tmp = elements.select(".detail-card_left-salary");
                        gothrough(tmp, salaries);
                        tmp = elements.select(".detail-card_right-compony").select(".name");
                        gothrough(tmp, comps);
                        tmp = document.getElementsByClass("detail-card_left");
                        int ii = 0;
                        for (Element element: tmp) {
                            hreF[ii] = element.attr("href");
                            ii++;
                        }

                        JobAdapter jobAdapter = SearchFragment.getJobAdapter();
                        for (int i = 0; i < size; i++) {
                            JobItem p = new JobItem(titles[i], comps[i], "面试官",
                                    salaries[i], "https://www.chinahr.com" + hreF[i], "中华英才网");
                            jobAdapter.add(p);
                        }
                        //jobAdapter.notifyDataSetChanged();
                        stopSelf();
                    }
                });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}