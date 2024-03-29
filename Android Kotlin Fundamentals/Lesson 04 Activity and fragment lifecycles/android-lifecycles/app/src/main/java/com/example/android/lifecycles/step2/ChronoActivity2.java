/*
 * Copyright 2019, The Android Open Source Project
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

package com.example.android.lifecycles.step2;

import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Chronometer;

import com.example.android.codelabs.lifecycle.R;

public class ChronoActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // The ViewModelStore provides a new ViewModel or one previously created.
        // Магазин ViewModelStore предоставляет новую модель представления или ранее созданную.
        ChronometerViewModel chronometerViewModel
                = new ViewModelProvider(this).get(ChronometerViewModel.class);

        // Get the chronometer reference Получить справку по хронометру
        Chronometer chronometer = findViewById(R.id.chronometer);

        if (chronometerViewModel.getStartTime() == null) {
            // If the start date is not defined, it's a new ViewModel so set it.
            // Если дата начала не определена,то это новая модель представления, поэтому установите ее.
            long startTime = SystemClock.elapsedRealtime();
            chronometerViewModel.setStartTime(startTime);
            chronometer.setBase(startTime);
        } else {
            // Otherwise the ViewModel has been retained, set the chronometer's base to the original
            // starting time.
            // В противном случае ViewModel был сохранен, установите основание хронометра на исходное
            // время начала.
            chronometer.setBase(chronometerViewModel.getStartTime());
        }

        chronometer.start();
    }
}
