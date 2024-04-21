/*****************************************
 * MainActivity is the driver for the app *
 ******************************************/
// app package
package com.cs360.mterry_inventory;

// Imported Android Libraries
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/********************************************************************************
 *                                  CustomAdapter                               *
 * This is the CustomAdapter that will help display the items in the database   *
 ********************************************************************************/

/* @noinspection FieldMayBeFinal*/
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    // Global variable. Accessible throughout page runtime
    private Context context;
    private Activity activity;
    private ArrayList<String> itemID,itemType, itemMake, itemModel, itemLocation, itemDepartment,
            itemUserAssigned;

    // CustomAdapter function
    CustomAdapter(Activity activity, Context context, ArrayList<String> itemID, ArrayList<String>  itemType,
                  ArrayList<String>  itemMake, ArrayList<String>  itemModel,
                  ArrayList<String>  itemLocation, ArrayList<String>  itemDepartment,
                  ArrayList<String>  itemUserAssigned) {

        this.activity = activity;
        this.context = context;
        this.itemID = itemID;
        this.itemType = itemType;
        this.itemMake = itemMake;
        this.itemModel = itemModel;
        this.itemLocation = itemLocation;
        this.itemDepartment = itemDepartment;
        this.itemUserAssigned = itemUserAssigned;
    }
    // CustomAdapter view
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.display_row, parent,false);
        return new MyViewHolder(view);
    }
    // CustomAdapter holder
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.displayItemID.setText(String.valueOf(itemID.get(position)));
        holder.displayItemType.setText(String.valueOf(itemType.get(position)));
        holder.displayItemMake.setText(String.valueOf(itemMake.get(position)));
        holder.displayItemModel.setText(String.valueOf(itemModel.get(position)));
        holder.displayItemLocation.setText(String.valueOf(itemLocation.get(position)));
        holder.displayItemDepartment.setText(String.valueOf(itemDepartment.get(position)));
        holder.displayItemUserAssigned.setText(String.valueOf(itemUserAssigned.get(position)));

        holder.itemsRowsDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateItem.class);
                intent.putExtra("id", String.valueOf(itemID.get(holder.getAdapterPosition())));
                intent.putExtra("type", String.valueOf(itemType.get(holder.getAdapterPosition())));
                intent.putExtra("make", String.valueOf(itemMake.get(holder.getAdapterPosition())));
                intent.putExtra("model", String.valueOf(itemModel.get(holder.getAdapterPosition())));
                intent.putExtra("location", String.valueOf(itemLocation.get(holder.getAdapterPosition())));
                intent.putExtra("department", String.valueOf(itemDepartment.get(holder.getAdapterPosition())));
                intent.putExtra("userAssigned", String.valueOf(itemUserAssigned.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent, 1);
            }
        });
    }
    // Get Item count for CustomAdapter
    @Override
    public int getItemCount() {
        return itemID.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView displayItemID, displayItemType, displayItemMake, displayItemModel, displayItemLocation,
                displayItemDepartment, displayItemUserAssigned;
        LinearLayout itemsRowsDisplay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            displayItemID = itemView.findViewById(R.id.itemID);
            displayItemType = itemView.findViewById(R.id.itemType);
            displayItemMake = itemView.findViewById(R.id.itemMake);
            displayItemModel = itemView.findViewById(R.id.itemModel);
            displayItemLocation = itemView.findViewById(R.id.itemLocation);
            displayItemDepartment = itemView.findViewById(R.id.itemDepartment);
            displayItemUserAssigned = itemView.findViewById(R.id.userAssigned);
            itemsRowsDisplay = itemView.findViewById(R.id.itemsRowsDisplay);
        }
    }

}
