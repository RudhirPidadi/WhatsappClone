package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class WhatsappUsersActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayList<String> listOfUsers;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_users);
        swipeRefreshLayout=findViewById(R.id.mySwipeRefreshlayout);
        listView=findViewById(R.id.listView);
        listOfUsers=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(WhatsappUsersActivity.this,android.R.layout.simple_list_item_1, listOfUsers);
        listView.setOnItemClickListener(this);

        try{
            ParseQuery<ParseUser> parseQuery= ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e)
                {
                 if(objects.size()>0 && e==null)
                 {
                     for(ParseUser whatsappUser : objects)
                     {
                         listOfUsers.add(whatsappUser.getUsername());
                     }
                     listView.setAdapter(arrayAdapter);
                 }
                }
            });
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {

                  try {

                      ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
                      parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
                      parseQuery.whereNotContainedIn("username", listOfUsers);
                      parseQuery.findInBackground(new FindCallback<ParseUser>() {
                          @Override
                          public void done(List<ParseUser> objects, ParseException e) {
                              if (e == null)
                              {
                                  if (objects.size() > 0) {

                                      for (ParseUser whatsappUser : objects)
                                      {
                                          listOfUsers.add(whatsappUser.getUsername());
                                      }

                                      arrayAdapter.notifyDataSetChanged();

                                      if (swipeRefreshLayout.isRefreshing())
                                      {
                                          swipeRefreshLayout.setRefreshing(false);
                                      }

                                  }
                                  else
                                      {
                                      if (swipeRefreshLayout.isRefreshing())
                                      {
                                          swipeRefreshLayout.setRefreshing(false);
                                      }
                                  }
                              }

                          }
                      });


                  }catch (Exception e)
                  {
                      e.printStackTrace();
                  }

            }

        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

    }
}
