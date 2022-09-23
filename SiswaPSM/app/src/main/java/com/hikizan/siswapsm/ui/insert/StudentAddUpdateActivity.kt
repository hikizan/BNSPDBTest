package com.hikizan.siswapsm.ui.insert

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.hikizan.siswapsm.R
import com.hikizan.siswapsm.database.Student
import com.hikizan.siswapsm.databinding.ActivityStudentAddUpdateBinding
import com.hikizan.siswapsm.helper.DataHelper
import com.hikizan.siswapsm.helper.ViewModelFactory

class StudentAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentAddUpdateBinding
    private lateinit var viewModel: StudentAddUpdateViewModel

    private var isEdit = false
    private var student: Student? = null
    private var isMale: Boolean = false

    //for take image
    private lateinit var imagePath: Uri
    private lateinit var imageToStore: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = obtainViewModel(this@StudentAddUpdateActivity)

        student = intent.getParcelableExtra(EXTRA_STUDENT)
        if (student != null) {
            isEdit = true
        } else {
            student = Student()
        }

        val actionbarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionbarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (student != null) {
                student?.let { student ->
                    binding.edtName.setText(student.name)
                    binding.edtAddress.setText(student.address)
                    binding.edtPhoneNumber.setText(student.phoneNumber.toString())
                    if (student.isMale == true) {
                        binding.rbMale.isChecked = true
                        binding.rbFemale.isChecked = false
                    }else{
                        binding.rbMale.isChecked = false
                        binding.rbFemale.isChecked = true
                    }
                }
            }
        }else{
            actionbarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionbarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSubmit.text = btnTitle

        binding.btnSubmit.setOnClickListener {
            val checkedRadioButtonId = binding.rgJk.checkedRadioButtonId
            if (checkedRadioButtonId > 0) {
                when(checkedRadioButtonId){
                    R.id.rb_male -> isMale = true
                    R.id.rb_female -> isMale = false
                }
            }

            val name = binding.edtName.text.toString().trim()
            val address = binding.edtAddress.text.toString().trim()
            val phone = binding.edtPhoneNumber.text.toString().trim()
            when{
                name.isEmpty() -> {
                    binding.edtName.error = getString(R.string.empty)
                }
                address.isEmpty() -> {
                    binding.edtAddress.error = getString(R.string.empty)
                }
                phone.isEmpty() -> {
                    binding.edtPhoneNumber.error = getString(R.string.empty)
                } else -> {
                    student?.let { student ->
                        student.name = name
                        student.address = address
                        student.phoneNumber = phone
                        student.isMale = isMale
                    }
                    if (isEdit) {
                        viewModel.update(student as Student)
                        showToast(getString(R.string.changed))
                    } else {
                        student?.let { student ->
                            student.date = DataHelper.getCurrentDate()
                        }
                        viewModel.insert(student as Student)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }

        //for take image
        binding.imgStudent.setOnClickListener {
            choseImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type==ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogTitle = getString(R.string.delete)
            dialogMessage = getString(R.string.message_delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) {_,_ ->
                if (!isDialogClose) {
                    viewModel.delete(student as Student)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): StudentAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[StudentAddUpdateViewModel::class.java]
    }

    //for take image
    private fun choseImage() {
        try {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    //for take image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data?.data !=null) {
                imagePath = data.data!!
                imageToStore = MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                binding.imgStudent.setImageBitmap(imageToStore)
                Log.d("getDataCamera", "dataGambar = $imageToStore")
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val EXTRA_STUDENT = "extra_student"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20

        //for take image
        const val PICK_IMAGE_REQUEST = 99
    }
}