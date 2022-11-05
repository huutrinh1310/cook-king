package fptu.prm.cookcook.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.AccountDaoImpl;
import fptu.prm.cookcook.databinding.ActivityMainBinding;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.fragment.AddFragment;
import fptu.prm.cookcook.ui.fragment.DetailRecipeFragment;
import fptu.prm.cookcook.ui.fragment.HomeFragment;
import fptu.prm.cookcook.ui.fragment.ProfileFragment;
import fptu.prm.cookcook.ui.fragment.SearchFragment;
import fptu.prm.cookcook.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mViewBinding;

    private BottomNavigationView bottomNavigation;
    public static MainActivity instance;

    public static MainActivity getInstance() {
        if (instance == null) {
            instance = new MainActivity();
        }
        return instance;
    }

    private void bindingView() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
    }

    private void bindingAction() {
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

    public void goToDetailRecipe(Recipe recipe) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailRecipeFragment detailFragment = new DetailRecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);

        detailFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.body_container, detailFragment);
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

    public void goToProfile(String accountId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Account account = new Account();
        ProfileFragment profileFragment = new ProfileFragment(account);
        AccountDaoImpl.getInstance().getAccountById(accountId).observe(this, account1 -> {
            account.setId(account1.getId());
            account.setAddress(account1.getAddress());
            account.setAvatar(account1.getAvatar());
            account.setName(account1.getName());
            account.setUsername(account1.getUsername());
            account.setDescription(account1.getDescription());
            account.setAvatar(account1.getAvatar());
            account.setListOfRecipes(account1.getListOfRecipes());
            profileFragment.setAccount(account);
            fragmentTransaction.replace(R.id.body_container, profileFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}