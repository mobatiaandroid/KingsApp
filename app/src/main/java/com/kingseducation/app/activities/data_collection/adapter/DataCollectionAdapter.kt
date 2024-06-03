package com.kingseducation.app.activities.data_collection.adapter

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.kingseducation.app.R
import com.kingseducation.app.activities.data_collection.model.DataCollectionResponseModel
import com.kingseducation.app.activities.teacher_contact.model.GeneralSubmitResponseModel
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response


class DataCollectionAdapter(
    private val context: Context,
    private val dataCollectionFields: ArrayList<DataCollectionResponseModel.CollectionField>,
    private val imagePickerCallback: ImagePickerCallback
) :
    RecyclerView.Adapter<DataCollectionAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fieldTitleTV: TextView = view.findViewById(R.id.fieldTitleTV)
        var uploadedFieldTV: TextView = view.findViewById(R.id.uploadedFieldTV)
        var attachButton: TextView = view.findViewById(R.id.attachButton)
        var uploadButton: Button = view.findViewById(R.id.uploadButton)
        var editTextView: EditText = view.findViewById(R.id.editText)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_collection, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fieldTitleTV.text = dataCollectionFields[position].fieldLabel
        var customFont: Typeface = Typeface.createFromAsset(context.assets, "font/montserrat_regular.ttf")
        holder.editTextView.typeface = customFont
        if (dataCollectionFields[position].fieldType == "file") {
            holder.uploadedFieldTV.visibility = View.VISIBLE
            if (dataCollectionFields[position].fieldValue.isEmpty()) {
                holder.uploadedFieldTV.text = context.resources.getString(R.string.no_file)
            } else {
                holder.uploadedFieldTV.text = dataCollectionFields[position].fieldValue.replace(
                    "http://gama.mobatia.in:8080/kingseducation/public/storage/student-data-collection/",
                    ""
                )
            }
            holder.attachButton.visibility = View.VISIBLE
            holder.uploadButton.visibility = View.VISIBLE
            holder.editTextView.visibility = View.GONE
        } else {
            if (dataCollectionFields[position].fieldValue.isEmpty()) {
                holder.editTextView.setText("")
            } else {
                holder.editTextView.setText(dataCollectionFields[position].fieldValue)
            }
            holder.uploadedFieldTV.visibility = View.GONE
            holder.attachButton.visibility = View.GONE
            holder.uploadButton.visibility = View.VISIBLE
            holder.editTextView.visibility = View.VISIBLE
        }
        holder.attachButton.setOnClickListener {
            imagePickerCallback.onPickImage(position)
        }
        holder.uploadButton.setOnClickListener {
            if (dataCollectionFields[position].fieldType == "file") {
                if (holder.uploadedFieldTV.text.toString().isEmpty()) {
                    Toast.makeText(context, "Please attach a file!", Toast.LENGTH_SHORT).show()
                } else {
                    dataCollectionFileUploadAPI(position)
                }
            } else {
                if (holder.editTextView.text.toString().isEmpty()) {
                    Toast.makeText(context, "Please enter a value!", Toast.LENGTH_SHORT).show()
                } else {
                    val fieldValue = holder.editTextView.text.toString()
                    dataCollectionFields[position].fieldValue = fieldValue
                    dataCollectionTextUploadAPI(position)
                }
            }

        }
    }

    private fun dataCollectionTextUploadAPI(position: Int) {
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
            addProperty("field_name", dataCollectionFields[position].fieldName)
            addProperty("field_value", dataCollectionFields[position].fieldValue)
        }
        val call: Call<GeneralSubmitResponseModel> = ApiClient.getApiService().submitDataCollection(
            "Bearer " + PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<GeneralSubmitResponseModel> {
            override fun onResponse(
                call: Call<GeneralSubmitResponseModel>,
                response: Response<GeneralSubmitResponseModel>
            ) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == "100") {
                        Toast.makeText(context, "Upload Successful!", Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT).show()
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<GeneralSubmitResponseModel>, t: Throwable) {
                Toast.makeText(context, "Please try again later!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun dataCollectionFileUploadAPI(position: Int) {

    }

    interface ImagePickerCallback {
        fun onPickImage(position: Int)
    }


    override fun getItemCount(): Int {
        return dataCollectionFields.size
    }
}