package fptu.prm.cookcook.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.FragmentUserBinding;
import fptu.prm.cookcook.model.AccountMenu;
import fptu.prm.cookcook.ui.activity.SplashActivity;
import fptu.prm.cookcook.ui.activity.UserInfoActivity;
import fptu.prm.cookcook.ui.adapter.AccountMenuAdapter;

public class UserFragment extends Fragment {
    private static final int NOTIFY = 0;
    private static final int SAVED_FOOD = 1;
    private static final int SELF_FOOD = 2;
    private static final int INFORMATION = 3;
    private static final int SETTING = 4;
    private static final int EXIT = 5;
    private FragmentUserBinding mViewBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewBinding = FragmentUserBinding.inflate(inflater, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUserInfo();
        initMenu();
    }

    private void initUserInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;
        mViewBinding.tvUsername.setText(user.getDisplayName());
        mViewBinding.tvEmail.setText(user.getEmail());
        Glide.with(getContext())
                .load(user.getPhotoUrl())
                .error(R.drawable.ic_person)
                .into(mViewBinding.avatar);
    }

    private void initMenu() {
        ArrayList<AccountMenu> accountMenuList = new ArrayList();
        accountMenuList.add(new AccountMenu(
                NOTIFY,
                R.drawable.ic_notify_32,
                R.string.account_menu_notify,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                SAVED_FOOD,
                R.drawable.icon_book_heart,
                R.string.account_menu_saved_food,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                SELF_FOOD,
                R.drawable.icon_noodles,
                R.string.account_menu_self_food,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                INFORMATION,
                R.drawable.icon_account_information,
                R.string.account_menu_info,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                SETTING,
                R.drawable.icon_account_setting,
                R.string.account_menu_setting,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                EXIT,
                R.drawable.icon_account_exit,
                R.string.account_menu_exit,
                R.string.account_menu_desc,
                true
        ));

        AccountMenuAdapter adapter = new AccountMenuAdapter(
                accountMenuList,
                accountMenu -> {
                    switch (accountMenu.getId()) {
                        case NOTIFY: {
                            openNotify(accountMenu);
                            break;
                        }
                        case SAVED_FOOD: {
                            openSavedFood(accountMenu);
                            break;
                        }
                        case SELF_FOOD: {
                            openSelfFood(accountMenu);
                            break;
                        }
                        case INFORMATION: {
                            openUserInfo();
                            break;
                        }
                        case SETTING: {
                            openSetting(accountMenu);
                            break;
                        }
                        case EXIT: {
                            logout();
                            break;
                        }
                        default:
                            break;
                    }
                });
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
        );
        mViewBinding.rvMenuList.setLayoutManager(verticalLayoutManager);
        mViewBinding.rvMenuList.setAdapter(adapter);
        mViewBinding.rvMenuList.addItemDecoration(
                new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL)
        );
    }

    private void openNotify(AccountMenu accountMenu) {
        Toast.makeText(requireActivity(), getString(accountMenu.getTitleRes()), Toast.LENGTH_SHORT).show();
    }

    private void openSavedFood(AccountMenu accountMenu) {
        Toast.makeText(requireActivity(), getString(accountMenu.getTitleRes()), Toast.LENGTH_SHORT).show();
    }

    private void openSelfFood(AccountMenu accountMenu) {
        Toast.makeText(requireActivity(), getString(accountMenu.getTitleRes()), Toast.LENGTH_SHORT).show();
    }

    private void openUserInfo() {
        Intent intent = new Intent(requireActivity(), UserInfoActivity.class);
        startActivity(intent);
    }

    private void openSetting(AccountMenu accountMenu) {
        Toast.makeText(requireActivity(), getString(accountMenu.getTitleRes()), Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireActivity(), SplashActivity.class);
        startActivity(intent);
    }
}