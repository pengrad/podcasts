package com.github.pengrad.podcasts;

import com.github.pengrad.podcasts.api.ItunesSearchApi;
import com.google.gson.Gson;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Stas Parshin
 * 28 September 2015
 */
public class ItunesModel {

    private final ItunesSearchApi mItunesSearchApi;

    public ItunesModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mItunesSearchApi = retrofit.create(ItunesSearchApi.class);
    }

    public Observable<ItunesResult> search(String query) {
        return mItunesSearchApi.searchPodcasts(query);
    }

    ItunesResult testSearch() {
        String res = "{\n" +
                "  \"resultCount\" : 3,\n" +
                "  \"results\" : [\n" +
                "    {\n" +
                "      \"artistName\" : \"Umputun, Bobuk, Gray, Ksenks\",\n" +
                "      \"artworkUrl100\" : \"http://is2.mzstatic.com/image/thumb/Podcasts6/v4/88/0e/d4/880ed419-518a-45d0-f1f9-20d27ca1dca1/mza_888995852398367874.jpg/100x100bb-85.jpg\",\n" +
                "      \"artworkUrl30\" : \"http://is3.mzstatic.com/image/thumb/Podcasts6/v4/88/0e/d4/880ed419-518a-45d0-f1f9-20d27ca1dca1/mza_888995852398367874.jpg/30x30bb-85.jpg\",\n" +
                "      \"artworkUrl60\" : \"http://is3.mzstatic.com/image/thumb/Podcasts6/v4/88/0e/d4/880ed419-518a-45d0-f1f9-20d27ca1dca1/mza_888995852398367874.jpg/60x60bb-85.jpg\",\n" +
                "      \"artworkUrl600\" : \"http://is5.mzstatic.com/image/thumb/Podcasts6/v4/88/0e/d4/880ed419-518a-45d0-f1f9-20d27ca1dca1/mza_888995852398367874.jpg/600x600bb-85.jpg\",\n" +
                "      \"collectionCensoredName\" : \"Радио-Т\",\n" +
                "      \"collectionExplicitness\" : \"notExplicit\",\n" +
                "      \"collectionHdPrice\" : 0,\n" +
                "      \"collectionId\" : 256504435,\n" +
                "      \"collectionName\" : \"Радио-Т\",\n" +
                "      \"collectionPrice\" : 0,\n" +
                "      \"collectionViewUrl\" : \"https://itunes.apple.com/us/podcast/radio-t/id256504435?mt=2&uo=4\",\n" +
                "      \"country\" : \"USA\",\n" +
                "      \"currency\" : \"USD\",\n" +
                "      \"feedUrl\" : \"http://feeds.feedburner.com/Radio-t\",\n" +
                "      \"genreIds\" : [\n" +
                "        \"1448\",\n" +
                "        \"26\",\n" +
                "        \"1318\"\n" +
                "      ],\n" +
                "      \"genres\" : [\n" +
                "        \"Tech News\",\n" +
                "        \"Podcasts\",\n" +
                "        \"Technology\"\n" +
                "      ],\n" +
                "      \"kind\" : \"podcast\",\n" +
                "      \"primaryGenreName\" : \"Tech News\",\n" +
                "      \"radioStationUrl\" : \"https://itunes.apple.com/station/idra.256504435\",\n" +
                "      \"releaseDate\" : \"2015-08-29T18:09:00Z\",\n" +
                "      \"trackCensoredName\" : \"Радио-Т\",\n" +
                "      \"trackCount\" : 20,\n" +
                "      \"trackExplicitness\" : \"notExplicit\",\n" +
                "      \"trackHdPrice\" : 0,\n" +
                "      \"trackHdRentalPrice\" : 0,\n" +
                "      \"trackId\" : 256504435,\n" +
                "      \"trackName\" : \"Радио-Т\",\n" +
                "      \"trackPrice\" : 0,\n" +
                "      \"trackRentalPrice\" : 0,\n" +
                "      \"trackViewUrl\" : \"https://itunes.apple.com/us/podcast/radio-t/id256504435?mt=2&uo=4\",\n" +
                "      \"wrapperType\" : \"track\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"artistName\" : \"Umputun, Bobuk, Gray, Ksenks\",\n" +
                "      \"artworkUrl100\" : \"http://is2.mzstatic.com/image/thumb/Podcasts/v4/42/52/32/4252327a-e48f-c9af-9784-c7530d1b703e/mza_4772964520770428920.jpg/100x100bb-85.jpg\",\n" +
                "      \"artworkUrl30\" : \"http://is3.mzstatic.com/image/thumb/Podcasts/v4/42/52/32/4252327a-e48f-c9af-9784-c7530d1b703e/mza_4772964520770428920.jpg/30x30bb-85.jpg\",\n" +
                "      \"artworkUrl60\" : \"http://is5.mzstatic.com/image/thumb/Podcasts/v4/42/52/32/4252327a-e48f-c9af-9784-c7530d1b703e/mza_4772964520770428920.jpg/60x60bb-85.jpg\",\n" +
                "      \"artworkUrl600\" : \"http://is5.mzstatic.com/image/thumb/Podcasts/v4/42/52/32/4252327a-e48f-c9af-9784-c7530d1b703e/mza_4772964520770428920.jpg/600x600bb-85.jpg\",\n" +
                "      \"collectionCensoredName\" : \"Пираты-РТ\",\n" +
                "      \"collectionExplicitness\" : \"explicit\",\n" +
                "      \"collectionHdPrice\" : 0,\n" +
                "      \"collectionId\" : 288705606,\n" +
                "      \"collectionName\" : \"Пираты-РТ\",\n" +
                "      \"collectionPrice\" : 0,\n" +
                "      \"collectionViewUrl\" : \"https://itunes.apple.com/us/podcast/piraty-rt/id288705606?mt=2&uo=4\",\n" +
                "      \"contentAdvisoryRating\" : \"Explicit\",\n" +
                "      \"country\" : \"USA\",\n" +
                "      \"currency\" : \"USD\",\n" +
                "      \"feedUrl\" : \"http://feeds.feedburner.com/pirate-radio-t\",\n" +
                "      \"genreIds\" : [\n" +
                "        \"1448\",\n" +
                "        \"26\",\n" +
                "        \"1318\"\n" +
                "      ],\n" +
                "      \"genres\" : [\n" +
                "        \"Tech News\",\n" +
                "        \"Podcasts\",\n" +
                "        \"Technology\"\n" +
                "      ],\n" +
                "      \"kind\" : \"podcast\",\n" +
                "      \"primaryGenreName\" : \"Tech News\",\n" +
                "      \"radioStationUrl\" : \"https://itunes.apple.com/station/idra.288705606\",\n" +
                "      \"releaseDate\" : \"2015-05-31T15:06:00Z\",\n" +
                "      \"trackCensoredName\" : \"Пираты-РТ\",\n" +
                "      \"trackCount\" : 5,\n" +
                "      \"trackExplicitness\" : \"explicit\",\n" +
                "      \"trackHdPrice\" : 0,\n" +
                "      \"trackHdRentalPrice\" : 0,\n" +
                "      \"trackId\" : 288705606,\n" +
                "      \"trackName\" : \"Пираты-РТ\",\n" +
                "      \"trackPrice\" : 0,\n" +
                "      \"trackRentalPrice\" : 0,\n" +
                "      \"trackViewUrl\" : \"https://itunes.apple.com/us/podcast/piraty-rt/id288705606?mt=2&uo=4\",\n" +
                "      \"wrapperType\" : \"track\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"artistName\" : \"Umputun, Bobuk, Gray, Marinka\",\n" +
                "      \"artworkUrl100\" : \"http://is3.mzstatic.com/image/thumb/Podcasts/v4/00/e2/b6/00e2b61c-9333-fca9-78db-60f72b6cb24c/mza_844179292471065289.png/100x100bb-85.jpg\",\n" +
                "      \"artworkUrl30\" : \"http://is4.mzstatic.com/image/thumb/Podcasts/v4/00/e2/b6/00e2b61c-9333-fca9-78db-60f72b6cb24c/mza_844179292471065289.png/30x30bb-85.jpg\",\n" +
                "      \"artworkUrl60\" : \"http://is1.mzstatic.com/image/thumb/Podcasts/v4/00/e2/b6/00e2b61c-9333-fca9-78db-60f72b6cb24c/mza_844179292471065289.png/60x60bb-85.jpg\",\n" +
                "      \"artworkUrl600\" : \"http://is1.mzstatic.com/image/thumb/Podcasts/v4/00/e2/b6/00e2b61c-9333-fca9-78db-60f72b6cb24c/mza_844179292471065289.png/600x600bb-85.jpg\",\n" +
                "      \"collectionCensoredName\" : \"Архив Радио-Т\",\n" +
                "      \"collectionExplicitness\" : \"notExplicit\",\n" +
                "      \"collectionHdPrice\" : 0,\n" +
                "      \"collectionId\" : 505841407,\n" +
                "      \"collectionName\" : \"Архив Радио-Т\",\n" +
                "      \"collectionPrice\" : 0,\n" +
                "      \"collectionViewUrl\" : \"https://itunes.apple.com/us/podcast/arhiv-radio-t/id505841407?mt=2&uo=4\",\n" +
                "      \"country\" : \"USA\",\n" +
                "      \"currency\" : \"USD\",\n" +
                "      \"feedUrl\" : \"http://feeds.feedburner.com/radio-t-archives\",\n" +
                "      \"genreIds\" : [\n" +
                "        \"1448\",\n" +
                "        \"26\",\n" +
                "        \"1318\"\n" +
                "      ],\n" +
                "      \"genres\" : [\n" +
                "        \"Tech News\",\n" +
                "        \"Podcasts\",\n" +
                "        \"Technology\"\n" +
                "      ],\n" +
                "      \"kind\" : \"podcast\",\n" +
                "      \"primaryGenreName\" : \"Tech News\",\n" +
                "      \"radioStationUrl\" : \"https://itunes.apple.com/station/idra.505841407\",\n" +
                "      \"releaseDate\" : \"2015-08-29T18:09:00Z\",\n" +
                "      \"trackCensoredName\" : \"Архив Радио-Т\",\n" +
                "      \"trackCount\" : 301,\n" +
                "      \"trackExplicitness\" : \"notExplicit\",\n" +
                "      \"trackHdPrice\" : 0,\n" +
                "      \"trackHdRentalPrice\" : 0,\n" +
                "      \"trackId\" : 505841407,\n" +
                "      \"trackName\" : \"Архив Радио-Т\",\n" +
                "      \"trackPrice\" : 0,\n" +
                "      \"trackRentalPrice\" : 0,\n" +
                "      \"trackViewUrl\" : \"https://itunes.apple.com/us/podcast/arhiv-radio-t/id505841407?mt=2&uo=4\",\n" +
                "      \"wrapperType\" : \"track\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return new Gson().fromJson(res, ItunesResult.class);
    }
}