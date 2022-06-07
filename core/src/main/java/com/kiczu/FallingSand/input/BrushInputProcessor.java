package com.kiczu.FallingSand.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class BrushInputProcessor implements InputProcessor {

    private InputManager inputManager;

    public BrushInputProcessor(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            inputManager.toggleRunning();
        } else if (keycode == Input.Keys.COMMA) {
            inputManager.increaseGameSpeed();
        } else if (keycode == Input.Keys.PERIOD) {
            inputManager.decreaseGameSpeed();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            inputManager.paintWithBrush();
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            inputManager.setMenuActive();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        inputManager.unTouchBrush();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            inputManager.paintWithBrush();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        inputManager.changeBrushSize(amountY);
        return false;
    }

}
