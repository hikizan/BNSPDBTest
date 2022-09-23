package com.hikizan.siswapsm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.databinding.ItemRowStudentBinding
import com.hikizan.siswapsm.helper.StudentDiffCallback
import com.hikizan.siswapsm.ui.insert.StudentAddUpdateActivity

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
            with(binding) {
                tvItemName.text = student.name
                tvItemAddress.text = student.address
                tvItemDate.text = student.date
                tvItemPhone.text = student.phoneNumber
                tvItemJk.text = if (student.isMale == true) male else female
                cvItemStudent.setOnClickListener {
                    val intent = Intent(it.context, StudentAddUpdateActivity::class.java)
                    intent.putExtra(StudentAddUpdateActivity.EXTRA_STUDENT, student)
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
}