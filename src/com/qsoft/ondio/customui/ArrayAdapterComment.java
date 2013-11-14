package com.qsoft.ondio.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.ondio.R;
import com.qsoft.ondio.model.Comment;

import java.util.ArrayList;

public class ArrayAdapterComment extends ArrayAdapter<Comment>
{
    private TextView tvAccount;
    private TextView tvComment;
    private TextView tvTimeComment;
    private ImageView ivAvatar;
    private Context context;
    private ArrayList<Comment> comments;

    public ArrayAdapterComment(Context context, int textViewResourceId, ArrayList<Comment> comments)
    {
        super(context, textViewResourceId, comments);
        this.comments = comments;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;
        if (v == null)
        {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.comment_list, null);
        }
        Comment comment = comments.get(position);
        setUpViewFindByID(v);
        if (comment != null)
        {
            setDataToItemListComment(comment);
        }
        return v;
    }

    private void setDataToItemListComment(Comment comment)
    {
        tvAccount.setText(comment.getAccount());
        tvComment.setText(comment.getComments());
        tvTimeComment.setText(comment.getTimeComment());
    }

    private void setUpViewFindByID(View v)
    {
        tvAccount = (TextView) v.findViewById(R.id.comment_tvAccount);
        tvComment = (TextView) v.findViewById(R.id.comment_tvComment);
        tvTimeComment = (TextView) v.findViewById(R.id.comment_tvTimeComment);
    }
}
