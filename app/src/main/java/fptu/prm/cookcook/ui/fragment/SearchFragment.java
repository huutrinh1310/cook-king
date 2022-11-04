package fptu.prm.cookcook.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.ui.adapter.RecipeAdapter;
import fptu.prm.cookcook.ui.adapter.RecipeSearchAdapter;
import fptu.prm.cookcook.utils.ToastUtil;

public class SearchFragment extends Fragment {
    public final static String TAG = "SearchFragment";
    private RecyclerView rcvTodayPopularSearch;
    private RecyclerView rcvRecentSearch;

    private List<Recipe> listRecipe;
    private RecipeAdapter searchFoodAdapter;
    private RecipeSearchAdapter recipeSearchAdapter;

    private SearchView searchView;
    private ListView myListView;
    private ArrayAdapter<Recipe> adapter;
    private Recipe recipe;


    private void bindingView(View view) {
        rcvTodayPopularSearch = view.findViewById(R.id.rcvTodayPopularSearch);
        rcvRecentSearch = view.findViewById(R.id.rcvRecentSearch);
        searchView =  view.findViewById(R.id.searchView);
        myListView = view.findViewById(R.id.listView);

        listRecipe = new ArrayList<>();
    }
    private void bindingAction(View view) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equalsIgnoreCase("") ){
                    myListView.setVisibility(View.GONE);
                    return true;
                }

                myListView.setVisibility(View.VISIBLE);
                adapter.getFilter().filter(s);
                return false;

            }
        });
        myListView.setOnItemClickListener((adapterView, view1, i, l) -> {
            recipe = adapter.getItem(i);
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.goToDetailRecipe(recipe);
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindingView(view);
        loadData();
        myListView.setVisibility(View.GONE);

        adapter = new ArrayAdapter<>(getContext(), R.layout.item_list, getListSearchRecipes());
        myListView.setAdapter(adapter);
        bindingAction(view);

        // initialize sub-view
        searchFoodAdapter = new RecipeAdapter(getContext(), listRecipe, R.layout.item_card_add_screen, this::onItemClick);
        rcvRecentSearch.setAdapter(searchFoodAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rcvRecentSearch.setLayoutManager(layoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvTodayPopularSearch.setLayoutManager(linearLayoutManager);
        recipeSearchAdapter =  new RecipeSearchAdapter(getListSearchRecipes());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        rcvTodayPopularSearch.addItemDecoration(itemDecoration);

    }


    private List<Recipe> getListSearchRecipes() {
        LiveData<List<Recipe>> listLiveData = RecipeDaoImpl.getInstance().getAllRecipe();
        listLiveData.observe(getViewLifecycleOwner(), recipes -> {
            listRecipe = recipes;
            adapter.addAll(listRecipe);
            adapter.setNotifyOnChange(true);
            searchFoodAdapter.setListRecipe(listRecipe);
            recipeSearchAdapter.notifyDataSetChanged();
        });
        return listRecipe;

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
            searchFoodAdapter.setListRecipe(listRecipe);
            searchFoodAdapter.notifyDataSetChanged();
        });
    }
}