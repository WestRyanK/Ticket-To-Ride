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
import byu.codemonkeys.tickettoride.mvpcontracts.home.ConnectionSettingsContract;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ConnectionSettingsFragment extends Fragment implements ConnectionSettingsContract.View {
	private ConnectionSettingsContract.Presenter presenter;
	private EditText editTextHost;
	private EditText editTextPort;
	private Button buttonSave;
	private Button textViewCancel;
	
	public ConnectionSettingsFragment() {
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
		View view = inflater.inflate(R.layout.fragment_connection_settings, container, false);
		
		getViews(view);
		setTextChangedListeners();
		setOnClickListeners();
		
		presenter.setDefaults();
		return view;
	}
	
	private void setTextChangedListeners() {
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
	}
	
	private void setOnClickListeners() {
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
	}
	
	private void getViews(View view) {
		this.editTextHost = (EditText) view.findViewById(R.id.connection_editTextHost);
		this.editTextPort = (EditText) view.findViewById(R.id.connection_editTextPort);
		this.buttonSave = (Button) view.findViewById(R.id.connection_buttonSave);
		this.textViewCancel = (Button) view.findViewById(R.id.connection_textViewCancel);
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
