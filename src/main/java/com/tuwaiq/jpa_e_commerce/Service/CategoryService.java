package com.tuwaiq.jpa_e_commerce.Service;

import com.tuwaiq.jpa_e_commerce.Model.Category;
import com.tuwaiq.jpa_e_commerce.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(Category category){
        categoryRepository.save(category);
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Boolean updateCategory(Integer id,Category category){
        Category oldCategory=categoryRepository.getById(id);
        if (oldCategory==null){
            return false;
        }
        else {
            oldCategory.setName(category.getName());
            categoryRepository.save(oldCategory);
            return true;
        }
    }

    public Boolean deleteCategory(Integer id){
        Category category= categoryRepository.getById(id);
        if (category==null){
            return false;
        }else {
            categoryRepository.delete(category);
            return true;
        }
    }
}
