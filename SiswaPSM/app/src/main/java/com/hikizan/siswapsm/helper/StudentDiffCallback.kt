package com.hikizan.siswapsm.helper

import androidx.recyclerview.widget.DiffUtil
import com.hikizan.siswapsm.database.Student

class StudentDiffCallback(
    private val mOldNoteList: List<Student>,
    private val mNewNoteList: List<Student>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldNotes = mOldNoteList[oldItemPosition]
        val newNotes = mNewNoteList[newItemPosition]
        return oldNotes.name==newNotes.name && oldNotes.phoneNumber==newNotes.phoneNumber
    }


}