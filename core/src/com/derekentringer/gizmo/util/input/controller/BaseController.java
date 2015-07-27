package com.derekentringer.gizmo.util.input.controller;

import com.badlogic.gdx.controllers.PovDirection;

public class BaseController {

    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;

    public static final PovDirection BUTTON_DPAD_CENTER = PovDirection.center;

    public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;

    public static final PovDirection BUTTON_DPAD_UP_RIGHT = PovDirection.northEast;
    public static final PovDirection BUTTON_DPAD_RIGHT_DOWN = PovDirection.southEast;
    public static final PovDirection BUTTON_DPAD_DOWN_LEFT = PovDirection.southWest;
    public static final PovDirection BUTTON_DPAD_UP_LEFT = PovDirection.northWest;

}