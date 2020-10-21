import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import org.junit.Test;

public class DownloadTest {

    @Test   //Fails!
    public void downdloadLatestCore() throws Exception {
        head("https://downloads.cloudbees.com/cje/rolling/war/2.249.2.4/jenkins.war");
    }

    @Test   //Fails!!
    public void downloadOldCore() throws Exception {
        head("https://downloads.cloudbees.com/cje/rolling/war/2.222.4.3/jenkins.war");
    }

    @Test   //Success!!
    public void downloadPlugin() throws Exception {
        head("https://jenkins-updates.cloudbees.com/download/plugins/azure-cli/1.3/azure-cli.hpi");
    }

    private void head(String url) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        // Copied from AHC.getInstance
        // The code about the Jenkins proxy has been removed for simplicity. The outcome is the same
        AsyncHttpClient instance = new AsyncHttpClient(new AsyncHttpClientConfig.Builder().setMaxRequestRetry(1).build());

        //Copied from UpdateCenter._downloadCore and UpdateCenter._downloadPlugin (the code is the same)
        Request request = new RequestBuilder("HEAD").setUrl(url).setFollowRedirects(true).build();
        ListenableFuture future = instance.executeRequest(request);
        future.get(TimeUnit.MINUTES.toMillis(1), TimeUnit.MILLISECONDS);
    }
}
