package com.coveo.blitz.client;

import com.coveo.blitz.client.lamothe.KillerBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.coveo.blitz.client.bot.RandomBot;
import com.coveo.blitz.client.bot.SimpleBotRunner;
import com.coveo.blitz.client.dto.ApiKey;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Strings;

/**
 * CLI program for launching a bot
 */
public class Main
{
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String args[]) throws Exception
    {

        if (args == null || args.length < 2) {
            logger.info("Please provide the required parameters.");
            return;
        }

        final String key = args[0];
        final String arena = args[1];
        final String gameId = args.length > 2 ? args[2] : "";

        final GenericUrl gameUrl;

        if ("TRAINING".equals(arena))
            gameUrl = GameUrl.getTrainingUrl();
        else if ("COMPETITION".equals(arena)) {
            if (gameId.equals("")) {
                logger.info("Game ID is required for COMPETITION.");
                return;
            }
            gameUrl = GameUrl.getCompetitionUrl(gameId);
        } else {
            gameUrl = new GameUrl(arena, gameId);
        }

        SimpleBotRunner runner = new SimpleBotRunner(new ApiKey(key), gameUrl, new KillerBot());
        runner.call();
    }

    /**
     * Represents the endpoint URL
     */
    public static class GameUrl extends GenericUrl
    {
        private final static String BASE_URL = "http://blitz2016.xyz:8080";

        private final static String TRAINING_URL = BASE_URL + "/api/training";
        private final static String COMPETITION_URL = BASE_URL + "/api/arena";

        public GameUrl(String encodedUrl,
                       String gameId)
        {
            super(Strings.isNullOrEmpty(gameId) ? encodedUrl : encodedUrl.concat("?gameId=" + gameId));
        }

        public static GameUrl getCompetitionUrl(String gameId)
        {
            return new GameUrl(COMPETITION_URL, gameId);
        }

        public static GameUrl getTrainingUrl()
        {
            return new GameUrl(TRAINING_URL, "");
        }
    }
}
