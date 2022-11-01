package fptu.prm.cookcook.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Pair;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.ActivityUserInfoBinding;
import fptu.prm.cookcook.ui.adapter.UserInfoAdapter;

@SuppressWarnings({"rawtypes", "unchecked", "ConstantConditions"})
public class UserInfoActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 914;
    private ActivityUserInfoBinding mViewBinding;
    private FirebaseAuth mAuth;
    private final ActivityResultLauncher<Intent> mActivityResultLauncher
            = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent == null) return;
                    Uri uri = intent.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        setBitmapAvatar(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

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
        mViewBinding.avatar.setOnClickListener(view -> changeAvatar());
    }

    private void changeAvatar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/").setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Choose Avatar"));
    }

    private void setBitmapAvatar(Bitmap bitmapAvatar){
        mViewBinding.avatar.setImageBitmap(bitmapAvatar);
    }
}
