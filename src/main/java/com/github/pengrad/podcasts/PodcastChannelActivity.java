package com.github.pengrad.podcasts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.pengrad.json.XML;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Document;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * stas
 * 8/27/15
 */
public class PodcastChannelActivity extends AppCompatActivity {

    private static final String TAG = "PodcastActivity";
    @Bind(R.id.listview) ListView mListView;
    ArrayAdapter<Object> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.bind(this);

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        mListView.setAdapter(mAdapter);

        Ion.with(this)
                .load("http://feeds.feedburner.com/umputun")
                .asDocument()
                .setCallback((e, result) -> {
                    Log.d(TAG, "onCreate " + getStringFromDoc(result));

                    String json = XML.toJSONObject(toString(result)).getJSONObject("rss").getJSONObject("channel").toString();


                    Channel channel = new Gson().fromJson(json, Channel.class);
                    mAdapter.addAll(channel.item);

                    Log.d(TAG, "channel " + channel);
                });
    }

    public static class Channel {
        String title;
        List<Item> item;

        @Override
        public String toString() {
            return title + " \n " + Arrays.toString(item.toArray());
        }
    }

    public static class Item {
        String title;

        @Override
        public String toString() {
            return title;
        }
    }



    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public String getStringFromDoc(org.w3c.dom.Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            writer.flush();
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
