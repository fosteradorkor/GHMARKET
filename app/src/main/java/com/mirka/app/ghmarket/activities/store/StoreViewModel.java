package com.mirka.app.ghmarket.activities.store;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mirka.app.ghmarket.DB.Category;
import com.parse.ParseException;

import java.util.List;

import bolts.Continuation;
import bolts.Task;

/**
 * Created by Foster on 3/23/2018.
 */

public class StoreViewModel extends ViewModel {
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();

    public LiveData<List<Category>> getCategories() {
//        Category.

//        Category.getCategories(new Continuation<List<Category>, Task<List<Category>>>() {
//            @Override
//            public Task<List<Category>> then(Task<List<Category>> task) throws Exception {
//                if (task.getError() instanceof ParseException && ((ParseException) task.getError()).getCode() == ParseException.CACHE_MISS) {
//                    //no hits from local datastore
//                } else {
//                    categories.postValue(task.getResult());
//                }
//
//
//                return Category.getQuery().findInBackground();
//            }
//        }, new Continuation<List<Category>, Task<List<Category>>>() {
//            @Override
//            public Task<List<Category>> then(Task<List<Category>> task) throws Exception {
//
//                //error occurred
//                if (task.getError() != null && task.getError() instanceof ParseException) {
//
//                    return task;
//                }
//
//                // successful query
//                categories.postValue(task.getResult());
//                Category.pinAllInBackground(task.getResult());
//
//                return task;
//            }
//        });

        return categories;
    }
}
