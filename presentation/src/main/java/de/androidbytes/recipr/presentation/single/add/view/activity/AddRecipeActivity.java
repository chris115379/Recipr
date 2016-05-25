package de.androidbytes.recipr.presentation.single.add.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.fuck_boilerplate.rx_paparazzo.RxPaparazzo;
import com.fuck_boilerplate.rx_paparazzo.entities.Response;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.linearlistview.LinearListView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;
import de.androidbytes.recipr.domain.model.User;
import de.androidbytes.recipr.presentation.R;
import de.androidbytes.recipr.presentation.ReciprApplication;
import de.androidbytes.recipr.presentation.core.util.GlideUtility;
import de.androidbytes.recipr.presentation.core.view.activity.PresenterActivity;
import de.androidbytes.recipr.presentation.single.add.di.AddRecipeComponent;
import de.androidbytes.recipr.presentation.single.add.di.AddRecipeModule;
import de.androidbytes.recipr.presentation.single.add.di.DaggerAddRecipeComponent;
import de.androidbytes.recipr.presentation.single.add.presenter.AddRecipePresenter;
import de.androidbytes.recipr.presentation.single.add.view.AddRecipeView;
import de.androidbytes.recipr.presentation.single.add.view.adapter.CategoryAutoCompleteAdapter;
import de.androidbytes.recipr.presentation.single.core.model.IngredientViewModel;
import de.androidbytes.recipr.presentation.single.core.model.StepViewModel;
import de.androidbytes.recipr.presentation.single.core.view.adapter.IngredientListAdapter;
import de.androidbytes.recipr.presentation.single.core.view.adapter.StepListAdapter;
import rx.functions.Action1;

import javax.inject.Inject;
import java.util.List;

public class AddRecipeActivity extends PresenterActivity<AddRecipeComponent, AddRecipePresenter> implements AddRecipeView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_name)
    EditText etRecipeName;

    @BindView(R.id.et_category)
    AutoCompleteTextView etRecipeCategory;

    @BindView(R.id.et_name_wrapper)
    TextInputLayout etRecipeNameWrapper;

    @BindView(R.id.et_category_wrapper)
    TextInputLayout etRecipeCategoryWrapper;

    @BindView(R.id.add_recipe_ingredients_title)
    TextView ingredientsTitle;

    @BindView(R.id.add_recipe_steps_title)
    TextView stepsTitle;

    @BindView(R.id.ingredients_list)
    LinearListView ingredientsListView;

    @BindView(R.id.steps_list)
    LinearListView stepListView;

    @BindView(R.id.iv_recipe_image)
    ImageView recipeImage;

    @Inject
    AddRecipePresenter presenter;

    @Inject
    Tracker tracker;

    @Inject
    CategoryAutoCompleteAdapter autoCompleteAdapter;

    @Inject
    IngredientListAdapter ingredientAdapter;

    @Inject
    StepListAdapter stepAdapter;
    private String imageUrl = "";

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_recipe;
    }

    public static Intent getLaunchIntent(Context context) {
        return new Intent(context, AddRecipeActivity.class);
    }

    @Override
    protected AddRecipeComponent onCreateNonConfigurationComponent() {
        return DaggerAddRecipeComponent.builder()
                .applicationComponent(((ReciprApplication) getApplicationContext()).getApplicationComponent())
                .addRecipeModule(new AddRecipeModule(getLoggedInUserId()))
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_recipe_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final boolean handled;

        switch (item.getItemId()) {
            case R.id.menu_save_recipe:
                User loggedInUser = getLoggedInUser();
                presenter.saveRecipeAction(loggedInUser);
                handled = true;
                break;
            case android.R.id.home:
                handled = !presenter.shouldClose() || super.onOptionsItemSelected(item);
                break;
            default:
                handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void showImageChooserDialog() {
        CharSequence colors[] = new CharSequence[]{getString(R.string.add_recipe_image_selection_camera), getString(R.string.add_recipe_image_selection_gallery)};

        final UCrop.Options options = new UCrop.Options();
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.NONE, UCropActivity.NONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary, getTheme()));
            options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, getTheme()));
            options.setToolbarColor(getResources().getColor(R.color.colorPrimary, getTheme()));
        } else {
            //noinspection deprecation
            options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
            //noinspection deprecation
            options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            //noinspection deprecation
            options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        }
        options.setToolbarTitle(getResources().getString(R.string.action_select_image_title));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:


                        RxPaparazzo
                                .takeImage(AddRecipeActivity.this)
                                .crop(options)
                                .usingCamera()
                                .subscribe(new Action1<Response<AddRecipeActivity, String>>() {
                                    @Override
                                    public void call(Response<AddRecipeActivity, String> response) {
                                        if (response.resultCode() != RESULT_OK) {
                                            response.targetUI().showUserCanceled();
                                            return;
                                        }

                                        response.targetUI().loadImage(response.data());
                                    }
                                });
                        break;
                    case 1:
                        RxPaparazzo
                                .takeImage(AddRecipeActivity.this)
                                .crop(options)
                                .usingGallery()
                                .subscribe(new Action1<Response<AddRecipeActivity, String>>() {
                                    @Override
                                    public void call(Response<AddRecipeActivity, String> response) {
                                        if (response.resultCode() != RESULT_OK) {
                                            response.targetUI().showUserCanceled();
                                            return;
                                        }

                                        response.targetUI().loadImage(response.data());
                                    }
                                });
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    private void loadImage(String imageUrl) {
        this.imageUrl = imageUrl;
        GlideUtility.loadRecipeImage(this, imageUrl, recipeImage);
    }

    private void showUserCanceled() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        setUpToolbar();
        setUpLayout(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker.setScreenName(AddRecipeActivity.class.getSimpleName());
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void setUpInjection() {
        getComponent().inject(this);
    }

    private void setUpToolbar() {
        ActionBar actionBar = setSupportActionBarAndReturn(toolbar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setUpLayout(Bundle savedInstanceState) {

        setUpTextChangedListener();

        etRecipeCategory.setAdapter(autoCompleteAdapter);
        etRecipeCategory.setThreshold(3);

        ingredientsListView.setAdapter(ingredientAdapter);
        stepListView.setAdapter(stepAdapter);

        if (savedInstanceState == null) {
            initializeData();
        }
    }

    private void setUpTextChangedListener() {
        etRecipeName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showRecipeNameError(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etRecipeCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showCategoryError(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initializeData() {
        presenter.loadData();
    }

    @Override
    public void changeCategoryAutocompletionData(List<String> cachedCategoryTitles) {
        autoCompleteAdapter.swapData(cachedCategoryTitles);
    }

    @Override
    public String getRecipeName() {
        return etRecipeName.getText().toString();
    }

    @Override
    public String getCategoryName() {
        return etRecipeCategory.getText().toString();
    }

    @Override
    public List<IngredientViewModel> getIngredients() {
        return ingredientAdapter.getData();
    }

    @Override
    public List<StepViewModel> getSteps() {
        return stepAdapter.getData();
    }

    @Override
    public void destroy() {
        finish();
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void showStepsError(boolean show) {
        if (show)
            stepsTitle.setError(getString(R.string.error_steps));
        else
            stepsTitle.setError(null);
    }

    @Override
    public void showIngredientsError(boolean show) {
        if (show)
            ingredientsTitle.setError(getString(R.string.error_ingredients));
        else
            ingredientsTitle.setError(null);
    }

    @Override
    public void showCategoryError(boolean show) {
        etRecipeCategoryWrapper.setError(getString(R.string.error_category_name));
        etRecipeCategoryWrapper.setErrorEnabled(show);
    }

    @Override
    public void showRecipeNameError(boolean show) {
        etRecipeNameWrapper.setError(getString(R.string.error_recipe_name));
        etRecipeNameWrapper.setErrorEnabled(show);
    }

    @Override
    public void onBackPressed() {
        if (presenter.shouldClose())
            super.onBackPressed();
    }

    @Override
    public void renderConfirmCloseDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirm_add_recipe_abort_title))
                .setMessage(getString(R.string.confirm_add_recipe_abort_message))
                .setPositiveButton(getString(R.string.confirm_add_recipe_abort_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(getString(R.string.confirm_add_recipe_abort_no), null)
                .show();
    }

    @OnClick(R.id.fab)
    public void onChooseImageButtonClicked() {
        showImageChooserDialog();
    }

    @OnClick(R.id.addPreparationStepButton)
    public void onAddPreparationStepButtonClicked() {
        showStepsError(false);
        showPreparationDialog(new StepViewModel(-1, stepAdapter.getCount() + 1, ""));
    }

    private void showPreparationDialog(final StepViewModel step) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View addStepAlert = inflater.inflate(R.layout.alert_add_step, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.add_recipe_alert_add_step_title));
        builder.setView(addStepAlert);

        final EditText instructionEditText = (EditText) addStepAlert.findViewById(R.id.et_instruction);

        final String initialInstruction = step.getInstruction();
        if (initialInstruction != null) {
            instructionEditText.setText(initialInstruction);
        }

        builder
                .setCancelable(false)
                .setPositiveButton(
                        getString(R.string.add_recipe_alert_add_step_add),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String instruction = instructionEditText.getText().toString();
                                if (!instruction.isEmpty()) {
                                    step.setInstruction(instruction);

                                    stepAdapter.addStep(step);
                                    stepAdapter.notifyDataSetChanged();

                                    dialog.dismiss();
                                }
                            }
                        })
                .setNegativeButton(
                        getString(R.string.add_recipe_alert_add_step_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        builder.create().show();

    }

    @OnClick(R.id.addIngredientButton)
    public void onAddIngredientButtonClicked() {
        showIngredientsError(false);
        showIngredientDialog(new IngredientViewModel(-1, "", "", ""));
    }

    private void showIngredientDialog(final IngredientViewModel ingredient) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View addIngredientAlert = inflater.inflate(R.layout.alert_add_ingredient, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(getString(R.string.add_recipe_add_ingredient_title));
        builder.setView(addIngredientAlert);

        final EditText quantityEditText = (EditText) addIngredientAlert.findViewById(R.id.tv_quantity);
        final EditText unitEditText = (EditText) addIngredientAlert.findViewById(R.id.tv_unit);
        final EditText ingredientEditText = (EditText) addIngredientAlert.findViewById(R.id.tv_ingredient);

        final String initialQuantity = ingredient.getQuantity();
        if (initialQuantity != null) {
            quantityEditText.setText(initialQuantity);
        }

        final String initialUnit = ingredient.getUnit();
        if (initialUnit != null) {
            unitEditText.setText(initialUnit);
        }

        final String initialName = ingredient.getName();
        if (initialName != null) {
            ingredientEditText.setText(initialName);
        }

        builder
                .setCancelable(false)
                .setPositiveButton(
                        getString(R.string.add_recipe_add_ingredient_add),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                final String quantity = quantityEditText.getText().toString();
                                if (!quantity.isEmpty()) {
                                    ingredient.setQuantity(quantity);
                                }

                                final String unit = unitEditText.getText().toString();
                                if (!unit.isEmpty()) {
                                    ingredient.setUnit(unit);
                                }

                                final String ingredientName = ingredientEditText.getText().toString();
                                if (!ingredientName.isEmpty()) {
                                    ingredient.setName(ingredientName);

                                    ingredientAdapter.addIngredient(ingredient);
                                    ingredientAdapter.notifyDataSetChanged();

                                    dialog.dismiss();
                                }
                            }
                        })
                .setNegativeButton(
                        getString(R.string.add_recipe_add_ingredient_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

        builder.create().show();
    }
}
