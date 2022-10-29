package fptu.prm.cookcook.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import fptu.prm.cookcook.databinding.ActivityUserInfoBinding;

public class UserInfoActivity extends AppCompatActivity {
    private ActivityUserInfoBinding mViewBinding;
    private GoogleSignInClient mClient;
    private GoogleSignInOptions mOptions;
    private FirebaseAuth mAuth;
    private final int REQUEST_CODE_SIGN_IN = 200;
    private final String TAG = SignInActivity.class.getName();

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

    }

    private void initControl() {

    }
}
