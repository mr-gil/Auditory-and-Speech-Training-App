package hk.org.deaf.auditoryandspeechtrainingapp;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ManualFragmentActivity extends FullScreenFragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setToFullScreen();
		setContentView(R.layout.manual_page);
		setContentLayout();
		setupScrollView();
	}
	
	private void setContentLayout(){
		//xmlLayoutConnection(R.drawable.box_pg, R.layout.pg_content, R.drawable.bg_cover);
		
		ImageButton backPageBtn = (ImageButton) findViewById(R.id.main_page_back_btn);
		backPageBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void setupScrollView() {

		TextView tv = (TextView) findViewById(R.id.manual_page_content_txt);
		Typeface face = Typeface.createFromAsset(getAssets(),
				"fonts/DFT_WW7.TTF");
		tv.setTypeface(face);
		tv.setText(Html.fromHtml(getString(R.string.manual_txt), new ImageGetter(), null));
		
		
	}
	
	private class ImageGetter implements Html.ImageGetter {

	    public Drawable getDrawable(String source) {
	        int id;

	        if (source.equals("conson.png")) {
	            id = R.drawable.conson;
	        }
	        else if (source.equals("conson2.png")){
	        	id = R.drawable.conson2;
	        }
	        else if (source.equals("conson3.png")){
	        	id = R.drawable.conson3;
	        }
	        else {
	            return null;
	        }

	        Drawable d = getResources().getDrawable(id);
	        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
	        return d;
	    }
	}
}
