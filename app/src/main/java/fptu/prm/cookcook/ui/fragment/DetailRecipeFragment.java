package fptu.prm.cookcook.ui.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.developer.kalert.KAlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Set;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.AccountDaoImpl;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Account;
import fptu.prm.cookcook.entities.Ingredients;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.utils.AlertDialogUtil;
import fptu.prm.cookcook.utils.LoggerUtil;
import fptu.prm.cookcook.utils.ToastUtil;

public class DetailRecipeFragment extends Fragment {
    private Recipe recipe;
    private RecyclerView rcvRecipeSameAuthor;
    private List<Recipe> listRecipeSameAuthor;
    //recipe
    private ImageView imgRecipeDetail;
    private TextView txtRecipeNameDetail;
    private ImageView imgUserDetail, imgCreater;
    private TextView txtUserNameDetail, txtCreaterName;
    private TextView txtUserLinkDetail, txtCreaterLink;
    private TextView txtUserLocationDetail, txtCreaterLocation;
    private TextView txtRecipeDescriptionDetail, txtCreaterDescription;
    private TextView txtRecipeTimeDetail;
    private TextView txtServing;
    private LinearLayout lnrIngredientDetail;
    private LinearLayout lnrStepDetail;
    private ImageView imgShowMenu;

    private Button btnBookMark, btnUpdateRecipe;
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
        // recipe creater
        imgCreater = view.findViewById(R.id.imgCreater);
        txtCreaterName = view.findViewById(R.id.txtCreaterName);
        txtCreaterLink = view.findViewById(R.id.txtCreaterLink);
        txtCreaterLocation = view.findViewById(R.id.txtCreaterLocation);
        txtCreaterDescription = view.findViewById(R.id.txtCreaterDescription);
        imgShowMenu = view.findViewById(R.id.imgShowMenu);
        //button
        btnBookMark = view.findViewById(R.id.btnBookMark);
        btnUpdateRecipe = view.findViewById(R.id.btnUpdateRecipe);
    }

    private void bindingAction(View view) {
        txtCreaterLink.setOnClickListener(this::goToCreaterProfile);
        txtUserLinkDetail.setOnClickListener(this::goToCreaterProfile);
        imgShowMenu.setOnClickListener(this::showMenu);
    }

    private void showMenu(View view) {
        registerForContextMenu(view);
        getActivity().openContextMenu(view);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.floating_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteRecipe();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteRecipe() {
        AlertDialogUtil.warning(getContext(), "Are you sure?", "You want to delete this recipe?", " Yes", new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                RecipeDaoImpl recipeDao = new RecipeDaoImpl();
                recipeDao.deleteRecipe(recipe.getId(), new RecipeCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        ToastUtil.success(getContext(), "Delete recipe successfully");
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onFail(String message) {
                        ToastUtil.error(getContext(), message);
                    }
                });
            }
        });

    }

    // go to creater profile
    private void goToCreaterProfile(View view) {
        ToastUtil.info(getContext(), "go to creater profile");
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
        isOwnerRecipe();
    }

    private void isOwnerRecipe() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        recipe.getAccount().observe(getViewLifecycleOwner(), new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account != null && account.getId().equals(user.getUid())) {
                    btnBookMark.setVisibility(View.GONE);
                    btnUpdateRecipe.setVisibility(View.VISIBLE);
                } else {
                    btnUpdateRecipe.setVisibility(View.GONE);
                    btnBookMark.setVisibility(View.VISIBLE);
                }
            }
        });
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
    private void loadData(Recipe recipe) {
        loadRecipe(recipe);
        loadAuthor(recipe.getAccountId());
    }

    private void loadAuthor(String accountId) {
        LiveData<Account> liveData = AccountDaoImpl.getInstance().getAccountById(accountId);
        liveData.observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                Glide.with(getContext())
                        .load(account.getAvatar())
                        .apply(new RequestOptions().bitmapTransform(new RoundedCorners(500)))
                        .into(imgUserDetail);
                txtUserNameDetail.setText(account.getName());
                txtUserLinkDetail.setText("@" + account.getUsername());
                txtUserLocationDetail.setText(account.getAddress());

                Glide.with(getContext())
                        .load(account.getAvatar())
                        .apply(new RequestOptions().bitmapTransform(new RoundedCorners(500)))
                        .into(imgCreater);
                txtCreaterName.setText(account.getName());
                txtCreaterLink.setText("@" + account.getUsername());
                txtCreaterLocation.setText(account.getAddress());
                txtCreaterDescription.setText(account.getDescription());
                LoggerUtil.d("account" + account.toString());
            }
        });
    }

    private void loadRecipe(Recipe recipe) {
        int i = 0;
        //recipe
        Glide.with(getContext()).load(recipe.getImage()).into(imgRecipeDetail).onLoadFailed(getContext().getDrawable(R.drawable.ic_person));
        txtRecipeNameDetail.setText(recipe.getTitle());
        txtRecipeDescriptionDetail.setText(recipe.getDescription());
        txtServing.setText(recipe.getServings());
        txtRecipeTimeDetail.setText(recipe.getMinutes());
        lnrIngredientDetail.removeAllViews();
        Set<String> keyIngres = recipe.getIngredients().keySet();
        for (String key : keyIngres) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_ingredient_detail, lnrIngredientDetail, false);
            TextView txtIngredient = view.findViewById(R.id.txtIngredient);
            Ingredients item = recipe.getIngredients().get(key);
            txtIngredient.setText(item.getAmount() + " " + item.getUnit() + " " + item.getName());
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            lnrIngredientDetail.addView(view);
        }


        lnrStepDetail.removeAllViews();
        Set<String> keySteps = recipe.getSteps().keySet();

        for (String key : keySteps) {
            i++;
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_step_detail, lnrStepDetail, false);
            TextView txtStep = view.findViewById(R.id.txtStep);
            txtStep.setText("Bước " + i);
            TextView txtStepDetail = view.findViewById(R.id.txtStepDetail);
            txtStepDetail.setText(recipe.getSteps().get(key).getDescription());
            Set<String> keyStepImages = recipe.getSteps().get(key).getImages().keySet();
            LinearLayout lnrImgStep = view.findViewById(R.id.lnrImgStepDetail);
            for (String keyImage : keyStepImages) {
                View viewImage = LayoutInflater.from(getContext()).inflate(R.layout.item_image_step_detail, lnrImgStep, false);
                ImageView imgStep = viewImage.findViewById(R.id.img_step_image);
                Glide.with(getContext()).load(recipe.getSteps().get(key).getImages().get(keyImage)).into(imgStep).onLoadFailed(getContext().getDrawable(R.drawable.ic_person));
                if (viewImage.getParent() != null) {
                    ((ViewGroup) viewImage.getParent()).removeView(viewImage);
                }
                lnrImgStep.addView(viewImage);
            }
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            lnrStepDetail.addView(view);
        }
    }
}