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
import byu.codemonkeys.tickettoride.mvpcontracts.home.LoginContract;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginContract.View {
	private LoginContract.Presenter presenter;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button buttonLogin;
	private Button textViewRegister;
	private Button textViewConnectionSettings;
	
	
	public LoginFragment() {
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
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		getViews(view);
		setTextChangedListeners();
		setOnClickListeners();
		
		presenter.setDefaults();
		return view;
	}
	
	private void setOnClickListeners() {
		
		this.buttonLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.login();
			}
		});
		
		this.textViewRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateRegisterUser();
			}
		});
		
		this.textViewConnectionSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.navigateConnectionSettings();
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
				setCanLogin(presenter.canLogin());
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
				setCanLogin(presenter.canLogin());
			}
		});
	}
	
	private void getViews(View view) {
		// Get references to byu.codemonkeys.tickettoride.views.widgets in the view
		this.editTextUsername = (EditText) view.findViewById(R.id.login_editTextUsername);
		this.editTextPassword = (EditText) view.findViewById(R.id.login_editTextPassword);
		this.buttonLogin = (Button) view.findViewById(R.id.login_buttonLogin);
		this.textViewRegister = (Button) view.findViewById(R.id.login_textViewRegister);
		this.textViewConnectionSettings = (Button) view.findViewById(R.id.login_textViewConnectionSettings);
	}
	
	// region LoginContract.View implementation
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
	public void setCanLogin(Boolean canLogin) {
		this.buttonLogin.setEnabled(canLogin);
	}
	
	public void setPresenter(LoginContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
