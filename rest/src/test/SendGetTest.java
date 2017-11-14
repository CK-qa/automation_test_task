import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SendGetTest {
    private String checkWaterState() {
        JSONObject obj = new JSONObject(SendGet.body);
        return obj.getString("state");
    }

    @Test
    public void checkZero() throws IOException {
        String[] array = {"0", "-273"};
        for (String s : array) {
            System.out.println("Sending 'GET' request to URL: " + SendGet.basUrl + s);
            SendGet.sendGet(s);
            Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
            Assert.assertTrue(checkWaterState().equals("Ice"));
        }
    }

    @Test
    public void checkWater() throws IOException {
        String[] temp = {"1", "99"};
        for (String s : temp) {
            System.out.println("Sending 'GET' request to URL: " + SendGet.basUrl + s);
            SendGet.sendGet(s);
            Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
            Assert.assertTrue("Water with temperature between 1 and 99 should be treated as \"Water\"", checkWaterState().equals("Water"));
        }
    }

    @Test
    public void checkSteam() throws IOException {
        String[] temp = {"100", "373"};
        for (String s : temp) {
            System.out.println("Sending 'GET' request to URL: " + SendGet.basUrl + s);
            SendGet.sendGet(s);
            Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
            Assert.assertTrue("Water with temperature between 100 and 374 should be treated as \"Steam\"", checkWaterState().equals("Steam"));
        }
    }

    @Test
    public void checkMinimum() throws IOException {
        String lessMin = "-274";
        System.out.println("Sending 'GET' request to URL: " + SendGet.basUrl + lessMin);
        SendGet.sendGet(lessMin);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Temperature less than 274 C cannot be treated as \"Ice\" due to technical reasons", !checkWaterState().equals("Ice"));
    }

    @Test
    public void checkMax() throws IOException {
        String moreMax = "374";
        System.out.println("Sending 'GET' request to URL: " + SendGet.basUrl + moreMax);
        SendGet.sendGet(moreMax);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature more than 374 C should be treated as \"Supercritical fluid\"", checkWaterState().equals("Supercritical fluid"));
    }
}
