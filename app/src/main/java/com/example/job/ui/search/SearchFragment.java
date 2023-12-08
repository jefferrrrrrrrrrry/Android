package com.example.job.ui.search;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.Job.JobsAll;
import com.example.job.JobSearchService;
import com.example.job.R;
import com.example.job.SearchService;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ListView listView;
    private EditText text;
    private BroadcastReceiver broadcastReceiver;
    private static JobAdapter jobAdapter;


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text=view.findViewById(R.id.search_box);
        ArrayList<JobItem> jobItems = JobsAll.getAll();
        jobAdapter = new JobAdapter(getContext(), R.layout.job_item, jobItems);
        registerBroadcastReceiver();
        view.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                jobAdapter.clear();
                String search_str = text.getText().toString().replace("\n","");
                search_str = search_str.equals("") ? "java" : search_str;
                Intent intent=new Intent(getActivity(), JobSearchService.class);
                intent.putExtra("search_key",text.getText().toString());
                getActivity().startService(intent);
                Intent intent2 = new Intent(getActivity(), SearchService.class);
                intent2.putExtra("search_key", search_str);
                intent2.putExtra("mode", 2);
                getActivity().startService(intent2);
                Intent intent3 = new Intent(getActivity(), SearchService.class);
                intent3.putExtra("search_key", search_str);
                intent3.putExtra("mode", 3);
                getActivity().startService(intent3);
                Intent intent4 = new Intent(getActivity(), SearchService.class);
                intent4.putExtra("search_key", search_str);
                intent4.putExtra("mode", 4);
                getActivity().startService(intent4);
            }
        });
        // change
        listView = view.findViewById(R.id.result_list);
        listView.setAdapter(jobAdapter);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    private void registerBroadcastReceiver() {
        // 创建广播接收器
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 在这里执行 UI 更新操作
                if (intent.getAction() != null && intent.getAction().equals("com.example.job.JOB_SEARCH_COMPLETE")) {
                    // 执行你的 UI 更新代码
                    jobAdapter.notifyDataSetChanged();
                }
            }
        };

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter("com.example.job.JOB_SEARCH_COMPLETE");
        getActivity().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // 在 Fragment 销毁时注销广播接收器
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }
    }
    public static JobAdapter getJobAdapter() {
        return jobAdapter;
    }
}