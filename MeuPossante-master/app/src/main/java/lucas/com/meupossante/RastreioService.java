package lucas.com.meupossante;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import lucas.com.meupossante.DAO.VeiculoDAO;
import lucas.com.meupossante.VO.VeiculoVO;

/**
 * Created by Lucas on 23/05/2016.
 */
public class RastreioService extends IntentService implements GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private static final String TAG = "Rastreio-Service";

    public static float totalDistance = 0f;
    public static boolean isRunning = false;

    public RastreioService() {
        super("RastreioService");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        isRunning = true;
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        isRunning = false;
    }

    @Override
    public void onCreate(){
        Log.i(TAG, "create");
        super.onCreate();
        NotificationCompat.Builder n = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getResources().getString(R.string.rastreio_ligado))
                .setContentText(getResources().getString(R.string.clique_desabilitar))
                .setOngoing(true);

        Intent resultIntent = new Intent(this, Rastreio.class);
        resultIntent.putExtra("isRequesting", true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Manutencao.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        n.setContentIntent(resultPendingIntent);
        Notification notification = n.build();
        startForeground(1, notification);
        buildGoogleApiClient();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();
        Log.i(TAG, "start command");
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        Log.i(TAG, "Destroy");
        stopLocationUpdates();
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "connected");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Conexão suspensa");
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "location requested");
        float distance = mCurrentLocation.distanceTo(location);
        totalDistance += distance;
        Log.i(TAG, "Latitude: " + String.valueOf(mCurrentLocation.getLatitude()));
        Log.i(TAG, "Longitude: " + String.valueOf(mCurrentLocation.getLongitude()));
        Log.i(TAG, "total distance: " + String.valueOf(totalDistance));
        mCurrentLocation = location;
        Toast.makeText(this, "Localização atualizada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Falha na conexão: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }
}
