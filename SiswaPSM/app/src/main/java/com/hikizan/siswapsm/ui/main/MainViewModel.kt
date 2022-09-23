package com.hikizan.siswapsm.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.repository.StudentRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mStudentRepository = StudentRepository(application)

    fun getAllStudents(): LiveData<List<Student>> = mStudentRepository.getAllStudents()
}