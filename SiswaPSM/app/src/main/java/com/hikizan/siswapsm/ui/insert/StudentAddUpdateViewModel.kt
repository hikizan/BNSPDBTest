package com.hikizan.siswapsm.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.repository.StudentRepository

class StudentAddUpdateViewModel(application: Application) : ViewModel() {
    private val mStudentRepository: StudentRepository = StudentRepository(application)

    fun insert(student: Student) {
        mStudentRepository.insert(student)
    }

    fun update(student: Student) {
        mStudentRepository.update(student)
    }

    fun delete(student: Student) {
        mStudentRepository.delete(student)
    }
}