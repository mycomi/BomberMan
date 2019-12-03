package com.bomberman.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Boom {
    Sprite sprite;
    Texture texture;
    float x,y,width,height;


    public Boom(float x,float y) {
        //Constructor

        texture = new Texture(Gdx.files.internal("boom.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(64, 64);
    }


    public void setPosition(float x,float y) {
        //Set position of object
        sprite.setPosition(x, y);
    }

    public Rectangle rectangle(){
        Rectangle r = new Rectangle(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        return  r;
    }

    public float getY() {
        y = sprite.getY();
        return y;
    }
    public float getX() {
        x = sprite.getX();
        return x;
    }

    public float getHeight() {
        float height = sprite.getHeight();
        return height;
    }

    public float getWidth() {
        float width = sprite.getWidth();
        return width;
    }


    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
