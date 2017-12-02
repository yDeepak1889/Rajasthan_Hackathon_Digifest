package `in`.rehacktivebrains.yatriguide

import android.app.Application


/**
 * Created by betterclever on 3/12/17.
 */

class YatriApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /*val beacon = Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
                .setId2("1")
                .setId3("2")
                .setManufacturer(0x0118)
                .setTxPower(-59)
                .setDataFields(listOf(0L))
                .build()

        val beaconParser = BeaconParser()
                .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25")
        val beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)
        beaconTransmitter.startAdvertising(beacon)*/

    }

}