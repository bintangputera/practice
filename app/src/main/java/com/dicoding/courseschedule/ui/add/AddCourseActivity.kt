package com.dicoding.courseschedule.ui.add

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.R.string
import com.dicoding.courseschedule.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Locale

class AddCourseActivity : AppCompatActivity() {

    private lateinit var viewModel: AddCourseViewModel

    private lateinit var edtCourseName: TextInputEditText
    private lateinit var edtLecturer: TextInputEditText
    private lateinit var edtNote: TextInputEditText

    private lateinit var imgBtnStartTime: ImageButton
    private lateinit var imgBtnEndTime: ImageButton

    private lateinit var tvStartTimePrimary : TextView
    private lateinit var tvEndTimePrimary : TextView

    private lateinit var spinnerDay : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        initUI()
        initAction()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_insert -> {
                if (formValidate()) {
                    viewModel.insertCourse(
                        courseName = edtCourseName.text.toString(),
                        day = spinnerDay.selectedItemPosition,
                        startTime = tvStartTimePrimary.text.toString(),
                        endTime = tvEndTimePrimary.text.toString(),
                        lecturer = edtLecturer.text.toString(),
                        note = edtNote.text.toString()
                    )
                    Toast.makeText(this, getString(string.message_course_succesfull_added), Toast.LENGTH_SHORT).show()
                    finish()
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun formValidate(): Boolean {
        if (validateCourse() && validateTime()) {
            return true
        }
        return false
    }

    private fun validateTime(): Boolean {
        val startTime = tvStartTimePrimary.text.toString()
        val endTime = tvEndTimePrimary.text.toString()

        val format = SimpleDateFormat("HH:mm")
        val startTimeFormat = format.parse(startTime)
        val endTimeFormat = format.parse(endTime)

        if (startTime.isEmpty() || endTime.isEmpty()) {
            return false
        }

        if (startTimeFormat.time > endTimeFormat.time) {
            return false
        }

        return true
    }

    private fun validateCourse(): Boolean {
        val courseName = edtCourseName.text.toString()
        val lecturer = edtLecturer.text.toString()
        val note = edtNote.text.toString()

        if (courseName.isEmpty() || lecturer.isEmpty() || note.isEmpty()) {
            return false
        }
        return true
    }

    private fun initAction() {
        val timePickerListenerStart = TimePickerDialog.OnTimeSetListener {
                _, hour, minute ->
            tvStartTimePrimary.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }

        val timePickerListenerEnd = TimePickerDialog.OnTimeSetListener {
                _, hour, minute ->
            tvEndTimePrimary.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }
        imgBtnStartTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this, timePickerListenerStart, 0,0, true)
            timePickerDialog.show()
        }
        imgBtnEndTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this, timePickerListenerEnd, 0,0, true)
            timePickerDialog.show()
        }
    }

    private fun initUI() {
        edtCourseName = findViewById(R.id.edt_course_name)
        edtLecturer = findViewById(R.id.edt_lecturer)
        edtNote = findViewById(R.id.edt_note)

        imgBtnStartTime = findViewById(R.id.img_btn_start_time)
        imgBtnEndTime = findViewById(R.id.img_btn_end_time)

        tvStartTimePrimary = findViewById(R.id.tv_primary_start_time)
        tvEndTimePrimary = findViewById(R.id.tv_primary_end_time)

        spinnerDay = findViewById(R.id.spinner_day)
    }

}