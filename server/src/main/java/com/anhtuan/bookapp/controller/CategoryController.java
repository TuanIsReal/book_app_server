package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

    CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<Response> addCategory(@RequestParam String categoryName){
        Response response = new Response();
        if (categoryService.getCategoryByCategoryName(categoryName) != null){
            response.setCode(104);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        categoryService.insertCategory(new Category(categoryName));
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCategory")
    public ResponseEntity<Response> getCategory(){
        Response response = new Response();
        List<Category> categoryList = categoryService.findAll();

        response.setCode(100);
        response.setData(categoryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<Response> deleteCategory(@RequestParam String id){
        Response response = new Response();

        categoryService.deleteById(id);

        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/searchCategory")
    public ResponseEntity<Response> searchCategory(@RequestParam String text){
        Response response = new Response();
        List<Category> categoryList = categoryService.findCategoryByText(text);
        response.setCode(100);
        response.setData(categoryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
