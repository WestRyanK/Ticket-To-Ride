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
import byu.codemonkeys.tickettoride.mvpcontracts.home.ConnectionSettingsContract;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectionSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectionSettingsFragment extends Fragment implements ConnectionSettingsContract.View {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	//	private static final String ARG_PARAM1 = "param1";
	//	private static final String ARG_PARAM2 = "param2";
	private ConnectionSettingsContract.Presenter presenter;
	private EditText editTextHost;
	private EditText editTextPort;
	private TextView buttonSave;
	//	private Button buttonSave;
	private TextView textViewCancel;
	
	// TODO: Rename and change types of parameters
	//	private String mParam1;
	//	private String mParam2;
	
	
	public ConnectionSettingsFragment() {
		// Required empty public constructor
	}
	
	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 * <p>
	 * //	 * @param param1 Parameter 1.
	 * //	 * @param param2 Parameter 2.
	 *
	 * @return A new instance of fragment ConnectionSettingsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ConnectionSettingsFragment newInstance() {
		ConnectionSettingsFragment fragment = new ConnectionSettingsFragment();
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
		View view = inflater.inflate(R.layout.fragment_connection_settings, container, false);
		
		this.editTextHost = (EditText) view.findViewById(R.id.connection_editTextHost);
		this.editTextPort = (EditText) view.findViewById(R.id.connection_editTextPort);
		//		this.buttonSave = (Button) view.findViewById(R.id.connection_buttonSave);
		this.buttonSave = (TextView) view.findViewById(R.id.connection_buttonSave);
		this.textViewCancel = (TextView) view.findViewById(R.id.connection_textViewCancel);
		
		this.editTextHost.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanSave(presenter.canSaveConnectionSettings());
			}
		});
		
		this.editTextPort.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				
			}
			
			@Override
			public void afterTextChanged(Editable editable) {
				setCanSave(presenter.canSaveConnectionSettings());
			}
		});
		
		this.buttonSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				presenter.saveConnectionSettings();
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
	
	// region ConnectionSettingsContract.View
	@Override
	public void setHostName(String host) {
		this.editTextHost.setText(host);
	}
	
	@Override
	public String getHostName() {
		return this.editTextHost.getText().toString();
	}
	
	@Override
	public void setPort(String port) {
		this.editTextPort.setText(port);
	}
	
	@Override
	public String getPort() {
		return this.editTextPort.getText().toString();
	}
	
	@Override
	public void setCanSave(Boolean canSave) {
		this.buttonSave.setEnabled(canSave);
	}
	
	public void setPresenter(ConnectionSettingsContract.Presenter presenter) {
		this.presenter = presenter;
	}
	// endregion
}
