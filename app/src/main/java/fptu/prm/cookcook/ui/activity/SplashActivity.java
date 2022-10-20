package fptu.prm.cookcook.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.service.FirebaseService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // put data to firebase
        FirebaseDatabase database = FirebaseService.getInstance();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Account acc = new Account("2", "huutrinh", "huutrinh1310@gmail.com", "123456", "https://firebasestorage.googleapis.com/v0/b/cookcook-1f1f9.appspot.com/o/avatars%2Fdefault_avatar.png?alt=media&token=8b8b2b1a-8f9a-4b8f-8b1a-8f9a4b8f8b1a", "Bac Ninh", "08932456781", list);

        Map<String, Object> accMap = new HashMap<>();


        accMap.put(acc.getUserId(), acc);

        FirebaseService.setKeyValue("accounts", accMap);

        Handler handler = new Handler();
        handler.postDelayed(() -> checkAuth(), 2000);
    }

    private void checkAuth() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent;
        if (user == null) {
            intent = new Intent(this, SignInActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
