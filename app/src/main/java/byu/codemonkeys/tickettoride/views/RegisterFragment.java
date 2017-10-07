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
import android.widget.Toast;

import byu.codemonkeys.tickettoride.R;
import byu.codemonkeys.tickettoride.mvpcontracts.RegisterContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements RegisterContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	private RegisterContract.Presenter presenter;
	private EditText editTextUsername;
	private EditText editTextPassword;
	private Button buttonRegister;
	private TextView textViewCancel;
	
	public RegisterFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment RegisterFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static RegisterFragment newInstance() {
		RegisterFragment fragment = new RegisterFragment();
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
		View view = inflater.inflate(R.layout.fragment_register, container, false);
		this.editTextUsername = (EditText) view.findViewById(R.id.register_editTextUsername);
		this.editTextPassword = (EditText) view.findViewById(R.id.register_editTextPassword);
		this.buttonRegister = (Button) view.findViewById(R.id.register_buttonRegister);
		this.textViewCancel = (TextView) view.findViewById(R.id.register_textViewCancel);
		
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
		
		this.buttonRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.register();
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
