package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.FavouriteDayActivity;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.database.DatabaseHandler;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.CalenderItem;

import java.util.List;

public class FavouriteDayAdapter extends BaseAdapter {

    Context mContext;
    List<CalenderItem> favouriteDayList;
    private LayoutInflater mInflater;
    DatabaseHandler databaseHandler;

    public FavouriteDayAdapter(Context context, List<CalenderItem> favouriteDayList){

        mContext=context;
        this.favouriteDayList=favouriteDayList;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return favouriteDayList.size();
    }

    @Override
    public Object getItem(int position) {
        return favouriteDayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final  ViewHolder viewHolder;
        databaseHandler=new DatabaseHandler(mContext);
        if (convertView==null){

            viewHolder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.listview_important_days, null);

            viewHolder.textViewHeading= (TextView) convertView.findViewById(R.id.textView_month);
            viewHolder.textViewContent= (TextView) convertView.findViewById(R.id.textView_content);
            viewHolder.textViewDay= (TextView) convertView.findViewById(R.id.textView_day);
            viewHolder.textViewWeek= (TextView) convertView.findViewById(R.id.textView_week);
            viewHolder.imageButtonDelete = (ImageButton) convertView.findViewById(R.id.imageButton_delete);
            viewHolder.cardViewMain= (CardView) convertView.findViewById(R.id.cardView_main);
            viewHolder.textViewEmpty= (TextView) convertView.findViewById(R.id.textView_no_favourites);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

            String month = favouriteDayList.get(position).getMonth().trim();
            month = month.replace("2016", "").trim();
            month = month.replace("2017", "").trim();

            switch (month) {
                case "January":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.jan));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);
                    break;
                case "February":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.feb));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "March":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.mar));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "April":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.apr));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "May":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.may));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "June":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.june));
                    viewHolder.textViewHeading.setTextColor(Color.BLACK);

                    break;
                case "July":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.july));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "August":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.aug));
                    viewHolder.textViewHeading.setTextColor(Color.BLACK);

                    break;
                case "September":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.sep));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "October":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.oct));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "November":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.nov));
                    viewHolder.textViewHeading.setTextColor(Color.WHITE);

                    break;
                case "December":
                    viewHolder.textViewHeading.setBackgroundColor(mContext.getResources().getColor(R.color.dec));
                    viewHolder.textViewHeading.setTextColor(Color.BLACK);

                    break;

            }


            if (favouriteDayList.get(position).getHoliday().trim().equals("yes")) {

                viewHolder.textViewContent.setTextColor(Color.RED);

            } else {
                viewHolder.textViewContent.setTextColor(Color.BLACK);

            }
            viewHolder.textViewHeading.setText(favouriteDayList.get(position).getMonth());
            viewHolder.textViewDay.setText(String.valueOf(favouriteDayList.get(position).getDay()));
            viewHolder.textViewWeek.setText(favouriteDayList.get(position).getWeek());
            viewHolder.textViewContent.setText(favouriteDayList.get(position).getContent());

            viewHolder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHandler.deleteFavourite(favouriteDayList.get(position));
                    favouriteDayList.remove(position);
                    if (favouriteDayList.size()==0){
                        FavouriteDayActivity.textViewEmpty.setVisibility(View.VISIBLE);
                        FavouriteDayActivity.listViewFavourite.setVisibility(View.GONE);
                    }
                    else {
                        FavouriteDayActivity.textViewEmpty.setVisibility(View.GONE);
                        FavouriteDayActivity.listViewFavourite.setVisibility(View.VISIBLE);
                    }
                    notifyDataSetChanged();
                    Toast.makeText(mContext,"Removed successfully",Toast.LENGTH_SHORT).show();

                }
            });


        return convertView;
    }


    public static class ViewHolder {
        public TextView textViewHeading;
        public TextView textViewDay;
        public TextView textViewWeek;
        public TextView textViewContent;
        public ImageButton imageButtonDelete;
        public CardView cardViewMain;
        public TextView textViewEmpty;
    }
}
