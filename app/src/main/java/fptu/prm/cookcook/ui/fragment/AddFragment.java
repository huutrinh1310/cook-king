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
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.ui.adapter.RecipeAdapter;
import fptu.prm.cookcook.utils.ToastUtil;

public class AddFragment extends Fragment {
    public final static String TAG = "AddFragment";
    private RecyclerView rcvAddScreen;
    private Button btnAddNewRecipe;
    private Button btnShareTips;
    private List<Recipe> listRecipe;
    private RecipeAdapter addFoodAdapter;

    private void bindingView(View view) {
        rcvAddScreen = view.findViewById(R.id.rcvAddScreen);
        btnAddNewRecipe = view.findViewById(R.id.btnAddNewRecipe);
        btnShareTips = view.findViewById(R.id.btnShareTips);
        listRecipe = new ArrayList<>();
    }

    private void bindingAction(View view) {
        btnAddNewRecipe.setOnClickListener(this::onBtnAddNewRecipeClick);
        btnShareTips.setOnClickListener(this::onBtnShareTipsClick);
    }

    private void onBtnShareTipsClick(View view) {
        ToastUtil.success(getContext(), "Share tips");
    }


    private void onBtnAddNewRecipeClick(View view) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.replaceFragment(new AddRecipeFragment());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingView(view);
        loadData();
        bindingAction(view);
        // initialize sub-view
        addFoodAdapter = new RecipeAdapter(getContext(), listRecipe, R.layout.item_card_add_screen, this::onItemClick);
        rcvAddScreen.setAdapter(addFoodAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvAddScreen.setLayoutManager(layoutManager);
    }

    private void onItemClick(Recipe recipe) {
        MainActivity mainActivity = (MainActivity) getActivity();
        assert mainActivity != null;
        mainActivity.goToDetailRecipe(recipe);
    }

    private void loadData() {
        LiveData<List<Recipe>> listLiveData = RecipeDaoImpl.getInstance().getRecipeByUserId(FirebaseAuth.getInstance().getUid());
        listLiveData.observe(getViewLifecycleOwner(), recipes -> {
            listRecipe = recipes;
            addFoodAdapter.setListRecipe(listRecipe);
            if (addFoodAdapter.getRecipeList().size() == 0) {
                // show no data view
                TextView txtNoData = getView().findViewById(R.id.txtNoData);
                txtNoData.setVisibility(View.VISIBLE);
            }
            addFoodAdapter.notifyDataSetChanged();
        });
    }
}