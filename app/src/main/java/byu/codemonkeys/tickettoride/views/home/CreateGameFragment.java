package byu.codemonkeys.tickettoride.views.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.home.CreateGameContract;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CreateGameFragment extends Fragment implements CreateGameContract.View {
	private CreateGameContract.Presenter presenter;
	private EditText editTextGameName;
	private Button buttonCreateGame;
	private Button textViewCancel;
	
	
	public CreateGameFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_create_game, container, false);
		
		getViews(view);
		setTextChangedListeners();
		setOnClickListeners();
		
		presenter.setDefaults();
		return view;
	}
	
	private void setOnClickListeners() {
		
		this.buttonCreateGame.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.createGame();
			}
		});
		
		this.textViewCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.cancel();
			}
		});
		
	}
	
	private void setTextChangedListeners() {
		this.editTextGameName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanCreateGame(presenter.canCreateGame());
			}
		});
	}
	
	private void getViews(View view) {
		this.editTextGameName = (EditText) view.findViewById(R.id.createGame_editTextGameName);
		this.buttonCreateGame = (Button) view.findViewById(R.id.createGame_buttonCreateGame);
		//		this.buttonCreateGame = (Button) view.findViewById(R.id.createGame_buttonCreateGame);
		this.textViewCancel = (Button) view.findViewById(R.id.createGame_textViewCancel);
		
	}
	
	// region CreateGameContract.View implementation
	@Override
	public void setGameName(String gameName) {
		this.editTextGameName.setText(gameName);
	}
	
	@Override
	public String getGameName() {
		return this.editTextGameName.getText().toString();
	}
	
	@Override
	public void setCanCreateGame(boolean canCreateGame) {
		this.buttonCreateGame.setEnabled(canCreateGame);
	}
	
	public void setPresenter(CreateGameContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
