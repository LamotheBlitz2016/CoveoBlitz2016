package com.coveo.blitz.client.bot;

import java.util.concurrent.Callable;

import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coveo.blitz.client.dto.ApiKey;
import com.coveo.blitz.client.dto.GameState;
import com.coveo.blitz.client.dto.Move;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

public class SimpleBotRunner implements Callable<GameState>
{
    private static final Logger logger = LogManager.getLogger(SimpleBotRunner.class);

    private final ApiKey apiKey;
    private final GenericUrl gameUrl;
    private final SimpleBot bot;
    HttpRequestFactory requestFactory;

    public SimpleBotRunner(ApiKey apiKey,
                           GenericUrl gameUrl,
                           SimpleBot bot)
    {
        this.apiKey = apiKey;
        this.gameUrl = gameUrl;
        this.bot = bot;

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setIntParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 500);
        ApacheHttpTransport httpTransport = new ApacheHttpTransport(new DefaultHttpClient(httpParams));
        requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer()
        {
            @Override
            public void initialize(HttpRequest request)
            {
                request.setParser(new JsonObjectParser(new GsonFactory()));
            }
        });
    }

    @Override
    public GameState call()
    {
        HttpContent content;
        HttpRequest request;
        HttpResponse response;
        GameState gameState = null;

        try {
            // Initial request
            logger.info("Sending initial request...");
            content = new UrlEncodedContent(apiKey);
            request = requestFactory.buildPostRequest(gameUrl, content);
            request.setReadTimeout(0); // Wait forever to be assigned to a game
            response = request.execute();
            gameState = response.parseAs(GameState.class);
            logger.info("Game URL: {}", gameState.getViewUrl());

            // Game loop
            while (!gameState.getGame().isFinished() && !gameState.getHero().isCrashed()) {
                logger.info("Taking turn " + gameState.getGame().getTurn());
                BotMove direction = bot.move(gameState);
                Move move = new Move(apiKey.getKey(), direction.toString());

                HttpContent turn = new UrlEncodedContent(move);
                HttpRequest turnRequest = requestFactory.buildPostRequest(new GenericUrl(gameState.getPlayUrl()), turn);
                HttpResponse turnResponse = turnRequest.execute();

                gameState = turnResponse.parseAs(GameState.class);
            }

        } catch (Exception e) {
            logger.error("Error during game play", e);
        }

        logger.info("Game over");
        return gameState;
    }
}
