package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.CalenderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CalenderAdapter extends BaseAdapter{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private List<CalenderItem> mData = new ArrayList<CalenderItem>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;
    List<CalenderItem> calenderFavourite;
    List<Integer> calenderFavIdList;
    Context mContext;
    DatabaseHandler databaseHandler;

    public CalenderAdapter(Context context) {
        mContext=context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        databaseHandler=new DatabaseHandler(context);
            }

    public void addItem(final CalenderItem calenderItem) {
        mData.add(calenderItem);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final CalenderItem item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

     final ViewHolder holder;
        int rowType = getItemViewType(position);
        calenderFavourite=databaseHandler.getCalenderFavourite();
        calenderFavIdList=databaseHandler.getCalenderFavId();
        if (convertView == null) {

            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.listview_calender_content, null);

                    holder.textViewContent= (TextView) convertView.findViewById(R.id.textView_content);
                    holder.textViewDay= (TextView) convertView.findViewById(R.id.textView_day);
                    holder.textViewWeek= (TextView) convertView.findViewById(R.id.textView_week);
                    holder.toggleButtonFavourite = (ToggleButton) convertView.findViewById(R.id.imageButton_favorite);
                    break;

                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.listview_calender_header, null);
                    holder.textViewHeading= (TextView) convertView.findViewById(R.id.textView_header);
                    break;
            }

                    convertView.setTag(holder);
            }
            else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (rowType) {
            case TYPE_ITEM:

                holder.textViewDay.setText(String.valueOf(mData.get(position).getDay()));
                holder.textViewWeek.setText(String.valueOf(mData.get(position).getWeek()));
                holder.textViewContent.setText(String.valueOf(mData.get(position).getContent()));

                holder.toggleButtonFavourite.setText(null);
                holder.toggleButtonFavourite.setTextOff(null);
                holder.toggleButtonFavourite.setTextOn(null);
                holder.toggleButtonFavourite.setBackgroundResource(R.drawable.btn_star);


                if (mData.get(position).getHoliday().trim().equals("yes")){
                    holder.textViewContent.setTextColor(Color.RED);
                }
                else {
                    holder.textViewContent.setTextColor(Color.BLACK);
                }
                    if (calenderFavIdList.contains(mData.get(position).getId())) {
                        holder.toggleButtonFavourite.setBackgroundResource(R.drawable.btn_star_filled);
                    }

                final View finalConvertView = convertView;
                holder.toggleButtonFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (calenderFavIdList.contains(mData.get(position).getId())) {
                                databaseHandler.deleteFavourite(mData.get(position));
                                calenderFavourite.remove(mData.get(position));
                                buttonView.setBackgroundResource(R.drawable.btn_star);
                                Toast.makeText(mContext, "Removed successfully", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            } else {
                                databaseHandler.addCalenderFavourite(mData.get(position));
                                calenderFavourite.add(mData.get(position));
                                buttonView.setBackgroundResource(R.drawable.btn_star_filled);
                                Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }

                        }

                });

                break;
            case TYPE_SEPARATOR:
                holder.textViewHeading.setText(String.valueOf(mData.get(position).getMonth()));
                String month=mData.get(position).getMonth().trim();
                month=month.replace("2016","").trim();
                month=month.replace("2017","").trim();

                switch (month){
                    case "January":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.jan));
                        holder.textViewHeading.setTextColor(Color.WHITE);
                        break;
                    case "February":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.feb));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "March":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.mar));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "April":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.apr));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "May":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.may));
                        holder.textViewHeading .setTextColor(Color.WHITE);

                        break;
                    case "June":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.june));
                        holder.textViewHeading.setTextColor(Color.BLACK);

                        break;
                    case "July":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.july));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "August":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.aug));
                        holder.textViewHeading.setTextColor(Color.BLACK);

                        break;
                    case "September":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.sep));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "October":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.oct));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "November":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.nov));
                        holder.textViewHeading.setTextColor(Color.WHITE);

                        break;
                    case "December":
                        holder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.dec));
                        holder.textViewHeading.setTextColor(Color.BLACK);

                        break;

                }

                break;
        }


                return convertView;
    }

    private static class ViewHolder {
        private TextView textViewHeading;
        private TextView textViewDay;
        private TextView textViewWeek;
        private TextView textViewContent;
        private ToggleButton toggleButtonFavourite;

    }
}
