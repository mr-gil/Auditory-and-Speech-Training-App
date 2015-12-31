package hk.org.deaf.auditoryandspeechtrainingapp.result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;

import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewStyle;
import com.jjoe64.graphview.LineGraphView;

import hk.org.deaf.auditoryandspeechtrainingapp.L1andL2ResultCommonFunction;
import hk.org.deaf.auditoryandspeechtrainingapp.R;
import hk.org.deaf.auditoryandspeechtrainingapp.level1.L1aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.level2.L2aTestFragementActivity;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneResultRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.sync.AppDataSource;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.GetDataFromLocalDatabase;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.PMNameValuePair;
import hk.org.deaf.auditoryandspeechtrainingapp.utils.UserLoginOrLogoutProcess;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView.GraphViewData;

public class L1ResultFragementActivity extends L1andL2ResultCommonFunction {

	private ArrayList<StageOneResultRecord> locals;
	private String emailInputed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setToFullScreen();
		setContentView(R.layout.result_level1_revised);
		getConsonFromIntent();
		setContentLayout();
		

		if (isOnline()) {
			Log.d("isOnline()", String.valueOf(isOnline()));
			downloadLast10Records();
		} else {
			Log.d("isOnline()", String.valueOf(isOnline()));
			getLast10ResultRecordFromLocalDatabase();
			displayGraph();
		}
		// displayGraph();
	}

	private void setContentLayout() {
		SelectionBtnListener btnListener = new SelectionBtnListener();
		ImageButton backBtn = (ImageButton) findViewById(R.id.result_page_back_btn);
		backBtn.setOnClickListener(btnListener);
		getEmailBtn().setOnClickListener(btnListener);
		ImageButton shareBtn = (ImageButton) findViewById(R.id.result_page_share_btn);
		shareBtn.setOnClickListener(btnListener);
		
		ImageView consonTitle = (ImageView) findViewById(R.id.result1_table_title);
		consonTitle.setImageResource(getConsonDrawable(getConson()));
	}

	public ImageButton getEmailBtn() {
		return (ImageButton) findViewById(R.id.result_page_email_btn);
	}

	public String getConsonString(int conson) {
		switch (conson) {
		case 0:
			return "ph vs p";
		case 1:
			return "ph vs th";
		case 2:
			return "th vs t";
		case 3:
			return "th vs kh";
		case 4:
			return "kh vs k";
		case 5:
			return "s vs ts";
		case 6:
			return "tsh vs ts";
		case 7:
			return "n vs m";
		case 8:
			return "ng vs n";
		}
		return null;
	}
	
	public int getConsonDrawable(int conson) {
		switch (conson) {
		case 0:
			return R.drawable.level1a_page_ph_vs_p_sel_item;
		case 1:
			return R.drawable.level1a_page_ph_vs_th_sel_item;
		case 2:
			return R.drawable.level1a_page_th_vs_t_sel_item;
		case 3:
			return R.drawable.level1a_page_th_vs_kh_sel_item;
		case 4:
			return R.drawable.level1a_page_kh_vs_k_sel_item;
		case 5:
			return R.drawable.level1a_page_s_vs_ts_sel_item;
		case 6:
			return R.drawable.level1a_page_tsh_vs_ts_sel_item;
		case 7:
			return R.drawable.level1a_page_n_vs_m_sel_item;
		case 8:
			return R.drawable.level1a_page_ng_vs_n_sel_item;
		}
		return 0;
	}

	private void displayGraph() {
		int[] score = new int[10];
		if (getLocals() != null) {
			if (getLocals().size() < 10) {
				for (int i = 0; i < 10 - getLocals().size(); i++) {
					score[i] = 0;
				}

				int m = 0;
				for (int j = 10 - getLocals().size(); j < 10; j++) {

					score[j] = getLocals().get(m).getScore();
					m++;
				}
			}
			if (getLocals().size() == 10) {
				for (int i = 0; i < getLocals().size(); i++) {
					score[i] = getLocals().get(i).getScore();
				}
			}
			GraphViewSeries last10ResultRecordSeries = new GraphViewSeries("",
					new GraphViewSeriesStyle(0xFFCA3BB2, 3),
					new GraphViewData[] { new GraphViewData(1, score[0]),
							new GraphViewData(2, score[1]),
							new GraphViewData(3, score[2]),
							new GraphViewData(4, score[3]),
							new GraphViewData(5, score[4]),
							new GraphViewData(6, score[5]),
							new GraphViewData(7, score[6]),
							new GraphViewData(8, score[7]),
							new GraphViewData(9, score[8]),
							new GraphViewData(10, score[9]) });

			/*GraphView graphView = new LineGraphView(this // context
					, getConsonString(getConson()) // heading
			);*/
			GraphView graphView = new LineGraphView(this // context
					, "" // heading
			);
			graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			graphView.getGraphViewStyle().setTextSize(10f);
			graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			graphView.addSeries(last10ResultRecordSeries); // data

			graphView.setHorizontalLabels(new String[] { "1", "2", "3", "4",
					"5", "6", "7", "8", "9", "10" });

			// graphView.setVerticalLabels(new String[] {"100%", "50%", "0%"});

			graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
				public String formatLabel(double value, boolean isValueX) {
					if (!isValueX) {
						if (value == 0) {
							return "0";
						} else if (value == 25) {
							return "25";
						} else if (value == 50) {
							return "50";
						} else if (value == 75) {
							return "75";
						} else if (value == 100) {
							return "100";
						} else {
							return null;
						}
					}
					return null; // let graphview generate Y-axis label for us
				}
			});

			// "low", "1", "2", "3", "4"});
			// graphView.getGraphViewStyle().setNumVerticalLabels(100);

			LinearLayout layout = (LinearLayout) findViewById(R.id.result_level1_container);
			layout.addView(graphView);
		}
	}

	private void getLast10ResultRecordFromLocalDatabase() {
		GetDataFromLocalDatabase<StageOneResultRecord> getDataFromLocalDatabase = new GetDataFromLocalDatabase<StageOneResultRecord>(
				L1ResultFragementActivity.this, StageOneResultRecord.class);
		ArrayList<StageOneResultRecord> locals = getDataFromLocalDatabase
				.getLast10ResultRecordFromLocalDatabase(
						L1ResultFragementActivity.this, getConson());
		setLocals(locals);

	}

	public ArrayList<StageOneResultRecord> getLocals() {
		return locals;
	}

	public void setLocals(ArrayList<StageOneResultRecord> locals) {
		this.locals = locals;
	}

	class SelectionBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.result_page_back_btn:
				finish();
			case R.id.result_page_email_btn:
				showSentEmailDialog();
				break;
			case R.id.result_page_share_btn:
				share();
			}
		}

	}

	private void showSuccessfulMessageDialog() {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage("成功寄出").setTitle("將分數傳送到電郵");
		builder.setPositiveButton("關閉", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
		// 3. Get the AlertDialog from create()

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private void showUnSuccessfulMessageDialog() {
		// 1. Instantiate an AlertDialog.Builder with its constructor
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog
		// characteristics
		builder.setMessage("寄出不成功").setTitle("將分數傳送到電郵");
		builder.setPositiveButton("關閉", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
		// 3. Get the AlertDialog from create()

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private void downloadLast10Records() {
		AppDataSource appDataSource = AppDataSource.getInstance();
		appDataSource.getStage1Last10Records(this, this, this, new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getLast10ResultRecordFromLocalDatabase();
				displayGraph();
			}

		}, String.valueOf(getConson()));

	}

	
	private void showSentEmailDialog() {
		UserLoginOrLogoutProcess process = new UserLoginOrLogoutProcess(this,
				null);
		final Dialog sentEmailDialog;
		sentEmailDialog = new Dialog(this);
		sentEmailDialog
				.setContentView(R.layout.result_sent_confirm_email_dialog_box);
		final TextView errorMsg = (TextView) sentEmailDialog
				.findViewById(R.id.result_page_error_msg);
		TextView email = (TextView) sentEmailDialog
				.findViewById(R.id.test_show_email_txt);
		email.setText(process.getUserEmail());
		Button cancelBtn = (Button) sentEmailDialog
				.findViewById(R.id.test_sent_email_dialog_cancel_btn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				sentEmailDialog.dismiss();

			}
		});
		Button okBtn = (Button) sentEmailDialog
				.findViewById(R.id.test_sent_email_dialog_ok_btn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				EditText inputEmail = (EditText) sentEmailDialog
						.findViewById(R.id.result_page_email_input);
				emailInputed = inputEmail.getEditableText().toString();
				Pattern p2 = Pattern
						.compile("^[a-z]+([0-9]*[a-z]*\\.*)*@[a-z]+([0-9]*[a-z]*\\.*)*\\.[a-z]+$");
				Matcher m2 = p2.matcher(emailInputed);
				UserLoginOrLogoutProcess process = new UserLoginOrLogoutProcess(
						L1ResultFragementActivity.this, null);
				if (m2.find()) {
					if (emailInputed.equals(process.getUserEmail())) {
						sentEmailDialog.dismiss();
						NameValuePair emailParam = new PMNameValuePair("email",
								process.getUserEmail());
						ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(emailParam);
						AppDataSource.getInstance().postScoreToEmail(
								getApplicationContext(),
								L1ResultFragementActivity.this, params,
								new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										showSuccessfulMessageDialog();
									}

								}, new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										showUnSuccessfulMessageDialog();
									}

								}, "1");
					} else {
						errorMsg.setText("電郵輸入不符！");
					}
				} else {
					errorMsg.setText("電郵輸入不符！");
				}

			}
		});

		sentEmailDialog.show();

	}

	private static Uri takeScreenShot(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();

		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();

		Date date = new Date();
		File path = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File imageFile = new File(path, date.getTime() + ".png");
		FileOutputStream fileOutPutStream;
		try {
			fileOutPutStream = new FileOutputStream(imageFile);

			b.compress(Bitmap.CompressFormat.PNG, 80, fileOutPutStream);

			fileOutPutStream.flush();
			fileOutPutStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return Uri.parse("file://" + imageFile.getAbsolutePath());
	}

	private void share() {
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("image/png");
		sendIntent.putExtra(Intent.EXTRA_STREAM, takeScreenShot(this));
		sendIntent.putExtra(Intent.EXTRA_TEXT,
				"See my captured picture - wow :)");
		startActivity(Intent.createChooser(sendIntent, "share"));
	}
}
