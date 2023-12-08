package com.example.job.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.job.Job.JobAdapter;
import com.example.job.Job.JobItem;
import com.example.job.Module;
import com.example.job.R;
import com.example.job.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listViewFavorites);
        ArrayList<JobItem> jobItems = Module.getInstance().getUser().getFavjobs();
        JobAdapter jobAdapter = new JobAdapter(getContext(), R.layout.job_item,jobItems);
        listView.setAdapter(jobAdapter);

        //jobItems.add(new JobItem("Playing Genshin Impact", "Li Tang", "Ding Zhen", "5000/month", "www.baidu.com"));
        // TODO 此处更改以适应收藏夹
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
   /* public ArrayList<Position> getfav(){
        return Module.getInstance().getUser().getFavpositions();
    }*/
}