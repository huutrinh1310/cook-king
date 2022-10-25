package fptu.prm.cookcook.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import fptu.prm.cookcook.R;

public class AddRecipeFragment extends Fragment {
    public final static String TAG = "AddRecipeFragment";
    static int i = 2;

    private ImageView btnCardAddClose;
    private Button btnUpStream;
    private LinearLayout lnrIngredient;
    private LinearLayout lnrStep;
    LinearLayout.LayoutParams layoutParams;
    private Button btnAddIngredient;
    private Button btnAddMorePortion;
    private Button btnAddMoreStep;

    // binding view
    private ImageView imgAddScreen;


    private void bindingView(View view) {
        bindingViewAddRecipe(view);
        btnCardAddClose = view.findViewById(R.id.btnCardAddClose);
        btnUpStream = view.findViewById(R.id.btnUpStream);
        lnrIngredient = view.findViewById(R.id.lnrIngredient);
        lnrStep = view.findViewById(R.id.lnrAddRecipeStep);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        btnAddMorePortion = view.findViewById(R.id.btnAddMorePortion);
        btnAddMoreStep = view.findViewById(R.id.btnAddMoreStep);
    }

    private void bindingViewAddRecipe(View view) {
        imgAddScreen = view.findViewById(R.id.imgAddScreen);
    }

    private void bindingAction(View view) {
        btnCardAddClose.setOnClickListener(this::onBtnCardAddCloseClick);
        btnAddIngredient.setOnClickListener(this::onBtnAddIngredientClick);
        btnAddMorePortion.setOnClickListener(this::onBtnAddMorePortionClick);
        btnAddMoreStep.setOnClickListener(this::onBtnAddMoreStepClick);
    }

    private void onBtnAddMoreStepClick(View view) {
        View viewStep = LayoutInflater.from(getContext()).inflate(R.layout.step_item, null);
        viewStep.setLayoutParams(layoutParams);
        lnrStep.addView(viewStep);
    }

    private void onBtnAddMorePortionClick(View view) {
        View portion = LayoutInflater.from(getContext()).inflate(R.layout.portion_item, null, false);

        EditText edtAddRecipePortion = (EditText) portion.findViewById(R.id.edtAddRecipePortion);
        ImageView imgMorePortion = (ImageView) portion.findViewById(R.id.imgMorePortion);

        lnrIngredient.addView(portion, layoutParams);
    }

    private void onBtnAddIngredientClick(View view) {
        View ingredient = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_item, null, false);
        EditText edtAddRecipeIngredient = (EditText) ingredient.findViewById(R.id.edtAddRecipeIngredient);
        ImageView imgMoreIngredient = (ImageView) ingredient.findViewById(R.id.imgMoreIngredient);
        lnrIngredient.addView(ingredient, layoutParams);
    }

    private void onBtnCardAddCloseClick(View view) {
        getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindingAction(view);
    }
}