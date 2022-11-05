package fptu.prm.cookcook.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.Logger;

import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.ui.fragment.DetailRecipeFragment;
import fptu.prm.cookcook.utils.LoggerUtil;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context mContext;
    private List<Recipe> recipeList;
    private final LayoutInflater layoutInflater;
    private int mLayoutId;
    private MainActivity mainActivity;
    private IClickItemListener mIClickItemListener;


    public interface IClickItemListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, List<Recipe> recipeList, int layoutId, IClickItemListener iClickItemListener) {
        this.mContext = context;
        this.recipeList = recipeList;
        this.layoutInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mIClickItemListener = iClickItemListener;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setListRecipe(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(mLayoutId, parent, false);
        return new RecipeViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.setRecipeItem(recipe);
        recipe.getAccount().observe((LifecycleOwner) mContext, account -> {
            LoggerUtil.d("RecipeAdapter", "onBindViewHolder: " + account.getId());
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemListener.onItemClick(recipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }
        return 0;
    }
}
