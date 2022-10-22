package fptu.prm.cookcook.dao;

import fptu.prm.cookcook.entities.Recipe;

public interface RecipeDAO {
    void addRecipe(Recipe recipe);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
    void getRecipeById(String id);
    void getAllRecipe();
}
