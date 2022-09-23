package com.hikizan.siswapsm.adapter

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.databinding.ItemRowStudentBinding
import com.hikizan.siswapsm.helper.StudentDiffCallback
import com.hikizan.siswapsm.ui.insert.StudentAddUpdateActivity
import java.io.ByteArrayOutputStream

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    private val listStudents = ArrayList<Student>()
    fun setListStudents(listStudents: List<Student>) {
        val diffCallback = StudentDiffCallback(this.listStudents, listStudents)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listStudents.clear()
        this.listStudents.addAll(listStudents)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemRowStudentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(student: Student) {
            val male = "Laki-laki"
            val female = "Perempuan"
            binding.imgStudent.setImageBitmap(stringToBitMap(student.image))
            with(binding) {
                tvItemName.text = student.name
                tvItemAddress.text = student.address
                tvItemDate.text = student.date
                tvItemPhone.text = student.phoneNumber
                tvItemJk.text = if (student.isMale == true) male else female

                cvItemStudent.setOnClickListener {
                    val intent = Intent(it.context, StudentAddUpdateActivity::class.java)
                    student.image = "null"
                    intent.putExtra(StudentAddUpdateActivity.EXTRA_STUDENT, student)
                    Log.d("Adapter", "bind: image = ${student.image}\n student = $student")
                    it.context.startActivity(intent)
                }
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStudents[position])
    }

    override fun getItemCount(): Int {
        return listStudents.size
    }

    fun stringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: java.lang.Exception) {
            e.message
            null
        }
    }
}