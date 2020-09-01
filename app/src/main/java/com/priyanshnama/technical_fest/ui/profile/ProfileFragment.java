package com.priyanshnama.technical_fest.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.priyanshnama.technical_fest.R;
import com.priyanshnama.technical_fest.UpgradePassActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView name, email, festId, passView;
    private ImageView profilePic;
    private Button upgrade;
    private Integer pass;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        festId = root.findViewById(R.id.uid);
        profilePic = root.findViewById(R.id.profile_image);
        passView = root.findViewById(R.id.pass);
        upgrade = root.findViewById(R.id.upgrade);
        upgrade.setOnClickListener(this::upgrade);
        return root;
    }


    private void upgrade(View view) {
        startActivity(new Intent(getContext(), UpgradePassActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLocalData();
        setNewData();
    }

    private void setLocalData() {
        SharedPreferences userInfo =  this.requireActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.apply();

        name.setText(userInfo.getString("name","Name"));
        email.setText(userInfo.getString("email","Email"));
        //passView.setText(userInfo.getInt("pass",0));
        festId.setText(userInfo.getString("festId","festId"));
        Picasso.get().load(userInfo.getString("photoUrl","null")).into(profilePic);
    }

    private void setNewData() {
        profileViewModel.getName().observe(getViewLifecycleOwner(), s -> name.setText(s));
        profileViewModel.getEmail().observe(getViewLifecycleOwner(), s -> email.setText(s));
        profileViewModel.getFestId().observe(getViewLifecycleOwner(), s -> festId.setText(s));
        profileViewModel.getPass().observe(getViewLifecycleOwner(), integer -> passView.setText(String.valueOf(integer)));
        profileViewModel.getPhotoUrl().observe(getViewLifecycleOwner(),
                uri -> Picasso.get().load(uri.toString()).into(profilePic));
    }

}