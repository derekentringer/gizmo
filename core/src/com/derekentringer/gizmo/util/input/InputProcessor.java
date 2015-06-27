package com.derekentringer.gizmo.util.input;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.derekentringer.gizmo.util.input.controller.NexusPlayerController;
import com.derekentringer.gizmo.util.input.controller.PS3Controller;

public class InputProcessor extends InputAdapter implements ControllerListener {

    //KEYBOARD

    @Override
    public boolean keyDown (int keycode) {
        //arrow key movement
        if(keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        if(keycode == Input.Keys.UP) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        if(keycode == Input.Keys.RIGHT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        if(keycode == Input.Keys.LEFT) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        //wsad key movement
        if(keycode == Input.Keys.W) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        if(keycode == Input.Keys.D) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        if(keycode == Input.Keys.A) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        //arrow key movement
        if(keycode == Input.Keys.SPACE) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        if(keycode == Input.Keys.UP) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        if(keycode == Input.Keys.RIGHT) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }
        if(keycode == Input.Keys.LEFT) {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        //wsad key movement
        if(keycode == Input.Keys.W) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        if(keycode == Input.Keys.D) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }
        if(keycode == Input.Keys.A) {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        return true;
    }

    //MOBILE

    @Override
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
    }

    //CONTROLLER

    @Override
    public void connected(Controller controller) {
    }

    @Override
    public void disconnected(Controller controller) {
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.print("buttonCode: "+buttonCode);
        if(buttonCode == PS3Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, true);
        }
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        if(buttonCode == PS3Controller.BUTTON_X
                || buttonCode == NexusPlayerController.BUTTON_A) {
            UserInput.setKey(UserInput.JUMP_BUTTON, false);
        }
        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        if(controller.getAxis(PS3Controller.AXIS_X) > 0.2f) {
            UserInput.setKey(UserInput.RIGHT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.RIGHT_BUTTON, false);
        }

        if(controller.getAxis(PS3Controller.AXIS_X) < -0.2f) {
            UserInput.setKey(UserInput.LEFT_BUTTON, true);
        }
        else {
            UserInput.setKey(UserInput.LEFT_BUTTON, false);
        }
        return true;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
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

}