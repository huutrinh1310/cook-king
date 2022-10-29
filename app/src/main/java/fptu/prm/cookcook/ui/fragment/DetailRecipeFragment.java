package fptu.prm.cookcook.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Set;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Recipe;

public class DetailRecipeFragment extends Fragment {
    private Recipe recipe;
    private RecyclerView rcvRecipeSameAuthor;
    private List<Recipe> listRecipeSameAuthor;
    //recipe
    private ImageView imgRecipeDetail;
    private TextView txtRecipeNameDetail;
    private ImageView imgUserDetail;
    private TextView txtUserNameDetail;
    private TextView txtUserLinkDetail;
    private TextView txtUserLocationDetail;
    private TextView txtRecipeDescriptionDetail;
    private TextView txtRecipeTimeDetail;
    private TextView txtServing;
    private LinearLayout lnrIngredientDetail;
    private LinearLayout lnrStepDetail;

    public DetailRecipeFragment() {
    }

    private void bindingView(View view) {
        rcvRecipeSameAuthor = view.findViewById(R.id.rcvRecipeSameAuthor);

        //recipe
        imgRecipeDetail = view.findViewById(R.id.imgRecipeDetail);
        txtRecipeNameDetail = view.findViewById(R.id.txtRecipeNameDetail);
        imgUserDetail = view.findViewById(R.id.imgUserDetail);
        txtUserNameDetail = view.findViewById(R.id.txtUserNameDetail);
        txtUserLinkDetail = view.findViewById(R.id.txtUserLinkDetail);
        txtUserLocationDetail = view.findViewById(R.id.txtUserLocationDetail);
        txtRecipeDescriptionDetail = view.findViewById(R.id.txtRecipeDescriptionDetail);
        txtRecipeTimeDetail = view.findViewById(R.id.txtRecipeTimeDetail);
        txtServing = view.findViewById(R.id.txtServing);
        lnrIngredientDetail = view.findViewById(R.id.lnrIngredientDetail);
        lnrStepDetail = view.findViewById(R.id.lnrStepDetail);
    }

    private void bindingAction(View view) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction(view);
        receiveData();
    }

    // TODO: nhận dữ liệu từ fragment trước

    /***
     * Nhận dữ liệu từ fragment trước
     * author:
     * cần nhận listRecipeSameAuthor
     * nhận thêm recipe
     */
    private void receiveData() {
        Bundle bundleReceive = getArguments();
        if (bundleReceive != null) {
            recipe = (Recipe) bundleReceive.getSerializable("recipe");
            if (recipe != null) {
                loadData(recipe);
            }
        }
    }

    /***
     * Load dữ liệu lên giao diện
     * ingredient of recipe
     */
    private void loadData(Recipe recipe) {;
        //recipe
        Glide.with(getContext()).load(recipe.getImage()).into(imgRecipeDetail);
        txtRecipeNameDetail.setText(recipe.getTitle());
        Glide.with(getContext()).load(recipe.getAccount().getAvatar()).into(imgUserDetail);
        txtUserNameDetail.setText(recipe.getAccount().getName());
        txtUserLinkDetail.setText(recipe.getAccount().getId());
        txtUserLocationDetail.setText(recipe.getAccount().getAddress());
        txtRecipeDescriptionDetail.setText(recipe.getDescription());
        txtServing.setText(recipe.getServings());
//        txtRecipeTimeDetail.setText(recipe.getReadyInMinutes());
        lnrIngredientDetail.removeAllViews();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient_detail, lnrIngredientDetail, false);
            TextView txtIngredient = view.findViewById(R.id.txtIngredient);
            txtIngredient.setText((CharSequence) recipe.getIngredients().get(i));
            lnrIngredientDetail.addView(view);
        }
        lnrStepDetail.removeAllViews();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_step_detail, lnrStepDetail, false);
            Set<String> keySet = recipe.getSteps().keySet();
            for (String key : keySet) {
                TextView txtStep = view.findViewById(R.id.txtStep);
                txtStep.setText("Bước " + recipe.getSteps().get(key).getId());
                TextView txtStepDetail = view.findViewById(R.id.txtStepDetail);
                txtStepDetail.setText(recipe.getSteps().get(key).getDescription());
                ImageView imgStep = view.findViewById(R.id.imgStep);
                Glide.with(getContext()).load(recipe.getSteps().get(key).getImages()).into(imgStep);
            }
            if(view.getParent() != null) {
                ((ViewGroup)view.getParent()).removeView(view);
            }
            lnrStepDetail.addView(view);
        }

    }
}