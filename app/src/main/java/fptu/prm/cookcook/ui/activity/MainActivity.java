package fptu.prm.cookcook.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.ActivityMainBinding;
import fptu.prm.cookcook.utils.ToastUtil;
import fptu.prm.cookcook.ui.fragment.AddFragment;
import fptu.prm.cookcook.ui.fragment.HomeFragment;
import fptu.prm.cookcook.ui.fragment.SearchFragment;
import fptu.prm.cookcook.ui.fragment.UserFragment;
import fptu.prm.cookcook.utils.TranslateAnimationUtil;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mViewBinding;

    private BottomNavigationView bottomNavigation;
    private FrameLayout bodyContainer;

    private void bindingView() {
        bodyContainer = findViewById(R.id.body_container);
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void bindingAction() {
        bodyContainer.setOnTouchListener(new TranslateAnimationUtil(this, bottomNavigation));
        bottomNavigation.setOnItemSelectedListener(this::onItemNavigationClick);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onItemNavigationClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                return true;
            case R.id.nav_search:
                replaceFragment(new SearchFragment());
                return true;
            case R.id.nav_add:
                replaceFragment(new AddFragment());
                return true;
            case R.id.nav_user:
                replaceFragment(new UserFragment());
                return true;
        }
        return false;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mViewBinding.getRoot();
        setContentView(view);
        replaceFragment(new HomeFragment());
        bindingView();
        bindingAction();
    }
}