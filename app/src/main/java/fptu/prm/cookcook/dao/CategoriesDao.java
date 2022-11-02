package fptu.prm.cookcook.dao;

import androidx.lifecycle.LiveData;

import java.util.List;

import fptu.prm.cookcook.entities.Categories;

public interface CategoriesDao {
    LiveData<List<Categories>> getAllCategories();
}
