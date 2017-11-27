package byu.codemonkeys.tickettoride.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.game.ChatHistoryContract;
import byu.codemonkeys.tickettoride.views.widgets.VerticalSpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatHistoryFragment extends Fragment implements ChatHistoryContract.View {
	
	private ChatHistoryContract.Presenter presenter;
	private TextView textViewBack;
	private ImageButton imageButtonSendMessage;
	private EditText editTextMessage;
	private RecyclerView recyclerMessages;
	private LinearLayoutManager layoutManagerMessages;
	private MessagesRecyclerAdapter messagesAdapter;
	
	public ChatHistoryFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_chat_history, container, false);
		getViews(view);
		initRecycler(view);
		setEventListeners();
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		presenter.loadHistory();
	}
	
	private void setEventListeners() {
		this.textViewBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateBack();
			}
		});
		this.imageButtonSendMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.sendMessage(editTextMessage.getText().toString());
			}
		});
		
		this.editTextMessage.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanSendMessage(presenter.canSendMessage());
			}
		});
		this.editTextMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
				//				if (actionID == EditorInfo.IME_ACTION_DONE) {
				if (presenter.canSendMessage()) {
					presenter.sendMessage(editTextMessage.getText().toString());
				}
				//				}
				return false;
			}
		});
	}
	
	private void getViews(View view) {
		this.textViewBack = (TextView) view.findViewById(R.id.chatHistory_textViewBack);
		this.imageButtonSendMessage = (ImageButton) view.findViewById(R.id.chatHistory_imageButtonSendMessage);
		this.recyclerMessages = (RecyclerView) view.findViewById(R.id.chatHistory_recyclerMessages);
		this.editTextMessage = (EditText) view.findViewById(R.id.chatHistory_editTextMessage);
	}
	
	private void initRecycler(View view) {
		layoutManagerMessages = new LinearLayoutManager(getActivity());
		recyclerMessages.setLayoutManager(layoutManagerMessages);
		int messageSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
															 5,
															 this.getResources()
																 .getDisplayMetrics());
		recyclerMessages.addItemDecoration(new VerticalSpaceItemDecoration(messageSpacing));
	}
	
	// region ChatHistoryContract.View Implementation
	@Override
	public void clearMessages() {
		if (messagesAdapter == null) {
			List<String> messages = new ArrayList<>();
			messagesAdapter = new MessagesRecyclerAdapter(messages);
			recyclerMessages.setAdapter(messagesAdapter);
		} else {
			messagesAdapter.updateData(new ArrayList<String>());
		}
	}
	
	@Override
	public boolean isActive() {
		return this.isVisible();
	}
	
	@Override
	public void addMessage(final String message) {
		if (messagesAdapter == null) {
			List<String> messages = new ArrayList<>();
			messages.add(message);
			messagesAdapter = new MessagesRecyclerAdapter(messages);
			recyclerMessages.setAdapter(messagesAdapter);
		} else {
			messagesAdapter.appendData(message);
		}
		recyclerMessages.scrollToPosition(messagesAdapter.getItemCount() - 1);
	}
	
	@Override
	public void setCanSendMessage(boolean canSendMessage) {
		this.imageButtonSendMessage.setEnabled(canSendMessage);
	}
	
	@Override
	public String getCurrentMessage() {
		return this.editTextMessage.getText().toString();
	}
	
	@Override
	public void setCurrentMessage(final String message) {
		editTextMessage.setText(message);
	}
	
	public void setPresenter(ChatHistoryContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
