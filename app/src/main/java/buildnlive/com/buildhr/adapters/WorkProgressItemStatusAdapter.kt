package buildnlive.com.buildhr.adapters


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import buildnlive.com.buildhr.R
import buildnlive.com.buildhr.elements.Approval.ApproveWorkProgressItem
import java.util.*

class WorkProgressItemStatusAdapter
(private val context: Context, users: ArrayList<ApproveWorkProgressItem>, private val listener: OnItemClickListener) : RecyclerView.Adapter<WorkProgressItemStatusAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(project: ApproveWorkProgressItem, pos: Int)
        fun onItemUnchecked(project: ApproveWorkProgressItem, pos: Int)
    }

    private val items: List<ApproveWorkProgressItem>

    init {
        this.items = users
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkProgressItemStatusAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_approve, parent, false)
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

        fun bind(context: Context, item: ApproveWorkProgressItem, pos: Int, listener: OnItemClickListener) {
            name.text = item.workName
            inTime.text = "Quantity Achieved: " + item.qtyAchieved


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