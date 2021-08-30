package kr.ac.hansung.cse.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@PropertySource("classpath:db/props/api.properties")
@Service
public class TextTrans {

	@Value("${api_key}")
	private String api_key;
	
	public String translateKo(String text) {
		
		@SuppressWarnings("deprecation")
		Translate translate = TranslateOptions.newBuilder().setApiKey(api_key).build()
				.getService();

		
		Translation translation = translate.translate(text, TranslateOption.sourceLanguage("en"),
				TranslateOption.targetLanguage("ko"));

		String changeTrans = translation.getTranslatedText();

		return changeTrans;
	}

	public String translateEn(String text) {
		
		@SuppressWarnings("deprecation")
		Translate translate = TranslateOptions.newBuilder().setApiKey(api_key).build()
				.getService();
		
		Translation translation = translate.translate(text, TranslateOption.sourceLanguage("ko"),
				TranslateOption.targetLanguage("en"));

		String changeTrans = translation.getTranslatedText();

		return changeTrans;
	}
}