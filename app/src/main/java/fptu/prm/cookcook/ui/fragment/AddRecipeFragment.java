package fptu.prm.cookcook.ui.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.entities.Ingredients;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.entities.Step;
import fptu.prm.cookcook.utils.SeperateUtil;
import fptu.prm.cookcook.utils.ToastUtil;

public class AddRecipeFragment extends Fragment {
    public final static String TAG = "AddRecipeFragment";
    static int i = 2;
    int REQUEST_CODE_CAMERA = 1;

    private ImageView btnCardAddClose;
    private Button btnUpStream;
    private LinearLayout lnrIngredient;
    private LinearLayout lnrAddRecipeStep;
    LinearLayout.LayoutParams layoutParams;
    private Button btnAddIngredient;
    private Button btnAddMorePortion;
    private Button btnAddMoreStep;
    // data recipe
    private TextInputEditText edtAddRecipeTitle;
    private EditText edtAddRecipeDescription, edtAddRecipePortion, edtAddRecipeTime, edtAddRecipeIngredient1;
    private ImageView imgMoreIngredient1, imgMoreIngredient2;
    private EditText edtAddRecipeIngredient2, edtStep;
    // binding view
    private ImageView imgAddScreen;

    private void bindingView(View view) {
        bindingViewAddRecipe(view);
        btnCardAddClose = view.findViewById(R.id.btnCardAddClose);
        btnUpStream = view.findViewById(R.id.btnUpStream);

        lnrIngredient = view.findViewById(R.id.lnrIngredient);
        lnrAddRecipeStep = view.findViewById(R.id.lnrAddRecipeStep);

        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        btnAddMorePortion = view.findViewById(R.id.btnAddMorePortion);
        btnAddMoreStep = view.findViewById(R.id.btnAddMoreStep);
    }

    private void bindingViewAddRecipe(View view) {
        imgAddScreen = view.findViewById(R.id.imgAddScreen);
        edtAddRecipeTitle = view.findViewById(R.id.edtAddRecipeTitle);
        edtAddRecipeDescription = view.findViewById(R.id.edtAddRecipeDescription);
        edtAddRecipePortion = view.findViewById(R.id.edtAddRecipePortion);
        edtAddRecipeTime = view.findViewById(R.id.edtAddRecipeTime);
        edtAddRecipeIngredient1 = view.findViewById(R.id.edtAddRecipeIngredient1);
        imgMoreIngredient1 = view.findViewById(R.id.imgMoreIngredient1);
        imgMoreIngredient2 = view.findViewById(R.id.imgMoreIngredient2);
        edtAddRecipeIngredient2 = view.findViewById(R.id.edtAddRecipeIngredient2);
        edtStep = view.findViewById(R.id.edtStep);
    }

    private void bindingAction(View view) {
        btnCardAddClose.setOnClickListener(this::onBtnCardAddCloseClick);
        btnAddIngredient.setOnClickListener(this::onBtnAddIngredientClick);
        btnAddMorePortion.setOnClickListener(this::onBtnAddMorePortionClick);
        btnAddMoreStep.setOnClickListener(this::onBtnAddMoreStepClick);
        btnUpStream.setOnClickListener(this::upStream);
        imgAddScreen.setOnClickListener(this::addImage);
    }

    private void addImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);

    }

    private void upStream(View view) {
        Recipe recipe = new Recipe();
        Map<String, Ingredients> ingredientsMap = getAllIngredients();
        Map<String, Step> stepMap = getAllSteps();
        recipe.setTitle(edtAddRecipeTitle.getText().toString());
        recipe.setDescription(edtAddRecipeDescription.getText().toString());
        recipe.setServings(SeperateUtil.getNumber(edtAddRecipePortion.getText().toString()));
        recipe.setReadyInMinutes(SeperateUtil.getNumber(edtAddRecipeTime.getText().toString()));
        recipe.setIngredients(ingredientsMap);
        recipe.setSteps(stepMap);
        // TODO: add user id preferences to save recipe
    }

    private Map<String, Step> getAllSteps() {
        Map<String, Step> stepMap = new HashMap<>();
        if (edtStep.getText().toString().isEmpty()) {
            ToastUtil.error(getContext(), "Please enter step");
            return null;
        }
        for (int i = 0; i < lnrAddRecipeStep.getChildCount(); i++) {
            if (lnrAddRecipeStep.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) lnrAddRecipeStep.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof ImageView) {
                        ImageView imageView = (ImageView) linearLayout.getChildAt(j);

                    }
                    if (linearLayout.getChildAt(j) instanceof LinearLayout) {
                        LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(j);
                        for (int k = 0; k < linearLayout1.getChildCount(); k++) {
                            Step step = new Step();
                            if (linearLayout1.getChildAt(k) instanceof EditText) {
                                EditText editText = (EditText) linearLayout1.getChildAt(k);
                                step.setId(1);
                                step.setDescription(editText.getText().toString());
                            }
                            if (linearLayout1.getChildAt(k) instanceof ImageView) {
                                ImageView imageView = (ImageView) linearLayout1.getChildAt(k);
                                imageView.setOnClickListener(this::addImage);
                                Map<String, String> map = new HashMap<>();
                                map.put("1", imageView.toString());
                                step.setImages(map);
                            }
                            stepMap.put(k + "", step);
                        }
                    }
                }
            }
        }
        return stepMap;
    }

    private Map<String, Ingredients> getAllIngredients() {
        Map<String, Ingredients> ingredientsMap = new HashMap<>();

        for (int i = 0; i < lnrIngredient.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) lnrIngredient.getChildAt(0);
            if (linearLayout.getChildAt(i) instanceof EditText) {
                Ingredients ingredients = new Ingredients();
                EditText editText = (EditText) linearLayout.getChildAt(i);
                ingredients.setAmount(SeperateUtil.getNumber(editText.getText().toString()));
                ingredients.setUnit(SeperateUtil.getUnit(editText.getText().toString()));
                ingredients.setName(SeperateUtil.getName(editText.getText().toString()));
                ingredients.setId(i);
                ingredientsMap.put(String.valueOf(i), ingredients);
            }
        }
        System.out.println(ingredientsMap);
        return ingredientsMap;
    }

    private void onBtnAddMoreStepClick(View view) {
        View viewStep = LayoutInflater.from(getContext()).inflate(R.layout.step_item, null);
        viewStep.setLayoutParams(layoutParams);
        lnrAddRecipeStep.addView(viewStep);
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
        initLayout(view);
        bindingAction(view);
    }

    private void initLayout(View view) {
        onBtnAddMoreStepClick(view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAddScreen.setImageBitmap(bitmap);
        }
    }
}