package it.univaq.disim.netflics.vault.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import it.univaq.disim.netflics.vault.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class VaultServiceImpl implements VaultService {

	private static Logger logger = LoggerFactory.getLogger(VaultServiceImpl.class);


	@Value("#{cfg.omdburl}")
	private String omdburl;

	@Override
	public GetMovieResponse getMovie(GetMovieRequest parameters) throws BusinessException {
		return new GetMovieResponse();
	}

	@Override
	public AddMovieResponse addMovie(AddMovieRequest parameters) throws BusinessException {
		return new AddMovieResponse();
	}


/*
	@Override
	public TrafficResponse getTrafficInfo(TrafficRequest parameters) throws BusinessException {
		try {
			// build the url
			String url = trafficInformationurl + "?urLat=" + parameters.getUrLat() + "&urLon=" + parameters.getUrLon() + "&llLat=" + parameters.getLlLat() + "&llLon=" + parameters.getLlLon() + "&getId=" + parameters.isGetId() + "&getDist=" + parameters.isGetDist() + "&dt=" + parameters.getDt() + "&cenLat="
					+ parameters.getCenLat() + "&cenLon=" + parameters.getCenLon() + "&maxResults=" + parameters.getMaxResults() + "&s" + parameters.getS();

			// connect to url
			HttpURLConnection c = null;

			URL u = new URL(url);
			c = (HttpURLConnection) u.openConnection();
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-length", "0");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				if (c != null) {
					c.disconnect();
				}

				// map the json response to java classes
				TrafficMessageType[] messages = new Gson().fromJson(sb.toString(), TrafficMessageType[].class);
				TrafficResponse resp = new TrafficResponse();
				resp.getTrafficMessage().addAll(Arrays.asList(messages));
				return resp;

			default:
				if (c != null) {
					c.disconnect();
				}
				throw new Exception("Http Error: " + status);
			}
		} catch (Exception  e) {
			logger.error("error", e);
			throw new BusinessException(e);
		}
	}
	*/

}
