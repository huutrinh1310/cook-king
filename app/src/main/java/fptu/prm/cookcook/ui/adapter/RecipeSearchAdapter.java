package fptu.prm.cookcook.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Recipe;

public class RecipeSearchAdapter extends RecyclerView.Adapter<RecipeSearchAdapter.RecipeSearchViewHolder> implements Filterable {
    private List<Recipe> mListRecipes;
    private List<Recipe> mListRecipesOld;

    public RecipeSearchAdapter(List<Recipe> mListRecipes) {
        this.mListRecipes = mListRecipes;
        this.mListRecipesOld = mListRecipes;
    }

    @NonNull
    @Override
    public RecipeSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_search,parent,false);
        return new RecipeSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSearchViewHolder holder, int position) {
        Recipe recipe = mListRecipes.get(position);
        if(recipe == null) {
            return;
        }
//       holder.searchView.setText(recipe.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mListRecipes!= null) {
            return mListRecipes.size();
        }
        return 0;
    }



    public class RecipeSearchViewHolder extends RecyclerView.ViewHolder{
        private SearchView searchView;
        public RecipeSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            searchView = itemView.findViewById(R.id.searchView);

        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    mListRecipes = mListRecipesOld;
                } else {
                    List<Recipe> list = new ArrayList<>();
                    for (Recipe recipe : mListRecipesOld ){
                        if(recipe.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(recipe);
                        }
                    }
                    mListRecipes = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListRecipes;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListRecipes = (List<Recipe>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
