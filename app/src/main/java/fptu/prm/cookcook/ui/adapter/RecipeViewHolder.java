package fptu.prm.cookcook.ui.adapter;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.storage.SharePreferenceManager;

public class RecipeViewHolder extends RecyclerView.ViewHolder{
    private Context mContext;
    private ImageView imgRecipe;
    private TextView txtRecipeName;
    private TextView txtRecipeDescription;
    private TextView ready_time;

    public RecipeViewHolder(@NonNull View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        bindingView(itemView);
        bindingAction(itemView);
    }

    private void bindingAction(View itemView) {

    }

    private void bindingView(View itemView) {
        imgRecipe = itemView.findViewById(R.id.imgRecipe);
        txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
        txtRecipeDescription = itemView.findViewById(R.id.txtRecipeDescription);
        ready_time = itemView.findViewById(R.id.ready_time);
    }

    public void setRecipeItem(Recipe recipe) {
        //set data to item
        Glide.with(mContext)
                .load(recipe.getImage())
                .into(imgRecipe);
        setTextToView(txtRecipeName, recipe.getTitle());
        setTextToView(txtRecipeDescription, String.valueOf(recipe.getReadyInMinutes()));
        setTextToView(ready_time, recipe.getReadyInMinutes() + " minutes");
    }
//    check view not null
    public void setTextToView( View view, String value) {
        if(view != null) {
            ((TextView) view).setText(value);
        }
    }
}
