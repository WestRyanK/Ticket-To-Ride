package byu.codemonkeys.tickettoride.views;


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
import byu.codemonkeys.tickettoride.mvpcontracts.LoginContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	private LoginContract.Presenter presenter;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button buttonLogin;
	private TextView textViewRegister;
	private TextView textViewConnectionSettings;
	
	
	public LoginFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment LoginFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static LoginFragment newInstance() {
		LoginFragment fragment = new LoginFragment();
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
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		// Get references to widgets in the view
		this.editTextUsername = (EditText) view.findViewById(R.id.login_editTextUsername);
		this.editTextPassword = (EditText) view.findViewById(R.id.login_editTextPassword);
		this.buttonLogin = (Button) view.findViewById(R.id.login_buttonLogin);
		this.textViewRegister = (TextView) view.findViewById(R.id.login_textViewRegister);
		this.textViewConnectionSettings = (TextView) view.findViewById(R.id.login_textViewConnectionSettings);
		
		this.editTextUsername.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				presenter.setUsername(editable.toString());
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
				presenter.setPassword(editable.toString());
				//				Toast.makeText(getActivity(), editable.toString(), Toast.LENGTH_SHORT).show();
			}
		});
		
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
		return view;
	}
	
	// region LoginContract.View implementation
	@Override
	public void setUsername(String username) {
		this.editTextUsername.setText(username);
	}
	
	@Override
	public void setPassword(String password) {
		this.editTextPassword.setText(password);
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
