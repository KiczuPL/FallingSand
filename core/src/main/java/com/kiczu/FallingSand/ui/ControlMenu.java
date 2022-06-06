package com.kiczu.FallingSand.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kiczu.FallingSand.FallingSand;
import com.kiczu.FallingSand.cells.CellType;
import com.kiczu.FallingSand.input.InputManager;

import java.util.Arrays;
import java.util.List;

public class ControlMenu {
    private InputManager inputManager;
    private Stage stage;
    private Table selectableCellsTable;

    private final int buttonWidth;
    private final int buttonHeight;


    public ControlMenu(InputManager inputManager, Viewport viewport) {
        this.inputManager = inputManager;
        buttonWidth = 125;
        buttonHeight = 25;
        buildStage(viewport);
    }

    private void buildStage(Viewport viewport) {
        stage = new Stage(viewport);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        selectableCellsTable = new Table() {
            @Override
            public void setPosition(float x, float y) {
                int dropDownListY = (int) y - buttonHeight;
                if (FallingSand.screenHeight - dropDownListY < selectableCellsTable.getRows() / 2f * buttonHeight) {
                    dropDownListY = (int) (FallingSand.screenHeight - (selectableCellsTable.getRows() / 2f * buttonHeight));
                } else if (dropDownListY < selectableCellsTable.getRows() / 2f * buttonHeight) {
                    dropDownListY = (int) (selectableCellsTable.getRows() / 2f * buttonHeight);
                }

                int dropDownListX = (int) x + buttonWidth / 2;
                if (FallingSand.screenWidth - dropDownListX < buttonWidth / 2f) {
                    dropDownListX = (int) (FallingSand.screenWidth - buttonWidth / 2f);
                } else if (dropDownListX < buttonWidth / 2f) {
                    dropDownListX = (int) (buttonWidth / 2f);
                }
                super.setPosition(dropDownListX, dropDownListY);
            }
        };


        List<CellType> cellTypes = Arrays.asList(CellType.values());

        Button button;
        for (CellType cellType : cellTypes) {
            button = buildButton(cellType.name(), skin, cellType);
            selectableCellsTable.add(button).width(buttonWidth).height(buttonHeight);
            selectableCellsTable.row();
        }

        stage.addActor(selectableCellsTable);

    }

    private Button buildButton(String text, Skin skin, CellType cellType) {
        Button button = new TextButton(text.replace('_', ' '), skin);
        button.setColor(Color.WHITE);
        button.addListener(new ClickListener() {
            private final CellType buttonCellType = cellType;
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setColor(Color.GREEN);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setColor(Color.WHITE);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setColor(Color.GREEN);
                inputManager.setSelectedCellType(buttonCellType);
                inputManager.closeMenu();
            }
        });
        return button;
    }


    public void draw() {
        stage.act();
        stage.draw();
    }

    public void setPosition(Vector2 position) {
        selectableCellsTable.setPosition(position.x, position.y);
    }

    public InputProcessor getInputProcessor() {
        return stage;
    }
}
