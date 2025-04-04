package com.kucoin;

import com.kucoin.model.Ticker;
import com.kucoin.model.TickerShort;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTickerGetRequest {
    private List<Ticker> getAllTickers() {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("https://api.kucoin.com/api/v1/market/allTickers")
                .then()
                .log()
                .body()
                .extract()
                .jsonPath()
                .getList("data.ticker", Ticker.class);
    }

    @Test
    public void checkAllTickers() {
        List<Ticker> tickerList = getAllTickers();
        assertAll(
                () -> assertFalse(tickerList.isEmpty()),
                () -> assertEquals(1308, tickerList.size())
        );
    }

    @Test
    public void checkBTC() {
        List<Ticker> btcTickers = getAllTickers()
                .stream()
                .filter(ticker -> ticker.getSymbolName().endsWith("BTC"))
                .toList();

        assertAll(
                () -> assertFalse(btcTickers.isEmpty()),
                () -> assertTrue(btcTickers.stream().allMatch(ticker -> ticker.getSymbolName().endsWith("BTC"))),
                () -> assertEquals(151, btcTickers.size())
        );
    }

    @Test
    public void testSortASC() {
        List<Ticker> tickers = getAllTickers()
                .stream()
                .filter(ticker -> ticker.getSymbolName().startsWith("KA"))
                .sorted(Comparator.comparing(Ticker::getChangeRate))
                .limit(10)
                .toList();

        assertAll(
                () -> assertFalse(tickers.isEmpty()),
                () -> assertEquals(10, tickers.size())
        );
    }

    @Test
    public void testSortDESC() {
        List<Ticker> tickers = getAllTickers()
                .stream()
                .filter(ticker -> ticker.getSymbolName().startsWith("KA"))
                .sorted(Comparator.comparing(Ticker::getChangeRate).reversed())
                .skip(10)
                .toList();

        assertAll(
                () -> assertFalse(tickers.isEmpty()),
                () -> assertEquals(2, tickers.size())
        );
    }

    @Test
    public void testLowerCase() {
        List<String> tickers = getAllTickers()
                .stream()
                .map(ticker -> ticker.getSymbol().toLowerCase())
                .toList();
        assertAll(
                () -> assertFalse(tickers.isEmpty())
        );
    }

    @Test
    public void createMap() {
        List<TickerShort> tickers = new ArrayList<>();
        getAllTickers()
                .forEach(ticker -> tickers.add(new TickerShort(ticker.getSymbolName(), Float.parseFloat(ticker.getChangeRate()))));
        assertAll(
                () -> assertFalse(tickers.isEmpty())
        );
    }
}
