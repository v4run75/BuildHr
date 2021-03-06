package buildnlive.com.buildhr.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import buildnlive.com.buildhr.R;
import buildnlive.com.buildhr.elements.Item;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.ViewHolder> implements Filterable{


    public interface OnItemSelectedListener {
        void onItemCheck(boolean checked);

        void onItemInteract(int pos, int flag);

//        void onItemReturn(Item item,int pos);
    }

    private List<Item> items,filteredItems;
    private Context context;
    private OnItemSelectedListener listener;

    public AddItemAdapter(Context context, List<Item> users, OnItemSelectedListener listener) {
        this.items = users;
        this.filteredItems=users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_item, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (ViewHolder.CHECKOUT) {
            if (filteredItems.get(position).isUpdated())
                holder.bind(context, filteredItems.get(position), position, listener);
        } else {
            holder.bind(context, filteredItems.get(position), position, listener);
        }
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static int checkCount = 0;
        public static boolean CHECKOUT = false;
        private TextView name, unit;
        private EditText quantity;
        private CheckBox check;
        private ImageButton close;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            unit = view.findViewById(R.id.unit);
            check = view.findViewById(R.id.check);
            quantity = view.findViewById(R.id.quantity);
            close = view.findViewById(R.id.close);
        }

        public void bind(final Context context, final Item item, final int pos, final OnItemSelectedListener listener) {

            name.setText(item.getName());
            unit.setText(item.getUnit());
            if (CHECKOUT) {
                check.setChecked(true);
                check.setEnabled(false);
                quantity.setEnabled(false);
                quantity.setText(item.getQuantity());
                item.setUpdated(false);
                check.setVisibility(View.GONE);
                close.setVisibility(View.VISIBLE);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemInteract(pos, 100);
                    }
                });

            } else {
                check.setVisibility(View.VISIBLE);
                close.setVisibility(View.GONE);
//                check.setChecked(false);
                check.setEnabled(true);
                quantity.setEnabled(true);
                check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            if (quantity.getText().toString().length() > 0) {
                                checkCount++;
                                listener.onItemCheck(true);
                                item.setUpdated(true);
                                item.setQuantity(quantity.getText().toString());
//                                listener.onItemReturn(item,pos);

                            } else {
                                Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                                buttonView.setChecked(false);
                            }
                        } else {
                            checkCount--;
                            item.setUpdated(false);
                            if (checkCount > 0) {
                                listener.onItemCheck(true);
                            } else {
                                listener.onItemCheck(false);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredItems = items;
                } else {
                    List<Item> filteredList = new ArrayList<>();
                    for (Item row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
//                            Log.i("Filter",row.getName());
                        }
                    }

                    filteredItems = filteredList;
//                    Log.i("filteredItems",filteredItems.get(0).getName());
//                    Log.i("filteredItems",filteredItems.get(1).getName());

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItems;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredItems = (ArrayList<Item>) filterResults.values;
                for(Item i:filteredItems){
//                    Log.i("Publish:filteredItems",i.getName());
                }
                notifyDataSetChanged();
            }

        };
    }


}