package fptu.prm.cookcook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.ActivityMainBinding;
import fptu.prm.cookcook.utils.ToastUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);

        mViewBinding.btnSignOut.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        });

    }
}