package com.example.task4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task4.databinding.ListItemBinding
import java.text.DecimalFormat

class Adapter(val myItem: List<Data>) : RecyclerView.Adapter<Adapter.Holder>(){

    interface ItemClick{
        fun onClick(view : View, position: Int)
    }

    var itemClick : ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.Holder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Adapter.Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it,position)
        }
        holder.img.setImageResource(myItem[position].img)
        holder.title.text = myItem[position].title

        holder.title.maxLines = 2
        holder.address.text = myItem[position].address

        val dec = DecimalFormat("#,###원") //숫자 포맷 변경
        holder.price.text = dec.format(myItem[position].price)
        holder.good.text = myItem[position].good.toString()
        holder.chat.text = myItem[position].chat.toString()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return myItem.size
    }

    inner class Holder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){
        var img = binding.image
        var title = binding.title
        var address = binding.address
        var price = binding.price
        var good = binding.mainGoodText
        var chat = binding.mainChatText
    }
}