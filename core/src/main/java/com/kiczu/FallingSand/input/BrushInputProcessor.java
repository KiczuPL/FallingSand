package com.kiczu.FallingSand.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class BrushInputProcessor implements InputProcessor {

    private InputManager inputManager;

    public BrushInputProcessor(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            inputManager.switchType();
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
            inputManager.eraseWithBrush();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        inputManager.untouchBrush();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            inputManager.paintWithBrush();
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            inputManager.eraseWithBrush();
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
