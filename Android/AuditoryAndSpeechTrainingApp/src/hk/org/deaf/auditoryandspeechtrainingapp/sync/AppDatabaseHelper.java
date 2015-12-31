package hk.org.deaf.auditoryandspeechtrainingapp.sync;

import hk.com.playmore.syncframework.helper.PMDatabaseHelper;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeEightResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFiveResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeFourResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeNineResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeOneResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSevenResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeSixResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeThreeResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.ChallengeTwoResult;
import hk.org.deaf.auditoryandspeechtrainingapp.model.PassToStage2StateRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.Stage2Conson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneConson;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageOneResultRecord;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoIsFirst;
import hk.org.deaf.auditoryandspeechtrainingapp.model.StageTwoResultRecord;


import java.util.Arrays;
import java.util.List;

import android.content.Context;

public class AppDatabaseHelper extends PMDatabaseHelper {

	private static final String DATABASE_NAME = "auditory.db";
	private static final int DATABASE_VERSION = 21;
	
	public AppDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, DATABASE_VERSION);
	}

	@Override
	protected List<Class<?>> getClasses() {
		Class<?> classes[] = {PassToStage2StateRecord.class, StageTwoIsFirst.class, StageOneIsFirst.class, StageTwoResultRecord.class, StageOneResultRecord.class, ChallengeNineResult.class, ChallengeEightResult.class , ChallengeSevenResult.class,  ChallengeSixResult.class, ChallengeFiveResult.class, ChallengeFourResult.class, ChallengeThreeResult.class, ChallengeTwoResult.class, ChallengeOneResult.class, ChallengeOneRecord.class, StageOneConson.class, Stage2Conson.class};
		return Arrays.asList(classes);
	}

}
