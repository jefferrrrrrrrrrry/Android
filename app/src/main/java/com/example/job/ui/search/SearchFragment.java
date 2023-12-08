package com.example.job.ui.search;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.Job.JobsAll;
import com.example.job.Module;
import com.example.job.R;
import com.example.job.ReminderListActivity;
import com.example.job.SearchService;
import com.example.job.WebViewActivity;
import com.example.job.chat.Chat;
import com.example.job.chat.ChatAdapter;

import java.util.ArrayList;

import kotlinx.coroutines.Job;

public class SearchFragment extends Fragment {

    private SearchViewModel mViewModel;
    private ListView listView;
    private EditText text;
    private static JobAdapter jobAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // TODO: Use the ViewModel
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text=view.findViewById(R.id.search_box);
        ArrayList<JobItem> jobItems = JobsAll.getAll();
        jobAdapter = new JobAdapter(getContext(), R.layout.job_item, jobItems);
        view.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                Intent intent= new Intent(getActivity(), SearchService.class);
                intent.putExtra("search_key",text.getText().toString());
                getActivity().startService(intent);
            }
        });

        //jobItems.add(new JobItem("Smoking", "Li Tang", "Ding Zhen", "5000/month", "www.baidu.com"));
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

    public static JobAdapter getJobAdapter() {
        return jobAdapter;
    }
}