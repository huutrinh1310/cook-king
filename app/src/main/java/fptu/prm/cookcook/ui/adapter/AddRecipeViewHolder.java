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

public class AddRecipeViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private ImageView imgRecipe;
    private TextView txtRecipeName;
    private TextView txtRecipeDescription;

    public AddRecipeViewHolder(@NonNull View itemView, Context mContext) {
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
    }

    public void setRecipeItem(Recipe recipe) {
        Glide.with(mContext)
                .load(recipe.getImage())
                .into(imgRecipe);
        txtRecipeName.setText(recipe.getTitle());
        txtRecipeDescription.setText(recipe.getReadyInMinutes()+"");
    }
}
