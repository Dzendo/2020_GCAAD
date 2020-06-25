/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.basicsample.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
//import com.example.android.databinding.basicsample.R
//import kotlinx.android.synthetic.main.activity_main.view.*
import com.example.android.databinding.basicsample.databinding.ActivityMainBinding
// N:\2020_GCAAD\Android Kotlin Fundamentals\Lesson 02 Layouts\BasicSampleViewBinding
// \app\build\generated\data_binding_base_class_source_out\debug\out\com\example\android\databinding\basicsample\databinding
/**
 * Shows a menu. Показывает меню.
 */
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The layout for this activity is a Data Binding layout so it needs to be inflated using
        // Макет для этого действия является макетом привязки данных, поэтому его необходимо раздуть с помощью
        // DataBindingUtil.
       // val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Можно и так - работает : плюс не надо import R и DataBindingUtil.setContentView
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
/*
        // The returned binding has references to all the Views with an ID.
        // Возвращаемая привязка содержит ссылки на все представления с идентификатором.
        binding.observableFieldsActivityButton.setOnClickListener {
        //    startActivity(Intent(this, ObservableFieldActivity::class.java))
            startActivity<ObservableFieldActivity>()    
        }
        binding.viewmodelActivityButton.setOnClickListener {
        //    startActivity(Intent(this, ViewModelActivity::class.java))
            startActivity<ViewModelActivity>()    
        }
*/   }

    fun onClick(view: View) = when (view) {
        binding.observableFieldsActivityButton -> startActivity<ObservableFieldActivity>()
        binding.viewmodelActivityButton -> startActivity<ViewModelActivity>()
        else -> startActivity<MainActivity>()
    }
       /* when (view.id) {
            R.id.observable_fields_activity_button -> startActivity<ObservableFieldActivity>()
            R.id.viewmodel_activity_button -> startActivity<ViewModelActivity>()
        }*/
}
// AS
private inline fun <reified T: AppCompatActivity> AppCompatActivity.startActivity() =
        startActivity(Intent(this,T::class.java))

// N:\2020_GCAAD\Android Kotlin Fundamentals\Lesson 02 Layouts\BasicSampleViewBinding\app\build\generated\data_binding_base_class_source_out\debug\out\com\example\android\databinding\basicsample\databinding

/*
// Generated by view binder compiler. Do not edit!
package com.example.android.databinding.basicsample.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.example.android.databinding.basicsample.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button observableFieldsActivityButton;

  @NonNull
  public final TextView observableactivityActivityLabel;

  @NonNull
  public final TextView textView;

  @NonNull
  public final Button viewmodelActivityButton;

  @NonNull
  public final TextView viewmodelActivityLabel;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button observableFieldsActivityButton,
      @NonNull TextView observableactivityActivityLabel, @NonNull TextView textView,
      @NonNull Button viewmodelActivityButton, @NonNull TextView viewmodelActivityLabel) {
    this.rootView = rootView;
    this.observableFieldsActivityButton = observableFieldsActivityButton;
    this.observableactivityActivityLabel = observableactivityActivityLabel;
    this.textView = textView;
    this.viewmodelActivityButton = viewmodelActivityButton;
    this.viewmodelActivityLabel = viewmodelActivityLabel;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.observable_fields_activity_button;
      Button observableFieldsActivityButton = rootView.findViewById(id);
      if (observableFieldsActivityButton == null) {
        break missingId;
      }

      id = R.id.observableactivity_activity_label;
      TextView observableactivityActivityLabel = rootView.findViewById(id);
      if (observableactivityActivityLabel == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.viewmodel_activity_button;
      Button viewmodelActivityButton = rootView.findViewById(id);
      if (viewmodelActivityButton == null) {
        break missingId;
      }

      id = R.id.viewmodel_activity_label;
      TextView viewmodelActivityLabel = rootView.findViewById(id);
      if (viewmodelActivityLabel == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, observableFieldsActivityButton,
          observableactivityActivityLabel, textView, viewmodelActivityButton,
          viewmodelActivityLabel);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

 */