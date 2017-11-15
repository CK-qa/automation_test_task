import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SendGetTest {

    private String checkWaterState() {
        JSONObject obj = new JSONObject(SendGet.body);
        return obj.getString("state");
    }

    private String temp;

    @Test
    public void checkZeroTemperature() throws IOException {
        temp = "0";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Ice\"", checkWaterState().equals("Ice"));
    }

    @Test
    public void checkMinimumIceTemperature() throws IOException {
        temp = "-273";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Ice\"", checkWaterState().equals("Ice"));
    }

    @Test
    public void checkMinimumWaterTemperature() throws IOException {
        temp = "1";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Water\"", checkWaterState().equals("Water"));
    }

    @Test
    public void checkMaximumWaterTemperature() throws IOException {
        temp = "99";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Water\"", checkWaterState().equals("Water"));
    }

    @Test
    public void checkMinimumSteamTemperature() throws IOException {
        temp = "100";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Steam\"", checkWaterState().equals("Steam"));
    }

    @Test
    public void checkMaximumSteamTemperature() throws IOException {
        temp = "373";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature \'" + temp + "\' C should be treated as \"Steam\"", checkWaterState().equals("Steam"));
    }

    @Test
    public void checkOverMinimumTemperature() throws IOException {
        temp = "-274";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Temperature less than \'" + temp + "\' C cannot be treated as \"Ice\" due to technical reasons", !checkWaterState().equals("Ice"));
    }

    @Test
    public void checkOverMaximumTemperature() throws IOException {
        temp = "374";
        SendGet.sendGet(temp);
        Assert.assertTrue("Server response code returned error value: " + String.valueOf(SendGet.responseCode) + ". Expected code is 200.", SendGet.responseCode == 200);
        Assert.assertTrue("Water with temperature more than \'" + temp + "\' C should be treated as \"Supercritical fluid\"", checkWaterState().equals("Supercritical fluid"));
    }
}