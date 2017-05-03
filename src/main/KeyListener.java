package main;

/**
 * Created by arons on 2017. 04. 29..
 */
public interface KeyListener {
    void spacePressed();

    void spaceReleased();

    void rightPressed();

    void leftPressed();

    void rightReleased();

    void leftReleased();

    void highScoreName(String name);


    void exit();
}
