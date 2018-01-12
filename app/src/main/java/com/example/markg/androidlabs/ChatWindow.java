package com.example.markg.androidlabs;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ChatWindow extends Activity {

    protected static final int REQUEST_MSG_DETAILS = 1;
    protected static final int REQUEST_MSG_DELETE = 2;

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";

    SQLiteDatabase db;
    ChatDatabaseHelper dbHelper;

    ArrayList<Long> chatIDs;
    ArrayList<String> chatMsgs;
    ListView chatListView;
    EditText chatEditText;
    Button sendButton;
    //ChatAdapter messageAdapter;
    protected boolean isTablet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        String[] allColumns = {ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE};
        dbHelper = new ChatDatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        chatListView = (ListView) findViewById(R.id.chatListView);
        chatEditText = (EditText) findViewById(R.id.chatEditText);
        sendButton = (Button) findViewById(R.id.sendButton);
        chatMsgs = new ArrayList<>();
        chatIDs = new ArrayList<>();

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatListView.setAdapter(messageAdapter);

        isTablet = (findViewById(R.id.messageDetailFrame) != null);


        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_MESSAGES,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            chatMsgs.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            chatIDs.add(cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
            cursor.moveToNext();
        }
        cursor.close();

        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Bundle newBundle = new Bundle();
                newBundle.putString("message", messageAdapter.getItem(position));
                newBundle.putLong("id", messageAdapter.getItemID(position));

                // Action if tablet
                if (isTablet)
                {
                    MessageFragment messageFragment = new MessageFragment(ChatWindow.this);
                    messageFragment.setArguments(newBundle);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.messageDetailFrame, messageFragment);
                    ft.commit();
                }
                else
                {
                    Intent msgDetailsIntent = new Intent(getApplicationContext(), MessageDetails.class);
                    msgDetailsIntent.putExtras(newBundle);
                    startActivityForResult(msgDetailsIntent, REQUEST_MSG_DETAILS);
                }
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!chatEditText.getText().toString().trim().equals("")) {

                    // add text to messages array
                    chatMsgs.add(chatEditText.getText().toString());

                    // add text to database
                    ContentValues insertValues = new ContentValues();
                    insertValues.put(ChatDatabaseHelper.KEY_MESSAGE,
                            chatEditText.getText().toString());
                    // insert into the db while adding to the chatIDs array
                    chatIDs.add(db.insert(ChatDatabaseHelper.TABLE_MESSAGES, null, insertValues));


                    messageAdapter.notifyDataSetChanged();
                    chatEditText.setText("");
                }
            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<String>
    {
        private ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return chatMsgs.size();
        }

        public String getItem(int position)
        {
            return chatMsgs.get(position);
        }

        public Long getItemID(int position)
        {
            return chatIDs.get(position);
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent)
        {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
            {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            }
            else
            {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        db.close();
        dbHelper.close();
    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        if (resultCode == REQUEST_MSG_DELETE)
        {
            Long msgID = data.getLongExtra("msgID",-1);
            Log.i(ACTIVITY_NAME, "Request Deletion of message with id: " + Long.toString(msgID));
            deleteItem(msgID);
        }
    }

    public void deleteItem(long id)
    {
        db.delete(ChatDatabaseHelper.TABLE_MESSAGES, ChatDatabaseHelper.KEY_ID + "=" + id, null);

        int position = chatIDs.indexOf(id);
        chatMsgs.remove(position);
        chatIDs.remove(position);

        final ChatAdapter adapter = (ChatAdapter) chatListView.getAdapter();
        adapter.notifyDataSetChanged();
    }

}
