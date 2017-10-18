package byu.codemonkeys.tickettoride.views.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.CreateGameContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGameFragment extends Fragment implements CreateGameContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	private CreateGameContract.Presenter presenter;
	private EditText editTextGameName;
	private TextView buttonCreateGame;
	//	private Button buttonCreateGame;
	private TextView textViewCancel;
	
	
	public CreateGameFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment CreateGameFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static CreateGameFragment newInstance() {
		CreateGameFragment fragment = new CreateGameFragment();
		Bundle args = new Bundle();
		//		args.putString(ARG_PARAM1, param1);
		//		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			//			mParam1 = getArguments().getString(ARG_PARAM1);
			//			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_create_game, container, false);
		
		this.editTextGameName = (EditText) view.findViewById(R.id.createGame_editTextGameName);
		this.buttonCreateGame = (TextView) view.findViewById(R.id.createGame_buttonCreateGame);
		//		this.buttonCreateGame = (Button) view.findViewById(R.id.createGame_buttonCreateGame);
		this.textViewCancel = (TextView) view.findViewById(R.id.createGame_textViewCancel);
		
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
		
		presenter.setDefaults();
		return view;
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
