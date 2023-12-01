package com.example.job.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.job.LoginActivity;
import com.example.job.R;
import com.example.job.ReminderListActivity;
import com.example.job.ReminderSettingsActivity;
import com.example.job.UpdateProfileActivity;
import com.example.job.chat.Chat;
import com.example.job.chat.ChatAdapter;
import com.example.job.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.buttonByUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                startActivity(new Intent(getActivity(), ReminderListActivity.class));
            }
        });
        // Chats
        ListView listView = view.findViewById(R.id.note_list);
        ArrayList<Chat> chats = new ArrayList<>();
        ChatAdapter chatAdapter = new ChatAdapter(getContext(), R.layout.chat, chats);
        listView.setAdapter(chatAdapter);

        // TODO: add or remove chats
        chats.add(new Chat("老板1", "明天来面试", "10:05"));
        chats.add(new Chat("老板2", "v50来面试", "10:10"));
        chats.add(new Chat("老板3", "明天来面试", "10:05"));
        chats.add(new Chat("老板4", "v50来面试", "10:10"));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}