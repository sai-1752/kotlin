import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String){
    var deviceStatus="online"
        protected set

    open val deviceType="unknown"

    open fun turnOn(){
        deviceStatus="on"
    }
    open fun turnoff(){
        deviceStatus="off"
    }
}

class SmartTvDevice(deviceName: String,deviceCategory: String):
        SmartDevice(name = deviceName, category = deviceCategory) {
        override val deviceType="smart tv"
        private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
        private var channelNumber by RangeRegulator(initialValue =1,minValue= 0, maxValue=200)

    fun increaseSpeakerVolume(){
        speakerVolume++
        println("speaker volume increased to $speakerVolume.")
    }

    fun nextChannel(){
        channelNumber++
        println("channel number increased to $channelNumber")
    }

    override fun turnOn() {
        super.turnOn()
        println("$name is turned on.speaker volume is set to $speakerVolume and channel number is "+ "set to $channelNumber")
    }
}

class SmartLightDevice(deviceName: String,deviceCategory: String):
        SmartDevice(name=deviceName, category = deviceCategory) {
    override val deviceType= "smart light"
    private var brightnessLevel by RangeRegulator(initialValue = 0,  minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel")
    }

    override fun turnOn() {
        super.turnOn()
        brightnessLevel=2
        println("$name turned on.The brightness level is $brightnessLevel")
    }

    override fun turnoff() {
        super.turnoff()
        brightnessLevel=0
        println("smart light is turned off")
    }
}
class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
){
    var deviceTurnOnCount=0
        private set

    fun turnOnTv(){
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }
    fun turnOffTv(){
        deviceTurnOnCount--
        smartTvDevice.turnoff()
    }
    fun increaseTvVolume(){
        smartTvDevice.increaseSpeakerVolume();
    }
    fun changeTvChannelToNext(){
        smartTvDevice.nextChannel()
    }
    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnoff()
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
): ReadWriteProperty<Any?,Int>{

    var fieldData=initialValue
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue){
            fieldData=value
        }
    }
}

fun main() {
    var smartDevice:SmartDevice=SmartTvDevice("android tv","entertinement")
    smartDevice.turnOn()

    smartDevice=SmartLightDevice("google light","utility")
    smartDevice.turnOn()
}
