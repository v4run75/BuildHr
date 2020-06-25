package buildnlive.com.buildhr.adapters


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import buildnlive.com.buildhr.R
import buildnlive.com.buildhr.elements.Approval.ApproveIndentItem
import java.util.*

class IndentItemStatusAdapter
(private val context: Context, users: ArrayList<ApproveIndentItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<IndentItemStatusAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(project: ApproveIndentItem, pos: Int)
        fun onItemUnchecked(project: ApproveIndentItem, pos: Int)
    }

    private val items: List<ApproveIndentItem>

    init {
        this.items = users
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndentItemStatusAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_approve_labourreport_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, items[position], position, listener)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var name: TextView
        internal var inTime: TextView
        internal var container: LinearLayout
        internal var approve: RadioButton
        internal var disapprove: RadioButton
        internal var check: CheckBox

        init {
            name = itemView.findViewById(R.id.name)
            container = itemView.findViewById(R.id.container)
            inTime = itemView.findViewById(R.id.inTime)
            approve = itemView.findViewById(R.id.approve)
            disapprove = itemView.findViewById(R.id.disapprove)
            check = itemView.findViewById(R.id.check)
        }

        fun bind(context: Context, item: ApproveIndentItem, pos: Int, listener: OnItemClickListener) {
            name.text = item.itemName
            inTime.text = "Quantity Required: " + item.qtyRequired


            var status: String

            check.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    if ((!(approve.isChecked) && (!disapprove.isChecked))) {
                        Toast.makeText(context, "Choose approve/disapprove", Toast.LENGTH_SHORT).show()
                        check.isChecked = false

                    } else {
                        item.status = if (approve.isChecked) {
                            "approve"
                        } else {
                            "disapprove"
                        }


                        listener.onItemClick(item, pos)
                    }
                } else {
                    listener.onItemUnchecked(item, pos)
                }
            }

        }
    }

}