package com.skangude5.classmates.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skangude5.classmates.R;
import com.skangude5.classmates.model.ModelPost;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.MyHolder> {

    Context context;
    String myuid;
    private DatabaseReference liekeref, postref;
    boolean mprocesslike = false;
    boolean isNotice = false;

    public AdapterPosts(Context context, List<ModelPost> modelPosts, boolean isNotice) {
        this.context = context;
        this.modelPosts = modelPosts;
        myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        liekeref = FirebaseDatabase.getInstance().getReference().child("Likes");

        if (isNotice){
            postref = FirebaseDatabase.getInstance().getReference().child("Notices");
        } else {
            postref = FirebaseDatabase.getInstance().getReference().child("Posts");
        }


        this.isNotice = isNotice;
    }

    List<ModelPost> modelPosts;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_post, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, @SuppressLint("RecyclerView") int position) {
        final String uid = modelPosts.get(position).getUid();
        String nameh = modelPosts.get(position).getUname();
        final String titlee = modelPosts.get(position).getTitle();
        final String descri = modelPosts.get(position).getDescription();
        final String ptime = modelPosts.get(position).getPtime();
        String dp = modelPosts.get(position).getUdp();
        String plike = modelPosts.get(position).getPlike();
        final String image = modelPosts.get(position).getUimage();
        String email = modelPosts.get(position).getUemail();
        String comm = modelPosts.get(position).getPcomments();
        final String pid = modelPosts.get(position).getPtime();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(ptime));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
        holder.name.setText(nameh);
        if(isNotice){
            holder.title.setText(titlee);
        } else {
            holder.title.setVisibility(View.GONE);
        }
        holder.description.setText(descri);
        holder.time.setText(timedate);
        holder.like.setText(plike + " Likes");
        setLikes(holder, ptime);
//        try {
//            Glide.with(context).load(dp).into(holder.picture);
//        } catch (Exception e) {
//
//        }
        holder.image.setVisibility(View.VISIBLE);
        try {
            Glide.with(context).load(image).into(holder.image);
        } catch (Exception e) {

        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), PostLikedByActivity.class);
//                intent.putExtra("pid", pid);
//                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.likebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int plike = Integer.parseInt(modelPosts.get(position).getPlike());
                mprocesslike = true;
                final String postid = modelPosts.get(position).getPtime();
                liekeref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mprocesslike) {
                            if (dataSnapshot.child(postid).hasChild(myuid)) {
                                postref.child(postid).child("plike").setValue("" + (plike - 1));
                                liekeref.child(postid).child(myuid).removeValue();
                                mprocesslike = false;
                            } else {
                                postref.child(postid).child("plike").setValue("" + (plike + 1));
                                liekeref.child(postid).child(myuid).setValue("Liked");
                                mprocesslike = false;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    private void showMoreOptions(ImageButton more, String uid, String myuid, final String pid, final String image) {
        PopupMenu popupMenu = new PopupMenu(context, more, Gravity.END);
        if (uid.equals(myuid)) {
            popupMenu.getMenu().add(Menu.NONE, 0, 0, "DELETE");
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0) {
                    deltewithImage(pid, image);
                }

                return false;
            }
        });
        popupMenu.show();
    }

    private void deltewithImage(final String pid, String image) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Deleting");
        StorageReference picref = FirebaseStorage.getInstance().getReferenceFromUrl(image);
        picref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Query query = FirebaseDatabase.getInstance().getReference("Posts").orderByChild("ptime").equalTo(pid);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            dataSnapshot1.getRef().removeValue();
                        }
                        pd.dismiss();
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void setLikes(final MyHolder holder, final String pid) {
        liekeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(pid).hasChild(myuid)) {
                    holder.likebtn.setText("Liked");
                    holder.likebtn.setActivated(true);
                    holder.likebtn.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    holder.likebtn.setText("Like");
                    holder.likebtn.setActivated(false);
                    holder.likebtn.setTextColor(Color.parseColor("#455D7A"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return modelPosts.size();
    }

    public void updateData(List<ModelPost> posts) {
        this.modelPosts = posts;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, time, title, description, like;
        Button likebtn;
        LinearLayout profile;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            //picture = itemView.findViewById(R.id.picturetv);
            image = itemView.findViewById(R.id.pimagetv);
            name = itemView.findViewById(R.id.unametv);
            time = itemView.findViewById(R.id.utimetv);
            title = itemView.findViewById(R.id.ptitletv);
            description = itemView.findViewById(R.id.descript);
            like = itemView.findViewById(R.id.plikeb);
            likebtn = itemView.findViewById(R.id.like);
            profile = itemView.findViewById(R.id.profilelayout);
        }
    }
}
