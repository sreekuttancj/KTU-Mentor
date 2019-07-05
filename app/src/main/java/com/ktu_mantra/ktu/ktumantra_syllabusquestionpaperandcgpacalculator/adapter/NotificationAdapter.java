package com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.R;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.ExpandableNotification;
import com.ktu_mantra.ktu.ktumantra_syllabusquestionpaperandcgpacalculator.helper.NotificationItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<NotificationItem> notificationList;
    private Context context;
    long timeDiff;
    SimpleDateFormat df;
    long currentDate;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewContent;
        TextView textViewTimeStamp;
        TextView textViewHeading;
        TextView textViewLink;
        ImageButton buttonExpand;
        ImageView imageViewNotification;
        RelativeLayout relativeLayoutMore;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewContent= (TextView) itemView.findViewById(R.id.text_view_content);
            textViewTimeStamp= (TextView) itemView.findViewById(R.id.text_view_timestamp);
            textViewHeading= (TextView) itemView.findViewById(R.id.text_view_heading);
            textViewLink= (TextView) itemView.findViewById(R.id.text_view_link);
            buttonExpand= (ImageButton) itemView.findViewById(R.id.image_button_expand);
            imageViewNotification= (ImageView) itemView.findViewById(R.id.image_view_notification);
            relativeLayoutMore= (RelativeLayout) itemView.findViewById(R.id.re_more);
        }
    }

    public NotificationAdapter(List<NotificationItem> notificationList, Context context){

        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        currentDate=System.currentTimeMillis();

        this.notificationList=notificationList;
        this.context=context;
    }

    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotificationAdapter.MyViewHolder holder, int position) {


        final NotificationItem notification=notificationList.get(position);
        holder.textViewHeading.setText(notification.getHeading());
        holder.textViewContent.setText(Html.fromHtml(notification.getContent()));
        holder.textViewLink.setText(Html.fromHtml(notification.getLink()));
        holder.textViewLink.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            Date date = df.parse(notification.getTimestamp());
            long fromDate=date.getTime();
            timeDiff=currentDate-fromDate;
            timeDiff=timeDiff/(24 * 60 * 60 * 1000);
            Log.i("timeDiff",String.valueOf(timeDiff));
            String relativeTime= (String) DateUtils.getRelativeTimeSpanString(fromDate,System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);


            if (timeDiff<=7){

                holder.imageViewNotification.setVisibility(View.VISIBLE);
            }
            else {
                holder.imageViewNotification.setVisibility(View.GONE);

            }

            holder.relativeLayoutMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.textViewContent.isShown()){

                        holder.textViewContent.setVisibility(View.GONE);
                        holder.textViewLink.setVisibility(View.GONE);
                        holder.textViewTimeStamp.setVisibility(View.VISIBLE);
                        holder.buttonExpand.setBackgroundResource(R.drawable.button_down_disabled);
                        ExpandableNotification.slide_up(context,holder.textViewContent);
                        ExpandableNotification.slide_up(context,holder.textViewLink);

                    }
                    else {

                        holder.textViewContent.setVisibility(View.VISIBLE);
                        holder.textViewLink.setVisibility(View.VISIBLE);
                        holder.textViewTimeStamp.setVisibility(View.GONE);
                        holder.buttonExpand.setBackgroundResource(R.drawable.button_up_disabled);

                        ExpandableNotification.slide_down(context, holder.textViewContent);
                        ExpandableNotification.slide_down(context,holder.textViewLink);

                    }
                }
            });

            holder.textViewTimeStamp.setText(relativeTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
