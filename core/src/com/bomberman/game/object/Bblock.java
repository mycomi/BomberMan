package com.bomberman.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bblock {

    Sprite sprite;
    Texture texture;
    float x,y,width,height;



    /**
     * crate sprite that has texture from hero.png
     * and set size by 64,64 and recive position by x,y
     */
    public Bblock(float x,float y) {
        //Constructor

        texture = new Texture(Gdx.files.internal("Bblock.png"));
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(64, 64);
    }

    /**
     * set this sprite to x,y position
     */
    public void setPosition(float x,float y) {
        //Set position of object
        sprite.setPosition(x, y);
    }

    /**
     * @return rectangle of sprite for check collision
     */
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


    /**
     * draw sprite
     */
    public void Draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
