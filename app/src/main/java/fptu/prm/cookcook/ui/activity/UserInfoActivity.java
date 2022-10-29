package fptu.prm.cookcook.ui.activity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.ActivityUserInfoBinding;
import fptu.prm.cookcook.ui.adapter.UserInfoAdapter;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding mViewBinding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initControl();
    }

    private void initView() {
        ArrayList<Pair> userInfo = new ArrayList<>();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .error(R.drawable.ic_person)
                    .into(mViewBinding.avatar);
            userInfo.add(new Pair("Name", user.getDisplayName()));
            userInfo.add(new Pair("Email", user.getEmail()));
            if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty())
                userInfo.add(new Pair("PhoneNumber", user.getPhoneNumber()));
            if (user.getProviderId() != null && !user.getProviderId().isEmpty())
                userInfo.add(new Pair("ProviderId", user.getProviderId()));
            if (user.getTenantId() != null && !user.getTenantId().isEmpty())
                userInfo.add(new Pair("TenantId", user.getTenantId()));
        }

        UserInfoAdapter adapter = new UserInfoAdapter(userInfo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewBinding.rvUserInfo.setLayoutManager(linearLayoutManager);
        mViewBinding.rvUserInfo.setAdapter(adapter);
        mViewBinding.rvUserInfo.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );
    }

    private void initControl() {
        mViewBinding.btnUpdateInfo.setOnClickListener(view -> onBackPressed());
    }
}
