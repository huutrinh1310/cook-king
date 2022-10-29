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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.databinding.FragmentUserBinding;
import fptu.prm.cookcook.model.AccountMenu;
import fptu.prm.cookcook.ui.activity.SplashActivity;
import fptu.prm.cookcook.ui.adapter.AccountMenuAdapter;

public class UserFragment extends Fragment {
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
        ArrayList<AccountMenu> accountMenuList = new ArrayList();
        accountMenuList.add(new AccountMenu(
                0,
                R.drawable.ic_notify_32,
                R.string.account_menu_notify,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                1,
                R.drawable.icon_account_information,
                R.string.account_menu_info,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                2,
                R.drawable.icon_account_setting,
                R.string.account_menu_setting,
                R.string.account_menu_desc,
                true
        ));
        accountMenuList.add(new AccountMenu(
                3,
                R.drawable.icon_account_exit,
                R.string.account_menu_exit,
                R.string.account_menu_desc,
                true
        ));

        AccountMenuAdapter adapter = new AccountMenuAdapter(requireActivity(),
                accountMenuList,
                accountMenu -> {
                    switch (accountMenu.getId()) {
                        case 0: {
                            openNotify();
                            break;
                        }
                        case 1: {
                            openInfo();
                            break;
                        }
                        case 2: {
                            openSetting();
                            break;
                        }
                        case 3: {
                            logout();
                            break;
                        }
                        default:
                            break;
                    }
                    Toast.makeText(requireActivity(), getString(accountMenu.getTitleRes()), Toast.LENGTH_SHORT).show();
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

    private void openNotify() {

    }

    private void openInfo() {

    }

    private void openSetting() {

    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireActivity(), SplashActivity.class);
        startActivity(intent);
    }
}