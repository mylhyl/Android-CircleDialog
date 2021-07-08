package com.mylhyl.circledialog.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ProgressParams;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FgtAty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_fgt);
    }

    public static class BlankFragment extends Fragment {
        private BaseCircleDialog circleDialog;
        private OkHttpClient client = new OkHttpClient();
        private TextView textView;

        public BlankFragment() {
            // Required empty public constructor
        }

        public static BlankFragment newInstance() {
            BlankFragment fragment = new BlankFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_blank, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            textView = view.findViewById(R.id.textView);
            view.findViewById(R.id.button).setOnClickListener(v -> {
                circleDialog = new CircleDialog.Builder()
                        .setWidth(0.5f)
                        .setCanceledOnTouchOutside(false).setCancelable(false)
                        .setProgressText("努力加载中")
                        .setProgressStyle(ProgressParams.STYLE_SPINNER)
                        .show(getChildFragmentManager());
                post();
            });
        }

        private void post() {
            Request request = new Request.Builder().url("https://api.github.com/").get().build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    if (circleDialog != null) {
                        circleDialog.dismiss();
                    }
                    textView.post(() -> textView.setText(e.toString()));
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) {
                    if (circleDialog != null) {
                        circleDialog.dismiss();
                    }
                    textView.post(() -> textView.setText(response.toString()));
                }
            });
        }

    }
}