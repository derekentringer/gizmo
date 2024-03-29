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
        //GLog.d(TAG, "keyDown keycode: " + keycode);

        /*if(keycode > -1) {
            UserInput.setKey(UserInput.ANY_KEY, true);
        }*/

        if (keycode == Input.Keys.BACK) {
            UserInput.setKey(UserInput.BACK_BUTTON, true);
            // Optional back button handling (e.g. ask for confirmation)
            //if (shouldReallyQuit)
            //    Gdx.app.exit();
        }

        if (keycode == Input.Keys.ESCAPE) {
            UserInput.setKey(UserInput.START_BUTTON, true);
        }

        if (keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        if (keycode == Input.Keys.E) {
            UserInput.setKey(UserInput.DIG_BUTTON, true);
        }
        if (keycode == Input.Keys.SHIFT_LEFT
                || keycode == Input.Keys.SHIFT_RIGHT) {
            UserInput.setKey(UserInput.RUN_BUTTON, true);
        }
        if (keycode == Input.Keys.F) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, true);
        }
        if (keycode == Input.Keys.R) {
            UserInput.setKey(UserInput.SECONDARY_BUTTON, true);
        }

        if (keycode == Input.Keys.LEFT_BRACKET) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_BACKWARD, true);
        }

        if (keycode == Input.Keys.RIGHT_BRACKET) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, true);
        }

        if (keycode == Input.Keys.SEMICOLON) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_BACKWARD, true);
        }

        if (keycode == Input.Keys.APOSTROPHE) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD, true);
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
        if (keycode == Input.Keys.UP
                || keycode == Input.Keys.DPAD_UP) {
            UserInput.setKey(UserInput.UP, true);
        }
        if (keycode == Input.Keys.DOWN
                || keycode == Input.Keys.DPAD_DOWN) {
            UserInput.setKey(UserInput.DOWN, true);
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
        //GLog.d(TAG, "keyUp keycode: " + keycode);

        if (keycode == Input.Keys.BACK) {
            UserInput.setKey(UserInput.BACK_BUTTON, false);
        }

        if (keycode == Input.Keys.ESCAPE) {
            UserInput.setKey(UserInput.START_BUTTON, false);
        }

        if (keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        if (keycode == Input.Keys.E) {
            UserInput.setKey(UserInput.DIG_BUTTON, false);
        }
        if (keycode == Input.Keys.SHIFT_LEFT
                || keycode == Input.Keys.SHIFT_RIGHT) {
            UserInput.setKey(UserInput.RUN_BUTTON, false);
        }
        if (keycode == Input.Keys.F) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, false);
        }
        if (keycode == Input.Keys.R) {
            UserInput.setKey(UserInput.SECONDARY_BUTTON, false);
        }

        if (keycode == Input.Keys.LEFT_BRACKET) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_BACKWARD, false);
        }

        if (keycode == Input.Keys.RIGHT_BRACKET) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, false);
        }

        if (keycode == Input.Keys.SEMICOLON) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_BACKWARD, false);
        }

        if (keycode == Input.Keys.APOSTROPHE) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD, false);
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
        if (keycode == Input.Keys.UP
                || keycode == Input.Keys.DPAD_UP) {
            UserInput.setKey(UserInput.UP, false);
        }
        if (keycode == Input.Keys.DOWN
                || keycode == Input.Keys.DPAD_DOWN) {
            UserInput.setKey(UserInput.DOWN, false);
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
        GLog.d(TAG, "controller connected: " + controller.getName());
        UserInput.setController(controller);
    }

    @Override
    public void disconnected(Controller controller) {
        GLog.d(TAG, "controller disconnected: " + controller.getName());
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        //GLog.d(TAG, "buttonDown Code: " + buttonCode);
        /*if(buttonCode > -1) {
            UserInput.setKey(UserInput.ANY_BUTTON, true);
        }*/
        if (buttonCode == PS4Controller.BUTTON_START
                || buttonCode == NexusPlayerController.BUTTON_BACK) {
            UserInput.setKey(UserInput.START_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_SQUARE
                || buttonCode == NexusPlayerController.BUTTON_X) {
            UserInput.setKey(UserInput.RUN_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_TRIANGLE
                || buttonCode == NexusPlayerController.BUTTON_Y) {
            UserInput.setKey(UserInput.ENTER_DOOR, true);
        }
        if (buttonCode == PS4Controller.BUTTON_CIRCLE
                || buttonCode == NexusPlayerController.BUTTON_B) {
            UserInput.setKey(UserInput.DIG_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_RT) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_RB
                || buttonCode == NexusPlayerController.BUTTON_RB) {
            UserInput.setKey(UserInput.SECONDARY_BUTTON, true);
        }
        if (buttonCode == PS4Controller.BUTTON_LT) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, true);
        }
        if (buttonCode == PS4Controller.BUTTON_LB
                || buttonCode == NexusPlayerController.BUTTON_LB) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD, true);
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        /*if(buttonCode > -1) {
            UserInput.setKey(UserInput.ANY_BUTTON, false);
        }*/
        if (buttonCode == PS4Controller.BUTTON_START
                || buttonCode == NexusPlayerController.BUTTON_BACK) {
            UserInput.setKey(UserInput.START_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_SQUARE
                || buttonCode == NexusPlayerController.BUTTON_X) {
            UserInput.setKey(UserInput.RUN_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_TRIANGLE
                || buttonCode == NexusPlayerController.BUTTON_Y) {
            UserInput.setKey(UserInput.ENTER_DOOR, false);
        }
        if (buttonCode == PS4Controller.BUTTON_CIRCLE
                || buttonCode == NexusPlayerController.BUTTON_B) {
            UserInput.setKey(UserInput.DIG_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_RT) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_RB
                || buttonCode == NexusPlayerController.BUTTON_RB) {
            UserInput.setKey(UserInput.SECONDARY_BUTTON, false);
        }
        if (buttonCode == PS4Controller.BUTTON_LT) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, false);
        }
        if (buttonCode == PS4Controller.BUTTON_LB
                || buttonCode == NexusPlayerController.BUTTON_LB) {
            UserInput.setKey(UserInput.SWITCH_SECONDARY_BUTTON_FORWARD, false);
        }
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        //GLog.d(TAG, "axisCode: " + axisCode + " " + value);

        //stick left/right
        if (controller.getAxis(BaseController.AXIS_X) > 0.2f) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }

        if (controller.getAxis(BaseController.AXIS_X) < -0.2f) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }

        //stick up/down
        if (controller.getAxis(BaseController.AXIS_Y) < -0.2f) {
            UserInput.setKey(UserInput.UP, true);
        }
        else {
            UserInput.setKey(UserInput.UP, false);
        }
        if (controller.getAxis(BaseController.AXIS_Y) > 0.2f) {
            UserInput.setKey(UserInput.DOWN, true);
        }
        else {
            UserInput.setKey(UserInput.DOWN, false);
        }

        if (axisCode == NexusPlayerController.BUTTON_LT && value == 1.0) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, true);
        }
        if (axisCode == NexusPlayerController.BUTTON_LT && value < 1.0) {
            UserInput.setKey(UserInput.SWITCH_PRIMARY_BUTTON_FORWARD, false);
        }

        if (axisCode == NexusPlayerController.BUTTON_RT && value >= 0.5) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, true);
        }
        if (axisCode == NexusPlayerController.BUTTON_RT && value < 0.5) {
            UserInput.setKey(UserInput.ATTACK_BUTTON, false);
        }

        return true;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        //GLog.d(TAG, "povCode: " + povCode + " " + value);

        if (value == BaseController.BUTTON_DPAD_RIGHT
                || value == BaseController.BUTTON_DPAD_UP_RIGHT
                || value == BaseController.BUTTON_DPAD_DOWN_RIGHT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }

        if (value == BaseController.BUTTON_DPAD_LEFT
                || value == BaseController.BUTTON_DPAD_UP_LEFT
                || value == BaseController.BUTTON_DPAD_DOWN_LEFT) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }

        if (value == BaseController.BUTTON_DPAD_UP
                || value == BaseController.BUTTON_DPAD_UP_LEFT
                || value == BaseController.BUTTON_DPAD_UP_RIGHT) {
            UserInput.setKey(UserInput.UP, true);
        }
        else {
            UserInput.setKey(UserInput.UP, false);
        }

        if (value == BaseController.BUTTON_DPAD_DOWN
                || value == BaseController.BUTTON_DPAD_DOWN_LEFT
                || value == BaseController.BUTTON_DPAD_DOWN_RIGHT) {
            UserInput.setKey(UserInput.DOWN, true);
        }
        else {
            UserInput.setKey(UserInput.DOWN, false);
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