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

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.home.RegisterContract;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {
	// TODO: Rename parameter arguments, choose names that match
	
	// TODO: Rename and change types of parameters
	private RegisterContract.Presenter presenter;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button buttonRegister;
	private Button buttonCancel;
	
	public RegisterFragment() {
		// Required empty public constructor
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
		View view = inflater.inflate(R.layout.fragment_register, container, false);
		getViews(view);
		setTextChangedListeners();
		setOnClickListeners();
		
		presenter.setDefaults();
		return view;
	}
	
	private void setOnClickListeners() {
		this.buttonRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.register();
			}
		});
		
		this.buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.cancel();
			}
		});
		
	}
	
	private void setTextChangedListeners() {
		this.editTextUsername.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanRegister(presenter.canRegister());
			}
		});
		
		this.editTextPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanRegister(presenter.canRegister());
			}
		});
	}
	
	private void getViews(View view) {
		editTextUsername = (EditText) view.findViewById(R.id.register_editTextUsername);
		editTextPassword= (EditText) view.findViewById(R.id.register_editTextPassword);
		buttonRegister= (Button) view.findViewById(R.id.register_buttonRegister);
		buttonCancel = (Button) view.findViewById(R.id.register_buttonCancel);
	}
	
	// region RegisterContract.View implementation
	@Override
	public void setUsername(String username) {
		this.editTextUsername.setText(username);
	}
	
	@Override
	public String getUsername() {
		return this.editTextUsername.getText().toString();
	}
	
	@Override
	public void setPassword(String password) {
		this.editTextPassword.setText(password);
	}
	
	@Override
	public String getPassword() {
		return this.editTextPassword.getText().toString();
	}
	
	@Override
	public void setCanRegister(Boolean canRegister) {
		this.buttonRegister.setEnabled(canRegister);
	}
	
	public void setPresenter(RegisterContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
