/*******************************************************************************
 * Copyright 2013-2016 alladin-IT GmbH
 * Copyright 2013-2016 Rundfunk und Telekom Regulierungs-GmbH (RTR-GmbH)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package at.rtr.rmbt.android.terms;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import at.alladin.rmbt.android.R;
import at.rtr.rmbt.android.main.RMBTMainActivity;
import at.rtr.rmbt.android.terms.RMBTCheckFragment.CheckType;
import at.rtr.rmbt.android.util.ConfigHelper;

public class RMBTTermsCheckFragment extends Fragment
{
//    private static final String DEBUG_TAG = "RMBTTermsCheckFragment";
    
    private boolean firstTime = true;
    
    private View view;
    
    private CheckType followedByType;
    
    public static RMBTTermsCheckFragment newInstance(final CheckType followedBy) {
    	final RMBTTermsCheckFragment f = new RMBTTermsCheckFragment();
    	final Bundle bdl = new Bundle(1);
    	bdl.putSerializable("followedByType", followedBy);
        f.setArguments(bdl);
        return f;
    }
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		followedByType = (CheckType) getArguments().get("followedByType");
	}
    
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.terms_check, container, false);

        final WebView tcWvl = (WebView) view.findViewById(R.id.termsCheckWebView);

        //in case of an update to the terms and conditions, this may change
        String tcUrl = ConfigHelper.getTCUrl(getActivity());
        if (tcUrl != null) {
            tcWvl.loadUrl(tcUrl);
        }
        else {
            tcWvl.loadUrl("file:///android_res/raw/terms_conditions.html");
        }

        final Activity activity = getActivity();
        if (! (activity instanceof RMBTMainActivity))
            firstTime = false;
        
        if (! firstTime)
            view.findViewById(R.id.termsButtonDecline).setVisibility(View.GONE);
        
        final Button buttonTermsAccept = (Button) view.findViewById(R.id.termsAcceptButton);
        buttonTermsAccept.setFocusable(true); //set focus on button so that terms can be accepted on Android TV like devices (5-way-navigation)
        buttonTermsAccept.setFocusableInTouchMode(false);
        buttonTermsAccept.requestFocus();
        buttonTermsAccept.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                ConfigHelper.setTCAccepted(getActivity(), true);
                if (firstTime)
                {
                    ((RMBTMainActivity)getActivity()).checkSettings(true, null);
                    final boolean wasNDTTermsNecessary = ((RMBTMainActivity)getActivity()).showNdtCheckIfNecessary();
                    if (! wasNDTTermsNecessary)
                        ((RMBTMainActivity) activity).initApp(false);
                }
                else if (followedByType != null) {
                	((RMBTTermsActivity)getActivity()).continueWorkflow();
                }
            }
        });
        
        final Button buttonTermsDecline = (Button) view.findViewById(R.id.termsDeclineButton);
        buttonTermsDecline.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                onBackPressed();
            }
        });
        
        return view;
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        final Activity activity = getActivity();
        final boolean tcAccepted = ConfigHelper.isTCAccepted(activity);
        if (tcAccepted)
        {
            final TextView buttonTermsAccept = (TextView) view.findViewById(R.id.termsAcceptButton);
            buttonTermsAccept.setFocusable(true); //set focus on button so that terms can be accepted on Android TV like devices (5-way-navigation)
            buttonTermsAccept.setFocusableInTouchMode(false);
            buttonTermsAccept.requestFocus();
            buttonTermsAccept.setText(R.string.terms_accept_button_continue);
            view.findViewById(R.id.termsAcceptText).setVisibility(View.GONE);
        }
    }

    public boolean onBackPressed()
    {
        // user has declined t+c!
        
        ConfigHelper.setTCAccepted(getActivity(), false);
        ConfigHelper.setUUID(getActivity(), "");
        getActivity().finish();
        return true;
    }
}
