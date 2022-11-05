package fptu.prm.cookcook.ui.fragment;

import static android.app.Activity.RESULT_OK;

import static com.google.common.io.Files.getFileExtension;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import fptu.prm.cookcook.R;
import fptu.prm.cookcook.dao.Impl.RecipeDaoImpl;
import fptu.prm.cookcook.dao.callback.RecipeCallback;
import fptu.prm.cookcook.entities.Ingredients;
import fptu.prm.cookcook.entities.Recipe;
import fptu.prm.cookcook.entities.Step;
import fptu.prm.cookcook.service.FirebaseStorageService;
import fptu.prm.cookcook.ui.activity.MainActivity;
import fptu.prm.cookcook.utils.LoggerUtil;
import fptu.prm.cookcook.utils.SeperateUtil;
import fptu.prm.cookcook.utils.ToastUtil;

public class AddRecipeFragment extends Fragment {
    public final static String TAG = "AddRecipeFragment";
    static int i = 2;
    int Read_Permission = 1;
    private static final int PICK_IMAGE = 1;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
    private EditText edtAddRecipeDescription, edtAddRecipePortion, edtAddRecipeTime;
    private EditText edtStep;
    // binding view
    private ImageView imgAddScreen, imgAddStep;

    private ActivityResultLauncher<Intent> mTitleLauncher;
    private ActivityResultLauncher<Intent> mStepLauncher;
    private StorageReference reference;
    private List<Uri> urlImage;

    private int countRecipeStep = 2;

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

        mTitleLauncher = mActiviy(imgAddScreen);
        mStepLauncher = mActiviy(imgAddStep);
        reference = FirebaseStorageService.getInstance().getStorageReference();
        urlImage = new ArrayList<>();
    }

    private ActivityResultLauncher<Intent> mActiviy(View layout) {
        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            if (result.getData().getClipData() != null) {
                                int countOfImage = result.getData().getClipData().getItemCount();
                                // pick one image
                                if (countOfImage == 1) {
                                    try {
                                        Bitmap bitmaps = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getClipData().getItemAt(0).getUri());
                                        ((ImageView) layout).setImageBitmap(bitmaps);
                                        urlImage.add(result.getData().getData());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                try {
                                    Bitmap bitmaps = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), result.getData().getData());
                                    ((ImageView) layout).setImageBitmap(bitmaps);
                                    urlImage.add(result.getData().getData());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            ToastUtil.error(getContext(), "You haven't picked Image");
                        }
                    }
                }
        );
    }

    private void bindingViewAddRecipe(View view) {
        imgAddScreen = view.findViewById(R.id.imgAddScreen);
        edtAddRecipeTitle = view.findViewById(R.id.edtAddRecipeTitle);
        edtAddRecipeDescription = view.findViewById(R.id.edtAddRecipeDescription);
        edtAddRecipePortion = view.findViewById(R.id.edtAddRecipePortion);
        edtAddRecipeTime = view.findViewById(R.id.edtAddRecipeTime);
//        edtStep = view.findViewById(R.id.edtStep);
//        imgAddStep = view.findViewById(R.id.imgAddStep);
    }

    private void bindingAction(View view) {
        btnCardAddClose.setOnClickListener(this::onBtnCardAddCloseClick);
        btnAddIngredient.setOnClickListener(this::onBtnAddIngredientClick);
        btnAddMorePortion.setOnClickListener(this::onBtnAddMorePortionClick);
        btnAddMoreStep.setOnClickListener(v -> {
            lnrAddRecipeStep.getChildAt(countRecipeStep).setVisibility(View.VISIBLE);
            if (countRecipeStep < 4) {
                countRecipeStep++;
            } else btnAddMoreStep.setVisibility(View.GONE);
        });
        btnUpStream.setOnClickListener(this::upStream);

        imgAddScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage(v, mTitleLauncher);
            }
        });
//        imgAddStep.setOnClickListener(this::addImageStep);
    }

    private void addImage(View view, ActivityResultLauncher<Intent> mActivityLauncher) {
        requestPermission(mActivityLauncher);
    }

    private void requestPermission(ActivityResultLauncher<Intent> mActivityLauncher) {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CAMERA}, Read_Permission);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        }
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }

    private void upStream(View view) {
        Recipe recipe = new Recipe();
        if (validateAddRecipe()) {
            Map<String, Ingredients> ingredientsMap = getAllIngredients();
            Map<String, Step> stepMap = getAllSteps();
            recipe.setAccountId(user.getUid());
            recipe.setTitle(edtAddRecipeTitle.getText().toString());
            recipe.setDescription(edtAddRecipeDescription.getText().toString());
            recipe.setServings(edtAddRecipePortion.getText().toString());
            recipe.setReadyInMinutes(SeperateUtil.getNumber(edtAddRecipeTime.getText().toString()));
            recipe.setIngredients(ingredientsMap);
            recipe.setSteps(stepMap);
            // put to firebase

            String itemKey = RecipeDaoImpl.getInstance().addRecipe(recipe, new RecipeCallback() {
                @Override
                public void onSuccess(Object... object) {
                    ToastUtil.success(getContext(), "Add recipe success");
                }

                @Override
                public void onFail(String message) {
                    ToastUtil.error(getContext(), "Add recipe fail");
                }
            });
            if (urlImage != null) {
                uploadImageToFirebase(urlImage, itemKey);
            }

        } else {
            ToastUtil.error(getContext(), "Please fill all information");
        }
    }

    private void uploadImageToFirebase(List<Uri> urlImage, String itemKey) {
        LoggerUtil.d("urlImage", urlImage.toString());
        upLoadSingleImg(urlImage.get(0), itemKey, "image");
        for (int i = 1, size = urlImage.size(); i < size; i++) {
            upLoadMultiImg(urlImage.get(i), itemKey, "steps", user.getUid() + "_" + (i - 1), "images");
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.replaceFragment(new AddFragment());
    }

    private void upLoadMultiImg(Uri uri, String... path) {
        Map<String, String> map = new HashMap<>();
        String randomKey = UUID.randomUUID().toString();
        StorageReference imageRef = reference.child("food/" + randomKey);
        imageRef.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                map.put(path[2], uri1.toString());
                RecipeDaoImpl.getInstance().updateRecipe(path[0], path[1], path[2], path[3], map);
            });
        });
    }

    private void upLoadSingleImg(Uri uri, String... child) {
        String randomKey = UUID.randomUUID().toString();
        StorageReference imageRef = reference.child("food/" + randomKey);
        imageRef.putFile(uri).addOnSuccessListener(taskSnapshot ->
                imageRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    RecipeDaoImpl.getInstance().updateRecipe(child[0], child[1], uri1.toString());
                })).addOnFailureListener(e -> ToastUtil.error(getContext(), "Upload image fail"));
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(mUri));
    }

    // get all ingredients
    private Map<String, Step> getAllSteps() {
        Map<String, Step> stepMap = new HashMap<>();
        Map<String, String> mapImages = new HashMap<>();
        for (int i = 0; i < lnrAddRecipeStep.getChildCount(); i++) {
            if (lnrAddRecipeStep.getChildAt(i) instanceof LinearLayout) {
                Step step = new Step();
                LinearLayout linearLayout = (LinearLayout) lnrAddRecipeStep.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof LinearLayout) {
                        LinearLayout linearLayout1 = (LinearLayout) linearLayout.getChildAt(j);
                        for (int k = 0; k < linearLayout1.getChildCount(); k++) {
                            if (linearLayout1.getChildAt(k) instanceof EditText) {
                                EditText editText = (EditText) linearLayout1.getChildAt(k);
                                step.setId(i + 1);
                                step.setDescription(editText.getText().toString());
                                stepMap.put(user.getUid() + "_" + i, step);
                            }
                        }
                    }
                    if (linearLayout.getChildAt(j) instanceof ImageView) {
                        ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                        mapImages.put(user.getUid() + "_" + (j + 1), imageView.toString());
                    }
                }
                step.setImages(mapImages);
            }
            mapImages.clear();
        }
        LoggerUtil.d("stepMap", stepMap.toString());
        return stepMap;
    }

    // get all ingredients from view
    private Map<String, Ingredients> getAllIngredients() {
        Map<String, Ingredients> ingredientsMap = new HashMap<>();

        for (int i = 0; i < lnrIngredient.getChildCount(); i++) {
            if (lnrIngredient.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) lnrIngredient.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof EditText) {
                        EditText editText = (EditText) linearLayout.getChildAt(j);
                        Ingredients ingredients = new Ingredients();
                        ingredients.setId(i);
                        ingredients.setName(SeperateUtil.getName(editText.getText().toString()));
                        ingredients.setUnit(SeperateUtil.getUnit(editText.getText().toString()));
                        ingredients.setAmount(SeperateUtil.getNumber(editText.getText().toString()));
                        ingredientsMap.put(user.getUid() + "_" + i + "", ingredients);
                    }
                }
            }
        }
        LoggerUtil.d("ingredient: " + ingredientsMap.toString());
        return ingredientsMap;
    }

    // on click add more step
    private void onBtnAddMoreStepClick(View view) {
        View viewStep = LayoutInflater.from(getContext()).inflate(R.layout.step_item, null);
        viewStep.setLayoutParams(layoutParams);
        ImageView imgAddStep = viewStep.findViewById(R.id.imgAddStep);
        ActivityResultLauncher<Intent> mStepLauncher = mActiviy(imgAddStep);
        imgAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addImage(v, mStepLauncher);
            }
        });
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


    private void initLayout(View view) {
        onBtnAddMoreStepClick(view);
        onBtnAddMoreStepClick(view);
        onBtnAddMoreStepClick(view);
        onBtnAddMoreStepClick(view);
        onBtnAddMoreStepClick(view);
        lnrAddRecipeStep.getChildAt(2).setVisibility(View.GONE);
        lnrAddRecipeStep.getChildAt(3).setVisibility(View.GONE);
        lnrAddRecipeStep.getChildAt(4).setVisibility(View.GONE);
    }

    private boolean validateAddRecipe() {
        boolean isValid = true;
        if (edtAddRecipeTitle.getText().toString().isEmpty()) {
            ToastUtil.error(getContext(), "Please enter title");
            isValid = false;
        }
        if (edtAddRecipeDescription.getText().toString().isEmpty()) {
            ToastUtil.error(getContext(), "Please enter description");
            isValid = false;
        }
        if (edtAddRecipePortion.getText().toString().isEmpty()) {
            ToastUtil.error(getContext(), "Please enter portion");
            isValid = false;
        }
        if (edtAddRecipeTime.getText().toString().isEmpty()) {
            ToastUtil.error(getContext(), "Please enter time");
            isValid = false;
        }
        // check ingredients is empty or not
        for (int i = 0; i < lnrIngredient.getChildCount(); i++) {
            if (lnrIngredient.getChildAt(i) instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) lnrIngredient.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof EditText) {
                        EditText editText = (EditText) linearLayout.getChildAt(j);
                        if (editText.getText().toString().isEmpty()) {
                            ToastUtil.error(getContext(), "Please enter ingredient");
                            isValid = false;
                        }
                    }
                }
            }
        }
        // check steps is empty or not
        for (int i = 0; i < lnrAddRecipeStep.getChildCount(); i++) {
            if (lnrAddRecipeStep.getChildAt(i) instanceof LinearLayout && lnrAddRecipeStep.getChildAt(i).getVisibility() == View.VISIBLE) {
                LinearLayout linearLayout = (LinearLayout) lnrAddRecipeStep.getChildAt(i);
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    if (linearLayout.getChildAt(j) instanceof LinearLayout) {
                        LinearLayout linearLayoutChild = (LinearLayout) linearLayout.getChildAt(j);
                        for (int k = 0; k < linearLayoutChild.getChildCount(); k++) {
                            if (linearLayoutChild.getChildAt(k) instanceof EditText) {
                                EditText editText = (EditText) linearLayoutChild.getChildAt(k);
                                if (editText.getText().toString().isEmpty()) {
                                    ToastUtil.error(getContext(), "Please enter step");
                                    isValid = false;
                                }
                            }
                        }
                    } else {
                        if (linearLayout.getChildAt(j) instanceof ImageView) {
                            ImageView imageView = (ImageView) linearLayout.getChildAt(j);
                            if (imageView.getDrawable() == null) {
                                ToastUtil.error(getContext(), "Please add image");
                                isValid = false;
                            }
                        }
                    }
                }
            }
        }
        return isValid;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        initLayout(view);
        bindingAction(view);
    }
}