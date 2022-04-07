package com.ubaya.bakul_160419038.view

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.ubaya.bakul_160419038.R

class ExpandableListAdapter(var context: Context?, var title: MutableList<String>,
                            var child: MutableList<String>): BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return title.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return 1
    }

    override fun getGroup(p0: Int): String {
        return title[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return child[p0]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p0.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View? {
        var convertView = p2
        if(convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.group_layout, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.txtListTitle)
        title?.setTypeface(null, Typeface.BOLD)
        title?.text = getGroup(p0)

        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View? {
        var convertView = p3
        if(convertView == null) {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.child_layout, null)
        }
        val title = convertView?.findViewById<TextView>(R.id.txtListChild)
        title?.text = getChild(p0, p1) as String

        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return false
    }
}