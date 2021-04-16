package de.stefan_oltmann.smarthome.app.model

enum class DeviceType {

    /**
     * A simple light that can be turned off and on.
     */
    LIGHT_SWITCH,

    /**
     * A dimmer is a light that can be set to a percentage value.
     * In addition it can be turned off and on.
     */
    DIMMER,

    /**
     * A exterior blind that can be set to a percentage value.
     * Also "on" and "off" is supported for going all the way up or down.
     */
    ROLLER_SHUTTER,

    /**
     * A heating.
     */
    HEATING;

}
