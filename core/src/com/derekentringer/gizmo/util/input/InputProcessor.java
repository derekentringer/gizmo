package com.derekentringer.gizmo.util.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.derekentringer.gizmo.util.input.controller.BaseController;
import com.derekentringer.gizmo.util.input.controller.NexusPlayerController;
import com.derekentringer.gizmo.util.input.controller.PS4Controller;
import com.derekentringer.gizmo.util.log.GLog;

public class InputProcessor extends InputAdapter implements ControllerListener {

    private static final String TAG = InputProcessor.class.getSimpleName();

    //KEYBOARD

    @Override
    public boolean keyDown(int keycode) {
        GLog.d(TAG, "keycode: " + keycode);
        if (keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        //arrow key movement
        if (keycode == Input.Keys.UP) {
            UserInput.setKey(UserInput.ENTER_DOOR, true);
        }
        if (keycode == Input.Keys.RIGHT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        if (keycode == Input.Keys.LEFT) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        //wsad key movement
        if (keycode == Input.Keys.W) {
            UserInput.setKey(UserInput.ENTER_DOOR, true);
        }
        if (keycode == Input.Keys.D) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        if (keycode == Input.Keys.A) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        //arrow key movement
        if (keycode == Input.Keys.UP) {
            UserInput.setKey(UserInput.ENTER_DOOR, false);
        }
        if (keycode == Input.Keys.RIGHT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }
        if (keycode == Input.Keys.LEFT) {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        //wsad key movement
        if (keycode == Input.Keys.W) {
            UserInput.setKey(UserInput.ENTER_DOOR, false);
        }
        if (keycode == Input.Keys.D) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }
        if (keycode == Input.Keys.A) {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        return true;
    }

    //CONTROLLER

    @Override
    public void connected(Controller controller) {
        GLog.d(TAG, "controller connected: " + controller);
    }

    @Override
    public void disconnected(Controller controller) {
        GLog.d(TAG, "controller disconnected: " + controller);
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        GLog.d(TAG, "buttonCode: " + buttonCode);
        if (buttonCode == PS4Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        else if (buttonCode == PS4Controller.BUTTON_TRIANGLE
                || buttonCode == NexusPlayerController.BUTTON_Y) {
            UserInput.setKey(UserInput.ENTER_DOOR, true);
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if (buttonCode == PS4Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        else if (buttonCode == PS4Controller.BUTTON_TRIANGLE
                || buttonCode == NexusPlayerController.BUTTON_Y) {
            UserInput.setKey(UserInput.ENTER_DOOR, false);
        }
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if (controller.getAxis(PS4Controller.AXIS_X) > 0.2f) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }

        if (controller.getAxis(PS4Controller.AXIS_X) < -0.2f) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        return true;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        GLog.d(TAG, "controller: " + controller);
        GLog.d(TAG, "povCode: " + povCode);
        GLog.d(TAG, "value: " + value);
        if (value == BaseController.BUTTON_DPAD_RIGHT
                || value == BaseController.BUTTON_DPAD_UP_RIGHT
                || value == BaseController.BUTTON_DPAD_RIGHT_DOWN) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }

        if (value == BaseController.BUTTON_DPAD_LEFT
                || value == BaseController.BUTTON_DPAD_UP_LEFT
                || value == BaseController.BUTTON_DPAD_DOWN_LEFT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }
        return true;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return true;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return true;
    }

    //MOBILE not supported

    /*@Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android)) {
            return false;
        }
        UserInput.setKey(UserInput.JUMP_BUTTON, true);
        return true;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        if (!Gdx.app.getType().equals(Application.ApplicationType.Android)) {
            return false;
        }
        UserInput.setKey(UserInput.JUMP_BUTTON, false);
        return true;
    }*/

}