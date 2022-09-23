package com.hikizan.siswapsm.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.database.StudentDao
import com.hikizan.siswapsm.database.StudentRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StudentRepository(application: Application) {
    private val mStudentDao: StudentDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    
    init {
        val db = StudentRoomDatabase.getDatabase(application)
        mStudentDao = db.studentDao()
    }

    fun getAllStudents(): LiveData<List<Student>> = mStudentDao.getAllStudents()

    fun insert(student: Student) {
        executorService.execute { mStudentDao.insert(student) }
    }

    fun update(student: Student) {
        executorService.execute { mStudentDao.update(student) }
    }

    fun delete(student: Student) {
        executorService.execute { mStudentDao.delete(student) }
    }
}