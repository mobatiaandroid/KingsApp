package com.kingseducation.app.activities.calender.adapter


/*
class CustomLisAdapter(private val mContext: Context, eventArrayList: ArrayList<CalendarList>) :
    RecyclerView.Adapter<CustomLisAdapter.MyViewHolder>() {

    private val eventArrayList: ArrayList<CalendarList>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var event: TextView
        var eventDate: TextView
        var relSub: LinearLayout

        init {
            event = view.findViewById(R.id.event)
            eventDate = view.findViewById(R.id.eventTxt)
            relSub = view.findViewById(R.id.relSub)
        }
    }

    init {
        this.eventArrayList = eventArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.calender_listview_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.event.setText(eventArrayList[position].title)
           Log.e("titlie",eventArrayList[position].title)
        holder.eventDate.setText(eventArrayList[position].description)
        Log.e("desc",eventArrayList[position].description)

        // System.out.println("title::"+eventArrayList.get(position).getTittle());
    }

    override fun getItemCount(): Int {
        Log.e("adapter size", eventArrayList.size.toString())
        return eventArrayList.size

    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}*/
