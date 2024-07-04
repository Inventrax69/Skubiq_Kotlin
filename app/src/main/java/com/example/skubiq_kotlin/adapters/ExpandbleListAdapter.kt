package com.example.skubiq_kotlin.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.skubiq_kotlin.R

class ExpandbleListAdapter(
    private  val context :Context,
    private  val headerlist :List<String>,
    private val onItemClick: OnItemClick,
    var isSupervisor: Boolean = false,
    private val listChildDada: HashMap<String,List<String>>) : BaseExpandableListAdapter(){

    override fun getGroupCount(): Int {
        return this.headerlist.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.listChildDada[this.headerlist[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.headerlist[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.listChildDada[this.headerlist[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return  childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val listTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.adapter_list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.text_title)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle


        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val expandedListText = getChild(groupPosition, childPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.adapter_list_item, null)
        }
        val childText = getChild(groupPosition, childPosition) as String
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.text_desc)
        expandedListTextView.text = expandedListText

        convertView.setOnClickListener(View.OnClickListener {
            onItemClick.onItemClick(
                groupPosition,
                childPosition,
                childText
            )
        })


        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {

        return true
    }

    interface OnItemClick {
        fun onItemClick(gpos: Int, cpos: Int, text: String?)
    }
}