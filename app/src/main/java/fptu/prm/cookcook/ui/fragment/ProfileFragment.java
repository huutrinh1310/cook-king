package fptu.prm.cookcook.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.ui.adapter.RecipeAdapter;

public class ProfileFragment extends Fragment {
    private Account account;
    private CircleImageView imgAvatarProfile;
    private TextView txtNameProfile;
    private TextView txtUsernameProfile;
    private TextView txtDescriptionProfile;
    private TextView txtAddressProfile;
    private RecyclerView rcvProfile;
    private List<Recipe> recipeList;
    private RecipeAdapter adapter;


    public ProfileFragment(Account account) {
        this.account = account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    private void bindingView(View v) {
        imgAvatarProfile = v.findViewById(R.id.imgAvatarProfile);
        txtNameProfile = v.findViewById(R.id.txtNameProfile);
        txtUsernameProfile = v.findViewById(R.id.txtUsernameProfile);
        txtDescriptionProfile = v.findViewById(R.id.txtDescriptionProfile);
        txtAddressProfile = v.findViewById(R.id.txtAddressProfile);
        rcvProfile = v.findViewById(R.id.rcvProfile);
        recipeList = new ArrayList<>();
    }

    private void bindingAction(View v) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        setDataToView();
        bindingAction(view);
        recyclerViewSameRecipes();
    }

    private void setDataToView() {
        Glide.with(requireContext()).load(account.getAvatar()).into(imgAvatarProfile);
        txtNameProfile.setText(account.getName());
        txtUsernameProfile.setText("@" + account.getUsername());
        txtDescriptionProfile.setText(account.getDescription());
        txtAddressProfile.setText(account.getAddress());
    }

    private void recyclerViewSameRecipes() {
        loadRecipes();
        adapter = new RecipeAdapter(getContext(), recipeList, R.layout.item_popular, this::onItemClick);
        rcvProfile.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvProfile.setLayoutManager(linearLayoutManager1);
    }

    private void loadRecipes() {
        LiveData<List<Recipe>> liveData = RecipeDaoImpl.getInstance().getRecipeByUserId(account.getId());
        liveData.observe(getViewLifecycleOwner(), recipes -> {
            recipeList.clear();
            recipeList.addAll(recipes);
            adapter.notifyDataSetChanged();
        });
    }

    private void onItemClick(Recipe recipe) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.goToDetailRecipe(recipe);
    }
}