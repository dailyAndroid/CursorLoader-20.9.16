package com.example.hwhong.cursorloaderexplore;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//Common base class of common implementation for an Adapter that can be used in both ListView (by
// implementing the specialized ListAdapter interface) and Spinner (by implementing the specialized
// SpinnerAdapter interface).

public class MyAdapter extends BaseAdapter {

    private Context ctx;
    private Cursor cursor;
    private LayoutInflater inflater;

    public MyAdapter(Context context, Cursor cursor) {
        ctx = context;
        this.cursor = cursor;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {

        TextView name, number;
        ImageView imageview;

    }


    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;
        ViewHolder holder;
        cursor.moveToPosition(i);

        if (view == null) {

            //instantiating
            view = inflater.inflate(R.layout.row, viewGroup,
                    false);
            holder = new ViewHolder();
            holder.name = (TextView) view
                    .findViewById(R.id.name);
            holder.number = (TextView) view
                    .findViewById(R.id.number);
            holder.imageview = (ImageView) view.findViewById(R.id.imageview);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        //setting the data
        holder.name.setText(cursor.getString(cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
        holder.number.setText(cursor.getString(cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        String string = cursor.getString(cursor
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
        try {

            //setting the imageview
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    ctx.getContentResolver(), Uri.parse(string));
            holder.imageview.setImageBitmap(bitmap);
            scaleImage(holder.imageview);
        } catch (Exception e) {

            //if parsing is not successful
            holder.imageview.setImageResource(R.drawable.google);
            scaleImage(holder.imageview);
        }
        return view;
    }

    private void scaleImage(ImageView imageView) {
        Drawable drawing = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int bounding = dpToPx(50);
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        imageView.setImageDrawable(result);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView
                .getLayoutParams();
        params.width = width;
        params.height = height;
        imageView.setLayoutParams(params);
    }

    private int dpToPx(int dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

}
