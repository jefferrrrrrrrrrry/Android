package com.example.job.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.job.LoginActivity;
import com.example.job.Module;
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

        // Chats
        ListView listView = view.findViewById(R.id.note_list);
        ArrayList<Chat> chats= Module.getInstance().getUser().getChats();
        ChatAdapter chatAdapter = new ChatAdapter(getContext(), R.layout.chat, chats);
        listView.setAdapter(chatAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!chats.get(i).isRead()) {
                    chats.get(i).setRead(true);
                    chatAdapter.notifyDataSetChanged();
                }
                //
            }
        });

        view.findViewById(R.id.buttonByUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                startActivity(new Intent(getActivity(), ReminderListActivity.class));
            }
        });

        view.findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Module.getInstance().getUser().clearChats();
                chatAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}