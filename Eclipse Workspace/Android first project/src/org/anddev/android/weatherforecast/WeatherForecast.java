package org.anddev.android.weatherforecast;

import org.anddev.android.weatherforecast.views.SingleWeatherInfoView;
import org.anddev.android.weatherforecast.weather.GoogleWeatherHandler;
import org.anddev.android.weatherforecast.weather.WeatherCurrentCondition;
import org.anddev.android.weatherforecast.weather.WeatherForecastCondition;
import org.anddev.android.weatherforecast.weather.WeatherSet;
import org.anddev.android.weatherforecast.weather.WeatherUtils;

public class WeatherForecast extends Activity {

	private final String DEBUG_TAG = "WeatherForcaster";
	private CheckBox chk_usecelsius = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		setTheme(android.R.style.Theme_Dark);

		this.chk_usecelsius = (CheckBox) findViewById(R.id.chk_usecelsius);

		Button cmd_submit = (Button) findViewById(R.id.cmd_submit);
		cmd_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				URL url;
				try {
					/* Get what user typed to the EditText. */
					String cityParamString = ((EditText) findViewById(R.id.edit_input))
							.getText().toString();
					String queryString = "http://www.google.com/ig/api?weather="
							+ cityParamString;
					/* Replace blanks with HTML-Equivalent. */
					url = new URL(queryString.replace(" ", "%20"));

					/* Get a SAXParser from the SAXPArserFactory. */
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();

					/* Get the XMLReader of the SAXParser we created. */
					XMLReader xr = sp.getXMLReader();

					/*
					 * Create a new ContentHandler and apply it to the
					 * XML-Reader
					 */
					GoogleWeatherHandler gwh = new GoogleWeatherHandler();
					xr.setContentHandler(gwh);

					/* Parse the xml-data our URL-call returned. */
					xr.parse(new InputSource(url.openStream()));

					/* Our Handler now provides the parsed weather-data to us. */
					WeatherSet ws = gwh.getWeatherSet();

					/* Update the SingleWeatherInfoView with the parsed data. */
					updateWeatherInfoView(R.id.weather_today, ws
							.getWeatherCurrentCondition());

					updateWeatherInfoView(R.id.weather_1, ws
							.getWeatherForecastConditions().get(0));
					updateWeatherInfoView(R.id.weather_2, ws
							.getWeatherForecastConditions().get(1));
					updateWeatherInfoView(R.id.weather_3, ws
							.getWeatherForecastConditions().get(2));
					updateWeatherInfoView(R.id.weather_4, ws
							.getWeatherForecastConditions().get(3));

				} catch (Exception e) {
					resetWeatherInfoViews();
					Log.e(DEBUG_TAG, "WeatherQueryError", e);
				}
			}
		});
	}

	private void updateWeatherInfoView(int aResourceID,
			WeatherForecastCondition aWFIS) throws MalformedURLException {
		
		/* Construct the Image-URL. */
		URL imgURL = new URL("http://www.google.com" + aWFIS.getIconURL());
		((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);

		int tempMin = aWFIS.getTempMinCelsius();
		int tempMax = aWFIS.getTempMaxCelsius();

		/* Convert from Celsius to Fahrenheit if necessary. */
		if (this.chk_usecelsius.isChecked()) {
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempCelciusMinMax(tempMin, tempMax);
		} else {
			tempMin = WeatherUtils.celsiusToFahrenheit(tempMin);
			tempMax = WeatherUtils.celsiusToFahrenheit(tempMax);
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempFahrenheitMinMax(tempMin, tempMax);
		}
	}

	private void updateWeatherInfoView(int aResourceID,
			WeatherCurrentCondition aWCIS) throws MalformedURLException {
		
		/* Construct the Image-URL. */
		URL imgURL = new URL("http://www.google.com" + aWCIS.getIconURL());
		((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);

		/* Convert from Celsius to Fahrenheit if necessary. */
		if (this.chk_usecelsius.isChecked()){
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempCelcius(aWCIS.getTempCelcius());
		}else{
			((SingleWeatherInfoView) findViewById(aResourceID))
					.setTempFahrenheit(aWCIS.getTempFahrenheit());
		}
	}

	private void resetWeatherInfoViews() {
		((SingleWeatherInfoView)findViewById(R.id.weather_today)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_1)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_2)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_3)).reset();
		((SingleWeatherInfoView)findViewById(R.id.weather_4)).reset();
	}
}